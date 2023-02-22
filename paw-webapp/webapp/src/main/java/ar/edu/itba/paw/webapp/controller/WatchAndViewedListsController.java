package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.ContentNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.PaginationService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.dto.response.ContentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.List;

@Path("lists")
@Component
public class WatchAndViewedListsController {
    @Autowired
    private UserService us;
    @Autowired
    private ContentService cs;
    @Autowired
    private PaginationService ps;
    @Context
    private UriInfo uriInfo;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentController.class);
    private static final int CONTENT_AMOUNT = 18;

// TODO: VER ESTO

//    private void listPaginationSetup(ModelAndView mav,String name,List<Content> contentList,int page) throws PageNotFoundException{
//        if(ps.checkPagination(contentList.size(), page,CONTENT_AMOUNT)) {
//            LOGGER.warn("Wrong login path:", new PageNotFoundException());
//            throw new PageNotFoundException();
//        }
//
//        List<Content> contentListFilterPaginated = ps.pagePagination(contentList, page,CONTENT_AMOUNT);
//        mav.addObject(name, contentListFilterPaginated);
//
//        int amountOfPages = ps.amountOfContentPages(contentList.size(),CONTENT_AMOUNT);
//        mav.addObject("amountPages", amountOfPages);
//        mav.addObject("pageSelected",page);
//
//    }

    // * ------------------------------------------------User Watch List------------------------------------------------
    // Endpoint para getear la watchlist del ususario
    @GET
    @Path("/watchList/{userId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserWatchList(@PathParam("userId") final long userId,
                                     @QueryParam("pageNumber") @DefaultValue("1") int page,
                                     @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOGGER.info("GET /{}: Called", uriInfo.getPath());

        final User user = us.findById(userId).orElseThrow(UserNotFoundException::new);
        final User user2 = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        if(user.getId() != user2.getId()) {
            LOGGER.warn("GET /{}: Login user {} is different from watchlist owner {}", uriInfo.getPath(), user2.getId(), userId);
            throw new ForbiddenException();
        }
        List<Content> watchList = us.getWatchList(user);
        Collection<ContentDto> watchListDto = ContentDto.mapContentToContentDto(uriInfo,watchList);
        LOGGER.info("GET /{}: Watchlist from user {}", uriInfo.getPath(),userId);

//        TODO: El Return aca deberia ya estar paginado (Por el momento no lo esta, habria que cambairlo)
        return Response.ok(new GenericEntity<Collection<ContentDto>>(watchListDto){}).build();

    }


    // Endpoint para agregar a la watchlist del ususario
    @PUT
    @Path("/watchList/add/{contentId}")
    public Response addUserWatchList(@PathParam("contentId") final long contentId) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());
        final Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);

        try {
            us.addToWatchList(user, content);
            LOGGER.info("POST /{}: Content {} added successfully", uriInfo.getPath(), contentId);
        } catch (DuplicateKeyException ignore){
            LOGGER.warn("POST /{}: DuplicateKeyException, content {} already in watchlist", uriInfo.getPath(), contentId);
        }
        return Response.noContent().build();
    }


    @DELETE
    @Path("/watchList/delete/{contentId}")
    public Response deleteUserWatchList(@PathParam("contentId") final long contentId) {
        LOGGER.info("DELETE /{}: Called", uriInfo.getPath());

        final Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);

        try {
            us.deleteFromWatchList(user, content);
            LOGGER.info("DELETE /{}: Content {} deleted successfully", uriInfo.getPath(), contentId);
        } catch (DuplicateKeyException ignore){
            LOGGER.warn("POST /{}: DuplicateKeyException, content {} already deleted from watchlist", uriInfo.getPath(), contentId);
        }
        return Response.noContent().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ------------------------------------------------User Viewed List-----------------------------------------------
    // Endpoint para getear la viewedlist del ususario
    @GET
    @Path("/viewedList/{userId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserViewedList(@PathParam("userId") final long userId,
                                      @QueryParam("pageNumber") @DefaultValue("1") int page,
                                      @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOGGER.info("GET /{}: Called", uriInfo.getPath());

        final User user = us.findById(userId).orElseThrow(UserNotFoundException::new);
        final User user2 = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        if(user.getId() != user2.getId()) {
            LOGGER.warn("GET /{}: Logged user {} is different from viewedlist owner {}", uriInfo.getPath(), user2.getId(), userId);
            throw new ForbiddenException();
        }
        List<Content> viewedList = us.getUserViewedList(user);
        LOGGER.info("GET /{}: Viewedlist from user {}", uriInfo.getPath(),userId);

//        TODO: El Return aca deberia ya estar paginado (Por el momento no lo esta, habria que cambairlo)
        return Response.ok(ContentDto.mapContentToContentDto(uriInfo, viewedList)).build();
    }


    // Endpoint para agregar a la viewedlist del ususario
    @PUT
    @Path("/viewedList/add/{contentId}")
    public Response addUserViewedList(@PathParam("contentId") final long contentId) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());

        final Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);

        try {
            us.addToViewedList(user, content);
            LOGGER.info("PUT /{}: Content {} added successfully", uriInfo.getPath(), contentId);
        } catch (DuplicateKeyException ignore){
            LOGGER.warn("PUT /{}: DuplicateKeyException, content {} already in viewedlist", uriInfo.getPath(), contentId);
        }
        return Response.noContent().build();
    }


    @DELETE
    @Path("/viewedList/delete/{contentId}")
    public Response deleteUserViewedList(@PathParam("contentId") final long contentId) {
        LOGGER.info("DELETE /{}: Called", uriInfo.getPath());

        final Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);

        try {
            us.deleteFromViewedList(user, content);
            LOGGER.info("DELETE /{}: Content {} deleted successfully", uriInfo.getPath(), contentId);
        } catch (DuplicateKeyException ignore){
            LOGGER.warn("POST /{}: DuplicateKeyException, content {} already deleted from viewedlist", uriInfo.getPath(), contentId);
        }
        return Response.noContent().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------
}