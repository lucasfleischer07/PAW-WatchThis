<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>

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
        <title>Watch This</title>
    </head>

    <body>
        <jsp:include page="components/header.jsp">
            <jsp:param name="type" value="${contentType}"/>
            <jsp:param name="genre" value="${genre}"/>
            <jsp:param name="durationFrom" value="${durationFrom}"/>
            <jsp:param name="durationTo" value="${durationTo}"/>
        </jsp:include>

        <div class="W-column-display">
            <jsp:include page="components/filter.jsp">
                <jsp:param name="genre" value="${genre}"/>
                <jsp:param name="durationFrom" value="${durationFrom}"/>
                <jsp:param name="durationTo" value="${durationTo}"/>
                <jsp:param name="type" value="${contentType}"/>
            </jsp:include>

            <div class="W-films-div W-row-display">
                <div class="row row-cols-1 row-cols-md-2 g-2">
                    <c:forEach var="content" items="${allContent}">
                        <jsp:include page="components/movieCard.jsp">
                            <jsp:param name="movieName" value="${content.name}"/>
                            <jsp:param name="movieReleased" value="${content.released}"/>
                            <jsp:param name="movieCreator" value="${content.creator}"/>
                            <jsp:param name="movieGenre" value="${content.genre}"/>
                            <jsp:param name="movieImage" value="${content.image}"/>
                            <jsp:param name="movieId" value="${content.id}"/>
                            <jsp:param name="type" value="${content.type}"/>
                        </jsp:include>
                    </c:forEach>

                </div>


            </div>

            <c:if test="${allContent == null || allContent.size() == 0}">
                <div class="card W-not-found-card">
                    <div class="card-body W-row-display" >
                        <div>
                            <img class="W-not-found" src="<c:url value="/resources/img/noResults.png"/>" alt="Not_Found_Ing"/>
                        </div>
                        <div>
                            <h4 class="W-not-found-message"> We looked everywhere, but we couldn't find any <c:out value="${contentType}"/> with these filters.</h4>
                        </div>
                    </div>
                </div>
            </c:if>

        </div>
        <div>
            <ul class="pagination justify-content-center W-pagination">
                <li class="page-item disabled">
                    <a class="page-link" href="#" tabindex="-1">Previous</a>
                </li>
                <%--Si hay menos de 10 paginas, se muestran todas, si hay mas se muestran las 4 antes --%>
                <%--de la actual, la actual y las 5 proximas, con los bordes ... --%>


                    <c:choose>
                        <c:when test="${amountPages > 10 }">
                            <c:forEach var="page" begin="1" end="${amountPages}">
                                <c:choose>
                                    <c:when test="${page != pageSelected && ((page > pageSelected - 4 && page<pageSelected) || (page < pageSelected+5 && page > pageSelected))}">
                                        <li class="page-item"><a class="page-link" href="<c:url value="/${contentType}/filters/page/${page}">
                                                                                         <c:param name="genre" value="${genre}"/>
                                                                                         <c:param name="durationFrom" value="${durationFrom}"/>
                                                                                         <c:param name="durationTo" value="${durationTo}"/>

</c:url> "><c:out value="${page}"/></a></li>
                                    </c:when>
                                    <c:when test="${page == pageSelected - 4 || page == pageSelected + 5 }">
                                        <li class="page-item"><a class="page-link" href="<c:url value="/${contentType}/filters/page/${page}">
                                                                                         <c:param name="genre" value="${genre}"/>
                                                                                         <c:param name="durationFrom" value="${durationFrom}"/>
                                                                                         <c:param name="durationTo" value="${durationTo}"/>

</c:url> ">...</a></li>
                                    </c:when>
                                    <c:when test="${page == pageSelected}">
                                        <li class="page-item active"><a class="page-link" href="<c:url value="/${contentType}/filters/page/${page}">
                                                                                         <c:param name="genre" value="${genre}"/>
                                                                                         <c:param name="durationFrom" value="${durationFrom}"/>
                                                                                         <c:param name="durationTo" value="${durationTo}"/>

</c:url>  "><c:out value="${page}"/></a></li>
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
                                        <li class="page-item active"><a class="page-link" href="<c:url value="/${contentType}/filters/page/${page}">
                                                                                         <c:param name="genre" value="${genre}"/>
                                                                                         <c:param name="durationFrom" value="${durationFrom}"/>
                                                                                         <c:param name="durationTo" value="${durationTo}"/>

</c:url>"><c:out value="${page}"/></a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link" href="<c:url value="/${contentType}/filters/page/${page}">
                                                                                         <c:param name="genre" value="${genre}"/>
                                                                                         <c:param name="durationFrom" value="${durationFrom}"/>
                                                                                         <c:param name="durationTo" value="${durationTo}"/>

</c:url>"><c:out value="${page}"/></a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:otherwise>

                    </c:choose>

                <li class="page-item">
                    <a class="page-link" href="#">Next</a>
                </li>
            </ul>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    </body>
</html>
