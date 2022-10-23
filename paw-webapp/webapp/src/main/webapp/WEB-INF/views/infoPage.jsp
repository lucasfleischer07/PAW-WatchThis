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

    <c:if test="${admin==true}">
      <div class="W-delete-edit-buttons-content">
        <form  class="W-delete-form" id="<c:out value="formDelete"/>" method="post" action="<c:url value="/content/${contentId}/delete"/>">
          <button class="btn btn-danger text-nowrap"  type="button" data-bs-toggle="modal" data-bs-target="#<c:out value="modalDelete"/>" >
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
              <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
            </svg>
          </button>
        </form>
        <a class="W-edit-button-review" href="<c:url value="/content/editInfo/${contentId} "/>" onclick="(this).className += ' spinner-border text-dark'; (this).innerText = ''">
          <button id="editReviewButton" class="btn btn-dark text-nowrap" >
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
              <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
              <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
            </svg>
          </button>
        </a>
      </div>
    </c:if>

    <div class="W-column-display W-word-break">
        <div class="W-review-div-sizing">
          <div class="card W-inv-film-card-margin">
            <div class="W-general-div-infoPage-watchlistButton-and-review">
              <div class="W-watchList-and-ViewedList-div">
                <div class="W-watchlist-button-div-infopage">
                  <c:choose>
                    <c:when test="${userName != 'null' && isInWatchList == 'null'}">
                      <form id="<c:out value="form${details.id}"/>" method="post" action="<c:url value="/watchList/add/${details.id}"/>">
                        <button data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="<spring:message code="WatchList.Add"/>" id="watchListButton" class="btn btn-white W-watchlist-button-infopage" type="submit"  onclick="this.form.submit(); (this).className -= ' W-watchlist-button-infopage'; (this).className += ' spinner-border'; (this).innerText = ''">
                          <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                            <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"/>
                            <path d="M8 4a.5.5 0 0 1 .5.5V6H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V7H6a.5.5 0 0 1 0-1h1.5V4.5A.5.5 0 0 1 8 4z"/>
                          </svg>
                        </button>
                      </form>
                    </c:when>
                    <c:when test="${userName != 'null' && isInWatchList != 'null'}">
                      <form id="<c:out value="form${details.id}"/>" method="post" action="<c:url value="/watchList/delete/${details.id}"/>">
                        <button data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="<spring:message code="WatchList.Remove"/>" id="watchListButton" class="btn btn-white W-watchlist-button-infopage" type="submit" onclick="this.form.submit(); (this).className -= ' W-watchlist-button-infopage'; (this).className += ' spinner-border'; (this).innerText = ''">
                          <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                            <path fill-rule="evenodd" d="M2 15.5V2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.74.439L8 13.069l-5.26 2.87A.5.5 0 0 1 2 15.5zM6.854 5.146a.5.5 0 1 0-.708.708L7.293 7 6.146 8.146a.5.5 0 1 0 .708.708L8 7.707l1.146 1.147a.5.5 0 1 0 .708-.708L8.707 7l1.147-1.146a.5.5 0 0 0-.708-.708L8 6.293 6.854 5.146z"/>
                          </svg>
                        </button>
                      </form>
                    </c:when>
                    <c:otherwise>
                      <button id="watchListButton" class="btn btn-white W-watchlist-button-infopage" type="button" data-bs-toggle="modal" data-bs-target="#watchListModal">
                        <svg data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="<spring:message code="WatchList.Add"/>" xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
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
                      <form id="<c:out value="form${details.id}"/>" method="post" class="W-form-zero-margin" action="<c:url value="/viewedList/add/${details.id}"/>">
                        <button id="viewedListButton" class="btn btn-white W-watchlist-button-infopage" type="submit" onclick="this.form.submit(); (this).className -= ' W-watchlist-button-infopage'; (this).className += ' spinner-border'; (this).innerText = ''">
                          <svg data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="<spring:message code="ViewedList.Add"/>" xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                            <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                            <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
                          </svg>
                        </button>
                      </form>
                    </c:when>
                    <c:when test="${userName != 'null' && isInViewedList != 'null'}">
                      <form id="<c:out value="form${details.id}"/>" method="post" class="W-form-zero-margin" action="<c:url value="/viewedList/delete/${details.id}"/>">
                        <button id="viewedListButton" class="btn btn-white W-watchlist-button-infopage" type="submit" onclick="this.form.submit(); (this).className -= ' W-watchlist-button-infopage'; (this).className += ' spinner-border'; (this).innerText = ''">
                          <svg data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="<spring:message code="ViewedList.Remove"/>" xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                            <path d="m10.79 12.912-1.614-1.615a3.5 3.5 0 0 1-4.474-4.474l-2.06-2.06C.938 6.278 0 8 0 8s3 5.5 8 5.5a7.029 7.029 0 0 0 2.79-.588zM5.21 3.088A7.028 7.028 0 0 1 8 2.5c5 0 8 5.5 8 5.5s-.939 1.721-2.641 3.238l-2.062-2.062a3.5 3.5 0 0 0-4.474-4.474L5.21 3.089z"/>
                            <path d="M5.525 7.646a2.5 2.5 0 0 0 2.829 2.829l-2.83-2.829zm4.95.708-2.829-2.83a2.5 2.5 0 0 1 2.829 2.829zm3.171 6-12-12 .708-.708 12 12-.708.708z"/>
                          </svg>
                        </button>
                      </form>
                    </c:when>
                    <c:otherwise>
                      <button id="viewedListButton" class="btn btn-white W-watchlist-button-infopage" type="button" data-bs-toggle="modal" data-bs-target="#viewedListModal">
                        <svg data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="<spring:message code="ViewedList.Add"/>" xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                          <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                          <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
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
                    <p id="descriptionParagraphInfoPage" class="card-text W-subTitles-font-size"><c:out value="${details.description}"/></p>
                    <script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
                    <script>
                      let previousText = document.getElementById('descriptionParagraphInfoPage').innerHTML
                      document.getElementById('descriptionParagraphInfoPage').innerHTML = marked.parse(previousText);
                    </script>
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
                <c:when test="${userName != 'null' && reviews[0].creator.userName==userName}"/>
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
                <jsp:param name="reviewReputation" value="${review.reputation}" />
                <jsp:param name="userName" value="${review.creator.userName}"/>
                <jsp:param name="contentId" value="${contentId}"/>
                <jsp:param name="contentType" value="${review.type}"/>
                <jsp:param name="loggedUserName" value="${userName}"/>
                <jsp:param name="isAdmin" value="${admin}"/>
              </jsp:include>
            </c:forEach>
            <c:if test="${pageSelected<amountPages}">
              <div class="W-readMore-button" >
                <a id="readMore" class="W-readMore-a" data-toggle="collapse" href="<c:url value="/${type}/${contentId}/page/${pageSelected+1}"/>">
                  <button type="button" class="btn btn-dark W-add-review-button W-reviewText"><spring:message code="Reviews.ReadMore"/></button>
                </a>
              </div>
            </c:if>
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
    <div class="modal fade" id="<c:out value="modalDelete"/>" tabindex="-1" aria-labelledby="<c:out value="modalLabel${contentId}"/>" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="<c:out value="modalLabel${contentId}"/>"><spring:message code="Delete.Confirmation"/></h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <p><spring:message code="DeleteContent"/></p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><spring:message code="No"/></button>
            <button type="submit" form="<c:out value="formDelete"/>" class="btn btn-success"><spring:message code="Yes"/></button>
          </div>
        </div>
      </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    <script>
      const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
      const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
    </script>
  </body>
</html>
