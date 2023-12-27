package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.services.CommentService;
import ar.edu.itba.paw.services.ReviewService;
import ar.edu.itba.paw.webapp.auth.SecurityChecks;
import ar.edu.itba.paw.webapp.controller.queryParams.GetContentParams;
import ar.edu.itba.paw.webapp.dto.request.BasicRequestDto;
import ar.edu.itba.paw.webapp.dto.request.NewCommentDto;
import ar.edu.itba.paw.webapp.exceptions.ContentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.PageWrapper;
import ar.edu.itba.paw.models.Sorting;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.dto.request.NewContentDto;
import ar.edu.itba.paw.webapp.dto.response.ContentDto;
import ar.edu.itba.paw.webapp.dto.response.UserDto;
import ar.edu.itba.paw.webapp.utilities.ResponseBuildingUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.net.URLConnection;
import java.util.*;

@Path("content")
@Component
public class ContentController {
    @Autowired
    private ContentService cs;
    @Autowired
    private UserService us;
    @Autowired
    private ReviewService rs;
    @Autowired
    private CommentService ccs;

    @Context
    UriInfo uriInfo;
    private static final int CONTENT_AMOUNT = 20;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentController.class);

    // *  ----------------------------------- Movies and Serie Filters -------------------------------------------------
    // Endpoint para filtrar el las peliculas/series
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response filterContentByType(@QueryParam("type") final String contentType,
                                        @QueryParam("page") Integer pageNum,
                                        @QueryParam("durationFrom") @DefaultValue("ANY") final String durationFrom,
                                        @QueryParam("durationTo") @DefaultValue("ANY") final String durationTo,
                                        @QueryParam("sorting") final Sorting sorting,
                                        @QueryParam("query") @DefaultValue("ANY") final String query,
                                        @QueryParam("genre") final String genre,
                                        @QueryParam("watchListSavedBy") final Long watchListSavedBy,
                                        @QueryParam("viewedListSavedBy") final Long viewedListSavedBy,
                                        @QueryParam("paginated") @DefaultValue("true") final Boolean paginated) {

        LOGGER.info("GET /{}: Called",uriInfo.getPath());
        PageWrapper<Content> contentListFilter = GetContentParams.getContentByParams(contentType, pageNum, durationFrom, durationTo, sorting, query, genre, watchListSavedBy, viewedListSavedBy, paginated, cs, us, new SecurityChecks(us, cs, rs, ccs));

        List<Content> contentListFilterPaginated = contentListFilter.getPageContent();
        if(contentListFilterPaginated == null) {
            LOGGER.warn("GET /{}: Failed at requesting content", uriInfo.getPath());
            throw new ContentNotFoundException();
        }

        Collection<ContentDto> contentListFilterPaginatedDto = ContentDto.mapContentToContentDto(uriInfo, contentListFilterPaginated);
        LOGGER.info("GET /{}: Success filtering the content", uriInfo.getPath());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<Collection<ContentDto>>(contentListFilterPaginatedDto){});
        if(paginated) {
            ResponseBuildingUtils.setPaginationLinks(response,contentListFilter , uriInfo);
        }
        response.header("Total-Content", contentListFilter.getElemsAmount());

        return response.build();

    }

    // * ---------------------------------------------------------------------------------------------------------------

    // * ----------------------------------- Get a particual content----------------------------------------------------
    // Endpoint para getear un contenido a partir de su id
    @GET
    @Path("/{contentId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getSpecificContent(@PathParam("contentId") final long contentId) {
        LOGGER.info("GET /{}: Called", uriInfo.getPath());
        Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        ContentDto contentDto = new ContentDto(uriInfo, content);
        LOGGER.info("GET /{}: Return content {} with success", uriInfo.getPath(), content.getId());
        return Response.ok(contentDto).build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Get img from database -----------------------------------------------------
    // Endpoint para getear la imagen del contenido
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/{contentId}/contentImage")
    public Response getContentImage(@PathParam("contentId") final long contentId,
                                    @Context Request request) {

        LOGGER.info("GET /{}: Called", uriInfo.getPath());
        Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        if(content.getImage() == null) {
            return Response.noContent().build();
        }
        EntityTag eTag=new EntityTag(cs.getContentImageHash(content));
        final CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);
        Response.ResponseBuilder response = request.evaluatePreconditions(eTag);
        String contentType;
        try {
            contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(content.getImage()));
        } catch (Exception e) {
            contentType = "image/png";
            contentType = "image/png";
        }
        if (response == null) {
            final byte[] contentImage = content.getImage();
            response = Response.ok(contentImage).cacheControl(cacheControl).type(contentType).tag(eTag);
        }
        LOGGER.info("GET /{}: Content {} Image", uriInfo.getPath(), contentId);
        return response.build();
    }

    //Endpoint para editar la imagen del contenido
    @PUT
    @Path("/{contentId}/contentImage")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateContentImage(@FormDataParam("image") byte[] imageBytes,
                                       @PathParam("contentId") final Long contentId) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());

        if(imageBytes == null) {
            throw new BadRequestException("Must include edit data");
        }
        Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        cs.updateContent(content.getId(), content.getName(), content.getDescription(), content.getReleased(), content.getGenre(), content.getCreator(), content.getDurationNum(), content.getType(), imageBytes);
        LOGGER.info("PUT /{}: Content {} Image Updated", uriInfo.getPath(), contentId);
        return Response.noContent().contentLocation(ContentDto.getContentUriBuilder(uriInfo).path("content").path(String.valueOf(content.getId())).path("contentImage").build()).build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------------Content Editing--------------------------------------------------------
    //Endpoint para editar el contenido
    @PUT
    @Path("/{contentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateContentInfo(@PathParam("contentId") final Long contentId,
                                      @Valid NewContentDto contentDto,
                                      @Context final HttpServletRequest request) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());
        final Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        if(contentDto==null) {
            throw new BadRequestException("Must include edit data");
        }

        String auxGenre = "";
        for(String genre : contentDto.getGenre()) {
            auxGenre = auxGenre + " " + genre;
        }

        cs.updateContent(contentId, contentDto.getName(), contentDto.getDescription(), contentDto.getReleaseDate(), auxGenre, contentDto.getCreator(), contentDto.getDuration(), contentDto.getType(), content.getImage());

        LOGGER.info("PUT /{}: Content {} Info Updated", uriInfo.getPath(), contentId);
        return Response.ok().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Content Delete ------------------------------------------------------------
    @DELETE
    @Path("/{contentId}")
    public Response deleteContent(@PathParam("contentId") final Long contentId) {
        LOGGER.info("DELETE /{}: Called", uriInfo.getPath());
        Content oldContent = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        cs.deleteContent(contentId);
        LOGGER.info("DELETE /{}: Content {} deleted", uriInfo.getPath(), contentId);
        return Response.noContent().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Content Creation ----------------------------------------------------------
    // Endpoint para crear un contenido
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createContent(@Valid NewContentDto contentDto) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());
        if(contentDto == null) {
            throw new BadRequestException("Must include content data");
        }

        String auxGenre = "";
        for(String genre : contentDto.getGenre()) {
            auxGenre = auxGenre + " " + genre;
        }

        Content newContent = cs.contentCreate(contentDto.getName(),contentDto.getDescription(),contentDto.getReleaseDate(), auxGenre, contentDto.getCreator(),contentDto.getDuration(),contentDto.getType(),null);
        ContentDto newContentDto = new ContentDto(uriInfo, newContent);
        return Response.created(ContentDto.getContentUriBuilder(uriInfo).path("content").path(String.valueOf(newContent.getId())).build()).entity(newContentDto).build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Content Reviewers ---------------------------------------------------------
    // Endpoint para getear los reviewers de peliculas o series
    @GET
    @Path("/{contentId}/reviewers")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getContentReviewers(@PathParam("contentId") final long contentId) {
        LOGGER.info("GET /{}: Called", uriInfo.getPath());
        Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        List<User> contentReviewersList = cs.getContentReviewers(content.getId());
        Collection<UserDto> contentReviewersListPaginatedDto = null;
        if(contentReviewersList.isEmpty()) {
            LOGGER.info("GET /{}: No reviewers", uriInfo.getPath());
            return Response.noContent().build();
        } else {
            contentReviewersListPaginatedDto = UserDto.mapUserToUserDto(uriInfo, contentReviewersList);
        }

        LOGGER.info("GET /{}: Success getting the content reviewers list", uriInfo.getPath());
        return Response.ok(new GenericEntity<Collection<UserDto>>(contentReviewersListPaginatedDto){}).build();

    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Watchlist and Viewedlist --------------------------------------------------
    @POST
    @Path("/{contentId}/watchList")
    @PreAuthorize("@securityChecks.checkUser(#basicRequestDto.userId)")
    public Response addUserWatchList(@Valid BasicRequestDto basicRequestDto,
                                     @PathParam("contentId") final long contentId) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());
        final Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        try {
            us.addToWatchList(user, content);
            LOGGER.info("POST /{}: Content {} added successfully", uriInfo.getPath(), contentId);
        } catch (DuplicateKeyException ignore){
            LOGGER.warn("POST /{}: DuplicateKeyException, content {} already in watchlist", uriInfo.getPath(), contentId);
        }
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{contentId}/watchList")
    @PreAuthorize("@securityChecks.checkUser(#userId)")
    public Response deleteUserWatchList(@QueryParam("userId") final Long userId,
                                        @PathParam("contentId") final long contentId) {
        LOGGER.info("DELETE /{}: Called", uriInfo.getPath());

        final Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        try {
            us.deleteFromWatchList(user, content);
            LOGGER.info("DELETE /{}: Content {} deleted successfully", uriInfo.getPath(), contentId);
        } catch (DuplicateKeyException ignore){
            LOGGER.warn("DELETE /{}: DuplicateKeyException, content {} already deleted from watchlist", uriInfo.getPath(), contentId);
        }
        return Response.noContent().build();
    }

    @POST
    @Path("/{contentId}/viewedList")
    @PreAuthorize("@securityChecks.checkUser(#basicRequestDto.userId)")
    public Response addUserViewedList(@Valid BasicRequestDto basicRequestDto,
                                      @PathParam("contentId") final long contentId) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());

        final Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        try {
            us.addToViewedList(user, content);
            LOGGER.info("POST /{}: Content {} added successfully", uriInfo.getPath(), contentId);
        } catch (DuplicateKeyException ignore){
            LOGGER.warn("POST /{}: DuplicateKeyException, content {} already in viewedlist", uriInfo.getPath(), contentId);
        }
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{contentId}/viewedList")
    @PreAuthorize("@securityChecks.checkUser(#userId)")
    public Response deleteUserViewedList(@QueryParam("userId") final Long userId,
                                         @PathParam("contentId") final long contentId) {
        LOGGER.info("DELETE /{}: Called", uriInfo.getPath());

        final Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        try {
            us.deleteFromViewedList(user, content);
            LOGGER.info("DELETE /{}: Content {} deleted successfully", uriInfo.getPath(), contentId);
        } catch (DuplicateKeyException ignore){
            LOGGER.warn("DELETE /{}: DuplicateKeyException, content {} already deleted from viewedlist", uriInfo.getPath(), contentId);
        }
        return Response.noContent().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------



}
