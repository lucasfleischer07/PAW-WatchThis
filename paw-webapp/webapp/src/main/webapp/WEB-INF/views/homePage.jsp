<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <%--        Para el Fav icon--%>
        <link rel="icon" type="image/x-icon" href="<c:url value="/resources/img/favicon.ico"/>">
        <%--        <!-- * Link de la libreria de Bootstrap -->--%>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
        <%--       * Referencia a nuestra hoja de estilos propia --%>
        <link href="<c:url value="/resources/css/homeStyles.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/css/navBarStyles.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/css/reviewsStyles.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/css/carrouselStyles.css"/>" rel="stylesheet" type="text/css"/>

        <title><spring:message code="WatchThisMessage"/></title>
    </head>

    <body>
        <jsp:include page="components/header.jsp">
            <jsp:param name="type" value="${contentType}"/>
            <jsp:param name="genre" value="${genre}"/>
            <jsp:param name="durationFrom" value="${durationFrom}"/>
            <jsp:param name="durationTo" value="${durationTo}"/>
            <jsp:param name="sorting" value="${sorting}"/>
            <jsp:param name="userName" value="${userName}"/>
            <jsp:param name="userId" value="${userId}"/>
            <jsp:param name="admin" value="${admin}"/>
        </jsp:include>

        <div class="W-column-display">
            <jsp:include page="components/filter.jsp">
                <jsp:param name="genre" value="${genre}"/>
                <jsp:param name="durationFrom" value="${durationFrom}"/>
                <jsp:param name="durationTo" value="${durationTo}"/>
                <jsp:param name="type" value="${contentType}"/>
                <jsp:param name="sorting" value="${sorting}"/>
            </jsp:include>


            <div class="W-carousels-div">
                <c:choose>
                    <c:when test="${userName != 'null' && userWatchListContentId != 'null'}">
                        <h3 class="W-carousel-1-title"><spring:message code="Content.Carousel.RecommendedForYou"/></h3>
                        <div id="carouselRecommended" class="carousel slide W-margin-carousel" data-ride="carousel">
                            <div class="carousel-inner">
                                <c:forEach begin="0" step="5" end="${recommendedUserListSize-1}" var="externalIndex">
                                    <c:choose>
                                        <c:when test="${externalIndex == 0}">
                                            <div class="carousel-item active">
                                                <div class="cards-wrapper">
                                                    <c:forEach begin="${externalIndex}" step="1" end="${recommendedUserListSize-1 < 4+externalIndex ? recommendedUserListSize-1:4+externalIndex}" var="internalIndex" >
                                                        <jsp:include page="components/carrouselContent.jsp">
                                                            <jsp:param name="contentName" value="${recommendedUserList.get(internalIndex).name}"/>
                                                            <jsp:param name="contentReleased" value="${recommendedUserList.get(internalIndex).released}"/>
                                                            <jsp:param name="contentCreator" value="${recommendedUserList.get(internalIndex).creator}"/>
                                                            <jsp:param name="contentGenre" value="${recommendedUserList.get(internalIndex).genre}"/>
                                                            <jsp:param name="contentImage" value="${recommendedUserList.get(internalIndex).image}"/>
                                                            <jsp:param name="contentId" value="${recommendedUserList.get(internalIndex).id}"/>
                                                            <jsp:param name="contentType" value="${recommendedUserList.get(internalIndex).type}"/>
                                                            <jsp:param name="contentRating" value="${recommendedUserList.get(internalIndex).rating}"/>
                                                            <jsp:param name="reviewsAmount" value="${recommendedUserList.get(internalIndex).reviewsAmount}"/>
                                                            <jsp:param name="userName" value="${userName}"/>
                                                            <jsp:param name="userWatchListContentId" value="${userWatchListContentId}"/>
                                                        </jsp:include>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="carousel-item">
                                                <div class="cards-wrapper">
                                                    <c:forEach begin="${externalIndex}" step="1" end="${recommendedUserListSize-1 < 4+externalIndex ? recommendedUserListSize-1:4+externalIndex}" var="internalIndex" >
                                                        <jsp:include page="components/carrouselContent.jsp">
                                                            <jsp:param name="contentName" value="${recommendedUserList.get(internalIndex).name}"/>
                                                            <jsp:param name="contentReleased" value="${recommendedUserList.get(internalIndex).released}"/>
                                                            <jsp:param name="contentCreator" value="${recommendedUserList.get(internalIndex).creator}"/>
                                                            <jsp:param name="contentGenre" value="${recommendedUserList.get(internalIndex).genre}"/>
                                                            <jsp:param name="contentImage" value="${recommendedUserList.get(internalIndex).image}"/>
                                                            <jsp:param name="contentId" value="${recommendedUserList.get(internalIndex).id}"/>
                                                            <jsp:param name="contentType" value="${recommendedUserList.get(internalIndex).type}"/>
                                                            <jsp:param name="contentRating" value="${recommendedUserList.get(internalIndex).rating}"/>
                                                            <jsp:param name="reviewsAmount" value="${recommendedUserList.get(internalIndex).reviewsAmount}"/>
                                                            <jsp:param name="userName" value="${userName}"/>
                                                            <jsp:param name="userWatchListContentId" value="${userWatchListContentId}"/>
                                                        </jsp:include>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>
                            <button class="carousel-control-prev" type="button" data-bs-target="#carouselRecommended" data-bs-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="visually-hidden"><spring:message code="Content.Carousel.PreviousButton"/></span>
                            </button>
                            <button class="carousel-control-next" type="button" data-bs-target="#carouselRecommended" data-bs-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="visually-hidden"><spring:message code="Content.Carousel.NextButton"/></span>
                            </button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <h3><spring:message code="Content.Carousel.MostSaved"/></h3>
                        <div id="mostContentSavedByUsersList" class="carousel slide W-margin-carousel" data-ride="carousel">
                            <div class="carousel-inner">
                                <c:forEach begin="0" step="5" end="${mostContentSavedByUsersListSize-1}" var="externalIndex">
                                    <c:choose>
                                        <c:when test="${externalIndex == 0}">
                                            <div class="carousel-item active">
                                                <div class="cards-wrapper">
                                                    <c:forEach begin="${externalIndex}" step="1" end="${mostContentSavedByUsersListSize-1 < 4+externalIndex ? mostContentSavedByUsersListSize-1:4+externalIndex}" var="internalIndex" >
                                                        <jsp:include page="components/carrouselContent.jsp">
                                                            <jsp:param name="contentName" value="${mostContentSavedByUsersList.get(internalIndex).name}"/>
                                                            <jsp:param name="contentReleased" value="${mostContentSavedByUsersList.get(internalIndex).released}"/>
                                                            <jsp:param name="contentCreator" value="${mostContentSavedByUsersList.get(internalIndex).creator}"/>
                                                            <jsp:param name="contentGenre" value="${mostContentSavedByUsersList.get(internalIndex).genre}"/>
                                                            <jsp:param name="contentImage" value="${mostContentSavedByUsersList.get(internalIndex).image}"/>
                                                            <jsp:param name="contentId" value="${mostContentSavedByUsersList.get(internalIndex).id}"/>
                                                            <jsp:param name="contentType" value="${mostContentSavedByUsersList.get(internalIndex).type}"/>
                                                            <jsp:param name="contentRating" value="${mostContentSavedByUsersList.get(internalIndex).rating}"/>
                                                            <jsp:param name="reviewsAmount" value="${mostContentSavedByUsersList.get(internalIndex).reviewsAmount}"/>
                                                            <jsp:param name="userName" value="${userName}"/>
                                                            <jsp:param name="userWatchListContentId" value="${userWatchListContentId}"/>
                                                        </jsp:include>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="carousel-item">
                                                <div class="cards-wrapper">
                                                    <c:forEach begin="${externalIndex}" step="1" end="${mostContentSavedByUsersListSize-1 < 4+externalIndex ? mostContentSavedByUsersListSize-1:4+externalIndex}" var="internalIndex" >
                                                        <jsp:include page="components/carrouselContent.jsp">
                                                            <jsp:param name="contentName" value="${mostContentSavedByUsersList.get(internalIndex).name}"/>
                                                            <jsp:param name="contentReleased" value="${mostContentSavedByUsersList.get(internalIndex).released}"/>
                                                            <jsp:param name="contentCreator" value="${mostContentSavedByUsersList.get(internalIndex).creator}"/>
                                                            <jsp:param name="contentGenre" value="${mostContentSavedByUsersList.get(internalIndex).genre}"/>
                                                            <jsp:param name="contentImage" value="${mostContentSavedByUsersList.get(internalIndex).image}"/>
                                                            <jsp:param name="contentId" value="${mostContentSavedByUsersList.get(internalIndex).id}"/>
                                                            <jsp:param name="contentType" value="${mostContentSavedByUsersList.get(internalIndex).type}"/>
                                                            <jsp:param name="contentRating" value="${mostContentSavedByUsersList.get(internalIndex).rating}"/>
                                                            <jsp:param name="reviewsAmount" value="${mostContentSavedByUsersList.get(internalIndex).reviewsAmount}"/>
                                                            <jsp:param name="userName" value="${userName}"/>
                                                            <jsp:param name="userWatchListContentId" value="${userWatchListContentId}"/>
                                                        </jsp:include>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>
                            <button class="carousel-control-prev" type="button" data-bs-target="#mostContentSavedByUsersList" data-bs-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="visually-hidden"><spring:message code="Content.Carousel.PreviousButton"/></span>
                            </button>
                            <button class="carousel-control-next" type="button" data-bs-target="#mostContentSavedByUsersList" data-bs-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="visually-hidden"><spring:message code="Content.Carousel.NextButton"/></span>
                            </button>
                        </div>
                    </c:otherwise>
                </c:choose>

                <h3 class="W-carousel-1-title"><spring:message code="Content.Carousel.BestRatedContent"/></h3>
                <div id="carouselMostReviewed" class="carousel slide W-margin-carousel" data-ride="carousel">
                    <div class="carousel-inner">
                        <c:forEach begin="0" step="5" end="${bestRatedListSize-1}" var="externalIndex">
                            <c:choose>
                                <c:when test="${externalIndex == 0}">
                                    <div class="carousel-item active">
                                        <div class="cards-wrapper">
                                            <c:forEach begin="${externalIndex}" step="1" end="${bestRatedListSize-1 < 4+externalIndex ? bestRatedListSize-1:4+externalIndex}" var="internalIndex" >
                                                <jsp:include page="components/carrouselContent.jsp">
                                                    <jsp:param name="contentName" value="${bestRatedList.get(internalIndex).name}"/>
                                                    <jsp:param name="contentReleased" value="${bestRatedList.get(internalIndex).released}"/>
                                                    <jsp:param name="contentCreator" value="${bestRatedList.get(internalIndex).creator}"/>
                                                    <jsp:param name="contentGenre" value="${bestRatedList.get(internalIndex).genre}"/>
                                                    <jsp:param name="contentImage" value="${bestRatedList.get(internalIndex).image}"/>
                                                    <jsp:param name="contentId" value="${bestRatedList.get(internalIndex).id}"/>
                                                    <jsp:param name="contentType" value="${bestRatedList.get(internalIndex).type}"/>
                                                    <jsp:param name="contentRating" value="${bestRatedList.get(internalIndex).rating}"/>
                                                    <jsp:param name="reviewsAmount" value="${bestRatedList.get(internalIndex).reviewsAmount}"/>
                                                    <jsp:param name="userName" value="${userName}"/>
                                                    <jsp:param name="userWatchListContentId" value="${userWatchListContentId}"/>
                                                </jsp:include>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="carousel-item">
                                        <div class="cards-wrapper">
                                             <c:forEach begin="${externalIndex}" step="1" end="${bestRatedListSize-1 < 4+externalIndex ? bestRatedListSize-1:4+externalIndex}" var="internalIndex" >
                                                <jsp:include page="components/carrouselContent.jsp">
                                                    <jsp:param name="contentName" value="${bestRatedList.get(internalIndex).name}"/>
                                                    <jsp:param name="contentReleased" value="${bestRatedList.get(internalIndex).released}"/>
                                                    <jsp:param name="contentCreator" value="${bestRatedList.get(internalIndex).creator}"/>
                                                    <jsp:param name="contentGenre" value="${bestRatedList.get(internalIndex).genre}"/>
                                                    <jsp:param name="contentImage" value="${bestRatedList.get(internalIndex).image}"/>
                                                    <jsp:param name="contentId" value="${bestRatedList.get(internalIndex).id}"/>
                                                    <jsp:param name="contentType" value="${bestRatedList.get(internalIndex).type}"/>
                                                    <jsp:param name="contentRating" value="${bestRatedList.get(internalIndex).rating}"/>
                                                    <jsp:param name="reviewsAmount" value="${bestRatedList.get(internalIndex).reviewsAmount}"/>
                                                    <jsp:param name="userName" value="${userName}"/>
                                                    <jsp:param name="userWatchListContentId" value="${userWatchListContentId}"/>
                                                </jsp:include>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#carouselMostReviewed" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden"><spring:message code="Content.Carousel.PreviousButton"/></span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#carouselMostReviewed" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden"><spring:message code="Content.Carousel.NextButton"/></span>
                    </button>
                </div>


                <h3><spring:message code="Content.Carousel.LastAdded"/></h3>
                <div id="getLastAddedList" class="carousel slide W-margin-carousel" data-ride="carousel">
                    <div class="carousel-inner">
                        <c:forEach begin="0" step="5" end="${lastAddedListSize-1}" var="externalIndex">
                            <c:choose>
                                <c:when test="${externalIndex == 0}">
                                    <div class="carousel-item active">
                                        <div class="cards-wrapper">
                                            <c:forEach begin="${externalIndex}" step="1" end="${lastAddedListSize-1 < 4+externalIndex ? lastAddedListSize-1:4+externalIndex}" var="internalIndex" >
                                                <jsp:include page="components/carrouselContent.jsp">
                                                    <jsp:param name="contentName" value="${lastAddedList.get(internalIndex).name}"/>
                                                    <jsp:param name="contentReleased" value="${lastAddedList.get(internalIndex).released}"/>
                                                    <jsp:param name="contentCreator" value="${lastAddedList.get(internalIndex).creator}"/>
                                                    <jsp:param name="contentGenre" value="${lastAddedList.get(internalIndex).genre}"/>
                                                    <jsp:param name="contentImage" value="${lastAddedList.get(internalIndex).image}"/>
                                                    <jsp:param name="contentId" value="${lastAddedList.get(internalIndex).id}"/>
                                                    <jsp:param name="contentType" value="${lastAddedList.get(internalIndex).type}"/>
                                                    <jsp:param name="contentRating" value="${lastAddedList.get(internalIndex).rating}"/>
                                                    <jsp:param name="reviewsAmount" value="${lastAddedList.get(internalIndex).reviewsAmount}"/>
                                                    <jsp:param name="userName" value="${userName}"/>
                                                    <jsp:param name="userWatchListContentId" value="${userWatchListContentId}"/>
                                                </jsp:include>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="carousel-item">
                                        <div class="cards-wrapper">
                                            <c:forEach begin="${externalIndex}" step="1" end="${lastAddedListSize-1 < 4+externalIndex ? lastAddedListSize-1:4+externalIndex}" var="internalIndex" >
                                                <jsp:include page="components/carrouselContent.jsp">
                                                    <jsp:param name="contentName" value="${lastAddedList.get(internalIndex).name}"/>
                                                    <jsp:param name="contentReleased" value="${lastAddedList.get(internalIndex).released}"/>
                                                    <jsp:param name="contentCreator" value="${lastAddedList.get(internalIndex).creator}"/>
                                                    <jsp:param name="contentGenre" value="${lastAddedList.get(internalIndex).genre}"/>
                                                    <jsp:param name="contentImage" value="${lastAddedList.get(internalIndex).image}"/>
                                                    <jsp:param name="contentId" value="${lastAddedList.get(internalIndex).id}"/>
                                                    <jsp:param name="contentType" value="${lastAddedList.get(internalIndex).type}"/>
                                                    <jsp:param name="contentRating" value="${lastAddedList.get(internalIndex).rating}"/>
                                                    <jsp:param name="reviewsAmount" value="${lastAddedList.get(internalIndex).reviewsAmount}"/>
                                                    <jsp:param name="userName" value="${userName}"/>
                                                    <jsp:param name="userWatchListContentId" value="${userWatchListContentId}"/>
                                                </jsp:include>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#getLastAddedList" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden"><spring:message code="Content.Carousel.PreviousButton"/></span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#getLastAddedList" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden"><spring:message code="Content.Carousel.NextButton"/></span>
                    </button>
                </div>

            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    </body>
</html>
