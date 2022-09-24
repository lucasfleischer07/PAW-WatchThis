<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%--        Para el Fav ico--%>
    <link rel="icon" type="image/x-icon" href="<c:url value="/resources/img/favicon.ico"/>">
    <%--        <!-- * Link de la libreria de Bootstrap -->--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <link href="<c:url value="/resources/css/reviewsStyles.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/navBarStyles.css"/>" rel="stylesheet" type="text/css"/>
    <title>${details.name}</title>
  </head>

  <body>
    <jsp:include page="components/header.jsp">
      <jsp:param name="type" value="${details.type}"/>
      <jsp:param name="userName" value="${userName}"/>
      <jsp:param name="userId" value="${userId}"/>
    </jsp:include>
    <div class="W-column-display W-word-break">

        <div>
          <div class="card W-inv-film-card-margin">
            <div class="row g-0">
              <div class="col-md-4 W-img-aligment">
                <img src="<c:url value="${details.image}"/>" class="W-img-size" alt="foto ">
              </div>
              <div class="col-md-8">
                <div class="card-body W-card-body-aligment">
                  <h5 class="card-title W-card-title"><c:out value="${details.name}"/></h5>
                  <p class="card-text"><c:out value="${details.description}"/></p>
                  <p class="card-text"><span class="W-span-text-info-card-movie"><spring:message code="Content.Duration"/></span> <c:out value="${details.duration}"/></p>
                  <p class="card-text"><span class="W-span-text-info-card-movie"><spring:message code="Content.Genre"/></span> <c:out value="${details.genre}"/></p>
                  <p class="card-text"><span class="W-span-text-info-card-movie"><spring:message code="Content.Released"/></span> <c:out value="${details.released}"/></p>
                  <p class="card-text"><span class="W-span-text-info-card-movie"><spring:message code="Content.Creator"/></span> <c:out value="${details.creator}"/></p>
<%--                  <c:if test="${param.contentRating >= 1}">--%>
                    <c:forEach  begin="1" step="1" end="5" var="var">
                      <c:choose>
                        <c:when test="${details.rating >= var}">
                          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                          </svg>
                        </c:when>
                        <c:otherwise>
                          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star" viewBox="0 0 16 16">
                            <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                          </svg>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
<%--                  </c:if>--%>
                  <p class="card-text">(<c:out value="${details.reviewsAmount}"/> <spring:message code="Content.reviewAmount"/>)</p>

                </div>
              </div>
            </div>

          </div>

        </div>

      <div class="card W-inv-film-card-margin">
        <div class="card-header W-card-header">
          <h3 class="W-title-review"><spring:message code="Content.Review"/></h3>
          <div class="W-add-review">
            <c:choose>
              <c:when test="${userName != 'null' && reviews[0].userName==userName}"/>
              <c:when test="${userName != 'null'}">
                <a href="<c:url value="/reviewForm/${details.type}/${details.id}/${userId}"/>"><button type="button" class="btn btn-dark W-add-review-button W-reviewText W-add-review-button-add"><spring:message code="Content.AddReview"/></button></a>
              </c:when>
              <c:otherwise>
                <button type="button" class="btn btn-dark W-add-review-button W-reviewText W-add-review-button-add" data-bs-toggle="modal" data-bs-target="#exampleModal">Add review</button>
                <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Review</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>
                      <div class="modal-body">
                        <span>To make a review, you must be logged in.</span>
                        <span>Please log in to continue.</span>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <a href="<c:url value="/login/sign-in"/>"><button type="button" class="btn btn-success">Log in</button></a>
                      </div>
                    </div>
                  </div>
                </div>
              </c:otherwise>
            </c:choose>
          </div>
        </div>

        <div class="card-body">
          <c:forEach var="review" items="${reviews}">
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
          <c:if test="${reviews==null || reviews.size()==0}">
              <div>
                <div class="W-no-reviews-icon">
                <img src="<c:url value="/resources/img/noReviews.png"/>" alt="No_Review_Img"/>
                </div>
                <c:choose>
                  <c:when test="${details.type == 'movie'}">
                    <h3 class="W-no-reviews-text" ><spring:message code="Content.NoReviewMessage.Movie"/></h3>
                  </c:when>
                  <c:otherwise>
                    <h3 class="W-no-reviews-text" ><spring:message code="Content.NoReviewMessage.Serie"/></h3>
                  </c:otherwise>
                </c:choose>
              </div>
          </c:if>
        </div>
      </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
  </body>
</html>
