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

    <title><spring:message code="ViewedList.Title"/></title>
</head>

<body class="body">
<jsp:include page="components/header.jsp">
    <jsp:param name="type" value="profile"/>
    <jsp:param name="userName" value="${userName}"/>
    <jsp:param name="userId" value="${userId}"/>
    <jsp:param name="admin" value="${admin}"/>
</jsp:include>

<div>
    <h2 class="bg-dark W-watch-viewed-list-title"><spring:message code="ViewedList.Your"/></h2>
</div>

<div class="row px-4">
    <div class="W-profile-general-div-display">
        <div class="bg-white shadow rounded overflow-hidden W-viewed-watch-list-general-div">
            <div class="W-profile-background-color bg-dark">
                <div class="media align-items-end">
                    <div class="profile mr-3">
                        <div class="W-img-and-quote-div">
                            <div>
                                <c:choose>
                                    <c:when test="${user.image == null}">
                                        <img src="<c:url value="/resources/img/defaultUserImg.png"/> " alt="User_img" class="W-edit-profile-picture">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<c:url value="/profile/${user.userName}/profileImage"/> " alt="User_img" class="W-edit-profile-picture">
                                    </c:otherwise>
                                </c:choose>
                                <h4 class="W-username-profilepage"><c:out value="${user.userName}"/></h4>
                            </div>
                            <div class="W-margin-left-label-viewed-watch-list">
                                <p class="W-quote-in-profile">${quote}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="bg-light p-4 d-flex text-center">
                <h4><spring:message code="WatchList.Titles" arguments="${viewedListContentSize}"/></h4>
            </div>

            <c:choose>
                <c:when test="${viewedListContent == null || viewedListContentSize == 0}">
                    <div class="W-watchlist-div-info-empty">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-x-fill W-watchlist-empty-icon" viewBox="0 0 16 16">
                            <path d="m10.79 12.912-1.614-1.615a3.5 3.5 0 0 1-4.474-4.474l-2.06-2.06C.938 6.278 0 8 0 8s3 5.5 8 5.5a7.029 7.029 0 0 0 2.79-.588zM5.21 3.088A7.028 7.028 0 0 1 8 2.5c5 0 8 5.5 8 5.5s-.939 1.721-2.641 3.238l-2.062-2.062a3.5 3.5 0 0 0-4.474-4.474L5.21 3.089z"/>
                            <path d="M5.525 7.646a2.5 2.5 0 0 0 2.829 2.829l-2.83-2.829zm4.95.708-2.829-2.83a2.5 2.5 0 0 1 2.829 2.829zm3.171 6-12-12 .708-.708 12 12-.708.708z"/>
                        </svg>
                        <div>
                            <p><spring:message code="ViewedList.Empty"/></p>
                        </div>
                        <div>
                            <p><spring:message code="ViewedList.Empty2"/></p>
                        </div>
                        <div>
                            <a href="<c:url value="/"/>"><spring:message code="WatchList.Recomendation"/></a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="W-films-div">
                        <div class="row row-cols-1 row-cols-md-2 g-2">
                            <c:forEach var="content" items="${viewedListContent}">
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
            <c:set var = "baseUrl" scope = "session" value = "/profile/viewedList"/>
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
