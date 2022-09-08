<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
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
        <link href="<c:url value="/resources/css/loginStyles.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/css/reviewRegistrationStyles.css"/>" rel="stylesheet" type="text/css"/>
        <title>Review Registration</title>
    </head>

    <body>
        <jsp:include page="components/header.jsp">
            <jsp:param name="type" value="null"/>
        </jsp:include>

        <c:url value="/login" var="postPath"/>
        <form:form modelAttribute="loginForm" action="${postPath}" method="post">
            <div class="W-general-div-login">
                <div class="W-login-title">
                    <h4>Login to Watch This</h4>
                </div>
                <div class="card W-login-card">
<%--                    TODO: Ver esto de los errores--%>
<%--                    <c:if test="${errorMsg!=null && errorMsg != ''}">--%>
<%--                        <div class="alert alert-danger d-flex align-items-center" role="alert">--%>
<%--                            <div class="W-register-errorMsg">--%>
<%--                                <c:out value="${errorMsg}"/>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </c:if>--%>
                    <div class="mb-3 W-input-label-login-info">
                        <form:errors path="email" element="p" cssStyle="color: red"/>
                        <form:label path="email" class="form-label">Email</form:label>
                        <form:input type="email" class="form-control" path="email" placeholder="example@mail.com"/>
                    </div>
                    <div class="mb-3 W-input-label-login-info">
                        <form:errors path="password" element="p" cssStyle="color: red"/>
                        <form:label path="password" class="form-label">Password</form:label>
                        <form:input type="password" class="form-control" path="password"/>
                    </div>
                </div>
            </div>
            <div class="W-submit-cancel-buttons">
<%--                <a href="<c:url value="/"/>"><button type="button" class="btn btn-danger" id="cancelButton" onclick="(this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('submitButton').disabled=true">Cancel</button></a>--%>
                <button id="submitButton" type="submit" class="btn btn-success" onclick="this.form.submit(); (this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('cancelButton').disabled=true">Login</button>
            </div>
        </form:form>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    </body>
</html>