<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<div class="W-movie-card-size">
    <a class="card-group W-card-text W-films-margin" href="<c:url value='/${param.contentType}/${param.contentId}'/>">
        <div class="col">
            <div class="card W-films-card-body" style="display: flex; align-items: center">
                <img src="<c:url value="${param.contentImage}"/>" class="card-img-top" alt="Image <c:out value="${param.contentName}"/>">
                <div class="card-body">
                    <h4 class="card-title W-movie-title"><c:out value="${param.contentName}"/></h4>
                    <p class="card-text W-movie-description W-card-details-margin"><span class="W-span-text-info-card-movie W-card-details-color">Released: </span><c:out value="${param.contentReleased}"/></p>
                    <p class="card-text W-movie-description W-card-details-margin"><span class="W-span-text-info-card-movie W-card-details-color">Genre: </span><c:out value="${param.contentGenre}"/></p>
                    <p class="card-text W-movie-description W-card-details-margin"><span class="W-span-text-info-card-movie W-card-details-color">Creator: </span> <c:out value="${param.contentCreator}"/></p>
                </div>
            </div>
        </div>
    </a>
</div>
