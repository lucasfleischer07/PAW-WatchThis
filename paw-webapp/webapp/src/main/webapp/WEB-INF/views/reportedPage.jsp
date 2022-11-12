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
    <link href="<c:url value="/resources/css/reportedStyles.css"/>" rel="stylesheet" type="text/css"/>
    <title><spring:message code="Report.ReportedContent"/></title>
  </head>

  <body class="body">
    <jsp:include page="components/header.jsp">
      <jsp:param name="type" value="profile"/>
      <jsp:param name="userName" value="${userName}"/>
      <jsp:param name="userId" value="${userId}"/>
      <jsp:param name="admin" value="${true}"/>
    </jsp:include>


    <div class="row px-4">
      <div class="W-profile-general-div-display">
        <div class="bg-white shadow rounded overflow-hidden W-viewed-watch-list-general-div">
          <div class="W-profile-background-color">
            <div>
              <h2 class="W-watch-viewed-list-title"><spring:message code="Report.ReportedContent"/></h2>
            </div>
          </div>
        </div>
        <div class="bg-light p-4 text-center W-report-header-div">
            <div>
                <ul class="list-inline mb-0">
                    <li class="list-inline-item">
                        <h4 class="font-weight-bold mb-0 d-block"><c:out value="${reviewsReportedAmount}"/></h4>
                        <c:choose>
                            <c:when test="${reviewsReportedAmount == 1}">
                                <medium class="text-muted"><i class="fas fa-image mr-1"></i><spring:message code="Profile.Review"/></medium>
                            </c:when>
                            <c:otherwise>
                                <medium class="text-muted"><i class="fas fa-image mr-1"></i><spring:message code="Profile.Reviews"/></medium>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li class="list-inline-item" >
                        <h4 class="font-weight-bold mb-0 d-block"><c:out value="${commentsReportedAmount}"/></h4>
                        <c:choose>
                            <c:when test="${commentsReportedAmount == 1}">
                                <medium class="text-muted"><i class="fas fa-image mr-1"></i><spring:message code="Comment.Title2"/></medium>
                            </c:when>
                            <c:otherwise>
                                <medium class="text-muted"><i class="fas fa-image mr-1"></i><spring:message code="Comments.Title"/></medium>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </div>
            <div>
                <ul class="nav nav-tabs">
                    <c:choose>
                        <c:when test="${type == 'reviews'}">
                            <li class="nav-item">
                                <a class="nav-link active W-reported-reviews-comment-nav" aria-current="page" href="#"><spring:message code="Content.Review"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link W-reported-reviews-comment-nav" href="<c:url value="/report/reportedContent/comments"/>"><spring:message code="Comments.Title"/></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link W-reported-reviews-comment-nav" aria-current="page" href="<c:url value="/report/reportedContent/reviews"/>"><spring:message code="Content.Review"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active W-reported-reviews-comment-nav" href="<c:url value="/report/reportedContent/comments"/>"><spring:message code="Comments.Title"/></a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
            <div class="W-review-comment-tabs">
                <div class="list-group">
                    <div class="dropdown W-dropdown-button">
                        <c:choose>
                            <c:when test="${param.reason != null && param.reason != 'null' && param.reason != ''}">
                                <button id="reportSortByGroup" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                    <c:out value="${param.reason}"/>
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button id="reportSortByGroup" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                    <spring:message code="Report.Filter"/>
                                </button>
                            </c:otherwise>
                        </c:choose>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="<c:url value="/report/reportedContent/${type}/filters">
                                                                            </c:url>" onclick="showDuration(this)"><spring:message code="Duration.Clear"/></a></li>
                            <li><a class="dropdown-item" href="<c:url value="/report/reportedContent/${type}/filters">
                                                                            <c:param name="reason" value="Spam"/>
                                                                            </c:url>" onclick="showDuration(this)"><spring:message code="Report.Spam"/></a></li>
                            <li><a class="dropdown-item" href="<c:url value="/report/reportedContent/${type}/filters">
                                                                            <c:param name="reason" value="Insult"/>
                                                                            </c:url>" onclick="showDuration(this)"><spring:message code="Report.Insult"/></a></li>
                            <li><a class="dropdown-item" href="<c:url value="/report/reportedContent/${type}/filters">
                                                                            <c:param name="reason" value="Inappropriate"/>
                                                                            </c:url>" onclick="showDuration(this)"><spring:message code="Report.Inappropriate"/></a></li>
                            <li><a class="dropdown-item" href="<c:url value="/report/reportedContent/${type}/filters">
                                                                            <c:param name="reason" value="Unrelated"/>
                                                                            </c:url>" onclick="showDuration(this)"><spring:message code="Report.Unrelated"/></a></li>
                            <li><a class="dropdown-item" href="<c:url value="/report/reportedContent/${type}/filters">
                                                                            <c:param name="reason" value="Other"/>
                                                                            </c:url>" onclick="showDuration(this)"><spring:message code="Report.Other"/></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <c:choose>
          <c:when test="${reviewsReportedAmount == 0 && type == 'reviews'}">
            <div class="W-watchlist-div-info-empty">
              <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-x-fill W-watchlist-empty-icon" viewBox="0 0 16 16">
                <path d="m10.79 12.912-1.614-1.615a3.5 3.5 0 0 1-4.474-4.474l-2.06-2.06C.938 6.278 0 8 0 8s3 5.5 8 5.5a7.029 7.029 0 0 0 2.79-.588zM5.21 3.088A7.028 7.028 0 0 1 8 2.5c5 0 8 5.5 8 5.5s-.939 1.721-2.641 3.238l-2.062-2.062a3.5 3.5 0 0 0-4.474-4.474L5.21 3.089z"/>
                <path d="M5.525 7.646a2.5 2.5 0 0 0 2.829 2.829l-2.83-2.829zm4.95.708-2.829-2.83a2.5 2.5 0 0 1 2.829 2.829zm3.171 6-12-12 .708-.708 12 12-.708.708z"/>
              </svg>
              <div>
                <p><spring:message code="Report.Review.Empty"/></p>
              </div>
              <div>
                <a href="<c:url value="/"/>"><spring:message code="WatchList.Recomendation"/></a>
              </div>
            </div>
          </c:when>
          <c:when test="${commentsReportedAmount == 0 && type == 'comments'}">
            <div class="W-watchlist-div-info-empty">
                <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-bookmark-x-fill W-watchlist-empty-icon" viewBox="0 0 16 16">
                    <path d="m10.79 12.912-1.614-1.615a3.5 3.5 0 0 1-4.474-4.474l-2.06-2.06C.938 6.278 0 8 0 8s3 5.5 8 5.5a7.029 7.029 0 0 0 2.79-.588zM5.21 3.088A7.028 7.028 0 0 1 8 2.5c5 0 8 5.5 8 5.5s-.939 1.721-2.641 3.238l-2.062-2.062a3.5 3.5 0 0 0-4.474-4.474L5.21 3.089z"/>
                    <path d="M5.525 7.646a2.5 2.5 0 0 0 2.829 2.829l-2.83-2.829zm4.95.708-2.829-2.83a2.5 2.5 0 0 1 2.829 2.829zm3.171 6-12-12 .708-.708 12 12-.708.708z"/>
                </svg>
                <div>
                    <p><spring:message code="Report.Comment.Empty"/></p>
                </div>
                <div>
                    <a href="<c:url value="/"/>"><spring:message code="WatchList.Recomendation"/></a>
                </div>
            </div>
          </c:when>
          <c:otherwise>
            <div class="W-reported-div">
              <div class="row row-cols-1 row-cols-md-2 g-2 W-report-content-alignment">
                <c:choose>
                    <c:when test="${type == 'reviews'}">
                        <c:forEach var="content" items="${reportedList}">
                            <jsp:include page="components/reportedContent.jsp">
                                <jsp:param name="userName" value="${content.review.user.userName}"/>
                                <jsp:param name="contentId" value="${content.review.content.id}"/>
                                <jsp:param name="contentName" value="${content.review.content.name}"/>
                                <jsp:param name="contentType" value="${content.review.content.type}"/>
                                <jsp:param name="reportDescription" value="${content.review.name}"/>
                                <jsp:param name="reportDescription2" value="${content.review.description}"/>
                                <jsp:param name="reportReasons" value="${content.review.reportReasons}"/>
                                <jsp:param name="reportsAmount" value="${content.review.reportAmount}"/>
                                <jsp:param name="typeId" value="${content.id}"/>
                                <jsp:param name="reportType" value="${content.type}"/>
                            </jsp:include>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="content" items="${reportedList}">
                            <jsp:include page="components/reportedContent.jsp">
                                <jsp:param name="userName" value="${content.comment.user.userName}"/>
                                <jsp:param name="reviewCreatorUserName" value="${content.comment.review.user.userName}"/>
                                <jsp:param name="reviewNameOfReportedComment" value="${content.comment.review.name}"/>
                                <jsp:param name="contentId" value="${content.comment.review.content.id}"/>
                                <jsp:param name="contentName" value="${content.comment.review.content.name}"/>
                                <jsp:param name="contentType" value="${content.comment.review.content.type}"/>
                                <jsp:param name="reportDescription" value="${content.comment.text}"/>
                                <jsp:param name="reportReasons" value="${content.comment.reportReasons}"/>
                                <jsp:param name="reportsAmount" value="${content.comment.reportAmount}"/>
                                <jsp:param name="typeId" value="${content.comment.commentId}"/>
                                <jsp:param name="reportType" value="${content.type}"/>
                            </jsp:include>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
              </div>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>


    <c:if test="${amountPages > 1}">
      <div>
        <ul class="pagination justify-content-center W-pagination">
          <c:set var = "baseUrl" scope = "session" value = "/profile/viewedList"/>
          <c:choose>
            <c:when test="${pageSelected > 1}">
              <li class="page-item">
                <a class="page-link W-pagination-color" href="<c:url value="${baseUrl}/page/${pageSelected-1}"/>"><spring:message code="Pagination.Prev"/></a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="page-item disabled">
                <a class="page-link W-pagination-color" href="#"><spring:message code="Pagination.Prev"/></a>
              </li>
            </c:otherwise>
          </c:choose>
          <c:choose>
            <c:when test="${amountPages > 10 }">
              <c:forEach var="page" begin="1" end="${amountPages}">
                <c:choose>
                  <c:when test="${page != pageSelected && ((page > pageSelected - 4 && page<pageSelected) || (page < pageSelected+5 && page > pageSelected))}">
                    <li class="page-item">
                      <a class="page-link W-pagination-color" href="<c:url value="${baseUrl}/page/${page}"/>">
                        <c:out value="${page}"/>
                      </a>
                    </li>
                  </c:when>
                  <c:when test="${page == pageSelected - 4 || page == pageSelected + 5 }">
                    <li class="page-item">
                      <a class="page-link W-pagination-color" href="<c:url value="${baseUrl}/page/${page}"/>">
                        ...
                      </a>
                    </li>
                  </c:when>
                  <c:when test="${page == pageSelected}">
                    <li class="page-item active">
                      <a class="page-link W-pagination-color" href="<c:url value="${baseUrl}/page/${page}"/>">
                        <c:out value="${page}"/>
                      </a>
                    </li>
                  </c:when>

                  <c:otherwise>

                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <c:forEach var="page" begin="1" end="${amountPages}">
                <c:choose>
                  <c:when test="${page == pageSelected}">
                    <li class="page-item active">
                      <a class="page-link W-pagination-color" href="<c:url value="${baseUrl}/page/${page}"/>">
                        <c:out value="${page}"/>
                      </a>
                    </li>
                  </c:when>
                  <c:otherwise>
                    <li class="page-item">
                      <a class="page-link W-pagination-color" href="<c:url value="${baseUrl}/page/${page}"/>">
                        <c:out value="${page}"/>
                      </a>
                    </li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </c:otherwise>
          </c:choose>

          <c:choose>
            <c:when test="${pageSelected < amountPages}">
              <li class="page-item">
                <a class="page-link W-pagination-color" href="<c:url value="${baseUrl}/page/${pageSelected+1}"/>"><spring:message code="Pagination.Next"/></a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="page-item disabled">
                <a class="page-link W-pagination-color" href="#"><spring:message code="Pagination.Next"/></a>
              </li>
            </c:otherwise>
          </c:choose>
        </ul>
      </div>
    </c:if>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
    <script>
        const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
        const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
    </script>
  </body>
</html>
