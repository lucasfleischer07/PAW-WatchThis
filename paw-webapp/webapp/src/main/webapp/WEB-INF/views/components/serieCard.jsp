<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<div class="W-movie-card-size">
    <a class="card-group W-card-text W-films-margin" href="<c:url value='/serie/${param.serieId}'/>">
        <div class="col">
            <div class="card" style="display: flex; align-items: center">
                <img src="<c:url value="${param.serieImage}"/>" class="card-img-top" alt="Image <c:out value="${param.serieName}"/>">
                <div class="card-body">
                    <h5 class="card-title W-movie-title"><c:out value="${param.serieName}"/></h5>
                    <p class="card-text W-movie-description"><c:out value="${param.serieDescription}"/></p>
                </div>
            </div>
        </div>
    </a>
</div>
