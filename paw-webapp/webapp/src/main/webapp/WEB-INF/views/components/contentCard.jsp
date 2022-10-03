<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="W-movie-card-size">
    <a id="mainAtag2" class="card-group W-card-text W-films-margin" href="<c:url value='/${param.contentType}/${param.contentId}'/>">
        <div class="col">
            <div class="card W-films-card-body W-more-style">
                <div class="W-img-watchList-button-div">
                    <div class="d-grid gap-2 W-watchList-button-div">
                        <c:choose>
                            <c:when test="${param.userName != 'null' && !param.userWatchListContentId1}">
                                <form id="<c:out value="form${param.contentId}"/>" method="post" action="<c:url value="/watchList/add/${param.contentId}"/>">
                                    <button class="btn btn-secondary W-watchList-button" type="submit" onclick="document.getElsi ementById('mainAtag2').click(function (e) {e.stopPropagation();this.disabled = true; this.form.submit()})">
                                        <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                                            <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"/>
                                            <path d="M8 4a.5.5 0 0 1 .5.5V6H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V7H6a.5.5 0 0 1 0-1h1.5V4.5A.5.5 0 0 1 8 4z"/>
                                        </svg>
                                    </button>
                                </form>
                            </c:when>
                            <c:when test="${param.userName != 'null' && param.userWatchListContentId1}">
                                <form id="<c:out value="form${param.contentId}"/>" method="post" action="<c:url value="/watchList/delete/${param.contentId}"/>">
                                    <button class="btn btn-secondary W-watchList-button" type="submit" onclick="document.getElementById('mainAtag2').click(function (e) {e.stopPropagation();this.disabled = true; this.form.submit()})">
                                        <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                                            <path fill-rule="evenodd" d="M2 15.5V2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.74.439L8 13.069l-5.26 2.87A.5.5 0 0 1 2 15.5zM6.854 5.146a.5.5 0 1 0-.708.708L7.293 7 6.146 8.146a.5.5 0 1 0 .708.708L8 7.707l1.146 1.147a.5.5 0 1 0 .708-.708L8.707 7l1.147-1.146a.5.5 0 0 0-.708-.708L8 6.293 6.854 5.146z"/>
                                        </svg>
                                    </button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form id="<c:out value="login${param.contentId}"/>" method="post" action="<c:url value="/go/to/login"/>">
                                    <button class="btn btn-secondary W-watchList-button" type="submit" onclick="document.getElementById('mainAtag').click(function (e) {e.stopPropagation();this.disabled = true; this.form.submit()})">
                                        <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                                            <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"/>
                                            <path d="M8 4a.5.5 0 0 1 .5.5V6H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V7H6a.5.5 0 0 1 0-1h1.5V4.5A.5.5 0 0 1 8 4z"/>
                                        </svg>
                                    </button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <img src="<c:url value="/contentImage/${param.contentId}"/>" class="card-img-top" alt="Image <c:out value="${param.contentName}"/>">
                </div>

                <div class="card-body W-films-card-body-div">
                    <div>
                        <h4 class="card-title W-movie-title"><c:out value="${param.contentName}"/></h4>
                        <p class="card-text W-movie-description W-card-details-margin"><span class="W-span-text-info-card-movie W-card-details-color"><spring:message code="Content.Released"/> </span><c:out value="${param.contentReleased}"/></p>
                        <p class="card-text W-movie-description W-card-details-margin"><span class="W-span-text-info-card-movie W-card-details-color"><spring:message code="Content.Genre"/> </span><c:out value="${param.contentGenre}"/></p>
                        <p class="card-text W-movie-description W-card-details-margin"><span class="W-span-text-info-card-movie W-card-details-color"><spring:message code="Content.Creator"/> </span> <c:out value="${param.contentCreator}"/></p>
                        <c:forEach  begin="1" step="1" end="5" var="var">
                            <c:choose>
                                <c:when test="${param.contentRating >= var}">
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star-fill W-stars-width" viewBox="0 0 16 16">
                                        <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                                    </svg>
                                </c:when>
                                <c:otherwise>
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star W-stars-width" viewBox="0 0 16 16">
                                        <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                                    </svg>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <p class="card-text W-movie-description W-card-details-margin"><spring:message code="Content.ReviewAmount" arguments="${param.reviewsAmount}"/></p>
                    </div>
                </div>
            </div>
        </div>
    </a>
</div>
