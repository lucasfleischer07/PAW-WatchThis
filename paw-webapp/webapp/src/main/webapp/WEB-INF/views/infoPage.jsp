<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
      <jsp:param name="admin" value="${admin}"/>
    </jsp:include>

    <div class="W-column-display W-word-break">
        <div class="W-review-div-sizing">
          <div class="card W-inv-film-card-margin">
            <div class="W-general-div-infoPage-watchlistButton-and-review">
              <div class="W-watchList-and-ViewedList-div">
                <div class="W-watchlist-button-div-infopage">
                  <c:choose>
                    <c:when test="${userName != 'null' && isInWatchList == 'null'}">
                      <form id="<c:out value="form${details.id}"/>" method="post" action="<c:url value="/watchList/add/${details.id}"/>">
                        <button id="watchListButton" class="btn btn-white W-watchlist-button-infopage" type="submit" onclick="this.form.submit(); (this).disabled = true; (this).className -= ' W-watchlist-button-infopage'; (this).className += ' spinner-border'; (this).innerText = ''; document.getElementById('viewedListButton').disabled=true">
                          <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                            <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"/>
                            <path d="M8 4a.5.5 0 0 1 .5.5V6H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V7H6a.5.5 0 0 1 0-1h1.5V4.5A.5.5 0 0 1 8 4z"/>
                          </svg>
                        </button>
                      </form>
                    </c:when>
                    <c:when test="${userName != 'null' && isInWatchList != 'null'}">
                      <form id="<c:out value="form${details.id}"/>" method="post" action="<c:url value="/watchList/delete/${details.id}"/>">
                        <button id="watchListButton" class="btn btn-white W-watchlist-button-infopage" type="submit" onclick="this.form.submit(); (this).disabled = true; (this).className -= ' W-watchlist-button-infopage'; (this).className += ' spinner-border'; (this).innerText = ''; document.getElementById('viewedListButton').disabled=true">
                          <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                            <path fill-rule="evenodd" d="M2 15.5V2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.74.439L8 13.069l-5.26 2.87A.5.5 0 0 1 2 15.5zM6.854 5.146a.5.5 0 1 0-.708.708L7.293 7 6.146 8.146a.5.5 0 1 0 .708.708L8 7.707l1.146 1.147a.5.5 0 1 0 .708-.708L8.707 7l1.147-1.146a.5.5 0 0 0-.708-.708L8 6.293 6.854 5.146z"/>
                          </svg>
                        </button>
                      </form>
                    </c:when>
                    <c:otherwise>
                      <button id="watchListButton" class="btn btn-white W-watchlist-button-infopage" type="button" data-bs-toggle="modal" data-bs-target="#watchListModal">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                          <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"/>
                          <path d="M8 4a.5.5 0 0 1 .5.5V6H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V7H6a.5.5 0 0 1 0-1h1.5V4.5A.5.5 0 0 1 8 4z"/>
                        </svg>
                      </button>
                      <div class="modal fade" id="watchListModal" tabindex="-1" aria-labelledby="watchListModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                          <div class="modal-content">
                            <div class="modal-header">
                              <h5 class="modal-title" id="watchListModalLabel"><spring:message code="WatchList.Title"/></h5>
                              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                              <span><spring:message code="WatchList.WarningAdd"/></span>
                              <span><spring:message code="Review.WarningAddMessage"/></span>
                            </div>
                            <div class="modal-footer">
                              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><spring:message code="Close"/></button>
                              <a href="<c:url value="/login/sign-in"/>"><button type="button" class="btn btn-success"><spring:message code="Login.LoginMessage"/></button></a>
                            </div>
                          </div>
                        </div>
                      </div>
                    </c:otherwise>
                  </c:choose>
                </div>

                <div class="W-viewedlist-button-div-infopage">
                  <c:choose>
                    <c:when test="${userName != 'null' && isInViewedList == 'null'}">
                      <form id="<c:out value="form${details.id}"/>" method="post" action="<c:url value="/viewedList/add/${details.id}"/>">
                        <button id="viewedListButton" class="btn btn-white W-watchlist-button-infopage" type="submit" onclick="this.form.submit(); (this).disabled = true; (this).className -= ' W-watchlist-button-infopage'; (this).className += ' spinner-border'; (this).innerText = ''; document.getElementById('watchListButton').disabled=true">
                          <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                            <path d="M10.5 8a2.5 2.5 0 1 1-5 0 2.5 2.5 0 0 1 5 0z"/>
                            <path d="M0 8s3-5.5 8-5.5S16 8 16 8s-3 5.5-8 5.5S0 8 0 8zm8 3.5a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7z"/>
                          </svg>
                        </button>
                      </form>
                    </c:when>
                    <c:when test="${userName != 'null' && isInViewedList != 'null'}">
                      <form id="<c:out value="form${details.id}"/>" method="post" action="<c:url value="/viewedList/delete/${details.id}"/>">
                        <button id="viewedListButton" class="btn btn-white W-watchlist-button-infopage" type="submit" onclick="this.form.submit(); (this).disabled = true; (this).className -= ' W-watchlist-button-infopage'; (this).className += ' spinner-border'; (this).innerText = ''; document.getElementById('watchListButton').disabled=true">
                          <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                            <path d="m10.79 12.912-1.614-1.615a3.5 3.5 0 0 1-4.474-4.474l-2.06-2.06C.938 6.278 0 8 0 8s3 5.5 8 5.5a7.029 7.029 0 0 0 2.79-.588zM5.21 3.088A7.028 7.028 0 0 1 8 2.5c5 0 8 5.5 8 5.5s-.939 1.721-2.641 3.238l-2.062-2.062a3.5 3.5 0 0 0-4.474-4.474L5.21 3.089z"/>
                            <path d="M5.525 7.646a2.5 2.5 0 0 0 2.829 2.829l-2.83-2.829zm4.95.708-2.829-2.83a2.5 2.5 0 0 1 2.829 2.829zm3.171 6-12-12 .708-.708 12 12-.708.708z"/>
                          </svg>
                        </button>
                      </form>
                    </c:when>
                    <c:otherwise>
                      <button id="viewedListButton" class="btn btn-white W-watchlist-button-infopage" type="button" data-bs-toggle="modal" data-bs-target="#viewedListModal">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                          <path d="M10.5 8a2.5 2.5 0 1 1-5 0 2.5 2.5 0 0 1 5 0z"/>
                          <path d="M0 8s3-5.5 8-5.5S16 8 16 8s-3 5.5-8 5.5S0 8 0 8zm8 3.5a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7z"/>
                        </svg>
                      </button>
                      <div class="modal fade" id="viewedListModal" tabindex="-1" aria-labelledby="viewedListModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                          <div class="modal-content">
                            <div class="modal-header">
                              <h5 class="modal-title" id="viewedListModalLabel"><spring:message code="ViewedList.Title"/></h5>
                              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                              <span><spring:message code="ViewedList.WarningAdd"/></span>
                              <span><spring:message code="Review.WarningAddMessage"/></span>
                            </div>
                            <div class="modal-footer">
                              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><spring:message code="Close"/></button>
                              <a href="<c:url value="/login/sign-in"/>"><button type="button" class="btn btn-success"><spring:message code="Login.LoginMessage"/></button></a>
                            </div>
                          </div>
                        </div>
                      </div>
                    </c:otherwise>
                  </c:choose>
                </div>
              </div>

              <div class="W-div-img-and-reviewInfo">
                <div class="col-md-4 W-img-aligment">
                  <img src="<c:url value="/contentImage/${details.id}"/>" class="W-img-size" alt="Image <c:out value="${details.name}"/>">
                </div>
                <div class="col-md-8">
                  <div class="card-body W-card-body-aligment">
                    <h6 class="card-title W-card-title"><c:out value="${details.name}"/></h6>
                    <p class="card-text W-subTitles-font-size"><c:out value="${details.description}"/></p>
                    <p class="card-text W-subTitles-font-size"><span class="W-span-text-info-card-movie"><spring:message code="Content.Duration"/></span> <c:out value="${details.duration}"/></p>
                    <p class="card-text W-subTitles-font-size"><span class="W-span-text-info-card-movie"><spring:message code="Content.Genre"/></span> <c:out value="${details.genre}"/></p>
                    <p class="card-text W-subTitles-font-size"><span class="W-span-text-info-card-movie"><spring:message code="Content.Released"/></span> <c:out value="${details.released}"/></p>
                    <p class="card-text W-subTitles-font-size"><span class="W-span-text-info-card-movie"><spring:message code="Content.Creator"/></span> <c:out value="${details.creator}"/></p>
                    <div>
                      <c:forEach  begin="1" step="1" end="5" var="var">
                        <c:choose>
                          <c:when test="${details.rating >= var}">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star-fill W-star-style-info-page" viewBox="0 0 16 16">
                              <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                            </svg>
                          </c:when>
                          <c:otherwise>
                            <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star W-star-style-info-page" viewBox="0 0 16 16">
                              <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                            </svg>
                          </c:otherwise>
                        </c:choose>
                      </c:forEach>
                    </div>
                    <p class="card-text W-subTitles-font-size"><spring:message code="Content.ReviewAmount" arguments="${details.reviewsAmount}"/></p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

      <div class="card W-reviews-card-margins W-inv-film-card-margin">
        <div class="card-header W-card-header">
          <h3 class="W-title-review"><spring:message code="Content.Review"/></h3>
          <div class="W-add-review">
            <c:choose>
              <c:when test="${userName != 'null' && reviews[0].userName==userName}"/>
              <c:when test="${userName != 'null'}">
                <a href="<c:url value="/reviewForm/${details.type}/${details.id}/${userId}"/>"><button type="button" class="btn btn-dark W-add-review-button W-reviewText"><spring:message code="Content.AddReview"/></button></a>
              </c:when>
              <c:otherwise>
                <button type="button" class="btn btn-dark W-add-review-button W-reviewText" data-bs-toggle="modal" data-bs-target="#exampleModal"><spring:message code="Content.AddReview"/></button>
                <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel"><spring:message code="Profile.Reviews"/></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>
                      <div class="modal-body">
                        <span><spring:message code="Review.WarningAdd"/></span>
                        <span><spring:message code="Review.WarningAddMessage"/></span>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><spring:message code="Close"/></button>
                        <a href="<c:url value="/login/sign-in"/>"><button type="button" class="btn btn-success"><spring:message code="Login.LoginMessage"/></button></a>
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
