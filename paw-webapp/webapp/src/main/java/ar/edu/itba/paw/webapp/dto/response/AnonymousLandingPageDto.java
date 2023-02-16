package ar.edu.itba.paw.webapp.dto.response;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;

import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.List;

public class AnonymousLandingPageDto {
    private Collection<ContentDto> bestRatedList;
    private Collection<ContentDto> lastAddedList;
    private Collection<ContentDto> mostSavedContentByUsersList;

    public AnonymousLandingPageDto() {
    }

    public AnonymousLandingPageDto(UriInfo uriInfo, List<List<Content>> landindPageContentList) {
        this.bestRatedList = ContentDto.mapContentToContentDto(uriInfo, landindPageContentList.get(0));
        this.lastAddedList = ContentDto.mapContentToContentDto(uriInfo, landindPageContentList.get(1));
        this.mostSavedContentByUsersList = ContentDto.mapContentToContentDto(uriInfo, landindPageContentList.get(2));
    }

    public Collection<ContentDto> getBestRatedList() {
        return bestRatedList;
    }

    public void setBestRatedList(Collection<ContentDto> bestRatedList) {
        this.bestRatedList = bestRatedList;
    }

    public Collection<ContentDto> getLastAddedList() {
        return lastAddedList;
    }

    public void setLastAddedList(Collection<ContentDto> lastAddedList) {
        this.lastAddedList = lastAddedList;
    }

    public Collection<ContentDto> getMostSavedContentByUsersList() {
        return mostSavedContentByUsersList;
    }

    public void setMostSavedContentByUsersList(Collection<ContentDto> mostSavedContentByUsersList) {
        this.mostSavedContentByUsersList = mostSavedContentByUsersList;
    }
}
