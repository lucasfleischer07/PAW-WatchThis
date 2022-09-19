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
                        <div class="media align-items-end profile-head">
                            <div class="profile mr-3">
                                <c:choose>
                                    <c:when test="${user.image == null}">
                                        <img src="<c:url value="/resources/img/defaultUserImg.png"/> " alt="User_img" class="W-edit-profile-picture">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<c:url value="/profile/${userId}/profileImage"/> " alt="User_img" class="W-edit-profile-picture">
                                    </c:otherwise>
                                </c:choose>
                                <div class="media-body mb-5 text-white">
                                    <h5 class="W-username-profilepage"><c:out value="${user.userName}"/></h5>
                                </div>
                                <a href="<c:url value="/profile/${user.id}/edit-profile"/>" class="btn btn-outline-dark btn-sm btn-block">Edit profile</a>
                            </div>
                        </div>
                    </div>
                    <div class="bg-light p-4 d-flex justify-content-end text-center">
                        <ul class="list-inline mb-0">
                            <li class="list-inline-item">
                                <h5 class="font-weight-bold mb-0 d-block"><c:out value="${reviews.size()}"/></h5>
                                <small class="text-muted"><i class="fas fa-image mr-1"></i>Reviews</small>
                            </li>
                            <li class="list-inline-item">
                                <h5 class="font-weight-bold mb-0 d-block"><c:out value="${user.reputation}"/></h5>
                                <small class="text-muted"><i class="fas fa-user mr-1"></i>Reputation</small></li>
                        </ul>
                    </div>
                    <div class="py-4 px-4">
                        <div class="d-flex align-items-center justify-content-between mb-3">
                            <h4 class="mb-0">Recent reviews</h4>
                        </div>
                        <div class="card">
                            <div class="card-body">
                        <c:forEach var="review" items="${reviews}">
                            <h4><c:out value="${review.contentName}"/></h4>
                            <jsp:include page="components/reviewCard.jsp">
                                <jsp:param name="reviewTitle" value="${review.name}" />
                                <jsp:param name="reviewDescription" value="${review.description}" />
                                <jsp:param name="reviewRating" value="${review.rating}"/>
                                <jsp:param name="reviewId" value="${review.id}" />
                                <jsp:param name="userName" value="${review.userName}"/>
                                <jsp:param name="contentId" value="${review.contentId}"/>
                                <jsp:param name="contentType" value="${review.type}"/>
                                <jsp:param name="loggedUserName" value="${user.userName}"/>
                            </jsp:include>
                        </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    </body>
</html>
