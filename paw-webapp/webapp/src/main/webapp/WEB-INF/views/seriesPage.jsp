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
        <title>Series</title>
    </head>
    <body>
    <jsp:include page="components/header.jsp" />

    <div class="W-row-display">
        <jsp:include page="components/filter.jsp">
            <jsp:param name="type" value="series"/>
        </jsp:include>

        <div class="W-films-div W-row-display">
            <div class="row row-cols-1 row-cols-md-2 g-2">
                <c:forEach var="serie" items="${series}">
                    <jsp:include page="components/serieCard.jsp">
                        <jsp:param name="serieName" value="${serie.name}"/>
                        <jsp:param name="serieDescription" value="${serie.description}"/>
                        <jsp:param name="serieImage" value="${serie.image}"/>
                        <jsp:param name="serieId" value="${serie.id}"/>
                        <jsp:param name="type" value="${serie.type}"/>
                    </jsp:include>
                </c:forEach>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    </body>
</html>
