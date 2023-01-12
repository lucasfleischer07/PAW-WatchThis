package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.PaginationService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.dto.response.ContentDto;
import ar.edu.itba.paw.webapp.dto.response.UserDto;
import ar.edu.itba.paw.webapp.exceptions.ForbiddenException;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
//    TODO: FALTA LO DE PAGINAR
    @GET
    @Path("/watchList/{userId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserWatchList(@PathParam("userId") final long userId,
                                     @QueryParam("page")@DefaultValue("1")final int page) {
        final User user = us.findById(userId).orElseThrow(PageNotFoundException::new);
        final User user2 = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(PageNotFoundException::new);

        if(user.getId() != user2.getId()) {
            LOGGER.warn("GET /{}: Login user {} is different from watchlist owner {}", uriInfo.getPath(), user2.getId(), userId);
            throw new ForbiddenException();
        }
        List<Content> watchList = us.getWatchList(user);
        LOGGER.info("GET /{}: Watchlist from user {}", uriInfo.getPath(),userId);
        return Response.ok(ContentDto.mapContentToContentDto(uriInfo, watchList, user)).build();
    }

//    @RequestMapping(value = {"/profile/watchList","/profile/watchList/page/{pageNum:[0-9]+}"}, method = {RequestMethod.GET})
//    public ModelAndView watchList(Principal userDetails,
//                                  @PathVariable("pageNum")final Optional<Integer> pageNum,
//                                  HttpServletRequest request) {
//        String userEmail = userDetails.getName();
//        final String locale = LocaleContextHolder.getLocale().getDisplayLanguage();
//        User user = us.findByEmail(userEmail).orElseThrow(ServerErrorException::new);
//        List<Content> watchListContent = us.getWatchList(user);
//        List<Long> userWatchListContentId = us.getUserWatchListContent(user);
//        final ModelAndView mav = new ModelAndView("watchListPage");
//        Optional<String> quote = cs.getContentQuote(locale);
//        mav.addObject("quote", quote.get());
//        mav.addObject("user", user);
//        mav.addObject("type","all");
//        mav.addObject("userName", user.getUserName());
//        mav.addObject("userId", user.getId());
//        listPaginationSetup(mav,"watchListContent",watchListContent,pageNum.orElse(1));
//        mav.addObject("watchListSize", watchListContent.size());
//        mav.addObject("userWatchListContentId", userWatchListContentId);
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//            mav.addObject("admin", true);
//        } else {
//            mav.addObject("admin", false);
//        }
//        request.getSession().setAttribute("referer","/profile/watchList"+(pageNum.isPresent()?"/page/"+pageNum.get():""));
//        return mav;
//    }


    // Endpoint para agregar a la watchlist del ususario
    @PUT
    @Path("/watchList/add/{contentId}")
    public Response addUserWatchList(@PathParam("contentId") final long contentId) {
        final Content content = cs.findById(contentId).orElseThrow(PageNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);

        try {
            us.addToWatchList(user, content);
            LOGGER.info("POST /{}: Content {} added successfully", uriInfo.getPath(), contentId);
        } catch (DuplicateKeyException ignore){
            LOGGER.warn("POST /{}: DuplicateKeyException, content {} already in watchlist", uriInfo.getPath(), contentId);
        }
        return Response.noContent().build();
    }

//    @RequestMapping(value = "/watchList/add/{contentId:[0-9]+}", method = {RequestMethod.POST})
//    public ModelAndView watchListAddPost(Principal userDetails,
//                                         @PathVariable("contentId") final Optional<Long> contentId,
//                                         HttpServletRequest request) {
//        if(!contentId.isPresent()) {
//            LOGGER.warn("Wrong contentID:",new PageNotFoundException());
//            throw new PageNotFoundException();
//        }
//
//        if(userDetails != null) {
//            String userEmail = userDetails.getName();
//            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
//            try {
//                Content content = cs.findById(contentId.get()).orElseThrow(PageNotFoundException::new);
//                us.addToWatchList(user, content);
//            } catch (DuplicateKeyException ignore){}
//        } else {
//            throw new ForbiddenException();
//        }
//
//        String referer = request.getSession().getAttribute("referer").toString();
//        return new ModelAndView("redirect:" + (referer==null ? "/" : referer));
//    }

    @DELETE
    @Path("/watchList/delete/{contentId}")
    public Response deleteUserWatchList(@PathParam("contentId") final long contentId) {
        final Content content = cs.findById(contentId).orElseThrow(PageNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);

        try {
            us.deleteFromWatchList(user, content);
            LOGGER.info("DELETE /{}: Content {} deleted successfully", uriInfo.getPath(), contentId);
        } catch (DuplicateKeyException ignore){
            LOGGER.warn("POST /{}: DuplicateKeyException, content {} already deleted from watchlist", uriInfo.getPath(), contentId);
        }
        return Response.noContent().build();
    }

//    @RequestMapping(value = "/watchList/delete/{contentId:[0-9]+}", method = {RequestMethod.POST})
//    public ModelAndView watchListDeletePost(Principal userDetails,
//                                            @PathVariable("contentId") final Optional<Long> contentId,
//                                            HttpServletRequest request) {
//        if(!contentId.isPresent()) {
//            LOGGER.warn("No content Specified:",new PageNotFoundException());
//            throw new PageNotFoundException();
//        }
//
//        if(userDetails != null) {
//            String userEmail = userDetails.getName();
//            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
//            Content content = cs.findById(contentId.get()).orElseThrow(PageNotFoundException::new);
//            us.deleteFromWatchList(user, content);
//        } else {
//            throw new ForbiddenException();
//        }
//
//        String referer = request.getSession().getAttribute("referer").toString();
//        return new ModelAndView("redirect:" + (referer==null ? "/" : referer));
//
//    }

//    @RequestMapping(value = "/go/to/login", method = {RequestMethod.POST})
//    public ModelAndView goToLogin()  {
//        return new ModelAndView("redirect:/login/sign-in");
//    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ------------------------------------------------User Viewed List-----------------------------------------------
    // Endpoint para getear la viewedlist del ususario
//    TODO: FALTA LO DE PAGINAR
    @GET
    @Path("/viewedList/{userId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserViewedList(@PathParam("userId") final long userId,
                                      @QueryParam("page")@DefaultValue("1")final int page) {
        final User user = us.findById(userId).orElseThrow(PageNotFoundException::new);
        final User user2 = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(PageNotFoundException::new);

        if(user.getId() != user2.getId()) {
            LOGGER.warn("GET /{}: Logged user {} is different from viewedlist owner {}", uriInfo.getPath(), user2.getId(), userId);
            throw new ForbiddenException();
        }
        List<Content> viewedList = us.getUserViewedList(user);
        LOGGER.info("GET /{}: Viewedlist from user {}", uriInfo.getPath(),userId);
        return Response.ok(ContentDto.mapContentToContentDto(uriInfo, viewedList, user)).build();
    }

//    @RequestMapping(value = {"/profile/viewedList","/profile/viewedList/page/{pageNum:[0-9]+}"}, method = {RequestMethod.GET})
//    public ModelAndView viewedList(Principal userDetails,
//                                   @PathVariable("pageNum")final Optional<Integer> pageNum,
//                                   HttpServletRequest request) {
//        String userEmail = userDetails.getName();
//        final String locale = LocaleContextHolder.getLocale().getDisplayLanguage();
//        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
//        List<Content> viewedListContent = us.getUserViewedList(user);
//        List<Long> userWatchListContentId = us.getUserWatchListContent(user);
//        final ModelAndView mav = new ModelAndView("viewedListPage");
//        Optional<String> quote = cs.getContentQuote(locale);
//        mav.addObject("quote", quote.get());
//        mav.addObject("user", user);
//        mav.addObject("userName", user.getUserName());
//        mav.addObject("userId", user.getId());
//        mav.addObject("type","all");
//        listPaginationSetup(mav,"viewedListContent",viewedListContent,pageNum.orElse(1));
//        mav.addObject("viewedListContentSize", viewedListContent.size());
//        mav.addObject("userWatchListContentId", userWatchListContentId);
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
//            mav.addObject("admin", true);
//        } else {
//            mav.addObject("admin", false);
//        }
//        request.getSession().setAttribute("referer","/profile/viewedList"+(pageNum.isPresent()?"/page/"+pageNum.get():""));
//        return mav;
//    }


    // Endpoint para agregar a la viewedlist del ususario
    @PUT
    @Path("/viewedList/add/{contentId}")
    public Response addUserViewedList(@PathParam("contentId") final long contentId) {
        final Content content = cs.findById(contentId).orElseThrow(PageNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);

        try {
            us.addToViewedList(user, content);
            LOGGER.info("POST /{}: Content {} added successfully", uriInfo.getPath(), contentId);
        } catch (DuplicateKeyException ignore){
            LOGGER.warn("POST /{}: DuplicateKeyException, content {} already in viewedlist", uriInfo.getPath(), contentId);
        }
        return Response.noContent().build();
    }

//    @RequestMapping(value = "/viewedList/add/{contentId:[0-9]+}", method = {RequestMethod.POST})
//    public ModelAndView viewedListAddPost(Principal userDetails,
//                                          @PathVariable("contentId") final Optional<Long> contentId,
//                                          HttpServletRequest request) {
//        if(!contentId.isPresent()) {
//            throw new PageNotFoundException();
//        }
//        String userEmail = userDetails.getName();
//        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
//        try{
//            Content content = cs.findById(contentId.get()).orElseThrow(PageNotFoundException::new);
//            us.addToViewedList(user, content);
//        } catch (DuplicateKeyException ignore) {}
//        String referer = request.getSession().getAttribute("referer").toString();
//        return new ModelAndView("redirect:" + (referer==null?"/":referer));
//    }


    @DELETE
    @Path("/viewedList/delete/{contentId}")
    public Response deleteUserViewedList(@PathParam("contentId") final long contentId) {
        final Content content = cs.findById(contentId).orElseThrow(PageNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);

        try {
            us.deleteFromViewedList(user, content);
            LOGGER.info("DELETE /{}: Content {} deleted successfully", uriInfo.getPath(), contentId);
        } catch (DuplicateKeyException ignore){
            LOGGER.warn("POST /{}: DuplicateKeyException, content {} already deleted from viewedlist", uriInfo.getPath(), contentId);
        }
        return Response.noContent().build();
    }


//    @RequestMapping(value = "/viewedList/delete/{contentId:[0-9]+}", method = {RequestMethod.POST})
//    public ModelAndView viewedListDeletePost(Principal userDetails,
//                                             @PathVariable("contentId") final Optional<Long> contentId,
//                                             HttpServletRequest request) {
//        if(!contentId.isPresent()) {
//            throw new PageNotFoundException();
//        }
//        String userEmail = userDetails.getName();
//        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
//        Content content = cs.findById(contentId.get()).orElseThrow(PageNotFoundException::new);
//        us.deleteFromViewedList(user, content);
//        String referer = request.getSession().getAttribute("referer").toString();
//        return new ModelAndView("redirect:" + (referer==null?"/":referer));
//    }
    // * ---------------------------------------------------------------------------------------------------------------
}