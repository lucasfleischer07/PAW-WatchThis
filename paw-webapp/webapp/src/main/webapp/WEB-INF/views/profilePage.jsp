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
            <jsp:param name="type" value="profile"/>
        </jsp:include>



        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    </body>
</html>