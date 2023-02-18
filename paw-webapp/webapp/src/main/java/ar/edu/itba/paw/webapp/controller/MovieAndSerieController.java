package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("contentType")
@Component
public class MovieAndSerieController {
    @Autowired
    private UserService us;
    @Autowired
    private ContentService cs;
    @Autowired
    private PaginationService ps;
    @Context
    UriInfo uriInfo;
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieAndSerieController.class);
    private static final int CONTENT_AMOUNT = 18;

//    private void HeaderSetUp(ModelAndView mav,Principal userDetails) {
//        if(userDetails != null) {
//            String userEmail = userDetails.getName();
//            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
//            List<Long> userWatchListContentId = us.getUserWatchListContent(user);
//            mav.addObject("userName", user.getUserName());
//            mav.addObject("userId", user.getId());
//            mav.addObject("userWatchListContentId",userWatchListContentId);
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
//                mav.addObject("admin",true);
//            }else{
//                mav.addObject("admin",false);
//            }
//        } else {
//            mav.addObject("userName", "null");
//            mav.addObject("userId", "null");
//            mav.addObject("admin", false);
//            mav.addObject("userWatchListContentId",new ArrayList<Long>());
//        }
//    }





    // *  ----------------------------------- Movies and Serie Filters -------------------------------------------------
//    @RequestMapping(value = {"/{type:movies|series|all}/filters" , "/{type:movies|series|all}/filters/page/{pageNum}"})
//    public ModelAndView moviesWithFilters(
//            Principal userDetails,
//            @ModelAttribute("genreFilterForm") final GenreFilterForm genreFilterForm,
//            @PathVariable("type") final String type,
//            @PathVariable("pageNum")final Optional<Integer> pageNum,
//            @RequestParam(name = "durationFrom",defaultValue = "ANY",required = false)final String durationFrom,
//            @RequestParam(name = "durationTo",defaultValue = "ANY",required = false)final String durationTo,
//            @RequestParam(name = "sorting", required = false) final Optional<Sorting> sorting,
//            @RequestParam(name = "query", defaultValue = "ANY") final String query,
//            @RequestParam(name = "genre",required = false)final List<String> genre,
//            HttpServletRequest request) {
//
//        ModelAndView mav;
//        String auxType;
//        int page = pageNum.orElse(1);
//
//        if (Objects.equals(type, "movies")) {
//            auxType = "movie";
//        } else if (Objects.equals(type, "series")) {
//            auxType = "serie";
//        } else {
//            auxType = "all";
//        }
//        List<String> genreList = (genreFilterForm.getFormGenre()!=null && genreFilterForm.getFormGenre().length > 0 ) ? Arrays.asList(genreFilterForm.getFormGenre()) : genre;
//
//        if(genreList!=null){
//            genreFilterForm.setFormGenre(genreList.toArray(new String[0]));
//        }
//        List<Content> contentListFilter = cs.getMasterContent(auxType,genreList,durationFrom,durationTo,sorting.orElse(null),query);
//
//        mav = new ModelAndView("contentPage");
//
//        if(contentListFilter == null) {
//            LOGGER.warn("Failed at requesting content",new PageNotFoundException());
//            throw new PageNotFoundException();
//        } else {
//            List<Content> contentListFilterPaginated = ps.pagePagination(contentListFilter, page,CONTENT_AMOUNT);
//            mav.addObject("allContent", contentListFilterPaginated);
//
//            int amountOfPages = ps.amountOfContentPages(contentListFilter.size(),CONTENT_AMOUNT);
//            mav.addObject("amountPages", amountOfPages);
//            mav.addObject("pageSelected",page);
//            mav.addObject("contentType", type);
//            mav.addObject("contentType2", type);
//        }
//
//        mav.addObject("genre", cs.getGenreString(genreList));
//        mav.addObject("durationFrom",durationFrom);
//        mav.addObject("durationTo",durationTo);
//        mav.addObject("sorting", sorting.isPresent()? sorting.get().toString() : "ANY");
//        mav.addObject("query", query);
//        mav.addObject("sortingTypes", Sorting.values());
//        HeaderSetUp(mav,userDetails);
//        StringBuilder referer=new StringBuilder();
//        referer.append("/"+type+"/filters"+(pageNum.isPresent()?"/page/"+pageNum.get():""));
//        if(genreList!=null||query!="ANY"||durationFrom!=null||durationTo!=null||sorting.isPresent()){
//            referer.append("?");
//            if(genreList!=null){
//                referer.append(referer.charAt(referer.length()-1)=='?'?"genre="+genreList.get(0):"&genre+"+genreList.get(0));
//                for(int i=1;i<genreList.size();i++){
//                    referer.append("%2c"+genreList.get(i));
//                }
//            }
//            if(!query.equals("ANY")){
//                referer.append(referer.charAt(referer.length()-1)=='?'?"query="+query:"&query="+query);
//            }
//            if(durationFrom!=null){
//                referer.append(referer.charAt(referer.length()-1)=='?' ? "durationFrom="+durationFrom:"&durationFrom="+durationFrom);
//            }
//            if(durationTo!=null){
//                referer.append(referer.charAt(referer.length()-1)=='?'?"durationTo="+durationTo:"&durationTo="+durationTo);
//            }
//            if(sorting.isPresent()){
//                referer.append(referer.charAt(referer.length()-1)=='?'?"sorting="+sorting.get():"&sorting="+sorting.get());
//            }
//        }
//        request.getSession().setAttribute("referer",referer.toString());
//        return mav;
//    }
    // * ---------------------------------------------------------------------------------------------------------------

}