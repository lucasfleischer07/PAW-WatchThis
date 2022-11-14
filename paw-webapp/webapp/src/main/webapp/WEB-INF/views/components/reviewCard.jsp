<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="accordion W-accordion-margin" id="<c:out value="accordion${param.reviewId}"/>">
    <div class="accordion-item">
        <div class="accordion-header" id="<c:out value="heading${param.reviewId}"/>">
            <div class="card">
                <div class="card-header W-accordion-card-header">
                    <a href="<c:url value="/profile/${param.userName}"/>" class="W-creator-review"><c:out value="${param.userName}"/></a>
                    <div class="W-delete-edit-report-review-buttons">
                        <c:choose>
                            <c:when test="${param.userName.equals(param.loggedUserName)}">
                                <div>
                                    <div class="W-delete-edit-buttons">
                                        <form  class="W-delete-form" id="<c:out value="form${param.reviewId}"/>" method="post" action="<c:url value="/review/${param.reviewId}/delete"/>">
                                            <button id="deleteReviewButton" class="btn btn-danger text-nowrap"  type="button" data-bs-toggle="modal" data-bs-target="#<c:out value="modal${param.reviewId}"/>">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                                                    <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
                                                </svg>
                                            </button>
                                        </form>
                                        <a class="W-edit-button-review" href="<c:url value="/reviewForm/edit/${param.contentType}/${param.contentId}/${param.reviewId}"/>" onclick="(this).className += ' spinner-border text-dark'; (this).innerText = ''">
                                        <button id="editReviewButton" class="btn btn-dark text-nowrap" >
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                                                <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                                <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                            </svg>
                                        </button>
                                    </a>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${param.isAdmin}">
                                <div>
                                    <div class="W-delete-edit-buttons">
                                        <form  class="W-delete-form" id="<c:out value="form${param.reviewId}"/>" method="post" action="<c:url value="/review/${param.reviewId}/delete"/>">
                                            <button class="btn btn-danger text-nowrap"  type="button" data-bs-toggle="modal" data-bs-target="#<c:out value="modal${param.reviewId}"/>">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                                                    <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
                                                </svg>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>

                        <div>
                            <c:choose>
                                <c:when test="${param.loggedUserName != 'null'}">
                                    <c:if test="${(!param.alreadyReport) && (param.userName != param.loggedUserName)}">
                                        <spring:message code="Report.Review.Add" var="reportReviewAdd"/>
                                        <span title=${reportReviewAdd}>
                                        <button id="reportReviewButton" type="button" class="btn btn-light W-background-color-report" data-bs-toggle="modal" data-bs-target="#<c:out value="reportReviewModal${param.reviewId}"/>">
                                            <svg data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="<spring:message code="Report.Review.Add"/>" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#b21e26" class="bi bi-exclamation-circle" viewBox="0 0 16 16">
                                                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                                <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
                                            </svg>
                                        </button>
                                        </span>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${param.userName != param.loggedUserName}">
                                        <button id="reportReviewButton" type="button" class="btn btn-light" data-bs-toggle="modal" data-bs-target="#<c:out value="reportReviewModalNoLogin${param.reviewId}"/>">
                                            <svg data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="<spring:message code="Report.Review.Add"/>" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#b21e26" class="bi bi-exclamation-circle" viewBox="0 0 16 16">
                                                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                                <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
                                            </svg>
                                        </button>
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${param.userName != param.loggedUserName}">
                                        <spring:message code="Report.Review.Add" var="reportReviewAdd"/>
                                        <span title="${reportReviewAdd}">
                                            <button id="reportReviewButton" type="button" class="btn btn-light" data-bs-toggle="modal" data-bs-target="#<c:out value="reportReviewModalNoLogin${param.reviewId}"/>">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#b21e26" class="bi bi-exclamation-circle" viewBox="0 0 16 16">
                                                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                                    <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
                                                </svg>
                                            </button>
                                        </span>
                                        <div class="modal fade" id="<c:out value="reportReviewModalNoLogin${param.reviewId}"/>" tabindex="-1" aria-labelledby="<c:out value="reportReviewModalNoLoginLabel${param.reviewId}"/>" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="<c:out value="reportReviewModalNoLoginLabel${param.reviewId}"/>"><spring:message code="Report.ReviewTitle"/></h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <span><spring:message code="Report.ReviewWarningAdd"/></span>
                                                        <span><spring:message code="Review.WarningAddMessage"/></span>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><spring:message code="Close"/></button>
                                                        <a href="<c:url value="/login/sign-in"/>"><button type="button" class="btn btn-success"><spring:message code="Login.LoginMessage"/></button></a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="card-body W-accordion-card-body">
                    <button id="<c:out value="button${param.reviewId}"/>" class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="<c:out value="#collapse${param.reviewId}"/>" aria-expanded="false" aria-controls="<c:out value="collapse${param.reviewId}"/>">
                        <div class="W-stars">
                            <c:if test="${param.reviewRating!= null && param.reviewRating != 0}">
                                <c:forEach  begin="1" step="1" end="5" var="var">
                                    <c:choose>
                                        <c:when test="${param.reviewRating >= var}">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star-fill W-reviewCard-stars" viewBox="0 0 16 16">
                                                <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                                            </svg>
                                        </c:when>
                                        <c:otherwise>
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star W-reviewCard-stars" viewBox="0 0 16 16">
                                                <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                                            </svg>
                                        </c:otherwise>
                                </c:choose>
                                </c:forEach>
                            </c:if>
                        </div>
                        <div class="W-review-title-creator">
                            <h2 class="W-title-review"><c:out value="${param.reviewTitle}"/></h2>
                        </div>

                    </button>
                </div>
            </div>
        </div>
        <div id="<c:out value="collapse${param.reviewId}"/>" class="accordion-collapse collapse" aria-labelledby="<c:out value="heading${param.reviewId}"/>" data-bs-parent="<c:out value="#accordion${param.reviewId}"/>">
            <div class="accordion-body">
                <jsp:include page="reputation.jsp">
                    <jsp:param name="commentText" value="${param.reviewDescription}"/>
                    <jsp:param name="reviewId" value="${param.reviewId}"/>
                    <jsp:param name="loggedUserName" value="${param.loggedUserName}"/>
                    <jsp:param name="reviewReputation" value="${param.reviewReputation}"/>
                    <jsp:param name="isLikeReviews" value="${param.isLikeReviews}"/>
                    <jsp:param name="isDislikeReviews" value="${param.isDislikeReviews}"/>
                    <jsp:param name="isAdmin" value="${param.isAdmin}"/>
                </jsp:include>

            </div>
        </div>
    </div>
    <div class="modal fade" id="<c:out value="modal${param.reviewId}"/>" tabindex="-1" aria-labelledby="<c:out value="modalLabel${param.reviewId}"/>" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="<c:out value="modalLabel${param.reviewId}"/>"><spring:message code="Delete.Confirmation"/></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p><spring:message code="DeleteReview"/></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><spring:message code="No"/></button>
                    <button type="submit" form="<c:out value="form${param.reviewId}"/>" class="btn btn-success" onclick="this.form.submit(); (this).className += ' spinner-border'; (this).innerText = '|'"><spring:message code="Yes"/></button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="<c:out value="reportReviewModal${param.reviewId}"/>" tabindex="-1" aria-labelledby="<c:out value="reportReviewModalLabel${param.reviewId}"/>" aria-hidden="true">
        <div class="modal-dialog">
            <c:url value="/report/review/${param.reviewId}" var="postPath"/>
            <form:form id="reportReviewForm" modelAttribute="reportReviewForm" action="${postPath}" method="post" enctype="multipart/form-data">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="<c:out value="reportReviewModalLabel${param.reviewId}"/>"><spring:message code="Report.ReviewTitle"/></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div>
                            <ul class="W-no-bullets-list">
                                <li>
                                    <label>
                                        <form:radiobutton path="reportType" value="Spam"/> <spring:message code="Report.Spam"/>
                                        <p class="W-modal-comment-desc"><spring:message code="Report.Spam.Description"/></p>
                                    </label>
                                </li>
                                <li>
                                    <label>
                                        <form:radiobutton path="reportType" value="Insult"/> <spring:message code="Report.Insult"/>
                                        <p class="W-modal-comment-desc"><spring:message code="Report.Insult.Description"/></p>
                                    </label>
                                </li>
                                <li>
                                    <label>
                                        <form:radiobutton path="reportType" value="Inappropriate"/> <spring:message code="Report.Inappropriate"/>
                                        <p class="W-modal-comment-desc"><spring:message code="Report.Inappropriate.Description"/></p>
                                    </label>
                                </li>
                                <li>
                                    <label>
                                        <form:radiobutton path="reportType" value="Unrelated"/> <spring:message code="Report.Unrelated"/>
                                        <p class="W-modal-comment-desc"><spring:message code="Report.Unrelated.Description"/></p>
                                    </label>
                                </li>
                                <li>
                                    <label>
                                        <form:radiobutton path="reportType" value="Other"/> <spring:message code="Report.Other"/>
                                        <p class="W-modal-comment-desc"><spring:message code="Report.Other.Description"/></p>
                                    </label>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><spring:message code="Close"/></button>
                        <button type="submit" class="btn btn-success" onclick="this.form.submit(); (this).className += ' spinner-border'; (this).innerText = '|'"><spring:message code="Form.Submit"/></button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>