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
    <link href="<c:url value="/resources/css/profileStyles.css"/>" rel="stylesheet" type="text/css"/>

    <title><spring:message code="WatchList"/></title>
</head>

<body class="body">
<jsp:include page="components/header.jsp">
    <jsp:param name="type" value="profile"/>
    <jsp:param name="userName" value="${userName}"/>
    <jsp:param name="userId" value="${userId}"/>
    <jsp:param name="admin" value="${admin}"/>
</jsp:include>

<div class="row px-4">
    <div class="W-profile-general-div-display">
        <div class="bg-white shadow rounded overflow-hidden W-viewed-watch-list-general-div">
            <div class="W-profile-background-color">
                    <div>
                        <h2 class="W-watch-viewed-list-title"><spring:message code="WatchList.Your"/></h2>
                    </div>
            </div>
            <div class="bg-light p-4 d-flex text-center">
                <h4><spring:message code="WatchList.Titles" arguments="${watchListSize}"/></h4>
            </div>

            <c:choose>
                <c:when test="${watchListContent == null || watchListSize == 0}">
                            <div class="W-watchlist-div-info-empty">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-x-fill W-watchlist-empty-icon" viewBox="0 0 16 16">
                                    <path fill-rule="evenodd" d="M2 15.5V2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.74.439L8 13.069l-5.26 2.87A.5.5 0 0 1 2 15.5zM6.854 5.146a.5.5 0 1 0-.708.708L7.293 7 6.146 8.146a.5.5 0 1 0 .708.708L8 7.707l1.146 1.147a.5.5 0 1 0 .708-.708L8.707 7l1.147-1.146a.5.5 0 0 0-.708-.708L8 6.293 6.854 5.146z"/>
                                </svg>
                                <div>
                                    <p><spring:message code="WatchList.Empty"/></p>
                                </div>
                                <div>
                                    <p><spring:message code="WatchList.Empty2"/></p>
                                </div>
                                <div>
                                    <a href="<c:url value="/"/>"><spring:message code="WatchList.Recomendation"/></a>
                                </div>
                            </div>
                </c:when>
                <c:otherwise>
                <div class="W-films-div">
                    <div class="row row-cols-1 row-cols-md-2 g-2">
                        <c:forEach var="content" items="${watchListContent}">
                            <jsp:include page="components/contentCard.jsp">
                                <jsp:param name="contentName" value="${content.name}"/>
                                <jsp:param name="contentReleased" value="${content.released}"/>
                                <jsp:param name="contentCreator" value="${content.creator}"/>
                                <jsp:param name="contentGenre" value="${content.genre}"/>
                                <jsp:param name="contentImage" value="${content.image}"/>
                                <jsp:param name="contentId" value="${content.id}"/>
                                <jsp:param name="contentType" value="${content.type}"/>
                                <jsp:param name="contentRating" value="${content.rating}"/>
                                <jsp:param name="reviewsAmount" value="${content.reviewsAmount}"/>
                                <jsp:param name="userWatchListContentId1" value="${userWatchListContentId.contains(content.id)}"/>
                            </jsp:include>
                        </c:forEach>
                    </div>
                </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<c:if test="${amountPages > 1}">
    <div>
        <ul class="pagination justify-content-center W-pagination">
            <c:set var = "baseUrl" scope = "session" value = "/profile/watchList"/>
            <c:choose>
                <c:when test="${pageSelected > 1}">
                    <li class="page-item">
                        <a class="page-link W-pagination-color" href="<c:url value="${baseUrl}/page/${pageSelected-1}"/>"><spring:message code="Pagination.Prev"/></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item disabled">
                        <a class="page-link W-pagination-color" href="#"><spring:message code="Pagination.Prev"/></a>
                    </li>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${amountPages > 10 }">
                    <c:forEach var="page" begin="1" end="${amountPages}">
                        <c:choose>
                            <c:when test="${page != pageSelected && ((page > pageSelected - 4 && page<pageSelected) || (page < pageSelected+5 && page > pageSelected))}">
                                <li class="page-item">
                                    <a class="page-link W-pagination-color" href="<c:url value="${baseUrl}/page/${page}"/>">
                                        <c:out value="${page}"/>
                                    </a>
                                </li>
                            </c:when>
                            <c:when test="${page == pageSelected - 4 || page == pageSelected + 5 }">
                                <li class="page-item">
                                    <a class="page-link W-pagination-color" href="<c:url value="${baseUrl}/page/${page}"/>">
                                        ...
                                    </a>
                                </li>
                            </c:when>
                            <c:when test="${page == pageSelected}">
                                <li class="page-item active">
                                    <a class="page-link W-pagination-color" href="<c:url value="${baseUrl}/page/${page}"/>">
                                        <c:out value="${page}"/>
                                    </a>
                                </li>
                            </c:when>

                            <c:otherwise>

                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <c:forEach var="page" begin="1" end="${amountPages}">
                        <c:choose>
                            <c:when test="${page == pageSelected}">
                                <li class="page-item active">
                                    <a class="page-link W-pagination-color" href="<c:url value="${baseUrl}/page/${page}"/>">
                                        <c:out value="${page}"/>
                                    </a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item">
                                    <a class="page-link W-pagination-color" href="<c:url value="${baseUrl}/page/${page}"/>">
                                        <c:out value="${page}"/>
                                    </a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:otherwise>
            </c:choose>


            <c:choose>
                <c:when test="${pageSelected < amountPages}">
                    <li class="page-item">
                        <a class="page-link W-pagination-color" href="<c:url value="${baseUrl}/page/${pageSelected+1}"/>"><spring:message code="Pagination.Next"/></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item disabled">
                        <a class="page-link W-pagination-color" href="#"><spring:message code="Pagination.Next"/></a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</c:if>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
</body>
</html>
