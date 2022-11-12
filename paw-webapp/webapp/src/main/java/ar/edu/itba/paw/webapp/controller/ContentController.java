package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Sorting;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.PageNotFoundException;
import ar.edu.itba.paw.webapp.form.ContentForm;
import ar.edu.itba.paw.webapp.form.ContentEditForm;
import ar.edu.itba.paw.webapp.form.GenreFilterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
public class ContentController {

    private final ContentService cs;
    private final UserService us;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    public ContentController(ContentService cs,UserService us){
        this.cs = cs;
        this.us = us;
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
    @RequestMapping(value= {"/","page/{pageNum}"})
    public ModelAndView helloWorld(Principal userDetails,
                                   @ModelAttribute("genreFilterForm") final GenreFilterForm genreFilterForm,
                                   @PathVariable("pageNum")final Optional<Integer> pageNum,
                                   HttpServletRequest request) {
        final ModelAndView mav = new ModelAndView("homePage");
        List<Content> bestRatedList = cs.getBestRated();
        List<Content> lastAddedList = cs.getLastAdded();
        if(bestRatedList.size() == 0 || lastAddedList.size() == 0) {
            LOGGER.warn("Error bringing data from the db",new PageNotFoundException());
            throw new PageNotFoundException();
        }

        mav.addObject("bestRatedList", bestRatedList);
        mav.addObject("bestRatedListSize", bestRatedList.size());
        mav.addObject("lastAddedList", lastAddedList);
        mav.addObject("lastAddedListSize", lastAddedList.size());
        mav.addObject("genre","ANY");
        mav.addObject("durationFrom","ANY");
        mav.addObject("durationTo","ANY");
        mav.addObject("sorting","ANY");
        mav.addObject("contentType", "all");
        mav.addObject("sortingTypes", Sorting.values());
        if(userDetails != null) {
            String userEmail = userDetails.getName();
            User user = us.findByEmail(userEmail).orElseThrow(PageNotFoundException::new);
            List<Long> userWatchListContentId = us.getUserWatchListContent(user);
            mav.addObject("userName", user.getUserName());
            mav.addObject("userId", user.getId());
            mav.addObject("userWatchListContentId", userWatchListContentId);
            if(userWatchListContentId.size() != 0) {
                List<Content> recommendedUserList = cs.getUserRecommended(user);
                if(recommendedUserList.size() != 0) {
                    mav.addObject("recommendedUserList", recommendedUserList);
                    mav.addObject("recommendedUserListSize", recommendedUserList.size());
                } else {
                    mav.addObject("recommendedUserList", "null");
                    List<Content> mostSavedContentByUsersList = cs.getMostUserSaved();
                    mav.addObject("mostSavedContentByUsersList", mostSavedContentByUsersList);
                    mav.addObject("mostSavedContentByUsersListSize", mostSavedContentByUsersList.size());
                }
            } else {
                List<Content> mostSavedContentByUsersList = cs.getMostUserSaved();
                mav.addObject("mostSavedContentByUsersList", mostSavedContentByUsersList);
                mav.addObject("mostSavedContentByUsersListSize", mostSavedContentByUsersList.size());
                mav.addObject("recommendedUserList", "null");
            }
            if(user.getRole().equals("admin")){
                mav.addObject("admin",true);
            } else {
                mav.addObject("admin",false);
            }
        } else {
            mav.addObject("userName", "null");
            mav.addObject("userId", "null");
            mav.addObject("admin", false);
            mav.addObject("userWatchListContentId", new ArrayList<Long>());
            List<Content> mostSavedContentByUsersList = cs.getMostUserSaved();
            mav.addObject("mostSavedContentByUsersList", mostSavedContentByUsersList);
            mav.addObject("mostSavedContentByUsersListSize", mostSavedContentByUsersList.size());
            mav.addObject("recommendedUserList", "null");
        }

        request.getSession().setAttribute("referer",pageNum.isPresent()?"/page/"+pageNum.get():"/");
        return mav;
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Get img from database -----------------------------------------------------
    @RequestMapping(path = "/contentImage/{contentId:[0-9]+}", method = RequestMethod.GET, produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] contentImage(@PathVariable("contentId") final Long contentId) {
        if(contentId==null || contentId < 0){
            LOGGER.warn("Invalid",new PageNotFoundException());
            throw new PageNotFoundException();
        }

        Content content = cs.findById(contentId).orElseThrow(PageNotFoundException::new);
        return content.getImage();
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------------Content Editing--------------------------------------------------------
    @RequestMapping(value = "/content/editInfo/{contentId:[0-9]+}", method = {RequestMethod.GET})
    public ModelAndView editContent(Principal userDetails,
                                    @ModelAttribute("contentEditForm") final ContentEditForm contentEditForm,
                                    @PathVariable("contentId")final long contentId) {
        final ModelAndView mav = new ModelAndView("contentCreatePage");
        Optional<Content> oldContent = cs.findById(contentId);
        if(!oldContent.isPresent()){
            throw new PageNotFoundException();
        }
        mav.addObject("create",false);
        mav.addObject("contentId",contentId);
        mav.addObject("type","profile");
        contentEditForm.setName(oldContent.get().getName());
        contentEditForm.setCreator(oldContent.get().getCreator());
        contentEditForm.setDescription(oldContent.get().getDescription());
        //Para obtener la duracion en minutos
        contentEditForm.setDuration(transformDate(oldContent.get().getDuration()));
        contentEditForm.setGenre(oldContent.get().getGenre());
        contentEditForm.setReleaseDate(oldContent.get().getReleased());
        contentEditForm.setType(oldContent.get().getType());
        HeaderSetUp(mav,userDetails);
        return mav;
    }

    @RequestMapping(value = "/content/editInfo/{contentId:[0-9]+}", method = {RequestMethod.POST})
    public ModelAndView editContent(Principal userDetails,
                                    @Valid @ModelAttribute("contentEditForm") final ContentEditForm contentEditForm,
                                    @PathVariable("contentId")final long contentId,
                                    final BindingResult errors,
                                    HttpServletRequest request) throws  IOException {
        if(errors.hasErrors()) {
            return editContent(userDetails, contentEditForm,contentId);
        }
        cs.updateContent(contentId,contentEditForm.getName(), contentEditForm.getDescription(), contentEditForm.getReleaseDate(), contentEditForm.getGenre(), contentEditForm.getCreator(), contentEditForm.getDuration(), contentEditForm.getType(),contentEditForm.getContentPicture().getBytes());
        String referer = request.getSession().getAttribute("referer").toString();
        return new ModelAndView("redirect:" + (referer==null?"/":referer));
    }

    // * ----------------------------------- Content Delete ------------------------------------------------------------
    @RequestMapping(value = "/content/{contentId:[0-9]+}/delete", method = {RequestMethod.POST})
    public ModelAndView deleteContent(@PathVariable("contentId")final long contentId){
        Content oldContent = cs.findById(contentId).orElseThrow(PageNotFoundException::new);
        cs.deleteContent(contentId);
        return new ModelAndView("redirect:/");
    }
    // * ---------------------------------------------------------------------------------------------------------------


    // * ----------------------------------- Content Creation ----------------------------------------------------------
    @RequestMapping(value = "/content/create",method = {RequestMethod.GET})
    public ModelAndView createContent(Principal userDetails,
                                      @ModelAttribute("contentCreate") final ContentForm contentForm,
                                      @ModelAttribute("genreFilterForm") final GenreFilterForm genreFilterForm) {
        final ModelAndView mav = new ModelAndView("contentCreatePage");
        mav.addObject("create",true);
        mav.addObject("type","profile");
        HeaderSetUp(mav,userDetails);
        return mav;
    }

    @RequestMapping(value = "/content/create",method = {RequestMethod.POST})
    public ModelAndView createContent(Principal userDetails,
                                      @Valid @ModelAttribute("contentCreate") final ContentForm contentForm,
                                      @ModelAttribute("genreFilterForm") final GenreFilterForm genreFilterForm,
                                      final BindingResult errors) throws IOException {
        if(errors.hasErrors()) {
            return createContent(userDetails,contentForm,genreFilterForm);
        }

        String auxGenre = "";
        for(String genre : contentForm.getGenre()) {
            auxGenre = auxGenre + " " + genre;
        }

        cs.contentCreate(contentForm.getName(),contentForm.getDescription(),contentForm.getReleaseDate(),auxGenre,contentForm.getCreator(),contentForm.getDuration(),contentForm.getType(),contentForm.getContentPicture().getBytes());
        Optional<Content> newContent = cs.findByName(contentForm.getName());
        return new ModelAndView("redirect:/" + newContent.get().getType() + "/" + newContent.get().getId());
    }
    // * ---------------------------------------------------------------------------------------------------------------

}
