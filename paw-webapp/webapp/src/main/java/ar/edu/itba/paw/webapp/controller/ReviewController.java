package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.ContentNotFoundException;
import ar.edu.itba.paw.exceptions.ReviewNotFoundException;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.dto.request.NewReviewDto;
import ar.edu.itba.paw.webapp.dto.response.ContentDto;
import ar.edu.itba.paw.webapp.dto.response.ReviewDto;
import ar.edu.itba.paw.webapp.utilities.ResponseBuildingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.Objects;

@Path("reviews")
@Component
public class ReviewController {
    @Autowired
    private ReviewService rs;
    @Autowired
    private ContentService cs;
    @Autowired
    private UserService us;
    @Autowired
    private PaginationService ps;
    @Autowired
    private ReportService rrs;
    @Context
    private UriInfo uriInfo;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);
    private static final int REVIEW_AMOUNT = 3;

// TODO: VER PAGINACION

//    private void paginationSetup(ModelAndView mav,int page,List<Review> reviewList){
//        if(reviewList.size() == 0) {
//            mav.addObject("reviews",reviewList);
//            mav.addObject("pageSelected",1);
//            mav.addObject("amountPages",1);
//            return;
//        }
//
//        List<Review> reviewListPaginated = ps.infiniteScrollPagination(reviewList, page,REVIEW_AMOUNT);
//        mav.addObject("reviews", reviewListPaginated);
//
//        int amountOfPages = ps.amountOfContentPages(reviewList.size(),REVIEW_AMOUNT);
//        mav.addObject("amountPages", amountOfPages);
//        mav.addObject("pageSelected",page);
//    }


    // * ----------------------------------- Movies and Series Review Gets ---------------------------------------------
    /*
    TODO: HAY QUE VER COMO MANDAMOS LA CANTIDAD DE PAGINAS Y ESO
     */

    @GET
    @Path("/{contentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reviews(@PathParam("contentId") final long contentId,
                            @QueryParam("pageNumber") @DefaultValue("1") int pageNumber,
                            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOGGER.info("GET /{}: Called",uriInfo.getPath());
        Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        PageWrapper<Review> reviewList = rs.getAllReviews(content,pageNumber,REVIEW_AMOUNT);
        if(reviewList == null) {
            LOGGER.warn("GET /{}: Cant find a the content specified",uriInfo.getPath());
            throw new ContentNotFoundException();
        }
        Collection<ReviewDto> reviewDtoList = ReviewDto.mapReviewToReviewDto(uriInfo, reviewList.getPageContent());

        LOGGER.info("GET /{}: Review list for content {}",uriInfo.getPath(), contentId);

//        TODO: El Return aca deberia ya estar paginado (Por el momento no lo esta, habria que cambairlo)
        final Response.ResponseBuilder response = Response.ok(new GenericEntity<Collection<ReviewDto>>(reviewDtoList){});
        ResponseBuildingUtils.setPaginationLinks(response,reviewList , uriInfo);
        return response.build();


    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Movies and Series Lists Gets ----------------------------------------------

// TODO: VER QUE ONDA ESTO

//    @GET
//    @Path("/{contentId}/isInWatchlist")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response contentWatchList(@PathParam("contentId") final long contentId) {
//        Content content = cs.findById(contentId).orElseThrow(PageNotFoundException::new);
//        final Optional<User> user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
//        Optional<Long> isInWatchList = us.searchContentInWatchList(user.get(), contentId);
//
//        LOGGER.info("GET /{}: Review list for content {}", uriInfo.getPath(), contentId);
//        return Response.ok(ReviewDto.mapReviewToReviewDto(uriInfo, reviewList)).build();
//    }



//    @RequestMapping(value={"/{type:movie|serie}/{contentId:[0-9]+}","/{type:movie|serie}/{contentId:[0-9]+}/page/{pageNum:[0-9]+}"})
//    public ModelAndView reviews(Principal userDetails,
//                                @PathVariable("contentId")final long contentId,
//                                @PathVariable("type") final String type,
//                                @Valid @ModelAttribute("commentForm") final CommentForm commentForm,
//                                @ModelAttribute("reportReviewForm") final ReportCommentForm reportReviewForm,
//                                @ModelAttribute("reportCommentForm") final ReportCommentForm reportCommentForm,
//                                @PathVariable("pageNum")final Optional<Integer> pageNum,
//                                HttpServletRequest request) {
//        final ModelAndView mav = new ModelAndView("infoPage");
//        Content content=cs.findById(contentId).orElseThrow(PageNotFoundException::new);
//        mav.addObject("details", content);
//        List<Review> reviewList = rs.getAllReviews(content);
//        User user=null;
//        if(reviewList == null) {
//            LOGGER.warn("Cant find a the content specified",new PageNotFoundException());
//            throw new PageNotFoundException();
//        }
//
//        String auxType;
//        if (Objects.equals(type, "movie")) {
//            auxType = "movies";
//        } else if (Objects.equals(type, "serie")) {
//            auxType = "series";
//        } else {
//            auxType = "all";
//        }
//
//        mav.addObject("contentId",contentId);
//        mav.addObject("type",auxType);
//        mav.addObject("type2",auxType);
//
//        if(userDetails != null) {
//            String userEmail = userDetails.getName();
//            user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
//            mav.addObject("userName",user.getUserName());
//            mav.addObject("user",user);
//            mav.addObject("userId",user.getId());
//            rs.userLikeAndDislikeReviewsId(user.getUserVotes());
//            mav.addObject("userLikeReviews", rs.getUserLikeReviews());
//            mav.addObject("userDislikeReviews", rs.getUserDislikeReviews());
//
//            Optional<Long> isInWatchList = us.searchContentInWatchList(user, contentId);
//            if(isInWatchList.get() != -1) {
//                mav.addObject("isInWatchList",isInWatchList);
//            } else {
//                mav.addObject("isInWatchList","null");
//            }
//
//            Optional<Long> isInViewedList = us.searchContentInViewedList(user, contentId);
//            if(isInViewedList.get() != -1) {
//                mav.addObject("isInViewedList",isInViewedList);
//            } else {
//                mav.addObject("isInViewedList","null");
//            }
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//                mav.addObject("admin",true);
//            } else {
//                mav.addObject("admin",false);
//            }
//        } else {
//            mav.addObject("userName","null");
//            mav.addObject("userId","null");
//            mav.addObject("isInWatchList","null");
//            mav.addObject("isInViewedList","null");
//            mav.addObject("admin",false);
//            mav.addObject("userLikeReviews", rs.getUserLikeReviews());
//            mav.addObject("userDislikeReviews", rs.getUserDislikeReviews());
//        }
//
//        reviewList = rs.sortReviews(user,reviewList);
//        paginationSetup(mav,pageNum.orElse(1),reviewList);
//        request.getSession().setAttribute("referer","/"+type+"/"+contentId+(pageNum.isPresent()?"/page/"+pageNum.get():""));
//        return mav;
//    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Review Creation -----------------------------------------------------------
//    Endpoint para crear una resenia
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/create/{type}/{contentId}")
    public Response reviews(@PathParam("type") final String type,
                            @PathParam("contentId") final long contentId,
                            @Valid NewReviewDto reviewDto) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());
        final Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);
        try {
            rs.addReview(reviewDto.getName(), reviewDto.getDescription(), reviewDto.getRating(), reviewDto.getType(), user, content);
            LOGGER.info("POST /{}: Review added", uriInfo.getPath());

        } catch (DuplicateKeyException e) {
            LOGGER.warn("POST /{}: Duplicate review", uriInfo.getPath(), new ContentNotFoundException());
        }

        return Response.created(ReviewDto.getReviewUriBuilder(content, uriInfo).build()).build();
    }

//    @RequestMapping(value = "/reviewForm/{type:movie|serie}/{id:[0-9]+}", method = {RequestMethod.GET})
//    public ModelAndView reviewFormCreate(Principal userDetails,
//                                         @ModelAttribute("registerForm") final ReviewForm reviewForm,
//                                         @PathVariable("id")final long id,
//                                         @PathVariable("type")final String type) {
//        final ModelAndView mav = new ModelAndView("reviewRegistrationPage");
//        mav.addObject("details", cs.findById(id).orElseThrow(PageNotFoundException::new));
//        if(Objects.equals(type, "movie")) {
//            mav.addObject("type", "movies");
//        } else if(Objects.equals(type, "serie")) {
//            mav.addObject("type", "series");
//        }
//        if(userDetails != null) {
//            String userEmail = userDetails.getName();
//            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
//            mav.addObject("userName",user.getUserName());
//            mav.addObject("userId",user.getId());
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//                mav.addObject("admin",true);
//            } else {
//                mav.addObject("admin",false);
//            }
//        } else {
//            mav.addObject("userName","null");
//            mav.addObject("userId","null");
//            mav.addObject("admin",false);
//        }
//        return mav;
//    }

//    @RequestMapping(value = "/reviewForm/{type:movie|serie}/{id:[0-9]+}", method = {RequestMethod.POST})
//    public ModelAndView reviewFormMovie(Principal userDetails,
//                                        @Valid @ModelAttribute("registerForm") final ReviewForm form,
//                                        final BindingResult errors,
//                                        @PathVariable("id")final long id,
//                                        @PathVariable("type")final String type,
//                                        HttpServletRequest request) {
//        if(errors.hasErrors()) {
//            return reviewFormCreate(userDetails,form,id,type);
//        }
//        if(form.getRating() < 0 || form.getRating() > 5) {
//            return reviewFormCreate(userDetails,form,id,type);
//        }
//
//        Optional<User> user = us.findByEmail(userDetails.getName());
//        try {
//            Content content= cs.findById(id).orElseThrow(PageNotFoundException ::new);
//            rs.addReview(form.getName(),form.getDescription(), form.getRating(), type,user.get(),content);
//        }
//        catch(DuplicateKeyException e){
//            ModelAndView mav = reviewFormCreate(userDetails,form,id,type);
//            mav.addObject("errorMsg","You have already written a review for this " + type + ".");
//            return mav;
//        }
//
//        ModelMap model =new ModelMap();
//        String referer = request.getSession().getAttribute("referer").toString();
//        return new ModelAndView("redirect:" + (referer==null?"/":referer),model);
//
//    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Review delete-----------------------------------------------------
//    Endpoint para eliminar una resenia
    @DELETE
    @Path("/delete/{reviewId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response deleteReview(@PathParam("reviewId") final Long reviewId) {
        LOGGER.info("DELETE /{}: Called", uriInfo.getPath());
        final Review review = rs.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);
        if(review.getUser().getUserName().equals(user.getUserName())) {
            rs.deleteReview(reviewId);
            LOGGER.info("DELETE /{}: Review Deleted by user owner", uriInfo.getPath());
            return Response.noContent().build();
        }
//        TODO: VER ESTE QUE SERIA SI UN ADIN LO ELIMINA
        else if(Objects.equals(user.getRole(), "admin")) {
            rrs.delete(review, null);
            LOGGER.info("DELETE /{}: Review Deleted by admin", uriInfo.getPath());
            return Response.noContent().build();

        } else {
            LOGGER.warn("DELETE /{}: Not allowed to delete", uriInfo.getPath());
            throw new ForbiddenException();
        }

    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Review edition----------------------------------------------------
//    Endpoint para editar una review
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/editReview/{reviewId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response reviewEdition(@PathParam("reviewId") final Long reviewId,
                                  @Valid final NewReviewDto reviewDto) {

        LOGGER.info("PUT /{}: Called", uriInfo.getPath());

        final Review oldReview = rs.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(ForbiddenException::new);
        if(!oldReview.getUser().getUserName().equals(user.getUserName())){
            LOGGER.warn("PUT /{}: The editor is not owner of the review", uriInfo.getPath());
            throw new ForbiddenException();
        }
        rs.updateReview(reviewDto.getName(), reviewDto.getDescription(), reviewDto.getRating(), reviewId);
        return Response.noContent().build();
    }

//    @RequestMapping(value = "/reviewForm/edit/{type:movie|serie}/{contentId:[0-9]+}/{reviewId:[0-9]+}", method = {RequestMethod.GET})
//    public ModelAndView reviewFormEdition(Principal userDetails,
//                                          @ModelAttribute("registerForm") final ReviewForm reviewForm,
//                                          @PathVariable("contentId")final long contentId,
//                                          @PathVariable("reviewId")final long reviewId,
//                                          @PathVariable("type")final String type,
//                                          HttpServletRequest request) {
//        final ModelAndView mav = new ModelAndView("reviewEditionPage");
//        mav.addObject("details", cs.findById(contentId).orElseThrow(PageNotFoundException::new));
//        if(Objects.equals(type, "movie")) {
//            mav.addObject("type", "movies");
//        } else if(Objects.equals(type, "serie")) {
//            mav.addObject("type", "series");
//        }
//        Optional<Review> oldReview = rs.findById(reviewId);
//        if(!oldReview.isPresent()){
//            LOGGER.warn("Cant find a the review specified", new PageNotFoundException());
//            throw new PageNotFoundException();
//        }
//        if(userDetails != null) {
//            String userEmail = userDetails.getName();
//            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
//            mav.addObject("user",user);
//            mav.addObject("userName",user.getUserName());
//            mav.addObject("userId",user.getId());
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
//                mav.addObject("admin",true);
//            } else {
//                mav.addObject("admin",false);
//            }
//
//            if(!oldReview.get().getUser().getUserName().equals(us.findByEmail(userDetails.getName()).get().getUserName())){
//                LOGGER.warn("The editor is not owner of the review",new ForbiddenException());
//                throw new ForbiddenException();
//            }
//
//        } else {
//            mav.addObject("userName","null");
//            mav.addObject("userId","null");
//            mav.addObject("admin",false);
//        }
//
//        reviewForm.setDescription(oldReview.get().getDescription());
//        reviewForm.setRating(oldReview.get().getRating());
//        reviewForm.setName(oldReview.get().getName());
//        String referer = request.getHeader("Referer");
//        mav.addObject("backLink",referer);
//        mav.addObject("reviewInfo", rs.findById(reviewId).orElseThrow(PageNotFoundException::new));
//        return mav;
//    }
//
//    @RequestMapping(value = "/reviewForm/edit/{type:movie|serie}/{contentId:[0-9]+}/{reviewId:[0-9]+}", method = {RequestMethod.POST})
//    public ModelAndView reviewFormEditionPost(Principal userDetails,
//                                              @Valid @ModelAttribute("registerForm") final ReviewForm form,
//                                              final BindingResult errors, @PathVariable("type")final String type,
//                                              @PathVariable("contentId")final long contentId,
//                                              @PathVariable("reviewId")final long reviewId,
//                                              HttpServletRequest request) {
//        if(errors.hasErrors()) {
//            return reviewFormEdition(userDetails,form,contentId,reviewId,type,request);
//        }
//        if(form.getRating() < 0 || form.getRating() > 5) {
//            return reviewFormEdition(userDetails,form,contentId,reviewId,type,request);
//        }
//
//        Optional<Review> oldReview = rs.findById(reviewId);
//        if(!oldReview.isPresent()){
//            LOGGER.warn("Cant find a the review specified",new PageNotFoundException());
//            throw new PageNotFoundException();
//        }
//
//        Optional<User> user = us.findByEmail(userDetails.getName());
//        if(!oldReview.get().getUser().getUserName().equals(user.get().getUserName())){
//            LOGGER.warn("The editor is not owner of the review",new PageNotFoundException());
//            throw new ForbiddenException();
//        }
//        rs.updateReview(form.getName(), form.getDescription(), form.getRating(), reviewId);
//        ModelMap model =new ModelMap();
//        String referer = request.getSession().getAttribute("referer").toString();
//        return new ModelAndView("redirect:" + (referer==null?"/":referer),model);
//
//    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ---------------------------------------------Review reputation-------------------------------------------------
//    Endpoint para likear una review
    @PUT
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/reviewReputation/thumbUp/{reviewId}")
    public Response reviewThumbUp(@PathParam("reviewId") final long reviewId) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());
        if(us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).isPresent()) {
            Review review = rs.getReview(reviewId).orElseThrow(ReviewNotFoundException::new);
            User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
            rs.thumbUpReview(review,loggedUser);
        } else {
            LOGGER.warn("PUT /{}: Not allowed to thumb up the review", uriInfo.getPath());
            throw new ForbiddenException();
        }
        LOGGER.info("PUT /{}: Thumb up successful", uriInfo.getPath());

        return Response.noContent().build();
    }


//    Endpoint para likear una review
    @PUT
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/reviewReputation/thumbDown/{reviewId}")
    public Response reviewThumbDown(@PathParam("reviewId") final long reviewId) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());
        if(us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).isPresent()) {
            Review review = rs.getReview(reviewId).orElseThrow(ReviewNotFoundException::new);
            User loggedUser = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
            rs.thumbDownReview(review,loggedUser);
        } else {
            LOGGER.warn("PUT /{}: Not allowed to thumb down the review", uriInfo.getPath());
            throw new ForbiddenException();
        }
        LOGGER.info("PUT /{}: Thumb down successful", uriInfo.getPath());

        return Response.noContent().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------

}
