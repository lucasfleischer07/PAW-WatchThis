package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.auth.PawUserDetails;
import ar.edu.itba.paw.webapp.exceptions.MethodNotAllowedException;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ContentController {

    private final ContentService cs;
    private final UserService us;
    private final int ELEMS_AMOUNT = 15;

    @Autowired
    public ContentController(ContentService cs,UserService us){
        this.cs=cs;
        this.us=us;
    }

    // * ----------------------------------- Movie and Series Info -----------------------------------------------------------------------
    @RequestMapping(value= {"/","page/{pageNum}"})
    public ModelAndView helloWorld(@AuthenticationPrincipal PawUserDetails userDetails, @PathVariable("pageNum")final Optional<Integer> pageNum, @RequestParam(name = "query", defaultValue = "") final String query) {
        final ModelAndView mav = new ModelAndView("HomePage");
        List<Content> bestRatedList = cs.getBestRated();
        if(bestRatedList == null) {
            throw new PageNotFoundException();
        }
        mav.addObject("bestRatedList", bestRatedList);
        mav.addObject("bestRatedListSize", bestRatedList.size());

        mav.addObject("genre","ANY");
        mav.addObject("durationFrom","ANY");
        mav.addObject("durationTo","ANY");
        mav.addObject("sorting","ANY");
        mav.addObject("contentType", "all");


        try {
            String userEmail = userDetails.getUsername();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());
        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
        }

        return mav;
    }


    @RequestMapping(value= {"/{type:movies|series}","/{type:movies|series}/page/{pageNum}"})
    public ModelAndView contentType(@AuthenticationPrincipal PawUserDetails userDetails, @PathVariable("type") final String type,@PathVariable("pageNum")final Optional<Integer> pageNum) {
        String auxType = null;
        final ModelAndView mav = new ModelAndView("ContentPage");
        if(Objects.equals(type, "movies")) {
            auxType = "movie";
        } else if(Objects.equals(type, "series")) {
            auxType = "serie";
        }
        int page= pageNum.orElse(1);
        List<Content> contentList = cs.getAllContent(auxType, "ANY");
        if( contentList == null) {
            throw new PageNotFoundException();
        } else {
            if(contentList.size() < (page)*ELEMS_AMOUNT){       //Si no llega a completar la pagina entera, que sirva los que pueda
                mav.addObject("allContent", contentList.subList((page-1)*ELEMS_AMOUNT,contentList.size()));
            }else {
                mav.addObject("allContent", contentList.subList((page - 1) * ELEMS_AMOUNT, (page - 1) * ELEMS_AMOUNT + ELEMS_AMOUNT));
            }
            if(Objects.equals(type, "movies") || Objects.equals(type, "series")){
                mav.addObject("contentType", type);
            }else {
                mav.addObject("contentType", "all");
            }
            mav.addObject("amountPages",(int)Math.ceil((double) contentList.size()/(double)ELEMS_AMOUNT));
            mav.addObject("pageSelected",page);
            mav.addObject("genre","ANY");
            mav.addObject("durationFrom","ANY");
            mav.addObject("durationTo","ANY");
        }

        try {
            String userEmail = userDetails.getUsername();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());

        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
        }
        return mav;
    }

    @RequestMapping(value = {"/search", "/page/{pageNum}"})
    public ModelAndView search(@AuthenticationPrincipal PawUserDetails userDetails, @PathVariable("pageNum")final Optional<Integer> pageNum, @RequestParam(name = "query", defaultValue = "") final String query) {
        final ModelAndView mav = new ModelAndView("ContentPage");

        mav.addObject("query", query);
        List<Content> contentList = cs.getSearchedContent(query);
        mav.addObject("contentType", "all");

        int page= pageNum.orElse(1);

        if(contentList == null) {
            throw new PageNotFoundException();
        }else {
            if(contentList.size() < (page)*ELEMS_AMOUNT){       //Si no llega a completar la pagina entera, que sirva los que pueda
                mav.addObject("allContent", contentList.subList((page-1)*ELEMS_AMOUNT,contentList.size()));
            }else {
                mav.addObject("allContent", contentList.subList((page - 1) * ELEMS_AMOUNT, (page - 1) * ELEMS_AMOUNT + ELEMS_AMOUNT));
            }
            mav.addObject("amountPages", (int) Math.ceil((double) contentList.size() / (double) ELEMS_AMOUNT));
            mav.addObject("pageSelected", page);
        }

        try {
            String userEmail = userDetails.getUsername();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());

        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
        }
        return mav;
    }

    // *  ----------------------------------- Movies and Serie Filters -----------------------------------------------------------------------

    @RequestMapping(value = {"/{type:movies|series|all}/filters" , "/{type:movies|series|all}/filters/page/{pageNum}"})
    public ModelAndView moviesWithFilters(
            @AuthenticationPrincipal PawUserDetails userDetails,
            @PathVariable("type") final String type,
            @PathVariable("pageNum")final Optional<Integer> pageNum,
            @RequestParam(name = "durationFrom",defaultValue = "ANY",required = false)final String durationFrom,
            @RequestParam(name = "durationTo",defaultValue = "ANY",required = false)final String durationTo,
            @RequestParam(name = "genre", defaultValue = "ANY",required = false)final String genre,
            @RequestParam(name = "sorting", defaultValue = "ANY", required = false) final String sorting) {

        ModelAndView mav = null;
        int page = pageNum.orElse(1);
        String auxType = null;

        if(Objects.equals(type, "movies")) {
            auxType = "movie";
        } else if(Objects.equals(type, "series")) {
            auxType = "serie";
        } else {
            auxType = "all";
        }

        mav = new ModelAndView("ContentPage");
        mav.addObject("genre",genre);
        mav.addObject("durationFrom",durationFrom);
        mav.addObject("durationTo",durationTo);
        mav.addObject("sorting", sorting);

        List<Content> contentListFilter;

        if(!Objects.equals(genre, "ANY") && Objects.equals(durationFrom, "ANY")) {
            contentListFilter = cs.findByGenre(auxType, genre, sorting) ;
        } else if(Objects.equals(genre, "ANY") && !Objects.equals(durationFrom, "ANY")) {
            contentListFilter = cs.findByDuration(auxType, Integer.parseInt(durationFrom), Integer.parseInt(durationTo), sorting);
        } else if(!Objects.equals(durationFrom, "ANY") && !Objects.equals(genre, "ANY")) {    // Caso de que si los filtros estan vacios
            contentListFilter = cs.findByDurationAndGenre(auxType, genre, Integer.parseInt(durationFrom), Integer.parseInt(durationTo), sorting);
        } else{
            contentListFilter = cs.getAllContent(auxType, sorting);
        }

        if(contentListFilter == null) {
            throw new PageNotFoundException();
        } else {
            if(contentListFilter.size() < (page)*ELEMS_AMOUNT){       //Si no llega a completar la pagina entera, que sirva los que pueda
                mav.addObject("allContent", contentListFilter.subList((page-1)*ELEMS_AMOUNT,contentListFilter.size()));
            }else {
                mav.addObject("allContent", contentListFilter.subList((page-1)*ELEMS_AMOUNT,(page-1)*ELEMS_AMOUNT + ELEMS_AMOUNT));
            }
            mav.addObject("amountPages",Math.ceil((double)contentListFilter.size()/(double)ELEMS_AMOUNT));
            mav.addObject("pageSelected",page);
            mav.addObject("contentType", type);
        }

        try {
            String userEmail = userDetails.getUsername();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            mav.addObject("userName",user.getUserName());
            mav.addObject("userId",user.getId());

        } catch (NullPointerException e) {
            mav.addObject("userName","null");
            mav.addObject("userId","null");
        }

        return mav;
    }


    @RequestMapping(path = "/contentImage/{contentId:[0-9]+}", method = RequestMethod.GET, produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] contentImage(@PathVariable("contentId") final Long contentId) {
        if(contentId==null || contentId < 0){
            throw new PageNotFoundException();
        }
        Content content = cs.findById(contentId).orElseThrow(PageNotFoundException::new);
        return content.getImage();
    }

    // * ----------------------------------- Errors Page -----------------------------------------------------------------------
    @ExceptionHandler(PageNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView PageNotFound(){
        return new ModelAndView("errorPage");
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ModelAndView MethodNotAllowed(){
        return new ModelAndView("errorPage");
    }
    // * -----------------------------------------------------------------------------------------------------------------------


}
