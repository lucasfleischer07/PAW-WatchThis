<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
        <link href="<c:url value="/resources/css/profileStyles.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/css/snackbarStyles.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/css/reviewRegistrationStyles.css"/>" rel="stylesheet" type="text/css"/>
        <title><spring:message code="Login.LoginMessage"/></title>
    </head>

    <body>
        <div>
            <div>
                <jsp:include page="components/header.jsp">
                    <jsp:param name="type" value="${type}"/>
                    <jsp:param name="userName" value="${userName}"/>
                    <jsp:param name="userId" value="${userId}"/>
                    <jsp:param name="admin" value="${admin}"/>
                </jsp:include>
            </div>

            <div class="W-background">
                <c:choose>
                    <c:when test="${loginStage == 'sign-in'}">
                        <c:if test="${param.forgotPasswordBoolean}">
                            <div id="snackbar"><spring:message code="Login.ForgotPass.Snackbar"/></div>
                        </c:if>
                        <c:url value="/login/sign-in" var="postPath"/>
                        <form action="<c:url value="/login/sign-in"/>" method="post" name="loginForm">
                            <div class="W-general-div-login">
                                <div class="W-login-title">
                                    <h4><spring:message code="Login.PageInfo"/></h4>
                                </div>
                                <div class="card W-login-card">
                                    <div class="W-email-verification-message">
                                        <h5><spring:message code="Login.WelcomeMessage"/></h5>
                                    </div>
                                    <div class="mb-3 W-input-label-login-info">
                                          <c:choose>
                                              <c:when test="${error == true}">
                                                  <div class="alert alert-danger d-flex align-items-center" role="alert">
                                                      <div class="W-register-errorMsg">
                                                          <spring:message code="Login.WrongEmail"/>
                                                      </div>
                                                  </div>
                                              </c:when>
                                          </c:choose>
                                        <label class="form-label"><spring:message code="Login.Email"/></label>
                                        <input name="email" type="email" class="form-control" placeholder="<spring:message code="Placeholder.EmailExample"/>"/>
                                    </div>
                                    <div class="mb-3 W-input-label-login-info">
                                        <label class="form-label"><spring:message code="Login.Password"/></label>
                                        <input name="password" type="password" class="form-control" placeholder="<spring:message code="Placeholder.Asterisk"/>"/>
                                        <div>
                                            <a class="W-forgot-password" href="<c:url value="/login/forgot-password"/>"><spring:message code="Login.ForgotPassword"/></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="W-div-login-rememberMe">
                                    <div class="form-check">
                                        <input name="rememberMe" class="form-check-input" type="checkbox" id="flexCheckDefault">
                                        <label class="form-check-label" for="flexCheckDefault"><spring:message code="Login.RememberMe"/></label>
                                    </div>
                                    <div class="W-div-login-button">
                                        <button id="submitButton1" type="submit" class="btn btn-success W-login-button" onclick="this.form.submit(); (this).className -= ' W-login-button'; (this).className += ' spinner-border text-success'; (this).innerText = ''"><spring:message code="Login.LoginMessage"/></button>
                                    </div>
                                </div>

                                <hr class="d-flex W-line-style-login"/>
                                <div class="W-alignment-signup-div W-margin-bottom">
                                    <h5><spring:message code="Login.NoAccountMessage"/></h5>
                                    <a href="<c:url value="/login/sign-up"/>"><button type="button" class="btn btn-secondary W-sign-up-button-link"><spring:message code="Login.SignUpMessage"/></button></a>
                                </div>
                            </div>
                        </form>
                    </c:when>
                    <c:when test="${loginStage == 'sign-up'}">
                        <c:url value="/login/${loginStage}" var="postPath"/>
                        <form:form modelAttribute="loginForm" action="${postPath}" method="post">
                            <div class="W-general-div-login">
                                <div class="W-login-title">
                                    <h4><spring:message code="Signup.PageInfo"/></h4>
                                </div>
                                <div class="card W-login-card">
                                    <div class="mb-3 W-input-label-login-info">
                                        <div class="mb-3 W-input-label-login-info">
                                            <form:errors path="username" element="p" cssStyle="color: #b21e26"/>
                                            <form:label path="username" class="form-label"><spring:message code="Signup.Username"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                            <p class="W-review-registration-text"><spring:message code="Signup.CharacterLimits" arguments="4,30"/></p>
                                            <spring:message code="Placeholder.UserExample" var="placeholder"/>
                                            <form:input type="text" class="form-control" path="username" placeholder='${placeholder}'/>
                                        </div>
                                    </div>
                                    <div class="mb-3 W-input-label-login-info">
                                        <div class="mb-3 W-input-label-login-info">
                                            <form:errors path="email" element="p" cssStyle="color: #b21e26"/>
                                            <form:label path="email" class="form-label"><spring:message code="Signup.Email"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                            <spring:message code="Placeholder.EmailExample" var="placeholder"/>
                                            <form:input type="email" class="form-control" value="${loginForm.email}" path="email" placeholder='${placeholder}'/>
                                        </div>
                                    </div>
                                    <div class="mb-3 W-input-label-login-info">
                                        <div class="mb-3 W-input-label-login-info">
                                            <form:errors path="password" element="p" cssStyle="color: #b21e26"/>
                                            <form:label path="password" class="form-label"><spring:message code="Signup.Password"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                            <p class="W-review-registration-text"><spring:message code="Signup.CharacterLimits" arguments="6,50"/></p>
                                            <spring:message code="Placeholder.Asterisk" var="placeholder"/>
                                            <form:input type="password" class="form-control" path="password" placeholder="${placeholder}"/>
                                        </div>
                                    </div>
                                    <div class="mb-3 W-input-label-login-info">
                                        <div class="mb-3 W-input-label-login-info">
                                            <form:errors path="confirmPassword" element="p" cssStyle="color: #b21e26"/>
                                            <form:label path="confirmPassword" class="form-label"><spring:message code="Signup.ConfirmPassword"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                            <spring:message code="Placeholder.Asterisk" var="placeholder"/>
                                            <form:input type="password" class="form-control" path="confirmPassword" placeholder="${placeholder}"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="W-margin-bottom">
                                    <button id="submitButton2" type="submit" class="btn btn-success W-login-button" onclick="this.form.submit(); (this).className -= ' W-login-button'; (this).className += ' spinner-border text-success'; (this).innerText = ''"><spring:message code="Signup.SignupMessage"/></button>
                                </div>
                            </div>
                        </form:form>
                    </c:when>

                    <c:when test="${loginStage == 'forgot-password'}">
                        <c:url value="/login/${loginStage}" var="postPath"/>
                        <form:form modelAttribute="forgotPasswordForm" action="${postPath}" method="post">
                            <div class="W-general-div-login">
                                <div class="W-login-title">
                                    <h4><spring:message code="Login.ForgotPass"/></h4>
                                </div>
                                <div class="card W-login-card">
                                    <div class="mb-3 W-input-label-login-info">
                                        <h5 class="W-password-title"><spring:message code="Login.ForgotPass.msg"/></h5>
                                        <div class="mb-3 W-input-label-login-info">
                                            <form:errors path="email" element="p" cssStyle="color: #b21e26"/>
                                            <form:label path="email" class="form-label"><spring:message code="Signup.Email"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                            <spring:message code="Placeholder.RecoveryEmail" var="placeholder"/>
                                            <form:input type="email" class="form-control" value="${loginForm.email}" path="email" placeholder='${placeholder}'/>
                                        </div>
                                    </div>
                                </div>
                                <div class="W-margin-bottom">
                                    <button id="submitButton3" type="submit" class="btn btn-success W-send-password" onclick="this.form.submit(); (this).className -= ' W-send-password'; (this).className += ' spinner-border text-success'; (this).innerText = ''; document.getElementById('snackbar').className = 'show';"><spring:message code="Send"/></button>
                                </div>
                            </div>
                        </form:form>
                    </c:when>
                </c:choose>
            </div>
        </div>

        <script src='<c:url value="/resources/js/showSnackbar.js"/>'></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    </body>
</html>