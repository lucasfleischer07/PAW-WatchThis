<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<div class="W-movie-card-size">
    <a class="card-group W-card-text W-films-margin" href="<c:url value='/movie/${param.movieId}'/>">
        <div class="col">
            <div class="card" style="display: flex; align-items: center">
                <img src="<c:url value="${param.movieImage}"/>" class="card-img-top" alt="Image <c:out value="${param.movieName}"/>">
                <div class="card-body">
                    <h5 class="card-title"><c:out value="${param.movieName}"/></h5>
                    <p class="card-text"><c:out value="${param.movieDescription}"/></p>
                </div>
            </div>
        </div>
    </a>
</div>
