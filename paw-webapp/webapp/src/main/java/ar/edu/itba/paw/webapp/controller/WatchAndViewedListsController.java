package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.PaginationService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.ForbiddenException;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class WatchAndViewedListsController {

    private final UserService us;
    private final ContentService cs;
    private final PaginationService ps;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    public WatchAndViewedListsController(final UserService us, final ContentService cs, PaginationService ps) {
        this.us = us;
        this.cs = cs;
        this.ps = ps;
    }

    private void listPaginationSetup(ModelAndView mav,String name,List<Content> contentList,int page) throws PageNotFoundException{
        if(ps.checkPagination(contentList.size(), page)) {
            LOGGER.warn("Wrong login path:", new PageNotFoundException());
            throw new PageNotFoundException();
        }

        List<Content> contentListFilterPaginated = ps.contentPagination(contentList, page);
        mav.addObject(name, contentListFilterPaginated);

        int amountOfPages = ps.amountOfPages(contentList.size());
        mav.addObject("amountPages", amountOfPages);
        mav.addObject("pageSelected",page);

    }

    // * ------------------------------------------------User Watch List------------------------------------------------
    @RequestMapping(value = {"/profile/watchList","/profile/watchList/page/{pageNum:[0-9]+}"}, method = {RequestMethod.GET})
    public ModelAndView watchList(Principal userDetails,
                                  @PathVariable("pageNum")final Optional<Integer> pageNum,
                                  HttpServletRequest request) {
        String userEmail = userDetails.getName();
        final String locale = LocaleContextHolder.getLocale().getDisplayLanguage();
        User user = us.findByEmail(userEmail).orElseThrow(ServerErrorException::new);
        List<Content> watchListContent = us.getWatchList(user);
        List<Long> userWatchListContentId = us.getUserWatchListContent(user);
        final ModelAndView mav = new ModelAndView("watchListPage");
        Optional<String> quote = cs.getContentQuote(locale);
        mav.addObject("quote", quote.get());
        mav.addObject("user", user);
        mav.addObject("type","all");
        mav.addObject("userName", user.getUserName());
        mav.addObject("userId", user.getId());
        listPaginationSetup(mav,"watchListContent",watchListContent,pageNum.orElse(1));
        mav.addObject("watchListSize", watchListContent.size());
        mav.addObject("userWatchListContentId", userWatchListContentId);

        if (user.getRole().equals("admin")) {
            mav.addObject("admin", true);
        } else {
            mav.addObject("admin", false);
        }
        request.getSession().setAttribute("referer","/profile/watchList");
        return mav;
    }

    @RequestMapping(value = "/watchList/add/{contentId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView watchListAddPost(Principal userDetails,
                                         @PathVariable("contentId") final Optional<Long> contentId,
                                         HttpServletRequest request) {
        if(!contentId.isPresent()) {
            LOGGER.warn("Wrong contentID:",new PageNotFoundException());
            throw new PageNotFoundException();
        }

        if(userDetails != null) {
            String userEmail = userDetails.getName();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            try {
                Content content = cs.findById(contentId.get()).orElseThrow(PageNotFoundException::new);
                us.addToWatchList(user, content);
            } catch (DuplicateKeyException ignore){}
        } else {
            throw new ForbiddenException();
        }

        String referer = request.getSession().getAttribute("referer").toString();
        return new ModelAndView("redirect:" + (referer==null ? "/" : referer));
    }

    @RequestMapping(value = "/watchList/delete/{contentId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView watchListDeletePost(Principal userDetails,
                                            @PathVariable("contentId") final Optional<Long> contentId,
                                            HttpServletRequest request) {
        if(!contentId.isPresent()) {
            LOGGER.warn("No content Specified:",new PageNotFoundException());
            throw new PageNotFoundException();
        }

        if(userDetails != null) {
            String userEmail = userDetails.getName();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            Content content = cs.findById(contentId.get()).orElseThrow(PageNotFoundException::new);
            us.deleteFromWatchList(user, content);
        } else {
            throw new ForbiddenException();
        }

        String referer = request.getSession().getAttribute("referer").toString();
        return new ModelAndView("redirect:" + (referer==null ? "/" : referer));

    }

    @RequestMapping(value = "/go/to/login", method = {RequestMethod.POST})
    public ModelAndView goToLogin()  {
        return new ModelAndView("redirect:/login/sign-in");
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ------------------------------------------------User Viewed List-----------------------------------------------
    @RequestMapping(value = {"/profile/viewedList","/profile/viewedList/page/{pageNum:[0-9]+}"}, method = {RequestMethod.GET})
    public ModelAndView viewedList(Principal userDetails,
                                   @PathVariable("pageNum")final Optional<Integer> pageNum,
                                   HttpServletRequest request) {
        String userEmail = userDetails.getName();
        final String locale = LocaleContextHolder.getLocale().getDisplayLanguage();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        List<Content> viewedListContent = us.getUserViewedList(user);
        List<Long> userWatchListContentId = us.getUserWatchListContent(user);
        final ModelAndView mav = new ModelAndView("viewedListPage");
        Optional<String> quote = cs.getContentQuote(locale);
        mav.addObject("quote", quote.get());
        mav.addObject("user", user);
        mav.addObject("userName", user.getUserName());
        mav.addObject("userId", user.getId());
        mav.addObject("type","all");
        listPaginationSetup(mav,"viewedListContent",viewedListContent,pageNum.orElse(1));
        mav.addObject("viewedListContentSize", viewedListContent.size());
        mav.addObject("userWatchListContentId", userWatchListContentId);

        if (user.getRole().equals("admin")) {
            mav.addObject("admin", true);
        } else {
            mav.addObject("admin", false);
        }
        request.getSession().setAttribute("referer","/profile/viewedList");
        return mav;
    }

    @RequestMapping(value = "/viewedList/add/{contentId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView viewedListAddPost(Principal userDetails,
                                          @PathVariable("contentId") final Optional<Long> contentId,
                                          HttpServletRequest request) {
        if(!contentId.isPresent()) {
            throw new PageNotFoundException();
        }
        String userEmail = userDetails.getName();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        try{
            Content content = cs.findById(contentId.get()).orElseThrow(PageNotFoundException::new);
            us.addToViewedList(user, content);
        } catch (DuplicateKeyException ignore) {}
        String referer = request.getSession().getAttribute("referer").toString();
        return new ModelAndView("redirect:" + (referer==null?"/":referer));
    }

    @RequestMapping(value = "/viewedList/delete/{contentId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView viewedListDeletePost(Principal userDetails,
                                             @PathVariable("contentId") final Optional<Long> contentId,
                                             HttpServletRequest request) {
        if(!contentId.isPresent()) {
            throw new PageNotFoundException();
        }
        String userEmail = userDetails.getName();
        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
        Content content = cs.findById(contentId.get()).orElseThrow(PageNotFoundException::new);
        us.deleteFromViewedList(user, content);
        String referer = request.getSession().getAttribute("referer").toString();
        return new ModelAndView("redirect:" + (referer==null?"/":referer));
    }
    // * ---------------------------------------------------------------------------------------------------------------
}
