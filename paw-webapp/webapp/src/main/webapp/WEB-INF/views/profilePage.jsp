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
        </jsp:include>

        <div class="row py-5 px-4">
            <div class="col-md-5 mx-auto">
                <div class="bg-white shadow rounded overflow-hidden">
                    <div class="px-4 pt-0 pb-4 cover">
                        <div class="media align-items-end profile-head">
                            <div class="profile mr-3">
                                <img src="https://images.unsplash.com/photo-1522075469751-3a6694fb2f61?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=80" alt="..." width="130" class="rounded mb-2 img-thumbnail">
                                <div class="media-body mb-5 text-white">
                                    <h4 class="mt-0 mb-0"><c:out value="${user.userName}"/></h4>
<%--                                    <p class="small mb-4"><i class="fas fa-map-marker-alt mr-2"></i>New York</p>--%>
                                </div>
                                <a href="<c:url value="/hola/${user.id}/edit-profile"/>" class="btn btn-outline-dark btn-sm btn-block">Edit profile</a>
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
                                <small class="text-muted" data-bs-toggle="tooltip" data-bs-placement="top" data-bs-custom-class="custom-tooltip" data-bs-title="This make reference to tha avg of the amount of likes and dislikes of all the reviews of the user."> <i class="fas fa-user mr-1"></i>Reputation</small></li>
                        </ul>
                    </div>
                    <div class="py-4 px-4">
                        <div class="d-flex align-items-center justify-content-between mb-3">
                            <h5 class="mb-0">Recent reviews</h5>
                        </div>
                        <div class="card">
                            <div class="card-body">
                        <c:forEach var="review" items="${reviews}">
                            <p><c:out value="${review.contentName}"/></p>
                            <jsp:include page="components/reviewCard.jsp">
                                <jsp:param name="reviewTitle" value="${review.name}" />
                                <jsp:param name="reviewDescription" value="${review.description}" />
                                <jsp:param name="reviewRating" value="${review.rating}"/>
                                <jsp:param name="reviewId" value="${review.id}" />
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
