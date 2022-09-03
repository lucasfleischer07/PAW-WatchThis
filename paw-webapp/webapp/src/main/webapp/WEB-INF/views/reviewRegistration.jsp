<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

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
        <link href="<c:url value="/resources/css/reviewStyle.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/css/reviewRegistrationStyles.css"/>" rel="stylesheet" type="text/css"/>
        <title>Review Registration</title>
    </head>

    <body>
        <jsp:include page="components/header.jsp" />

        <c:url value="/reviewForm/${type}/${id}" var="postPath"/>
        <form:form modelAttribute="registerForm" action="${postPath}" method="post">
            <div class="W-general-div-review-info">
                <div class="card W-review-card">
                    <div class="mb-3 W-input-label-review-info">
                        <form:errors path="userName" element="p" cssStyle="color: red"/>
                        <form:label class="form-label" path="userName">Username</form:label>
                        <form:input type="text" class="form-control" placeholder="example123" path="userName"/>
                    </div>
                    <div class="mb-3 W-input-label-review-info">
                        <form:errors path="email" element="p" cssStyle="color: red"/>
                        <form:label path="email" class="form-label">Email</form:label>
                        <form:input type="email" class="form-control" path="email" placeholder="example@mail.com"/>
                    </div>
                    <div class="mb-3 W-input-label-review-info">
                        <form:errors path="name" element="p" cssStyle="color: red"/>
                        <form:label path="name" class="form-label">Review name</form:label>
                        <form:input type="text" class="form-control" path="name" placeholder="My review"/>
                    </div>
                    <div class="mb-3 W-input-label-review-info">
                        <form:errors path="description" element="p" cssStyle="color: red"/>
                        <form:label path="description" class="form-label">Review description</form:label>
                        <form:textarea class="form-control" path="description" rows="3"/>
                    </div>
                </div>
            </div>
            <div class="W-submit-cancel-buttons">
                <a href="<c:url value="/movie/${id}"/>"><button type="button" class="btn btn-danger">Cancel</button></a>
                <button type="submit" class="btn btn-success">Submit</button>
            </div>
        </form:form>

    </body>
</html>
