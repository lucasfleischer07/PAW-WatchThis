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
        <title>Tv Shows</title>
    </head>
    <body>
    <jsp:include page="components/header.jsp">
        <jsp:param name="type" value="series"/>
        <jsp:param name="genre" value="${genre}"/>
        <jsp:param name="durationFrom" value="${durationFrom}"/>
        <jsp:param name="durationTo" value="${durationTo}"/>
    </jsp:include>

    <div class="W-row-display">
        <jsp:include page="components/filter.jsp">
            <jsp:param name="type" value="series"/>
            <jsp:param name="genre" value="${genre}"/>
            <jsp:param name="durationFrom" value="${durationFrom}"/>
            <jsp:param name="durationTo" value="${durationTo}"/>
        </jsp:include>

        <div class="W-films-div W-row-display">
            <div class="row row-cols-1 row-cols-md-2 g-2">
                <c:forEach var="serie" items="${series}">
                    <jsp:include page="components/serieCard.jsp">
                        <jsp:param name="serieName" value="${serie.name}"/>
                        <jsp:param name="serieReleased" value="${serie.released}"/>
                        <jsp:param name="serieCreator" value="${serie.creator}"/>
                        <jsp:param name="serieGenre" value="${serie.genre}"/>
                        <jsp:param name="serieImage" value="${serie.image}"/>
                        <jsp:param name="serieId" value="${serie.id}"/>
                        <jsp:param name="type" value="${serie.type}"/>
                    </jsp:include>
                </c:forEach>

            </div>
        </div>
        <c:if test="${series==null || series.size()==0}">
            <div class="card W-not-found-card">
                <div class="card-body W-row-display" >
                    <div>
                        <img class="W-not-found" src="<c:url value="/resources/img/noResults.png"/>"/>
                    </div>
                    <div>
                        <h4 class="W-not-found-message"> We looked everywhere, but we couldn't find any series with these filters.</h4>
                    </div>
                </div>
            </div>
        </c:if>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    </body>
</html>
