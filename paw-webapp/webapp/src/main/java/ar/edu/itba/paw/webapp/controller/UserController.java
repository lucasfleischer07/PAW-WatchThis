package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.dto.request.EditProfileDto;
import ar.edu.itba.paw.webapp.dto.request.NewUser;
import ar.edu.itba.paw.webapp.dto.response.ReviewDto;
import ar.edu.itba.paw.webapp.dto.response.UserDto;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.util.*;

@Path("users")
@Component
public class UserController {
    @Autowired
    private UserService us;
//    @Autowired
//    private ContentService cs;
    @Autowired
    private ReviewService rs;
//    @Autowired
//    private PaginationService ps;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Context
    private UriInfo uriInfo;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final int REVIEW_AMOUNT = 3;


//    TODO: ESTO NI LO VI, HAY QUE VERLO
//    private void paginationSetup(ModelAndView mav,int page,List<Review> reviewList){
//        if(reviewList.size()==0){
//            mav.addObject("reviews",reviewList);
//            mav.addObject("reviewsAmount",reviewList.size());
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
//        mav.addObject("reviewsAmount",reviewList.size());
//
//    }

    // * ----------------------------------------------- User POST -----------------------------------------------------
    // Endpoint para crear un usuario
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response userCreate(@Valid final NewUser newUser) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());
        final User user = us.register(newUser.getEmail(), newUser.getUsername(), newUser.getPassword()).orElseThrow(PageNotFoundException::new);
        LOGGER.info("POST /{}: New user created with id {}", uriInfo.getPath(), user.getId());
        return Response.created(UserDto.getUserUriBuilder(user, uriInfo).build()).build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------------------- User GET ------------------------------------------------------
    // Endpoint para getear la informacion del usuario
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Response getUserInfo(@PathParam("id") final long id) {
        LOGGER.info("GET /{}: Called",  uriInfo.getPath());
        final User user = us.findById(id).orElseThrow(PageNotFoundException::new);
        LOGGER.info("GET /{}: User returned with success", uriInfo.getPath());
        return Response.ok(new UserDto(uriInfo, user)).build();
    }

    // Endpoint para getear las reviews del usuario
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/{id}/reviews")
    public Response getUserReviews(@PathParam("id") final long id,
                                   @QueryParam("page")@DefaultValue("1")final int page) {
        LOGGER.info("GET /{}: Called",  uriInfo.getPath());
        final User user = us.findById(id).orElseThrow(PageNotFoundException::new);
        List<Review> reviewList = rs.getAllUserReviews(user);
        Collection<ReviewDto> reviewDtoList = ReviewDto.mapReviewToReviewDto(uriInfo, reviewList);
        LOGGER.info("GET /{}: User {} reviews returned with success",  uriInfo.getPath(), id);
        return Response.ok(new GenericEntity<Collection<ReviewDto>>(reviewDtoList){}).build();
    }

//    @RequestMapping(value={"/profile","/profile/page/{pageNum:[0-9]+}"})
//    public ModelAndView profile(Principal userDetails,
//                                @PathVariable("pageNum")final Optional<Integer> pageNum,
//                                @ModelAttribute("reportReviewForm") final ReportCommentForm reportReviewForm,
//                                @Valid @ModelAttribute("commentForm") final CommentForm commentForm,
//                                @ModelAttribute("reportCommentForm") final ReportCommentForm reportCommentForm,
//                                HttpServletRequest request) {
//        final String locale = LocaleContextHolder.getLocale().getDisplayLanguage();
//        final ModelAndView mav = new ModelAndView("profileInfoPage");
//        String userEmail = userDetails.getName();
//        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
//        Optional<String> quote = cs.getContentQuote(locale);
//        mav.addObject("quote", quote.get());
//        mav.addObject("user", user);
//        mav.addObject("userName",user.getUserName());
//        mav.addObject("reputationAmount",user.getReputation());
//        mav.addObject("userId",user.getId());
//        mav.addObject("type","all");
//
//        rs.userLikeAndDislikeReviewsId(user.getUserVotes());
//        mav.addObject("userLikeReviews", rs.getUserLikeReviews());
//        mav.addObject("userDislikeReviews", rs.getUserDislikeReviews());
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//            mav.addObject("admin", true);
//        } else {
//            mav.addObject("admin", false);
//        }
//        mav.addObject("canPromote",false);
//
//        paginationSetup(mav,pageNum.orElse(1),rs.getAllUserReviews(user));
//
//        request.getSession().setAttribute("referer","/profile"+(pageNum.isPresent()?"/page/"+pageNum.get():""));
//        return mav;
//    }

    // * ---------------------------------------------------------------------------------------------------------------

    // * ----------------------------------------------- User Image ----------------------------------------------------

    // Endpoint para getear la imagen del usuario
    @GET
    @Produces(value = {"image/*", MediaType.APPLICATION_JSON})
    @Path("/{id}/profileImage")
    public Response getUserProfileImage(@PathParam("id") final long id,
                                        @Context Request request) {
        LOGGER.info("GET /{}: Called", uriInfo.getPath());
        final User user = us.findById(id).orElseThrow(PageNotFoundException::new);
        if(user.getImage() == null) {
            return Response.noContent().build();
        }

        EntityTag eTag = new EntityTag(String.valueOf(user.getId()));
        final CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);
        Response.ResponseBuilder response = request.evaluatePreconditions(eTag);

        if (response == null) {
            final byte[] userImage = user.getImage();
            response = Response.ok(userImage).tag(eTag);
        }

        LOGGER.info("GET /{}: User {} Profile Image", uriInfo.getPath(), id);

        return response.cacheControl(cacheControl).build();
    }

//    Endpoint para editar la imagen de perfil del usuario
    @PUT
    @Path("/{id}/profileImage")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateUserProfileImage(@Size(max = 1024 * 1024) @FormDataParam("image") byte[] imageBytes,
                                           @PathParam("id") final long id) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(PageNotFoundException::new);
        if(user.getId() != id) {
            throw new PageNotFoundException();
        }

        us.setProfilePicture(imageBytes, user);
        LOGGER.info("PUT /{}: User {} Profile Image Updated", uriInfo.getPath(), id);
        return Response.noContent().contentLocation(UserDto.getUserUriBuilder(user, uriInfo).path("profileImage").build()).build();
    }

//    @RequestMapping(path = "/profile/{userName:[a-zA-Z0-9\\s]+}/profileImage", method = RequestMethod.GET, produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
//    @ResponseBody
//    public byte[] profileImage(@PathVariable("userName") final String userName) {
//        if(userName==null || userName.equals("")){
//            LOGGER.warn("Wrong username photo request:",new PageNotFoundException());
//            throw new PageNotFoundException();
//        }
//        User user = us.findByUserName(userName).orElseThrow(PageNotFoundException::new);
//        return user.getImage();
//    }
    // * ---------------------------------------------------------------------------------------------------------------

//    @RequestMapping(value = {"/profile/{userName:[a-zA-Z0-9\\s]+}","/profile/{userName:[a-zA-Z0-9\\s]+}/page/{pageNum:[0-9]+}"},method = {RequestMethod.GET})
//    public ModelAndView profileInfo(Principal userDetails,
//                                    @PathVariable("userName") final String userName,
//                                    @PathVariable("pageNum")final Optional<Integer> pageNum,
//                                    @ModelAttribute("reportReviewForm") final ReportCommentForm reportReviewForm,
//                                    @Valid @ModelAttribute("commentForm") final CommentForm commentForm,
//                                    @ModelAttribute("reportCommentForm") final ReportCommentForm reportCommentForm,
//                                    HttpServletRequest request) {
//        if(userName==null || userName.equals("")){
//            LOGGER.warn("Wrong username photo request:",new PageNotFoundException());
//            throw new PageNotFoundException();
//        }
//        final ModelAndView mav = new ModelAndView("profileInfoPage");
//        Optional<User> user = us.findByUserName(userName);
//        mav.addObject("userProfile",userName);
//        if(user.isPresent()) {
//            final String locale = LocaleContextHolder.getLocale().getDisplayLanguage();
//            Optional<String> quote = cs.getContentQuote(locale);
//            mav.addObject("quote", quote.get());
//            mav.addObject("reputationAmount",user.get().getReputation());
//            mav.addObject("user", user.get());
//            paginationSetup(mav,pageNum.orElse(1),rs.getAllUserReviews(user.get()));
//        } else {
//            LOGGER.warn("Wrong username profile request:",new PageNotFoundException());
//            throw new PageNotFoundException();
//        }
//        mav.addObject("canPromote",false);
//        if(userDetails != null) {
//            String userEmail = userDetails.getName();
//            User userLogged = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
//            mav.addObject("userName",userLogged.getUserName());
//            mav.addObject("userId",userLogged.getId());
//
//            rs.userLikeAndDislikeReviewsId(userLogged.getUserVotes());
//            mav.addObject("userLikeReviews", rs.getUserLikeReviews());
//            mav.addObject("userDislikeReviews", rs.getUserDislikeReviews());
//
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//            if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
//                mav.addObject("admin",true);
//                if(!user.get().getRole().equals("admin")) {
//                    mav.addObject("canPromote", true);
//                }
//            } else {
//                mav.addObject("admin",false);
//            }
//
//        } else {
//            mav.addObject("userName","null");
//            mav.addObject("userId","null");
//            mav.addObject("admin",false);
//            mav.addObject("userLikeReviews", rs.getUserLikeReviews());
//            mav.addObject("userDislikeReviews", rs.getUserDislikeReviews());
//        }
//        request.getSession().setAttribute("referer","/profile/"+userName+(pageNum.isPresent()?"/page/"+pageNum.get():""));
//        return mav;
//    }
//
//    @RequestMapping(value = "/profile/{userName:[a-zA-Z0-9\\s]+}",method = {RequestMethod.POST})
//    public ModelAndView profileInfo(@Valid @ModelAttribute("editProfile") final EditProfile editProfile,
//                                    @PathVariable("userName") final String userName,
//                                    @ModelAttribute("reportReviewForm") final ReportCommentForm reportReviewForm,
//                                    @Valid @ModelAttribute("commentForm") final CommentForm commentForm,
//                                    @ModelAttribute("reportCommentForm") final ReportCommentForm reportCommentForm
//                                    ) {
//        Optional<User> user = us.findByUserName(userName);
//        if(user.isPresent()) {
//            us.promoteUser(user.get());
//        } else {
//            LOGGER.warn("Wrong username photo request:",new PageNotFoundException());
//            throw new PageNotFoundException();
//        }
//        return new ModelAndView("redirect:/profile/" + userName);
//    }


    // * ------------------------------------------------Profile Edition------------------------------------------------
    // Endpoint para editar la nformacion del usuario
    @PUT
    @Path("/{id}/editProfile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateUserProfileInfo(@Valid EditProfileDto editProfileDto,
                                          @PathParam("id") final long id) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());

        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(PageNotFoundException::new);
        if(user.getId() != id) {
            throw new PageNotFoundException();
        }
        us.setPassword(editProfileDto.getPassword(), user, "forgotten");

//      TODO: Hay que hace un boton para cambiar solamente la imagen y otro boton para cambiar la info, ya no puede ser le mismo boton para ambos
        LOGGER.info("PUT /{}: User {} profile Updated", uriInfo.getPath(), id);
        return Response.ok().build();
    }

//    @RequestMapping(value = "/profile/editProfile", method = {RequestMethod.GET})
//    public ModelAndView profileEdition(Principal userDetails,
//                                       @Valid @ModelAttribute("editProfile") final EditProfile editProfile) {
//        String userEmail = userDetails.getName();
//        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
//        final ModelAndView mav = new ModelAndView("profileEditionPage");
//        final String locale = LocaleContextHolder.getLocale().getDisplayLanguage();
//        Optional<String> quote = cs.getContentQuote(locale);
//        mav.addObject("quote", quote.get());
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
//            mav.addObject("admin",true);
//        } else {
//            mav.addObject("admin",false);
//        }
//        mav.addObject("user", user);
//        return mav;
//    }
//
//    @RequestMapping(value = "/profile/editProfile", method = {RequestMethod.POST})
//    public ModelAndView profileEditionPost(Principal userDetails,
//                                           @Valid @ModelAttribute("editProfile") final EditProfile editProfile,
//                                           final BindingResult errors) throws IOException {
//        String userEmail = userDetails.getName();
//        User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
//
//        if(Objects.equals(editProfile.getPassword(), "") && (editProfile.getProfilePicture().getSize() > 0)) {
//            us.setProfilePicture(editProfile.getProfilePicture().getBytes(), user);
//            return new ModelAndView("redirect:/profile");
//        }
//
//        if(errors.hasErrors()) {
//            return profileEdition(userDetails,editProfile);
//        }
//
//        if(!Objects.equals(editProfile.getPassword(), "")  && ((editProfile.getProfilePicture() == null) || editProfile.getProfilePicture().getSize() <= 0 )) {
//            if(!passwordEncoder.matches(editProfile.getCurrentPassword(),user.getPassword())) {
//                errors.rejectValue("currentPassword","EditProfile.IncorrectOldPassword");
//                return profileEdition(userDetails,editProfile);
//            } else if(!editProfile.getPassword().equals(editProfile.getConfirmPassword())){
//                errors.rejectValue("confirmPassword","EditProfile.NotSamePassword");
//                return profileEdition(userDetails,editProfile);
//            }
//            us.setPassword(editProfile.getPassword(), user, "restore");
//        } else if(!Objects.equals(editProfile.getPassword(), "")  && editProfile.getProfilePicture().getSize() > 0) {
//            if(!passwordEncoder.matches(editProfile.getCurrentPassword(),user.getPassword())) {
//                errors.rejectValue("currentPassword","EditProfile.IncorrectOldPassword");
//                return profileEdition(userDetails,editProfile);
//            } else if(!editProfile.getPassword().equals(editProfile.getConfirmPassword())) {
//                errors.rejectValue("confirmPassword","EditProfile.NotSamePassword");
//                return profileEdition(userDetails,editProfile);
//            }
//            us.setPassword(editProfile.getPassword(), user, "restore");
//            us.setProfilePicture(editProfile.getProfilePicture().getBytes(), user);
//        }
//
//        return new ModelAndView("redirect:/profile");
//    }
    // * ---------------------------------------------------------------------------------------------------------------

}
