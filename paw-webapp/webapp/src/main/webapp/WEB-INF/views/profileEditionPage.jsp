<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        <link href="<c:url value="/resources/css/profileStyles.css"/>" rel="stylesheet" type="text/css"/>

        <title>Profile</title>
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
                            <div class="W-picture-upload">
                                <img src="https://images.unsplash.com/photo-1522075469751-3a6694fb2f61?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=80" alt="..." class="rounded img-thumbnail W-profile-picture">
                                <div class="W-picture-upload-button-text">
                                    <h6 class="W-edit-picture-text">Edit profile picture:</h6>
                                    <div class="W-picture-upload">
                                        <input type="file" class="form-control" id="inputGroupFile01">
                                        <label class="input-group-text" for="inputGroupFile01">Upload</label>
                                    </div>
                                </div>
                            </div>
                                <h3 class="media-body mb-5 text-white">male</h3>
<%--                            <h4 class="mt-0 mb-0"><c:out value="${user.userName}"/></h4>--%>
                        </div>
                    </div>
                    <div>
                        <h6 class="W-change-pass-text">Change password:</h6>
                        <div class="bg-light d-flex justify-content-end text-center W-edit-divs-display">
                            <div>
                                <c:url value="/hola/${user.id}/edit-profile" var="postPath"/>
                                <form:form modelAttribute="editProfile" action="${postPath}" method="post">
                                    <div class="mb-3 W-input-label-edit-password">
                                        <form:errors path="password" element="p" cssStyle="color: red"/>
                                        <form:label path="password" class="form-label">New password:</form:label>
                                        <form:input type="password" class="form-control" value="${editProfile.password}" path="password"  placeholder="*****"/>
                                    </div>
                                </form:form>
                            </div>
                            <div>
                                <button type="submit" class="btn btn-success" onclick="this.form.submit(); (this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'">Change</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    </body>
</html>
