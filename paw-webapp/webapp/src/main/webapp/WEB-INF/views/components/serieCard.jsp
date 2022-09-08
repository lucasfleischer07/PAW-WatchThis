<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<div class="W-movie-card-size">
    <a class="card-group W-card-text W-films-margin" href="<c:url value='/serie/${param.serieId}'/>">
        <div class="col">
            <div class="card" style="display: flex; align-items: center">
                <img src="<c:url value="${param.serieImage}"/>" class="card-img-top" alt="Image <c:out value="${param.serieName}"/>">
                <div class="card-body">
                    <h4 class="card-title W-movie-title"><c:out value="${param.serieName}"/></h4>
                    <p class="card-text W-movie-description W-card-details-margin"><span class="W-span-text-info-card-movie W-card-details-color">Released: </span><c:out value="${param.serieReleased}"/></p>
                    <p class="card-text W-movie-description W-card-details-margin"><span class="W-span-text-info-card-movie W-card-details-color">Genre: </span><c:out value="${param.serieGenre}"/></p>
                    <p class="card-text W-movie-description W-card-details-margin"><span class="W-span-text-info-card-movie W-card-details-color">Creator: </span> <c:out value="${param.serieCreator}"/></p>
                </div>
            </div>
        </div>
    </a>
</div>
