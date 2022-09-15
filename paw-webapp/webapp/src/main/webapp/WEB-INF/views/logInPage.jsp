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

        <c:choose>
<%--            * Caso en el que ESTA registrado y tiene contrasena--%>
            <c:when test="${loginStage == 'sign-in'}">
                <c:url value="/login/${loginStage}" var="postPath"/>
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
                            <div class="W-email-verification-message">
                                <h6>Welcome back!!</h6>
                            </div>
                            <div class="mb-3 W-input-label-login-info">
                                <form:errors path="email" element="p" cssStyle="color: red"/>
                                <form:label path="email" class="form-label">Email</form:label>
                                <form:input type="email" class="form-control" value="${loginForm.email}" path="email" placeholder="Email" readonly="true"/>
                            </div>
                            <div class="mb-3 W-input-label-login-info">
                                <form:errors path="password" element="p" cssStyle="color: red"/>
                                <form:label path="password" class="form-label">Password</form:label>
                                <form:input type="password" class="form-control" path="password" placeholder="Password"/>
                                <a href="<c:url value="//login/forgot-password"/>" class="W-forgot-password">Forgot password?</a>
                            </div>
                        </div>
                        <div>
                            <button id="submitButton1" type="submit" class="btn btn-success W-login-button" onclick="this.form.submit(); (this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('cancelButton1').disabled=true">Log in</button>
                        </div>
                        <hr class="d-flex W-line-style-login"/>
                        <div>
                            <h5>Do not have an account?</h5>
                            <button type="button" class="btn btn-secondary W-sign-up-button-link"><a style="color: aliceblue" href="<c:url value="//login/sign-up"/>" class="W-sign-ip-link">Sign up!</a></button>
                        </div>
                    </div>
                </form:form>
            </c:when>

<%--            * En el caso de que el NO tenga mail registrado NI ESTE REGISTRADO--%>
            <c:when test="${loginStage == 'sign-up'}">
                <c:url value="/login/${loginStage}" var="postPath"/>
                <form:form modelAttribute="loginForm" action="${postPath}" method="post">
                    <div class="W-general-div-login">
                        <div class="W-login-title">
                            <h4>Sign up into Watch This</h4>
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
                                <div class="mb-3 W-input-label-login-info">
                                    <form:errors path="userName" element="p" cssStyle="color: red"/>
                                    <form:label path="userName" class="form-label">Username</form:label>
                                    <form:input type="text" class="form-control" path="userName" placeholder="Example123"/>
                                </div>
                            </div>
                            <div class="mb-3 W-input-label-login-info">
                                <div class="mb-3 W-input-label-login-info">
                                    <form:errors path="email" element="p" cssStyle="color: red"/>
                                    <form:label path="email" class="form-label">Email</form:label>
                                    <form:input type="email" class="form-control" value="${loginForm.email}" path="email" readonly="true" placeholder="Email"/>
                                </div>
                            </div>
                            <div class="mb-3 W-input-label-login-info">
                                <div class="mb-3 W-input-label-login-info">
                                    <form:errors path="password" element="p" cssStyle="color: red"/>
                                    <form:label path="password" class="form-label">Password</form:label>
                                    <form:input type="password" class="form-control" path="password" placeholder="Password"/>
                                </div>
                            </div>
                        </div>
                        <div>
                            <button id="submitButton2" type="submit" class="btn btn-success W-login-button" onclick="this.form.submit(); (this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('cancelButton2').disabled=true">Sign up</button>
                        </div>
                    </div>
                </form:form>
            </c:when>

            <c:when test="${loginStage == 'forgot-password'}">
                <c:url value="/login/${loginStage}" var="postPath"/>
                <form:form modelAttribute="loginForm" action="${postPath}" method="post">
                    <div class="W-general-div-login">
                        <div class="W-login-title">
                            <h4>Sign up into Watch This</h4>
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
                                <div class="mb-3 W-input-label-login-info">
                                    <form:errors path="email" element="p" cssStyle="color: red"/>
                                    <form:label path="email" class="form-label">Email</form:label>
                                    <form:input type="email" class="form-control" value="${loginForm.email}" path="email" readonly="true"/>
                                </div>
                            </div>
                        </div>
                        <div>
                            <button id="submitButton3" type="submit" class="btn btn-success W-send-password" onclick="this.form.submit(); (this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('cancelButton3').disabled=true">Send</button>
                        </div>
                    </div>
                </form:form>
            </c:when>

<%--            Para que cargue el verificar mail--%>
            <c:otherwise>
<%--                TODO: Llamar a la pagina de error?--%>
            </c:otherwise>
        </c:choose>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    </body>
</html>