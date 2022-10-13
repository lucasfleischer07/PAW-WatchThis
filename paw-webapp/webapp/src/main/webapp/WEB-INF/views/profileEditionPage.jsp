<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        <link href="<c:url value="/resources/css/profileStyles.css"/>" rel="stylesheet" type="text/css"/>

        <title><spring:message code="Profile"/></title>
    </head>

    <body class="body">
        <jsp:include page="components/header.jsp">
            <jsp:param name="type" value="profile"/>
            <jsp:param name="userName" value="${user.userName}"/>
            <jsp:param name="userId" value="${user.id}"/>
            <jsp:param name="admin" value="${admin}"/>
        </jsp:include>

        <div class="row py-5 px-4">
            <div class="col-md-5 mx-auto W-edit-profile-width">
                <div class="bg-white shadow rounded overflow-hidden">
                    <div class="W-profile-background-color bg-dark">
                        <div>
                            <div class="profile mr-3">
                                <div class="W-img-and-quote-div">
                                    <div class="W-div-img-quote">
                                        <c:choose>
                                            <c:when test="${user.image == null}">
                                                <img src="<c:url value="/resources/img/defaultUserImg.png"/> " alt="User_img" class="W-edit-profile-picture">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="<c:url value="/profile/${user.userName}/profileImage"/> " alt="User_img" class="W-edit-profile-picture">
                                            </c:otherwise>
                                        </c:choose>
                                        <h4 class="W-username-profilepage"><c:out value="${user.userName}"/></h4>
                                    </div>
                                    <div class="W-margin-left-label">
                                        <p class="W-quote-in-profile">${quote}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <c:url value="/profile/editProfile" var="postPath"/>
                    <form:form modelAttribute="editProfile" action="${postPath}" method="post" enctype="multipart/form-data">
                        <div class="W-edit-profile-div">
                            <div class="W-picture-upload-button-text">
                                <div class="W-input-profile-picture">
                                    <h5 class="W-edit-picture-text"><spring:message code="EditProfile.EditPicture"/></h5>
                                       <div class="bg-light W-div-img-username">
                                           <div>
                                               <form:errors path="profilePicture" element="p" cssClass="form-error-label"/>
                                               <form:input type="file" accept="image/gif, image/jpeg, image/jpg,  image/png" class="form-control" path="profilePicture" cssClass="W-input-width"/>
                                           </div>
                                       </div>
                                </div>
                            </div>
                            <div class="W-div-margin">
                                <h5 class="W-margin-bottom"><spring:message code="EditProfile.ChangePassword"/></h5>
                                <div class="bg-light d-flex justify-content-end text-center W-edit-divs-display">
                                    <div>
                                        <div class="mb-3 W-input-label-edit-password">
                                            <form:errors path="currentPassword" element="p" cssClass="W-form-color-red"/>
                                            <form:label path="currentPassword" class="form-label"><spring:message code="EditProfile.CurrentPassword"/></form:label>
                                            <spring:message code="Placeholder.Asterisk" var="placeholder"/>
                                            <form:input type="password" class="form-control" path="currentPassword" placeholder="${placeholder}"/>
                                        </div>
                                    </div>
                                    <div>
                                        <div class="mb-3 W-input-label-edit-password">
                                            <form:errors path="password" element="p" cssClass="W-form-color-red"/>
                                            <form:label path="password" class="form-label"><spring:message code="EditProfile.NewPassword"/></form:label>
                                            <spring:message code="Placeholder.Asterisk" var="placeholder"/>
                                            <form:input type="password" class="form-control" value="${editProfile.password}" path="password" placeholder="${placeholder}"/>
                                        </div>
                                    </div>
                                    <div>
                                        <div class="mb-3 W-input-label-edit-password">
                                            <form:errors path="confirmPassword" element="p" cssStyle="color: red"/>
                                            <form:label path="confirmPassword" class="form-label"><spring:message code="Signup.ConfirmPassword"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                            <spring:message code="Placeholder.Asterisk" var="placeholder"/>
                                            <form:input type="password" class="form-control" path="confirmPassword" placeholder="${placeholder}"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="W-submit-changes-edit-profile">
                                <button type="submit" class="btn btn-success" onclick="this.form.submit(); (this).className += ' spinner-border'; (this).innerText = '|'"><spring:message code="EditProfile.Upload"/></button>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    </body>
</html>
