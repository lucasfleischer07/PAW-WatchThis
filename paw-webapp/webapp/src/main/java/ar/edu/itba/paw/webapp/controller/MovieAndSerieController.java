package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.dto.request.GenreFilterDto;
import ar.edu.itba.paw.webapp.dto.request.NewCommentDto;
import ar.edu.itba.paw.webapp.dto.response.CommentDto;
import ar.edu.itba.paw.webapp.dto.response.ContentDto;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.GenreFilterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.security.Principal;
import java.util.*;

@Path("content")
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



    // * ----------------------------------- Movie and Series division -------------------------------------------------
    // Endpoint para getear las peliculas/series
    @GET
    @Path("/{contentType}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getContentByType(@PathParam("contentType") final String contentType,
                                     @QueryParam("pageNumber")Optional<Integer> pageNum,
                                     @QueryParam("pageSize") @DefaultValue("10") int pageSize) {

        int page= pageNum.orElse(1);
        List<Content> contentList = cs.getAllContent(contentType, null);
        List<Content> contentListPaginated;
        if(contentList == null) {
            LOGGER.warn("GET /{}: Failed at requesting content", uriInfo.getPath(), new PageNotFoundException());
            throw new PageNotFoundException();
        } else {
            contentListPaginated = ps.pagePagination(contentList, page,CONTENT_AMOUNT);
        }
//        TODO: VER QUE PASARLE ACA COMO USER
        Optional<User> user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(!user.isPresent()) {
            user = Optional.empty();
        }
        LOGGER.info("GET /{}: Success getting the content", uriInfo.getPath(), new PageNotFoundException());
        return Response.ok(ContentDto.mapContentToContentDto(uriInfo, contentListPaginated, user.get())).build();
    }

//    @RequestMapping(value= {"/{type:movies|series}","/{type:movies|series}/page/{pageNum}"})
//    public ModelAndView contentType(Principal userDetails,
//                                    @ModelAttribute("genreFilterForm") final GenreFilterForm genreFilterForm,
//                                    @PathVariable("type") final String type,
//                                    @PathVariable("pageNum")final Optional<Integer> pageNum,
//                                    HttpServletRequest request) {
//        String auxType = null;
//        final ModelAndView mav = new ModelAndView("contentPage");
//        if(Objects.equals(type, "movies")) {
//            auxType = "movie";
//        } else if(Objects.equals(type, "series")) {
//            auxType = "serie";
//        }
//        int page= pageNum.orElse(1);
//        List<Content> contentList = cs.getAllContent(auxType, null);
//        if(contentList == null) {
//            LOGGER.warn("Failed at requesting content",new PageNotFoundException());
//            throw new PageNotFoundException();
//        } else {
//            List<Content> contentListPaginated = ps.pagePagination(contentList, page,CONTENT_AMOUNT);
//            mav.addObject("allContent", contentListPaginated);
//            mav.addObject("contentType", auxType);
//            mav.addObject("contentType2", type);
//            int amountOfPages = ps.amountOfContentPages(contentList.size(),CONTENT_AMOUNT);
//            mav.addObject("amountPages", amountOfPages);
//            mav.addObject("pageSelected",page);
//            mav.addObject("genre","ANY");
//            mav.addObject("durationFrom","ANY");
//            mav.addObject("durationTo","ANY");
//            mav.addObject("sorting","ANY");
//        }
//        mav.addObject("sortingTypes", Sorting.values());
//        HeaderSetUp(mav,userDetails);
//        request.getSession().setAttribute("referer","/"+type+(pageNum.isPresent()?"/page/"+pageNum.get():""));
//
//        return mav;
//    }

    // * ---------------------------------------------------------------------------------------------------------------


    // *  ----------------------------------- Movies and Serie Filters -------------------------------------------------
    // Endpoint para filtrar el las peliculas/series
    @GET
    @Path("/{contentType}/filters")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response filterContentByType(@PathParam("contentType") final String contentType,
                                        @QueryParam("pageNumber") Optional<Integer> pageNum,
                                        @QueryParam("pageSize") @DefaultValue("10") int pageSize,
                                        @QueryParam("durationFrom") @DefaultValue("ANY") final String durationFrom,
                                        @QueryParam("durationTo") @DefaultValue("ANY") final String durationTo,
                                        @QueryParam("sorting") final Optional<Sorting> sorting,
                                        @QueryParam("query") @DefaultValue("ANY") final String query,
                                        @QueryParam("genre") final List<String> genre,
                                        @Valid GenreFilterDto genreFilterDto) {

        int page= pageNum.orElse(1);
        List<String> genreList = (genreFilterDto.getFormGenre()!=null && genreFilterDto.getFormGenre().length > 0 ) ? Arrays.asList(genreFilterDto.getFormGenre()) : genre;

        if(genreList != null){
            genreFilterDto.setFormGenre(genreList.toArray(new String[0]));
        }

        List<Content> contentListFilter = cs.getMasterContent(contentType, genreList,durationFrom,durationTo,sorting.orElse(null),query);
        List<Content> contentListFilterPaginated;
        int amountOfPages;
        if(contentListFilter == null) {
            LOGGER.warn("GET /{}: Failed at requesting content", uriInfo.getPath(), new PageNotFoundException());
            throw new PageNotFoundException();
        } else {
            contentListFilterPaginated = ps.pagePagination(contentListFilter, page,CONTENT_AMOUNT);
            amountOfPages = ps.amountOfContentPages(contentListFilter.size(),CONTENT_AMOUNT);
        }

//        TODO: VER QUE ONDA ESTO DE LA URL, CREO QUE NO ES NECESARIO PERO NDEA
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


//        TODO: VER QUE PASARLE ACA COMO USER
        Optional<User> user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(!user.isPresent()) {
            user = Optional.empty();
        }
        LOGGER.info("GET /{}: Success filtering the content", uriInfo.getPath(), new PageNotFoundException());
        return Response.ok(ContentDto.mapContentToContentDto(uriInfo, contentListFilterPaginated, user.get())).build();
    }


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