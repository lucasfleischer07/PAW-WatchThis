<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="card W-card-reported-width">
    <div class="card-body W-general-div-reports">
        <div class="W-username-report-description-div">
            <div class="W-comment-username-and-report">
                <div>
                    <c:choose>
                        <c:when test="${param.reportType == 'comment'}">
                            <a href="<c:url value="/profile/${param.userName}"/>" class="W-creator-review W-margin-right-reports"><spring:message code="Comment.Owner" arguments="${param.userName}"/></a>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="/profile/${param.userName}"/>" class="W-creator-review W-margin-right-reports"><spring:message code="Review.Owner" arguments="${param.userName}"/></a>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${param.reportType == 'comment'}">
                        <a href="<c:url value="/profile/${param.reviewCreatorUserName}"/>" class="W-creator-review W-margin-right-reports"><spring:message code="Review.Owner" arguments="${param.reviewCreatorUserName}"/></a>
                    </c:if>
                </div>
                <div>
                    <a href="<c:url value="/${param.contentType}/${param.contentId}"/>" class="W-creator-review"><c:out value="${param.contentName}"/></a>
                </div>
                <div class="W-amount-reports-and-delete-button">
                    <div class="W-delete-button-report">
                        <form class="W-delete-form-reported" id="<c:out value="form${param.typeId}${param.reportType}"/>" method="post" action="<c:url value="/${param.reportType}/${param.typeId}/delete"/>">
                            <button class="btn btn-danger text-nowrap"  type="button" data-bs-toggle="modal" data-bs-target="#<c:out value="modal${param.typeId}"/>">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                                    <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
                                </svg>
                            </button>
                        </form>

                        <div class="modal fade" id="<c:out value="modal${param.typeId}"/>" tabindex="-1" aria-labelledby="<c:out value="modalLabel${param.typeId}"/>" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="<c:out value="modalLabel${param.typeId}"/>"><spring:message code="Delete.Confirmation"/></h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <c:choose>
                                            <c:when test="${param.reportType == 'review'}">
                                                <p><spring:message code="DeleteReview"/></p>
                                            </c:when>
                                            <c:otherwise>
                                                <p><spring:message code="DeleteComment"/></p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><spring:message code="No"/></button>
                                        <button type="submit" form="<c:out value="form${param.typeId}${param.reportType}"/>" class="btn btn-success" onclick="this.form.submit(); (this).className += ' spinner-border'; (this).innerText = '|'"><spring:message code="Yes"/></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="W-comment-text">
                <c:choose>
                    <c:when test="${param.reportType == 'comment'}">
                        <p id="commentTextArea1" class="W-report-review-paragraph">"<c:out value="${param.reviewNameOfReportedComment}"/>"</p>
                        <p id="commentTextArea" class="W-report-description-paragraph" ><c:out value="${param.reportDescription}"/></p>
                    </c:when>
                    <c:otherwise>
                        <p id="commentTextArea" class="W-report-description-paragraph-review" ><c:out value="${param.reportDescription}"/></p>
                        <p id="commentTextArea1" class="W-report-review-paragraph-review">"<c:out value="${param.reportDescription2}"/>"</p>
                    </c:otherwise>
                </c:choose>
                <script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
                <script>
                    let previousText = document.getElementById('commentTextArea').innerHTML
                    document.getElementById('commentTextArea').innerHTML = marked.parse(previousText);
                </script>
            </div>
            <div class="W-type-of-report">
                <div>
                    <c:choose>
                        <c:when test="${param.reportsAmount == 1}">
                            <p class="W-report-margin-zero" data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="${param.reportReasons}"><spring:message code="Report.Report" arguments="${param.reportsAmount}"/></p>
                        </c:when>
                        <c:otherwise>
                            <p class="W-report-margin-zero" data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="${param.reportReasons}"><spring:message code="Report.Reports" arguments="${param.reportsAmount}"/></p>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div>
                    <c:choose>
                        <c:when test="${param.reportType == 'review'}">
                            <p class="W-report-margin-zero W-color-report-type"><spring:message code="Profile.Review"/></p>
                        </c:when>
                        <c:otherwise>
                            <p class="W-report-margin-zero W-color-report-type"><spring:message code="Comment.Title"/></p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>