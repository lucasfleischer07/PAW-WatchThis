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

        <title>Watch This</title>
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

        </jsp:include>

        <div class="W-column-display">
            <jsp:include page="components/filter.jsp">
                <jsp:param name="genre" value="${genre}"/>
                <jsp:param name="durationFrom" value="${durationFrom}"/>
                <jsp:param name="durationTo" value="${durationTo}"/>
                <jsp:param name="type" value="${contentType}"/>
                <jsp:param name="sorting" value="${sorting}"/>
            </jsp:include>


            <div id="carouselMostReviewed" class="carousel slide" data-ride="carousel">
                <div class="carousel-inner">
                    <c:forEach begin="0" step="4" end="${bestRatedListSize-1}" var="externalIndex">
                        <c:choose>
                            <c:when test="${externalIndex == 0}">
                                <div class="carousel-item active">
                                    <div class="cards-wrapper">
                                        <c:forEach begin="${externalIndex}" step="1" end="${bestRatedListSize-1 < 3+externalIndex ? bestRatedListSize-1:3+externalIndex}" var="internalIndex" >
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
                                            </jsp:include>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="carousel-item">
                                    <div class="cards-wrapper">
                                         <c:forEach begin="${externalIndex}" step="1" end="${bestRatedListSize-1 < 3+externalIndex ? bestRatedListSize-1:3+externalIndex}" var="internalIndex" >
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
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselMostReviewed" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    </body>
</html>
