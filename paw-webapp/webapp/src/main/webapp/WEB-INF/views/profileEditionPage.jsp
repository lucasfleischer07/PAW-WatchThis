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
        </jsp:include>

        <div class="row py-5 px-4">
            <div class="col-md-5 mx-auto">
                <div class="bg-white shadow rounded overflow-hidden">
                    <div class="px-4 pt-0 pb-4 cover">
                        <div class="profile-head W-profile-photo-name">
                            <div class="W-picture-upload profile mr-3">
                                <c:choose>
                                    <c:when test="${user.image == null}">
                                        <img src="<c:url value="/resources/img/defaultUserImg.png"/> " alt="User_img" class="W-edit-profile-picture">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<c:url value="/profile/${user.userName}/profileImage"/> " alt="User_img" class="W-edit-profile-picture">
                                    </c:otherwise>
                                </c:choose>
                                <div class="W-picture-upload-button-text">
                                    <h6 class="W-edit-picture-text"><spring:message code="EditProfile.EditPicture"/></h6>
                                    <div class="W-input-profile-picture">
                                        <c:url value="/profile/edit-profile" var="postPath"/>
                                        <form:form modelAttribute="editProfile" action="${postPath}" method="post" enctype="multipart/form-data">
                                            <form:errors path="profilePicture" element="p" cssClass="form-error-label"/>
                                            <form:input type="file" accept="image/gif, image/jpeg, image/jpg,  image/png" class="form-control" path="profilePicture" cssClass="W-input-width"/>
                                            <button type="submit" class="btn btn-success W-profile-picture-button" onclick="this.form.submit(); (this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'"><spring:message code="EditProfile.Upload"/></button>
                                        </form:form>
                                    </div>
                                </div>
                            </div>
                            <h4 class="W-username-profilepage"><c:out value="${user.userName}"/></h4>
                        </div>
                    </div>
                    <div>
                        <h6 class="W-change-pass-text"><spring:message code="EditProfile.ChangePassword"/></h6>
                        <div class="bg-light d-flex justify-content-end text-center W-edit-divs-display">
                            <c:url value="/profile/edit-profile" var="postPath"/>
                            <form:form modelAttribute="editProfile" action="${postPath}" method="post">
                                <div>
                                    <div class="mb-3 W-input-label-edit-password">
                                        <form:errors path="password" element="p" cssStyle="color: red"/>
                                        <form:label path="password" class="form-label"><spring:message code="EditProfile.NewPassword"/></form:label>
                                        <form:input type="password" class="form-control" value="${editProfile.password}" path="password" placeholder="*****"/>
                                    </div>
                                </div>
                                <div>
                                    <button type="submit" class="btn btn-success" onclick="this.form.submit(); (this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'"><spring:message code="EditProfile.Change"/></button>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    </body>
</html>
