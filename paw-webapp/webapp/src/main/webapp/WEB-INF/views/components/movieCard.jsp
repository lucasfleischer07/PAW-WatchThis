<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<div class="W-movie-card-size">
    <a class="card-group W-card-text W-films-margin" href="<c:url value='/movie/${param.movieId}'/>">
        <div class="col">
            <div class="card W-films-card-body" style="display: flex; align-items: center">
                <img src="<c:url value="${param.movieImage}"/>" class="card-img-top" alt="Image <c:out value="${param.movieName}"/>">
                <div class="card-body">
                    <h4 class="card-title W-movie-title"><c:out value="${param.movieName}"/></h4>
                    <p class="card-text W-movie-description W-card-details-margin"><span class="W-span-text-info-card-movie W-card-details-color">Released: </span><c:out value="${param.movieReleased}"/></p>
                    <p class="card-text W-movie-description W-card-details-margin"><span class="W-span-text-info-card-movie W-card-details-color">Genre: </span><c:out value="${param.movieGenre}"/></p>
                    <p class="card-text W-movie-description W-card-details-margin"><span class="W-span-text-info-card-movie W-card-details-color">Creator: </span> <c:out value="${param.movieCreator}"/></p>
                </div>
            </div>
        </div>
    </a>
</div>
