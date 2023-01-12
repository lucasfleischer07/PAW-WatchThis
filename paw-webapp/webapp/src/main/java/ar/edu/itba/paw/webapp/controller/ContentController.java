package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Sorting;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.dto.response.ContentDto;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.ContentForm;
import ar.edu.itba.paw.webapp.form.ContentEditForm;
import ar.edu.itba.paw.webapp.form.GenreFilterForm;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Path("/")
@Component
public class ContentController {
    @Autowired
    private ContentService cs;
    @Autowired
    private UserService us;
    @Context
    UriInfo uriInfo;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentController.class);

//    TODO: VER QUE HACER CON ESTO
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

    private int transformDate(String str){
        int minutes = 0;
        String[] arr= str.split(" ");
        if(arr.length==4){
            minutes=Integer.parseInt(arr[0])*60+Integer.parseInt(arr[2]);
        } else if(arr.length==2) {
            minutes=Integer.parseInt(arr[0]);
        }
        return minutes;
    }

    // * ----------------------------------- Home Page -----------------------------------------------------------------
    // Endpoint para getear el contenido de la home page
    @GET
    @Path("{contentType}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getContentByType(@PathParam("contentType") final String contentType) {
//        TODO: VER QUE PASARLE ACA COMO USER
        Optional<User> user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(!user.isPresent()) {
            user = Optional.empty();
        }

        if(Objects.equals(contentType, "bestRated")) {
            List<Content> bestRatedList = cs.getBestRated();
            if(bestRatedList.size() == 0) {
                LOGGER.warn("GET /{}: Error bringing {} data from the db", uriInfo.getPath(), contentType, new PageNotFoundException());
                throw new PageNotFoundException();
            }
            LOGGER.info("GET /{}: Bringing {} data from the db succeeded", uriInfo.getPath(), contentType);
            return Response.ok(ContentDto.mapContentToContentDto(uriInfo, bestRatedList, user.get())).build();

        } else if(Objects.equals(contentType, "lastAdded")) {
            List<Content> lastAddedList = cs.getLastAdded();
            if(lastAddedList.size() == 0) {
                LOGGER.warn("GET /{}: Error bringing {} data from the db", uriInfo.getPath(), contentType, new PageNotFoundException());
                throw new PageNotFoundException();
            }
            LOGGER.info("GET /{}: Bringing {} data from the db succeeded", uriInfo.getPath(), contentType);
            return Response.ok(ContentDto.mapContentToContentDto(uriInfo, lastAddedList, user.get())).build();
        } else if(Objects.equals(contentType, "recommendedUser")) {
            if(!user.isPresent()) {
                List<Content> mostSavedContentByUsersList = cs.getMostUserSaved();
                LOGGER.info("GET /{}: Bringing {} data from the db succeeded", uriInfo.getPath(), contentType);
                return Response.ok(ContentDto.mapContentToContentDto(uriInfo, mostSavedContentByUsersList, user.get())).build();
            } else {
                List<Long> userWatchListContentId = us.getUserWatchListContent(user.get());
                if(userWatchListContentId.size() != 0) {
                    List<Content> recommendedUserList = cs.getUserRecommended(user.get());
                    if (recommendedUserList.size() == 0) {
                        List<Content> mostSavedContentByUsersList = cs.getMostUserSaved();
                        LOGGER.info("GET /{}: Bringing {} data from the db succeeded", uriInfo.getPath(), contentType);
                        return Response.ok(ContentDto.mapContentToContentDto(uriInfo, mostSavedContentByUsersList, user.get())).build();
                    } else {
                        LOGGER.info("GET /{}: Bringing {} data from the db succeeded", uriInfo.getPath(), contentType);
                        return Response.ok(ContentDto.mapContentToContentDto(uriInfo, recommendedUserList, user.get())).build();
                    }
                } else {
                    List<Content> mostSavedContentByUsersList = cs.getMostUserSaved();
                    LOGGER.info("GET /{}: Bringing {} data from the db succeeded", uriInfo.getPath(), contentType);
                    return Response.ok(ContentDto.mapContentToContentDto(uriInfo, mostSavedContentByUsersList, user.get())).build();
                }
            }
        }
        LOGGER.warn("GET /{}: Error bringing {} data from the db", uriInfo.getPath(), contentType, new PageNotFoundException());
        return null;
    }


//    @RequestMapping(value= {"/","page/{pageNum}"})
//    public ModelAndView helloWorld(Principal userDetails,
//                                   @ModelAttribute("genreFilterForm") final GenreFilterForm genreFilterForm,
//                                   @PathVariable("pageNum")final Optional<Integer> pageNum,
//                                   HttpServletRequest request) {
//        final ModelAndView mav = new ModelAndView("homePage");
//        List<Content> bestRatedList = cs.getBestRated();
//        List<Content> lastAddedList = cs.getLastAdded();
//        if(bestRatedList.size() == 0 || lastAddedList.size() == 0) {
//            LOGGER.warn("Error bringing data from the db",new PageNotFoundException());
//            throw new PageNotFoundException();
//        }
//
//        mav.addObject("bestRatedList", bestRatedList);
//        mav.addObject("bestRatedListSize", bestRatedList.size());
//        mav.addObject("lastAddedList", lastAddedList);
//        mav.addObject("lastAddedListSize", lastAddedList.size());
//        mav.addObject("genre","ANY");
//        mav.addObject("durationFrom","ANY");
//        mav.addObject("durationTo","ANY");
//        mav.addObject("sorting","ANY");
//        mav.addObject("contentType", "all");
//        mav.addObject("contentType2", "all");
//        mav.addObject("sortingTypes", Sorting.values());
//        if(userDetails != null) {
//            String userEmail = userDetails.getName();
//            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
//            List<Long> userWatchListContentId = us.getUserWatchListContent(user);
//            mav.addObject("userName", user.getUserName());
//            mav.addObject("userId", user.getId());
//            mav.addObject("userWatchListContentId", userWatchListContentId);
//            if(userWatchListContentId.size() != 0) {
//                List<Content> recommendedUserList = cs.getUserRecommended(user);
//                if(recommendedUserList.size() != 0) {
//                    mav.addObject("recommendedUserList", recommendedUserList);
//                    mav.addObject("recommendedUserListSize", recommendedUserList.size());
//                } else {
//                    mav.addObject("recommendedUserList", "null");
//                    List<Content> mostSavedContentByUsersList = cs.getMostUserSaved();
//                    mav.addObject("mostSavedContentByUsersList", mostSavedContentByUsersList);
//                    mav.addObject("mostSavedContentByUsersListSize", mostSavedContentByUsersList.size());
//                }
//            } else {
//                List<Content> mostSavedContentByUsersList = cs.getMostUserSaved();
//                mav.addObject("mostSavedContentByUsersList", mostSavedContentByUsersList);
//                mav.addObject("mostSavedContentByUsersListSize", mostSavedContentByUsersList.size());
//                mav.addObject("recommendedUserList", "null");
//            }
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
//                mav.addObject("admin",true);
//            } else {
//                mav.addObject("admin",false);
//            }
//        } else {
//            mav.addObject("userName", "null");
//            mav.addObject("userId", "null");
//            mav.addObject("admin", false);
//            mav.addObject("userWatchListContentId", new ArrayList<Long>());
//            List<Content> mostSavedContentByUsersList = cs.getMostUserSaved();
//            mav.addObject("mostSavedContentByUsersList", mostSavedContentByUsersList);
//            mav.addObject("mostSavedContentByUsersListSize", mostSavedContentByUsersList.size());
//            mav.addObject("recommendedUserList", "null");
//        }
//
//        request.getSession().setAttribute("referer",pageNum.isPresent()?"/page/"+pageNum.get():"/");
//        return mav;
//    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Get img from database -----------------------------------------------------
    // Endpoint para getear la imagen del contenido
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/{contentId}/contentImage")
    public Response getContentImage(@PathParam("contentId") final long contentId,
                                    @Context Request request) {
        Content content = cs.findById(contentId).orElseThrow(PageNotFoundException::new);
        EntityTag eTag = new EntityTag(String.valueOf(content.getId()));
        final CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);
        Response.ResponseBuilder response = request.evaluatePreconditions(eTag);

        if (response == null) {
            final byte[] contentImage = content.getImage();
            response = Response.ok(contentImage).tag(eTag);
        }

        LOGGER.info("GET /{}: Content {} Image", uriInfo.getPath(), contentId);
        return response.cacheControl(cacheControl).build();
    }

    //Endpoint para editar la imagen del contenido
    @PUT
    @Path("/{contentId}/contentImage")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateContentImage(@Size(max = 1024 * 1024 * 2) @FormDataParam("image") byte[] imageBytes,
                                           @PathParam("contentId") final long contentId) {

        Content content = cs.findById(contentId).orElseThrow(PageNotFoundException::new);

        cs.updateContent(content.getId(), content.getName(), content.getDescription(), content.getReleased(), content.getGenre(), content.getCreator(), content.getDurationNum(), content.getType(), imageBytes);
        LOGGER.info("PUT /{}: Content {} Image Updated", uriInfo.getPath(), contentId);
        return Response.noContent().contentLocation(uriInfo.getAbsolutePathBuilder().path(String.valueOf(content.getId())).path("contentImage").build()).build();
    }


//    @RequestMapping(path = "/contentImage/{contentId:[0-9]+}", method = RequestMethod.GET, produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
//    @ResponseBody
//    public byte[] contentImage(@PathVariable("contentId") final Long contentId) {
//        if(contentId==null || contentId < 0){
//            LOGGER.warn("Invalid",new PageNotFoundException());
//            throw new PageNotFoundException();
//        }
//
//        Content content = cs.findById(contentId).orElseThrow(PageNotFoundException::new);
//        return content.getImage();
//    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------------Content Editing--------------------------------------------------------
    //Endpoint para editar el contenido
    @PUT
    @Path("/content/editInfo/{contentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateContentInfo(@Size(max = 1024 * 1024 * 2) @FormDataParam("image") byte[] imageBytes,
                                      @PathParam("contentId") final long contentId,
                                      @Valid ContentDto contentDto,
                                      @FormDataParam("images") FormDataBodyPart image) {

        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(PageNotFoundException::new);
        final Content content = cs.findById(contentId).orElseThrow(PageNotFoundException::new);

//        TODO: VER ESTO SI ERA ADMIN O QUE
        if(!Objects.equals(user.getRole(), "ADMIN")) {
            throw new PageNotFoundException();
        }

//        TODO: NOSE BIEN COMO SERIA ESTO, HAY QUE VERLO MEJOR

        cs.updateContent(contentDto.getId(), contentDto.getName(), contentDto.getDescription(), contentDto.getReleaseDate(), contentDto.getGenre(), contentDto.getCreator(), contentDto.getDurationNum(), contentDto.getType(), contentDto.getContentPicture());
        LOGGER.info("PUT /{}: Content {} Info Updated", uriInfo.getPath(), contentId);
        return Response.ok().build();
//        return Response.noContent().contentLocation(uriInfo.getAbsolutePathBuilder().path(String.valueOf(content.getId())).path("profileImage").build()).build();
    }

//    @RequestMapping(value = "/content/editInfo/{contentId:[0-9]+}", method = {RequestMethod.GET})
//    public ModelAndView editContent(Principal userDetails,
//                                    @ModelAttribute("contentEditForm") final ContentEditForm contentEditForm,
//                                    @PathVariable("contentId")final long contentId) {
//        final ModelAndView mav = new ModelAndView("contentCreatePage");
//        Optional<Content> oldContent = cs.findById(contentId);
//        if(!oldContent.isPresent()){
//            throw new PageNotFoundException();
//        }
//        mav.addObject("create",false);
//        mav.addObject("contentId",contentId);
//        mav.addObject("type","profile");
//        contentEditForm.setName(oldContent.get().getName());
//        contentEditForm.setCreator(oldContent.get().getCreator());
//        contentEditForm.setDescription(oldContent.get().getDescription());
//        contentEditForm.setDuration(transformDate(oldContent.get().getDuration()));
////        contentEditForm.setGenre(oldContent.get().getGenre());
//        String[] newGenre = oldContent.get().getGenre().split(" ");
//        contentEditForm.setGenre(newGenre);
//        contentEditForm.setReleaseDate(oldContent.get().getReleased());
//        contentEditForm.setType(oldContent.get().getType());
//        HeaderSetUp(mav,userDetails);
//        return mav;
//    }
//
//    @RequestMapping(value = "/content/editInfo/{contentId:[0-9]+}", method = {RequestMethod.POST})
//    public ModelAndView editContent(Principal userDetails,
//                                    @Valid @ModelAttribute("contentEditForm") final ContentEditForm contentEditForm,
//                                    final BindingResult errors,
//                                    @PathVariable("contentId")final long contentId,
//                                    HttpServletRequest request) throws  IOException {
//        if(errors.hasErrors()) {
//            return editContent(userDetails, contentEditForm,contentId);
//        }
//
//        String auxGenre = "";
//        for(String genre : contentEditForm.getGenre()) {
//            auxGenre = auxGenre + " " + genre;
//        }
//
//        cs.updateContent(contentId,contentEditForm.getName(), contentEditForm.getDescription(), contentEditForm.getReleaseDate(), auxGenre, contentEditForm.getCreator(), contentEditForm.getDuration(), contentEditForm.getType(),contentEditForm.getContentPicture().getBytes());
//        String referer = request.getSession().getAttribute("referer").toString();
//        return new ModelAndView("redirect:" + (referer==null?"/":referer));
//    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Content Delete ------------------------------------------------------------
    @DELETE
    @Path("/{contentId}/deleteContent")
    public Response deleteContent(@PathParam("contentId") final long contentId) {
        Content oldContent = cs.findById(contentId).orElseThrow(PageNotFoundException::new);
        cs.deleteContent(contentId);
        LOGGER.info("DELETE /{}: Content {} deleted", uriInfo.getPath(), contentId);
        return Response.noContent().build();
    }

//    @RequestMapping(value = "/content/{contentId:[0-9]+}/delete", method = {RequestMethod.POST})
//    public ModelAndView deleteContent(@PathVariable("contentId")final long contentId){
//        Content oldContent = cs.findById(contentId).orElseThrow(PageNotFoundException::new);
//        cs.deleteContent(contentId);
//        return new ModelAndView("redirect:/");
//    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Content Creation ----------------------------------------------------------
    // Endpoint para crear un contenido
//    TODO: VER BIEN
    @POST
    @Path("/{contentId}/deleteContent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createContent(@PathParam("contentId") final long contentId,
                                  @FormDataParam("images") FormDataBodyPart img,
                                  @Valid ContentDto contentDto) {
        Content newContent = cs.contentCreate(contentDto.getName(),contentDto.getDescription(),contentDto.getReleaseDate(),auxGenre,contentDto.getCreator(),contentDto.getDurationNum(),contentDto.getType(),contentDto.getContentPicture());
        return Response.created(ContentDto.getContentUriBuilder(newContent, uriInfo).build()).build();
    }
    
//    @RequestMapping(value = "/content/create",method = {RequestMethod.GET})
//    public ModelAndView createContent(Principal userDetails,
//                                      @ModelAttribute("contentCreate") final ContentForm contentForm) {
//        final ModelAndView mav = new ModelAndView("contentCreatePage");
//        mav.addObject("create",true);
//        mav.addObject("type","profile");
//        HeaderSetUp(mav,userDetails);
//        return mav;
//    }

//    @RequestMapping(value = "/content/create",method = {RequestMethod.POST})
//    public ModelAndView createContent(Principal userDetails,
//                                      @Valid @ModelAttribute("contentCreate") final ContentForm contentForm,
//                                      final BindingResult contentFormErrors) throws IOException {
//        if(contentFormErrors.hasErrors()) {
//            return createContent(userDetails,contentForm);
//        }
//
//        String auxGenre = "";
//        for(String genre : contentForm.getGenre()) {
//            auxGenre = auxGenre + " " + genre;
//        }
//
//        Content newContent=cs.contentCreate(contentForm.getName(),contentForm.getDescription(),contentForm.getReleaseDate(),auxGenre,contentForm.getCreator(),contentForm.getDuration(),contentForm.getType(),contentForm.getContentPicture().getBytes());
//        return new ModelAndView("redirect:/" + newContent.getType() + "/" + newContent.getId());
//
//    }

    // * ---------------------------------------------------------------------------------------------------------------

}
