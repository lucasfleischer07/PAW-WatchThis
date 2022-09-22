<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
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
<%--                    <c:if test="${param.contentRating >= 1}">--%>
                        <c:forEach  begin="1" step="1" end="5" var="var">
                            <c:choose>
                                <c:when test="${param.contentRating >= var}">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                                        <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                                    </svg>
                                </c:when>
                                <c:otherwise>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star" viewBox="0 0 16 16">
                                        <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                                    </svg>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
<%--                    </c:if>--%>
                    <p class="card-text W-movie-description W-card-details-margin">(<c:out value="${param.reviewsAmount}"/> reviews)</p>
                    <div class="d-grid gap-2">
                        <button class="btn btn-secondary W-watchList-button" type="button">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                                <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"/>
                                <path d="M8 4a.5.5 0 0 1 .5.5V6H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V7H6a.5.5 0 0 1 0-1h1.5V4.5A.5.5 0 0 1 8 4z"/>
                            </svg>Add to watch list</button>
                    </div>
                </div>
            </div>
        </div>
    </a>
</div>
