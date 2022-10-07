<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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

    <title><spring:message code="Profile.Information"/></title>
  </head>

  <body class="body">
    <jsp:include page="components/header.jsp">
      <jsp:param name="type" value="profile"/>
      <jsp:param name="userName" value="${userName}"/>
      <jsp:param name="userId" value="${userId}"/>
      <jsp:param name="admin" value="${admin}"/>
    </jsp:include>

    <div class="row py-5 px-4">
      <div class="col-md-5 mx-auto W-profile-general-div-display">
          <div class="bg-white shadow rounded overflow-hidden W-profile-general-div">
          <div class="px-4 pt-0 pb-4 W-profile-background-color">
            <div class="media align-items-end profile-head W-profile-photo-name">
              <div class="profile mr-3">
                  <div class="W-img-and-quote-div">
                      <c:choose>
                          <c:when test="${user.image == null}">
                              <img src="<c:url value="/resources/img/defaultUserImg.png"/> " alt="User_img" class="W-edit-profile-picture">
                          </c:when>
                          <c:otherwise>
                              <img src="<c:url value="/profile/${user.userName}/profileImage"/> " alt="User_img" class="W-edit-profile-picture">
                          </c:otherwise>
                      </c:choose>
                      <p class="W-quote-in-profile">${quote}</p>
                  </div>
                  <div class="media-body mb-5 text-white">
                      <h3 class="W-username-profilepage"><c:out value="${user.userName}"/></h3>
                  </div>
              </div>
            </div>
          </div>
          <div class="bg-light p-4 d-flex text-center W-editProfileButton-and-reviewsCant">
              <div class="W-edition-and-admin-buttons">
                  <c:if test="${userName==user.userName}">
                      <a href="<c:url value="/profile/edit-profile"/>" class="btn btn-outline-dark btn-block W-editProfile-button"><spring:message code="Profile.EditProfile"/></a>
                  </c:if>
                  <c:if test="${admin==true}">
                      <c:url value="/profile/${user.userName}" var="postPath"/>
                      <form class="W-delete-form" id="<c:out value="user${user.userName}"/>" method="post" action="${postPath}">
                          <button type="submit" class="btn btn-outline-dark btn-sm btn-block W-editProfile-button" onclick="this.form.submit(); (this).className -= ' W-editProfile-button'; (this).className += ' spinner-border'; (this).innerText = ''"><spring:message code="Profile.PromoteUser"/></button>
                      </form>
                  </c:if>
              </div>
              <ul class="list-inline mb-0">
                  <li class="list-inline-item">
                    <h4 class="font-weight-bold mb-0 d-block"><c:out value="${reviews.size()}"/></h4>
                      <medium class="text-muted"><i class="fas fa-image mr-1"></i><spring:message code="Profile.Reviews"/></medium>
                  </li>
              </ul>
          </div>
          <div class="py-4 px-4">
            <div class="d-flex align-items-center justify-content-between mb-3">
              <h4 class="mb-0"><spring:message code="Profile.RecentReviews"/></h4>
            </div>
            <div class="card">
              <div class="card-body">
                <c:if test="${reviews==null || reviews.size() == 0}">
                    <div class="W-no-reviews-icon">
                        <img class="W-no-reviews-image"  src="<c:url value="/resources/img/noReviews.png"/>" alt="No_Review_Img"/>
                    </div>
                    <c:choose>
                        <c:when test="${userName==user.userName}">
                            <h4 class="W-no-reviews-text" ><spring:message code="Profile.NoReviews.Owner" /></h4>
                        </c:when>
                        <c:otherwise>
                            <h4 class="W-no-reviews-text" ><spring:message code="Profile.NoReviews.NotOwner" arguments="${user.userName}"/></h4>
                        </c:otherwise>
                    </c:choose>

                </c:if>
                <c:forEach var="review" items="${reviews}">
                    <a class="W-movie-title" href="<c:url value="/${review.type}/${review.contentId}"/>">
                        <h5><c:out value="${review.contentName}"/></h5>
                    </a>
                    <jsp:include page="components/reviewCard.jsp">
                    <jsp:param name="reviewTitle" value="${review.name}" />
                    <jsp:param name="reviewDescription" value="${review.description}" />
                    <jsp:param name="reviewRating" value="${review.rating}"/>
                    <jsp:param name="reviewId" value="${review.id}" />
                    <jsp:param name="userName" value="${review.userName}"/>
                    <jsp:param name="contentId" value="${review.contentId}"/>
                    <jsp:param name="contentType" value="${review.type}"/>
                    <jsp:param name="loggedUserName" value="${userName}"/>
                    <jsp:param name="isAdmin" value="${admin}"/>
                  </jsp:include>
                </c:forEach>
                  <c:choose>
                      <c:when test="${userProfile!=null}">
                          <c:set var="readMorepath" value="/profile/${userProfile}"/>
                      </c:when>
                      <c:otherwise>
                          <c:set var="readMorepath" value="/profile"/>
                      </c:otherwise>
                  </c:choose>
                  <c:if test="${pageSelected<amountPages}">
                      <div class="W-readMore-button">
                          <a id="readMore" class="W-readMore-a" data-toggle="collapse" href="<c:url value="${readMorepath}/page/${pageSelected+1}"/>">
                              <button type="button" class="btn btn-dark W-add-review-button W-reviewText"><spring:message code="Reviews.ReadMore"/></button>
                          </a>
                      </div>
                  </c:if>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
  </body>
</html>
