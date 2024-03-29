package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.dto.response.LongDto;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.PageWrapper;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.dto.request.EditProfileDto;
import ar.edu.itba.paw.webapp.dto.request.NewUser;
import ar.edu.itba.paw.webapp.dto.response.ReviewDto;
import ar.edu.itba.paw.webapp.dto.response.UserDto;
import ar.edu.itba.paw.webapp.utilities.ResponseBuildingUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.net.URLConnection;
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

    // * ----------------------------------------------- User POST -----------------------------------------------------
    // Endpoint para crear un usuario
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response userCreate(@Valid final NewUser newUser) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());
        final Optional<User> user1 = us.findByEmail(newUser.getEmail());
        if(user1.isPresent()) {
            if(Objects.equals(user1.get().getUserName(), newUser.getUsername())) {
                throw new BadRequestException("Username already register");
            } else if(Objects.equals(user1.get().getEmail(), newUser.getEmail())) {
                throw new BadRequestException("Email already register");
            }
        }
        final User user = us.register(newUser.getEmail(), newUser.getUsername(), newUser.getPassword()).orElseThrow(UserNotFoundException::new);
        UserDto userDto = new UserDto(uriInfo, user);
        LOGGER.info("POST /{}: New user created with id {}", uriInfo.getPath(), user.getId());
        return Response.created(UserDto.getUserUriBuilder(user, uriInfo).build()).entity(userDto).build();
    }

    // * ---------------------------------------------------------------------------------------------------------------

    // * ------------------------------------------------Forgot Password (desde el login)-------------------------------
    @POST
    @Produces(value = {MediaType.TEXT_PLAIN})
    @Consumes(value = {MediaType.TEXT_PLAIN})
    public Response loginForgotPassword(String email) {
        User user = us.findByEmail(email).orElseThrow(UserNotFoundException::new);
        us.setPassword(null, user);
        return Response.noContent().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------------------- User GET ------------------------------------------------------
    // Endpoint para getear la informacion del usuario
    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getUserInfo(@PathParam("id") final long id) {
        LOGGER.info("GET /{}: Called",  uriInfo.getPath());
        final User user = us.findById(id).orElseThrow(UserNotFoundException::new);
        LOGGER.info("GET /{}: User returned with success", uriInfo.getPath());
        return Response.ok(new UserDto(uriInfo, user)).build();
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

        EntityTag eTag = new EntityTag(us.getUserImageHash(user));
        final CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);
        Response.ResponseBuilder response = request.evaluatePreconditions(eTag);
        String contentType;
        try {
            contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(user.getImage()));
        } catch (Exception e) {
            contentType = "image/png";
        }
        if (response == null) {
            final byte[] userImage = user.getImage();
            response = Response.ok(userImage).cacheControl(cacheControl).type(contentType).tag(eTag);
        }
        LOGGER.info("GET /{}: User {} Profile Image", uriInfo.getPath(), id);

        return response.build();
    }

//    Endpoint para editar la imagen de perfil del usuario
    @PUT
    @Path("/{id}/profileImage")
    @Produces({"image/*", MediaType.APPLICATION_JSON,})
    @PreAuthorize("@securityChecks.checkUser(#id)")
    public Response updateUserProfileImage(@FormDataParam("image") byte[] imageBytes,
                                           @PathParam("id") final Long id) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());
        if(imageBytes == null) {
            throw new BadRequestException("Must include image data");
        }

        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        us.setProfilePicture(imageBytes, user);
        LOGGER.info("PUT /{}: User {} Profile Image Updated", uriInfo.getPath(), id);
        return Response.noContent().contentLocation(UserDto.getUserUriBuilder(user, uriInfo).path("profileImage").build()).build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ------------------------------------------------Profile Edition------------------------------------------------
    // Endpoint para editar la informacion del usuario
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    @PreAuthorize("@securityChecks.checkUser(#id)")
    public Response updateUserProfileInfo(@Valid EditProfileDto editProfileDto,
                                          @PathParam("id") final Long id) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());
        if(editProfileDto == null) {
            throw new BadRequestException("Must include edit data");
        }

        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        if(us.checkPassword(editProfileDto.getCurrentPassword(), user)) {
            us.setPassword(editProfileDto.getNewPassword(), user);
        } else {
            throw new BadRequestException();
        }

        LOGGER.info("PUT /{}: User {} profile Updated", uriInfo.getPath(), id);
        return Response.noContent().build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ------------------------------------------------Promote User---------------------------------------------------
    @PUT
    @Path("/{id}/role")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response promoteUSer(@PathParam("id") final Long id) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());
        User user2 = us.findById(id).orElseThrow(UserNotFoundException::new);
        us.promoteUser(user2);
        LOGGER.info("PUT /{}: Returning user promoted", uriInfo.getPath());
        return Response.noContent().build();
    }
}
