package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.exceptions.ContentNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.PageWrapper;
import ar.edu.itba.paw.models.Sorting;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.dto.request.GenreFilterDto;
import ar.edu.itba.paw.webapp.dto.request.NewContentDto;
import ar.edu.itba.paw.webapp.dto.response.AnonymousLandingPageDto;
import ar.edu.itba.paw.webapp.dto.response.ContentDto;
import ar.edu.itba.paw.webapp.dto.response.UserDto;
import ar.edu.itba.paw.webapp.dto.response.UserLandingPageDto;
import ar.edu.itba.paw.webapp.utilities.ResponseBuildingUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
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
    @Context
    UriInfo uriInfo;
    private static final int CONTENT_AMOUNT = 18;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentController.class);

    // * ----------------------------------- Home Page -----------------------------------------------------------------
    // Endpoint para getear el contenido de la home page
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getContentByType() {
        LOGGER.info("GET /{}: Called", uriInfo.getPath());
        Optional<User> user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<List<Content>> landingPageContentList;
        if(!user.isPresent()) {
            landingPageContentList = cs.getLandingPageContent(null);
            LOGGER.info("GET /{}: Returning Landing Page Content for user NULL", uriInfo.getPath());
            return Response.ok(new GenericEntity<AnonymousLandingPageDto>(new AnonymousLandingPageDto(uriInfo, landingPageContentList)){}).build();
        } else {
            landingPageContentList = cs.getLandingPageContent(user.get());
            LOGGER.info("GET /{}: Returning Landing Page Content for userId {}", uriInfo.getPath(), user.get().getId());
            return Response.ok(new GenericEntity<UserLandingPageDto>(new UserLandingPageDto(uriInfo, landingPageContentList)){}).build();
        }
    }

    // * ---------------------------------------------------------------------------------------------------------------

    // * ----------------------------------- Get a particual content----------------------------------------------------
    // Endpoint para getear un contenido a partir de su id
    @GET
    @Path("/specificContent/{contentId}")
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

        EntityTag eTag = new EntityTag(String.valueOf(content.getId()));
        final CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);
        Response.ResponseBuilder response = request.evaluatePreconditions(eTag);
        String contentType;
        try {
            contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(content.getImage()));
        } catch (Exception e) {
            contentType = "image/png";
        }
        if (response == null) {
            final byte[] contentImage = content.getImage();
            response = Response.ok(contentImage).tag(eTag);
        }

        LOGGER.info("GET /{}: Content {} Image", uriInfo.getPath(), contentId);
        return response.cacheControl(cacheControl).type(contentType).build();
    }

    //Endpoint para editar la imagen del contenido
    @PUT
    @Path("/{contentId}/contentImage")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateContentImage(@Size(max = 1024 * 1024 * 2) @FormDataParam("image") byte[] imageBytes,
                                       @PathParam("contentId") final long contentId) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());
        if(imageBytes==null)
            throw new BadRequestException("Must include edit data");
        Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        cs.updateContent(content.getId(), content.getName(), content.getDescription(), content.getReleased(), content.getGenre(), content.getCreator(), content.getDurationNum(), content.getType(), imageBytes);
        LOGGER.info("PUT /{}: Content {} Image Updated", uriInfo.getPath(), contentId);
        return Response.noContent().contentLocation(ContentDto.getContentUriBuilder(uriInfo).path("content").path(String.valueOf(content.getId())).path("contentImage").build()).build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------------Content Editing--------------------------------------------------------
    //Endpoint para editar el contenido
    @PUT
    @Path("/editInfo/{contentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateContentInfo(@PathParam("contentId") final long contentId,
                                      @Valid NewContentDto contentDto,
                                      @Context final HttpServletRequest request) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        final Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        if(contentDto==null)
            throw new BadRequestException("Must include edit data");
        String auxGenre = "";
        for(String genre : contentDto.getGenre()) {
            auxGenre = auxGenre + " " + genre;
        }

//        TODO: NOSE BIEN COMO SERIA ESTO, HAY QUE VERLO MEJOR (POR EL MOMENTO, ESTA PUESTO LA IMAGEN ORIGINAL, NOSE BIEN COMO HACER PARA CAMBIAR SOLO LA IMAGEN, HAY QUE HACER UN BOTON APARTE Y ESO)
        cs.updateContent(contentId, contentDto.getName(), contentDto.getDescription(), contentDto.getReleaseDate(), auxGenre, contentDto.getCreator(), contentDto.getDuration(), contentDto.getType(), content.getImage());
        LOGGER.info("PUT /{}: Content {} Info Updated", uriInfo.getPath(), contentId);
        return Response.ok().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Content Delete ------------------------------------------------------------
    @DELETE
    @Path("/{contentId}/deleteContent")
    public Response deleteContent(@PathParam("contentId") final long contentId) {
        LOGGER.info("DELETE /{}: Called", uriInfo.getPath());
        Content oldContent = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        cs.deleteContent(contentId);
        LOGGER.info("DELETE /{}: Content {} deleted", uriInfo.getPath(), contentId);
        return Response.noContent().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Content Creation ----------------------------------------------------------
    // Endpoint para crear un contenido
//    TODO: VER BIEN
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createContent(@Valid NewContentDto contentDto) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());
        if(contentDto==null)
            throw new BadRequestException("Must include content data");
        User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        String auxGenre = "";
        for(String genre : contentDto.getGenre()) {
            auxGenre = auxGenre + " " + genre;
        }

        Content newContent = cs.contentCreate(contentDto.getName(),contentDto.getDescription(),contentDto.getReleaseDate(), auxGenre, contentDto.getCreator(),contentDto.getDuration(),contentDto.getType(),null);
        return Response.created(ContentDto.getContentUriBuilder(uriInfo).path("content").path(String.valueOf(newContent.getId())).build()).build();
    }

    // * ---------------------------------------------------------------------------------------------------------------

    // * ----------------------------------- Movie and Series division -------------------------------------------------
    // Endpoint para getear las peliculas o series
    @GET
    @Path("/{contentType}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getContentByType(@PathParam("contentType") final String contentType,
                                     @QueryParam("pageNumber") @DefaultValue("1") Integer pageNum,
                                     @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOGGER.info("GET /{}: Called", uriInfo.getPath());
        if(!contentType.equals("movie") && !contentType.equals("serie")){
            throw new PageNotFoundException();
        }
        int page= pageNum;
        PageWrapper<Content> contentList = cs.getAllContent(contentType, null, page, CONTENT_AMOUNT);
        Collection<ContentDto> contentListPaginatedDto = null;
        if(contentList.getPageContent() == null) {
            LOGGER.warn("GET /{}: Failed at requesting content", uriInfo.getPath());
            throw new ContentNotFoundException();
        } else {
            contentListPaginatedDto = ContentDto.mapContentToContentDto(uriInfo, contentList.getPageContent() );
        }

        LOGGER.info("GET /{}: Success getting the content", uriInfo.getPath());

        Response.ResponseBuilder response = Response.ok(new GenericEntity<Collection<ContentDto>>(contentListPaginatedDto){});
        ResponseBuildingUtils.setPaginationLinks(response,contentList , uriInfo);
        return response.build();

    }

    // * ---------------------------------------------------------------------------------------------------------------


    // *  ----------------------------------- Movies and Serie Filters -------------------------------------------------
    // Endpoint para filtrar el las peliculas/series
    @GET
    @Path("/{contentType}/filters")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response filterContentByType(@PathParam("contentType") final String contentType,
                                        @QueryParam("pageNumber") @DefaultValue("1") Integer pageNum,
                                        @QueryParam("pageSize") @DefaultValue("10") int pageSize,
                                        @QueryParam("durationFrom") @DefaultValue("ANY") final String durationFrom,
                                        @QueryParam("durationTo") @DefaultValue("ANY") final String durationTo,
                                        @QueryParam("sorting") final Sorting sorting,
                                        @QueryParam("query") @DefaultValue("ANY") final String query,
                                        @QueryParam("genre") final List<String> genre,
                                        @Valid GenreFilterDto genreFilterDto) {

        LOGGER.info("GET /{}: Called", uriInfo.getPath());
        if(!contentType.equals("movie") && !contentType.equals("serie") && !contentType.equals("all")){
            throw new PageNotFoundException();
        }
        int page= pageNum;
        String auxType;

//        TODO: VER ESTO DEL GENRE, NO SE PASABA COMO QUERYPARAM Y AHORA NI IDEA COMO SERIA. EL ORIGINAL ESTA EN EL ARCHIVO
//        TODO: MovieAndSerieController linea 76 (esta comentado)
        List<String> genreList = genre;

        PageWrapper<Content> contentListFilter = cs.getMasterContent(contentType, genre, durationFrom, durationTo, sorting, query,page,CONTENT_AMOUNT);
        List<Content> contentListFilterPaginated = contentListFilter.getPageContent();
        long amountOfPages;
        if(contentListFilterPaginated == null) {
            LOGGER.warn("GET /{}: Failed at requesting content", uriInfo.getPath());
            throw new ContentNotFoundException();
        }

        amountOfPages = contentListFilter.getPageAmount();
        Collection<ContentDto> contentListFilterPaginatedDto = ContentDto.mapContentToContentDto(uriInfo, contentListFilterPaginated);
        LOGGER.info("GET /{}: Success filtering the content", uriInfo.getPath());
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<Collection<ContentDto>>(contentListFilterPaginatedDto){});
        ResponseBuildingUtils.setPaginationLinks(response,contentListFilter , uriInfo);
        return response.build();

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


}
