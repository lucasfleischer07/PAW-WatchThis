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
            <c:when test="${loginStage == 'log'}">
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
                                <form:input type="email" class="form-control" value="${loginForm.email}" path="email" readonly="true"/>
                            </div>
                            <div class="mb-3 W-input-label-login-info">
                                <form:errors path="password" element="p" cssStyle="color: red"/>
                                <form:label path="password" class="form-label">Password</form:label>
                                <form:input type="password" class="form-control" path="password"/>
                            </div>
                        </div>
                        <div class="W-submit-cancel-buttons">
                            <a href="<c:url value="/"/>"><button type="button" class="btn btn-danger" id="cancelButton1" onclick="(this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('submitButton1').disabled=true">Cancel</button></a>
                            <button id="submitButton1" type="submit" class="btn btn-success" onclick="this.form.submit(); (this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('cancelButton1').disabled=true">Verify</button>
                        </div>
                    </div>
                </form:form>
            </c:when>

<%--            * En el caso de que el NO tenga mail registrado NI ESTE REGISTRADO--%>
            <c:when test="${loginStage == 'registration'}">
                <c:url value="/login/${loginStage}" var="postPath"/>
                <form:form modelAttribute="loginForm" action="${postPath}" method="post">
                    <div class="W-general-div-login">
                        <div class="W-login-title">
                            <h4>Register into Watch This</h4>
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
                                    <form:input type="email" class="form-control" value="${loginForm.email}" path="email" readonly="true"/>
                                </div>
                            </div>
                            <div class="mb-3 W-input-label-login-info">
                                <form:errors path="password" element="p" cssStyle="color: red"/>
                                <form:label path="password" class="form-label">Password</form:label>
                                <form:input type="password" class="form-control" path="password"/>
                            </div>
                        </div>
                        <div class="W-submit-cancel-buttons">
                            <a href="<c:url value="/"/>"><button type="button" class="btn btn-danger" id="cancelButton2" onclick="(this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('submitButton2').disabled=true">Cancel</button></a>
                            <button id="submitButton2" type="submit" class="btn btn-success" onclick="this.form.submit(); (this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('cancelButton2').disabled=true">Verify</button>
                        </div>
                    </div>
                </form:form>
            </c:when>

<%--            * En el caso de que el tenga mail registrado PERO SIN contrasena--%>
            <c:when test="${loginStage == 'setPassword'}">
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
                                <h6>You already had registered your email, please set a password</h6>
                            </div>
                            <div class="mb-3 W-input-label-login-info">
                                <form:errors path="email" element="p" cssStyle="color: red"/>
                                <form:label path="email" class="form-label">Email</form:label>
                                <form:input type="email" class="form-control" value="${loginForm.email}" path="email" readonly="true"/>
                            </div>
                            <div class="mb-3 W-input-label-login-info">
                                <form:errors path="password" element="p" cssStyle="color: red"/>
                                <form:label path="password" class="form-label">Password</form:label>
                                <form:input type="password" class="form-control" path="password"/>
                            </div>
                        </div>
                        <div class="W-submit-cancel-buttons">
                            <a href="<c:url value="/"/>"><button type="button" class="btn btn-danger" id="cancelButton3" onclick="(this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('submitButton3').disabled=true">Cancel</button></a>
                            <button id="submitButton3" type="submit" class="btn btn-success" onclick="this.form.submit(); (this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('cancelButton3').disabled=true">Verify</button>
                        </div>
                    </div>
                </form:form>
            </c:when>

<%--            Para que cargue el verificar mail--%>
            <c:otherwise>
                <c:url value="/login/${loginStage}" var="postPath"/>
                <form:form modelAttribute="loginForm" action="${postPath}" method="post">
                    <div class="W-general-div-login">
                        <div class="W-login-title">
                            <h4>Login to Watch This</h4>
                        </div>
<%--                    TODO: Ver esto de los errores--%>
<%--                    <c:if test="${errorMsg!=null && errorMsg != ''}">--%>
<%--                        <div class="alert alert-danger d-flex align-items-center" role="alert">--%>
<%--                            <div class="W-register-errorMsg">--%>
<%--                                <c:out value="${errorMsg}"/>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </c:if>--%>
                        <div class="card W-login-card">
                            <div class="W-email-verification-message">
                                <h6>Please enter your email to se if you are register</h6>
                            </div>

                            <div class="mb-3 W-input-label-login-info">
                                <form:errors path="email" element="p" cssStyle="color: red"/>
                                <form:label path="email" class="form-label">Email</form:label>
                                <form:input type="email" class="form-control" path="email" placeholder="example@mail.com"/>
                            </div>
                        </div>
                        <div class="W-submit-cancel-buttons">
                            <a href="<c:url value="/"/>"><button type="button" class="btn btn-danger" id="cancelButton4" onclick="(this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('submitButton4').disabled=true">Cancel</button></a>
                            <button id="submitButton4" type="submit" class="btn btn-success" onclick="this.form.submit(); (this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('cancelButton4').disabled=true">Verify</button>
                        </div>
                    </div>
                </form:form>
            </c:otherwise>
        </c:choose>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    </body>
</html>