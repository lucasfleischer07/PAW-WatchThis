package ar.edu.itba.paw.webapp.utilities;

import ar.edu.itba.paw.models.PageWrapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class ResponseBuildingUtils {

    private ResponseBuildingUtils() {
        throw new AssertionError();
    }

    public static <T> void setPaginationLinks(Response.ResponseBuilder response, PageWrapper<T> pageContainer, UriInfo uriInfo) {
        if (pageContainer.hasNextPage()) {
            response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", pageContainer.getPage() + 1).build().toString(), "next");
        }
        if (pageContainer.hasPrevPage()) {
            response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", pageContainer.getPage() - 1).build().toString(), "prev");
        }
        response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", pageContainer.getPageAmount()).build().toString(), "last");
        response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", 1).build().toString(), "first");
    }
}
