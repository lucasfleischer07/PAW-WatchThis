package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.CommentForm;
import ar.edu.itba.paw.webapp.form.GenreFilterForm;
import ar.edu.itba.paw.webapp.form.ReportCommentForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
public class MovieAndSerieController {
    private final UserService us;
    private final ContentService cs;
    private final PaginationService ps;
    private final ReviewService rs;
    private CommentService ccs;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    public MovieAndSerieController(final UserService us, final ContentService cs, final ReviewService rs, PaginationService ps, CommentService ccs) {
        this.us = us;
        this.cs = cs;
        this.rs = rs;
        this.ps = ps;
        this.ccs = ccs;
    }

    private void HeaderSetUp(ModelAndView mav,Principal userDetails) {
        if(userDetails != null) {
            String userEmail = userDetails.getName();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            List<Long> userWatchListContentId = us.getUserWatchListContent(user);
            mav.addObject("userName", user.getUserName());
            mav.addObject("userId", user.getId());
            mav.addObject("userWatchListContentId",userWatchListContentId);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
                mav.addObject("admin",true);
            }else{
                mav.addObject("admin",false);
            }
        } else {
            mav.addObject("userName", "null");
            mav.addObject("userId", "null");
            mav.addObject("admin", false);
            mav.addObject("userWatchListContentId",new ArrayList<Long>());
        }
    }

    private void paginationSetup(ModelAndView mav,int page,List<Review> reviewList){
        if(reviewList.size() == 0) {
            mav.addObject("reviews",reviewList);
            mav.addObject("pageSelected",1);
            mav.addObject("amountPages",1);
            return;
        }

        List<Review> reviewListPaginated = ps.reviewPagination(reviewList, page);
        mav.addObject("reviews", reviewListPaginated);

        int amountOfPages = ps.amountOfContentPages(reviewList.size());
        mav.addObject("amountPages", amountOfPages);
        mav.addObject("pageSelected",page);
    }

    // * ----------------------------------- Movie and Series division -------------------------------------------------
    @RequestMapping(value= {"/{type:movies|series}","/{type:movies|series}/page/{pageNum}"})
    public ModelAndView contentType(Principal userDetails,
                                    @ModelAttribute("genreFilterForm") final GenreFilterForm genreFilterForm,
                                    @PathVariable("type") final String type,
                                    @PathVariable("pageNum")final Optional<Integer> pageNum,
                                    HttpServletRequest request) {
        String auxType = null;
        final ModelAndView mav = new ModelAndView("contentPage");
        if(Objects.equals(type, "movies")) {
            auxType = "movie";
        } else if(Objects.equals(type, "series")) {
            auxType = "serie";
        }
        int page= pageNum.orElse(1);
        List<Content> contentList = cs.getAllContent(auxType, null);
        if(contentList == null) {
            LOGGER.warn("Failed at requesting content",new PageNotFoundException());
            throw new PageNotFoundException();
        } else {
            List<Content> contentListPaginated = ps.contentPagination(contentList, page);
            mav.addObject("allContent", contentListPaginated);
            if(Objects.equals(type, "movies") || Objects.equals(type, "series")){
                mav.addObject("contentType", type);
            } else {
                mav.addObject("contentType", "all");
            }
            int amountOfPages = ps.amountOfContentPages(contentList.size());
            mav.addObject("amountPages", amountOfPages);
            mav.addObject("pageSelected",page);
            mav.addObject("genre","ANY");
            mav.addObject("durationFrom","ANY");
            mav.addObject("durationTo","ANY");
            mav.addObject("sorting","ANY");
        }
        mav.addObject("sortingTypes", Sorting.values());
        HeaderSetUp(mav,userDetails);
        request.getSession().setAttribute("referer","/"+type);

        return mav;
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Movies and Series Info page -----------------------------------------------
    @RequestMapping(value={"/{type:movie|serie}/{contentId:[0-9]+}","/{type:movie|serie}/{contentId:[0-9]+}/page/{pageNum:[0-9]+}"})
    public ModelAndView reviews(Principal userDetails,
                                @PathVariable("contentId")final long contentId,
                                @PathVariable("type") final String type,
                                @Valid @ModelAttribute("commentForm") final CommentForm commentForm,
                                @ModelAttribute("reportReviewForm") final ReportCommentForm reportReviewForm,
                                @ModelAttribute("reportCommentForm") final ReportCommentForm reportCommentForm,
                                @PathVariable("pageNum")final Optional<Integer> pageNum,
                                HttpServletRequest request) {
        final ModelAndView mav = new ModelAndView("infoPage");
        Content content=cs.findById(contentId).orElseThrow(PageNotFoundException::new);
        mav.addObject("details", content);
        List<Review> reviewList = rs.getAllReviews(content);
        User user=null;
        if(reviewList == null) {
            LOGGER.warn("Cant find a the content specified",new PageNotFoundException());
            throw new PageNotFoundException();
        }

        String auxType;
        if (Objects.equals(type, "movie")) {
            auxType = "movies";
        } else if (Objects.equals(type, "serie")) {
            auxType = "series";
        } else {
            auxType = "all";
        }

        mav.addObject("contentId",contentId);
        mav.addObject("type",auxType);

        if(userDetails != null) {
            String userEmail = userDetails.getName();
            user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());
            rs.userLikeAndDislikeReviewsId(user.getUserVotes());
            mav.addObject("userLikeReviews", rs.getUserLikeReviews());
            mav.addObject("userDislikeReviews", rs.getUserDislikeReviews());

            Optional<Long> isInWatchList = us.searchContentInWatchList(user, contentId);
            if(isInWatchList.get() != -1) {
                mav.addObject("isInWatchList",isInWatchList);
            } else {
                mav.addObject("isInWatchList","null");
            }

            Optional<Long> isInViewedList = us.searchContentInViewedList(user, contentId);
            if(isInViewedList.get() != -1) {
                mav.addObject("isInViewedList",isInViewedList);
            } else {
                mav.addObject("isInViewedList","null");
            }

            if(user.getRole().equals("admin")) {
                mav.addObject("admin",true);
            } else {
                mav.addObject("admin",false);
            }
        } else {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
            mav.addObject("isInWatchList","null");
            mav.addObject("isInViewedList","null");
            mav.addObject("admin",false);
            mav.addObject("userLikeReviews", rs.getUserLikeReviews());
            mav.addObject("userDislikeReviews", rs.getUserDislikeReviews());
        }

        reviewList = rs.sortReviews(user,reviewList);
        paginationSetup(mav,pageNum.orElse(1),reviewList);
        request.getSession().setAttribute("referer","/"+type+"/"+contentId);
        return mav;
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // *  ----------------------------------- Movies and Serie Filters -------------------------------------------------
    @RequestMapping(value = {"/{type:movies|series|all|profile}/filters" , "/{type:movies|series|all|profile}/filters/page/{pageNum}"})
    public ModelAndView moviesWithFilters(
            Principal userDetails,
            @ModelAttribute("genreFilterForm") final GenreFilterForm genreFilterForm,
            @PathVariable("type") final String type,
            @PathVariable("pageNum")final Optional<Integer> pageNum,
            @RequestParam(name = "durationFrom",defaultValue = "ANY",required = false)final String durationFrom,
            @RequestParam(name = "durationTo",defaultValue = "ANY",required = false)final String durationTo,
            @RequestParam(name = "sorting", required = false) final Optional<Sorting> sorting,
            @RequestParam(name = "query", defaultValue = "ANY") final String query,
            @RequestParam(name = "genre",required = false)final List<String> genre,
            HttpServletRequest request) {

        ModelAndView mav;
        String auxType;
        int page = pageNum.orElse(1);

        if (Objects.equals(type, "movies")) {
            auxType = "movie";
        } else if (Objects.equals(type, "series")) {
            auxType = "serie";
        } else {
            auxType = "all";
        }

        List<String> genreList = genreFilterForm.getFormGenre() != null ? Arrays.asList(genreFilterForm.getFormGenre()) : genre;
        if(genreList!=null){
            genreFilterForm.setFormGenre(genreList.toArray(new String[0]));
        }
        List<Content> contentListFilter = cs.getMasterContent(auxType,genreList,durationFrom,durationTo,sorting.orElse(null),query);

        mav = new ModelAndView("contentPage");

        if(contentListFilter == null) {
            LOGGER.warn("Failed at requesting content",new PageNotFoundException());
            throw new PageNotFoundException();
        } else {
            List<Content> contentListFilterPaginated = ps.contentPagination(contentListFilter, page);
            mav.addObject("allContent", contentListFilterPaginated);

            int amountOfPages = ps.amountOfContentPages(contentListFilter.size());
            mav.addObject("amountPages", amountOfPages);
            mav.addObject("pageSelected",page);
            mav.addObject("contentType", type);
        }

        mav.addObject("genre", cs.getGenreString(genreList));
        mav.addObject("durationFrom",durationFrom);
        mav.addObject("durationTo",durationTo);
        mav.addObject("sorting", sorting.isPresent()? sorting.get().toString() : "ANY");
        mav.addObject("query", query);
        mav.addObject("sortingTypes", Sorting.values());
        HeaderSetUp(mav,userDetails);
        request.getSession().setAttribute("referer","/"+type+"/filters");
        return mav;
    }
    // * ---------------------------------------------------------------------------------------------------------------

}
