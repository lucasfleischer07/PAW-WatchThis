<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

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
            <jsp:param name="type" value="movies"/>
            <jsp:param name="genre" value="${genre}"/>
            <jsp:param name="durationFrom" value="${durationFrom}"/>
            <jsp:param name="durationTo" value="${durationTo}"/>
        </jsp:include>

        <div class="W-row-display">
            <jsp:include page="components/filter.jsp">
                <jsp:param name="genre" value="${genre}"/>
                <jsp:param name="durationFrom" value="${durationFrom}"/>
                <jsp:param name="durationTo" value="${durationTo}"/>
                <jsp:param name="type" value="movies"/>
            </jsp:include>

            <div class="W-films-div W-row-display">
                <div class="row row-cols-1 row-cols-md-2 g-2">
                    <c:forEach var="movie" items="${movies}">
                        <jsp:include page="components/movieCard.jsp">
                            <jsp:param name="movieName" value="${movie.name}"/>
                            <jsp:param name="movieDescription" value="${movie.description}"/>
                            <jsp:param name="movieImage" value="${movie.image}"/>
                            <jsp:param name="movieId" value="${movie.id}"/>
                            <jsp:param name="type" value="${movie.type}"/>
                        </jsp:include>
                    </c:forEach>
                </div>
            </div>
            <c:if test="${movies==null || movies.size()==0}">
                <div class="card W-not-found-card">
                    <div class="card-body W-row-display" >
                        <div>
                            <img class="W-not-found" src="<c:url value="/resources/img/noResults.png"/>" alt="Not_Found_Ing"/>
                        </div>
                        <div>
                            <h4 class="W-not-found-message"> We looked everywhere, but we couldn't find any movies with these filters.</h4>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    </body>
</html>
