<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%--        Para el Fav ico--%>
    <link rel="icon" type="image/x-icon" href="<c:url value="/resources/img/favicon.ico"/>">
    <%--        <!-- * Link de la libreria de Bootstrap -->--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <%--       * Referencia a nuestra hoja de estilos propia --%>

    <title><spring:message code="WatchThisMessage"/></title>
</head>

<body>
<div class="d-flex align-items-center justify-content-center vh-100">
    <div class="text-center">
        <h1 class="display-1 fw-bold"><c:out value="${errorCode}"/></h1>
        <p class="fs-3"> <span class="text-danger"><spring:message code="Error.Opps"/></span> <spring:message code="Error.Title${errorCode!=401 && errorCode!= 404 && errorCode!= 400 && errorCode!= 405 && errorCode!= 403 && errorCode!= 500? null:errorCode}" arguments="${errorCode}"/></p>
        <p class="lead">
            <spring:message code="Error.Body${errorCode!=401 && errorCode!= 404 && errorCode!= 400 && errorCode!= 405 && errorCode!= 403 && errorCode!= 500? null:errorCode}"/>
        </p>
        <a href="<c:url value="/"/>" class="btn btn-success"><spring:message code="Error.HomeButton"/></a>
    </div>
</div>

</body>
</html>