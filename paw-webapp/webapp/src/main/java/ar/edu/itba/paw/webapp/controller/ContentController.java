package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.ContentNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Sorting;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.PaginationService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.dto.request.GenreFilterDto;
import ar.edu.itba.paw.webapp.dto.request.NewContentDto;
import ar.edu.itba.paw.webapp.dto.response.AnonymousLandingPageDto;
import ar.edu.itba.paw.webapp.dto.response.ContentDto;
import ar.edu.itba.paw.webapp.dto.response.UserLandingPageDto;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("content")
@Component
public class ContentController {
    @Autowired
    private ContentService cs;
    @Autowired
    private UserService us;
    @Autowired
    private PaginationService ps;
    @Context
    UriInfo uriInfo;
    private static final int CONTENT_AMOUNT = 18;
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
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getContentByType(@QueryParam("pageNumber") @DefaultValue("1") int pageNumber,
                                     @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOGGER.info("GET /{}: Called", uriInfo.getPath());
        Optional<User> user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<List<Content>> landingPageContentList;
        if(!user.isPresent()) {
            landingPageContentList = cs.getLandingPageContent(null);
            LOGGER.info("GET /{}: Returning Landing Page Content for user NULL", uriInfo.getPath());
            return Response.ok(new GenericEntity<AnonymousLandingPageDto>(new AnonymousLandingPageDto(uriInfo, landingPageContentList)){}).build();
        } else {
            landingPageContentList = cs.getLandingPageContent(user.get());
            LOGGER.info("GET /{}: Returning Landing Page Content for userId {}", uriInfo.getPath(), user.get().getId());
            return Response.ok(new GenericEntity<UserLandingPageDto>(new UserLandingPageDto(uriInfo, landingPageContentList)){}).build();
        }

//        if(Objects.equals(contentType, "bestRated")) {
//            List<Content> bestRatedList = cs.getBestRated();
//            if(bestRatedList.size() == 0) {
//                LOGGER.warn("GET /{}: Error bringing {} data from the db", uriInfo.getPath(), contentType, new PageNotFoundException());
//                throw new PageNotFoundException();
//            }
//            LOGGER.info("GET /{}: Bringing {} data from the db succeeded", uriInfo.getPath(), contentType);
//            return Response.ok(ContentDto.mapContentToContentDto(uriInfo, bestRatedList, user.get())).build();
//
//        } else if(Objects.equals(contentType, "lastAdded")) {
//            List<Content> lastAddedList = cs.getLastAdded();
//            if(lastAddedList.size() == 0) {
//                LOGGER.warn("GET /{}: Error bringing {} data from the db", uriInfo.getPath(), contentType, new PageNotFoundException());
//                throw new PageNotFoundException();
//            }
//            LOGGER.info("GET /{}: Bringing {} data from the db succeeded", uriInfo.getPath(), contentType);
//            return Response.ok(ContentDto.mapContentToContentDto(uriInfo, lastAddedList, user.get())).build();
//        } else if(Objects.equals(contentType, "recommendedUser")) {
//            if(!user.isPresent()) {
//                List<Content> mostSavedContentByUsersList = cs.getMostUserSaved();
//                LOGGER.info("GET /{}: Bringing {} data from the db succeeded", uriInfo.getPath(), contentType);
//                return Response.ok(ContentDto.mapContentToContentDto(uriInfo, mostSavedContentByUsersList, user.get())).build();
//            } else {
//                List<Long> userWatchListContentId = us.getUserWatchListContent(user.get());
//                if(userWatchListContentId.size() != 0) {
//                    List<Content> recommendedUserList = cs.getUserRecommended(user.get());
//                    if (recommendedUserList.size() == 0) {
//                        List<Content> mostSavedContentByUsersList = cs.getMostUserSaved();
//                        LOGGER.info("GET /{}: Bringing {} data from the db succeeded", uriInfo.getPath(), contentType);
//                        return Response.ok(ContentDto.mapContentToContentDto(uriInfo, mostSavedContentByUsersList, user.get())).build();
//                    } else {
//                        LOGGER.info("GET /{}: Bringing {} data from the db succeeded", uriInfo.getPath(), contentType);
//                        return Response.ok(ContentDto.mapContentToContentDto(uriInfo, recommendedUserList, user.get())).build();
//                    }
//                } else {
//                    List<Content> mostSavedContentByUsersList = cs.getMostUserSaved();
//                    LOGGER.info("GET /{}: Bringing {} data from the db succeeded", uriInfo.getPath(), contentType);
//                    return Response.ok(ContentDto.mapContentToContentDto(uriInfo, mostSavedContentByUsersList, user.get())).build();
//                }
//            }
//        }
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

    // * ----------------------------------- Get a particual content----------------------------------------------------
    // Endpoint para getear un contenido a partir de su id
    @GET
    @Path("/specificContent/{contentId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getSpecificContent(@PathParam("contentId") final long contentId) {
        LOGGER.info("GET /{}: Called", uriInfo.getPath());
        Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        ContentDto contentDto = new ContentDto(uriInfo, content);
        LOGGER.info("GET /{}: Return content {} with success", uriInfo.getPath(), content.getId());
        return Response.ok(contentDto).build();
    }

    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Get img from database -----------------------------------------------------
    // Endpoint para getear la imagen del contenido
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/{contentId}/contentImage")
    public Response getContentImage(@PathParam("contentId") final long contentId,
                                    @Context Request request) {

        LOGGER.info("GET /{}: Called", uriInfo.getPath());
        Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
        if(content.getImage() == null) {
            return Response.noContent().build();
        }

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
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateContentImage(@Size(max = 1024 * 1024 * 2) @FormDataParam("image") byte[] imageBytes,
                                       @PathParam("contentId") final long contentId) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());
        Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);

        cs.updateContent(content.getId(), content.getName(), content.getDescription(), content.getReleased(), content.getGenre(), content.getCreator(), content.getDurationNum(), content.getType(), imageBytes);
        LOGGER.info("PUT /{}: Content {} Image Updated", uriInfo.getPath(), contentId);
        return Response.noContent().contentLocation(ContentDto.getContentUriBuilder(uriInfo).path("content").path(String.valueOf(content.getId())).path("contentImage").build()).build();
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
    @Path("/editInfo/{contentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response updateContentInfo(@PathParam("contentId") final long contentId,
                                      @Valid NewContentDto contentDto,
                                      @Context final HttpServletRequest request) {
        LOGGER.info("PUT /{}: Called", uriInfo.getPath());
        final User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);
        final Content content = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);

//        TODO: VER ESTO SI ERA ADMIN O QUE
        if(!Objects.equals(user.getRole(), "admin")) {
            throw new ForbiddenException();
        }

        String auxGenre = "";
        for(String genre : contentDto.getGenre()) {
            auxGenre = auxGenre + " " + genre;
        }

//        TODO: NOSE BIEN COMO SERIA ESTO, HAY QUE VERLO MEJOR (POR EL MOMENTO, ESTA PUESTO LA IMAGEN ORIGINAL, NOSE BIEN COMO HACER PARA CAMBIAR SOLO LA IMAGEN, HAY QUE HACER UN BOTON APARTE Y ESO)
        cs.updateContent(contentId, contentDto.getName(), contentDto.getDescription(), contentDto.getReleaseDate(), auxGenre, contentDto.getCreator(), contentDto.getDuration(), contentDto.getType(), content.getImage());
        LOGGER.info("PUT /{}: Content {} Info Updated", uriInfo.getPath(), contentId);
        return Response.ok().build();
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
        LOGGER.info("DELETE /{}: Called", uriInfo.getPath());
        Content oldContent = cs.findById(contentId).orElseThrow(ContentNotFoundException::new);
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
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createContent(@Valid NewContentDto contentDto) {
        LOGGER.info("POST /{}: Called", uriInfo.getPath());
        User user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(UserNotFoundException::new);

        String auxGenre = "";
        for(String genre : contentDto.getGenre()) {
            auxGenre = auxGenre + " " + genre;
        }

        Content newContent = cs.contentCreate(contentDto.getName(),contentDto.getDescription(),contentDto.getReleaseDate(), auxGenre, contentDto.getCreator(),contentDto.getDuration(),contentDto.getType(),null);
//        TODO: Revisar bien estas URls
        return Response.created(ContentDto.getContentUriBuilder(uriInfo).path("content").path(String.valueOf(newContent.getId())).build()).build();
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

    // * ----------------------------------- Movie and Series division -------------------------------------------------
    // Endpoint para getear las peliculas o series
    @GET
    @Path("/{contentType}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getContentByType(@PathParam("contentType") final String contentType,
                                     @QueryParam("pageNumber") @DefaultValue("1") Integer pageNum,
                                     @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOGGER.info("GET /{}: Called", uriInfo.getPath());

        int page= pageNum;
        List<Content> contentList = cs.getAllContent(contentType, null);
        List<Content> contentListPaginated;
        Collection<ContentDto> contentListPaginatedDto = null;
        if(contentList == null) {
            LOGGER.warn("GET /{}: Failed at requesting content", uriInfo.getPath());
            throw new ContentNotFoundException();
        } else {
            contentListPaginated = ps.pagePagination(contentList, page,CONTENT_AMOUNT);
            contentListPaginatedDto = ContentDto.mapContentToContentDto(uriInfo, contentListPaginated);
        }

        LOGGER.info("GET /{}: Success getting the content", uriInfo.getPath());

//        TODO: El Return aca deberia ya estar paginado (Por el momento no lo esta, habria que cambairlo)
        return Response.ok(new GenericEntity<Collection<ContentDto>>(contentListPaginatedDto){}).build();

    }

    // * ---------------------------------------------------------------------------------------------------------------


    // *  ----------------------------------- Movies and Serie Filters -------------------------------------------------
    // Endpoint para filtrar el las peliculas/series
    @GET
    @Path("/{contentType}/filters")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response filterContentByType(@PathParam("contentType") final String contentType,
                                        @QueryParam("pageNumber") @DefaultValue("1") Integer pageNum,
                                        @QueryParam("pageSize") @DefaultValue("10") int pageSize,
                                        @QueryParam("durationFrom") @DefaultValue("ANY") final String durationFrom,
                                        @QueryParam("durationTo") @DefaultValue("ANY") final String durationTo,
                                        @QueryParam("sorting") final Sorting sorting,
                                        @QueryParam("query") @DefaultValue("ANY") final String query,
                                        @QueryParam("genre") final List<String> genre,
                                        @Valid GenreFilterDto genreFilterDto) {

        LOGGER.info("GET /{}: Called", uriInfo.getPath());

        int page= pageNum;
        String auxType;
        if(!Objects.equals(contentType, "movie") && !Objects.equals(contentType, "serie")) {
            auxType = "all";
        } else {
            auxType = contentType;
        }

//        TODO: VER ESTO DEL GENRE, NO SE PASABA COMO QUERYPARAM Y AHORA NI IDEA COMO SERIA. EL ORIGINAL ESTA EN EL ARCHIVO
//        TODO: MovieAndSerieController linea 76 (esta comentado)
        List<String> genreList = (genreFilterDto.getFormGenre()!=null && genreFilterDto.getFormGenre().length > 0 ) ? Arrays.asList(genreFilterDto.getFormGenre()) : genre;
        if(genreList != null){
            genreFilterDto.setFormGenre(genreList.toArray(new String[0]));
        }

        List<Content> contentListFilter = cs.getMasterContent(auxType, genre, durationFrom, durationTo, sorting, query);
        List<Content> contentListFilterPaginated = null;
        int amountOfPages;
        if(contentListFilter == null) {
            LOGGER.warn("GET /{}: Failed at requesting content", uriInfo.getPath());
            throw new ContentNotFoundException();
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
//        Optional<User> user = us.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
//        if(!user.isPresent()) {
//            user = Optional.empty();
//        }

        Collection<ContentDto> contentListFilterPaginatedDto = ContentDto.mapContentToContentDto(uriInfo, contentListFilterPaginated);

        LOGGER.info("GET /{}: Success filtering the content", uriInfo.getPath());

//        TODO: El Return aca deberia ya estar paginado (Por el momento no lo esta, habria que cambairlo)
        return Response.ok(new GenericEntity<Collection<ContentDto>>(contentListFilterPaginatedDto){}).build();

    }

    // * ---------------------------------------------------------------------------------------------------------------

}
