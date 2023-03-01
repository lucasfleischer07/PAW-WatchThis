package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.PageWrapper;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.dto.request.EditProfileDto;
import ar.edu.itba.paw.webapp.dto.request.NewUser;
import ar.edu.itba.paw.webapp.dto.response.ReviewDto;
import ar.edu.itba.paw.webapp.dto.response.UserDto;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("users")
@Component
public class UserController {
    @Autowired
    private UserService us;
    @Autowired
    private ReviewService rs;
    @Context
    private UriInfo uriInfo;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final int REVIEW_AMOUNT = 3;


    // * ----------------------------------------------- User POST -----------------------------------------------------
    // Endpoint para crear un usuario
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response userCreate(@Valid final NewUser newUser) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());
        final User user = us.register(newUser.getEmail(), newUser.getUsername(), newUser.getPassword()).orElseThrow(UserNotFoundException::new);
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
        final User user = us.findById(id).orElseThrow(UserNotFoundException::new);
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
        final User user = us.findById(id).orElseThrow(UserNotFoundException::new);
        PageWrapper<Review> reviewList = rs.getAllUserReviews(user,page,REVIEW_AMOUNT);
        Collection<ReviewDto> reviewDtoList = ReviewDto.mapReviewToReviewDto(uriInfo, reviewList.getPageContent());
        LOGGER.info("GET /{}: User {} reviews returned with success",  uriInfo.getPath(), id);
        return Response.ok(new GenericEntity<Collection<ReviewDto>>(reviewDtoList){}).build();
    }

    // * ---------------------------------------------------------------------------------------------------------------

    // * ----------------------------------------------- User Image ----------------------------------------------------

    // Endpoint para getear la imagen del usuario
    @GET
    @Produces(value = {"image/*", MediaType.APPLICATION_JSON})
    @Path("/{id}/profileImage")
    public Response getUserProfileImage(@PathParam("id") final long id,
                                        @Context Request request) {
        LOGGER.info("GET /{}: Called", uriInfo.getPath());
        final User user = us.findById(id).orElseThrow(UserNotFoundException::new);
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
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        if(user.getId() != id) {
            throw new ForbiddenException();
        }

        us.setProfilePicture(imageBytes, user);
        LOGGER.info("PUT /{}: User {} Profile Image Updated", uriInfo.getPath(), id);
        return Response.noContent().contentLocation(UserDto.getUserUriBuilder(user, uriInfo).path("profileImage").build()).build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ------------------------------------------------Profile Edition------------------------------------------------
    // Endpoint para editar la informacion del usuario
    @PUT
    @Path("/{id}/editProfile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateUserProfileInfo(@Valid EditProfileDto editProfileDto,
                                          @PathParam("id") final long id) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());

        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        if(user.getId() != id) {
            throw new ForbiddenException();
        }
        us.setPassword(editProfileDto.getPassword(), user, "restore");

//      TODO: Hay que hace un boton para cambiar solamente la imagen y otro boton para cambiar la info, ya no puede ser le mismo boton para ambos
        LOGGER.info("PUT /{}: User {} profile Updated", uriInfo.getPath(), id);
        return Response.ok().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------

    // * ------------------------------------------------Forgot Password (desde el login)-------------------------------
    @Path("/login/{email}/forgotPassword")
    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response loginForgotPassword(@PathParam("email") final String email) {
        User user = us.findByEmail(email).orElseThrow(UserNotFoundException::new);
        us.setPassword(null, user, "forgotten");
        return Response.noContent().build();
    }
    // * ---------------------------------------------------------------------------------------------------------------

}
