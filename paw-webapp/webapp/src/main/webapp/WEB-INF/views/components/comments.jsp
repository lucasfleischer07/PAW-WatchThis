<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="card W-comment-general-card">
    <div class="card-body W-general-div-comment">
        <div class="W-img-comment-div-margin-right">
            <c:choose>
                <c:when test="${comment.user.image == null}">
                    <img src="<c:url value="/resources/img/backgorundImage.png"/> " alt="User_img" class="W-comment-profile-picture">
                </c:when>
                <c:otherwise>
                    <img src="<c:url value="/profile/${comment.user.userName}/profileImage"/> " alt="User_img" class="W-comment-profile-picture">
                </c:otherwise>
            </c:choose>
        </div>
        <div class="W-comment-username-report-description-div ">
            <div class="W-comment-username-and-report">
                <div>
                    <a href="<c:url value="/profile/${comment.user.userName}"/>" class="W-creator-review"><c:url value="${comment.user.userName}"/></a>
                </div>
                <div class="W-report-and-delete-comment-buttons">
                    <c:choose>
                        <c:when test="${param.loggedUserName != 'null'}">
                            <c:if test="${comment.user.userName == param.loggedUserName || param.isAdmin == 'true'}">
                                <form class="W-delete-form" id="form${comment.commentId}" method="post" action="<c:url value="/comment/${comment.commentId}/delete"/>">
                                    <button class="btn btn-danger text-nowrap"  type="button" data-bs-toggle="modal" data-bs-target="#deleteCommentModal" >
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                                            <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
                                        </svg>
                                    </button>
                                </form>
                                <div class="modal fade" id="deleteCommentModal" tabindex="-1" aria-labelledby="<c:out value="modalLabelDeleteComment"/>" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title"><spring:message code="Delete.Confirmation"/></h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <p><spring:message code="DeleteComment"/></p>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><spring:message code="No"/></button>
                                                <button type="submit" form="<c:out value="form${comment.commentId}"/>" class="btn btn-success" onclick="this.form.submit(); (this).className += ' spinner-border'; (this).innerText = '|'"><spring:message code="Yes"/></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${comment.user.userName != param.loggedUserName && !param.alreadyReport}">
                                <button id="reportCommentButton" type="button" class="btn btn-light W-background-color-report" data-bs-toggle="modal" data-bs-target="#reportCommentModal">
                                    <svg data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="<spring:message code="Report.Add"/>" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#b21e26" class="bi bi-exclamation-circle" viewBox="0 0 16 16">
                                        <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                        <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
                                    </svg>
                                </button>
                                <div class="modal fade" id="reportCommentModal" tabindex="-1" aria-labelledby="reportCommentModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <c:url value="/report/comment/${comment.commentId}" var="postPath"/>
                                        <form:form id="reportCommentForm" modelAttribute="reportCommentForm" action="${postPath}" method="post" enctype="multipart/form-data">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="reportCommentModalLabel"><spring:message code="Report.CommentTitle"/></h5>
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
                                                                    <p class="W-modal-comment-desc"><spring:message code="Report.Insult.Description"/></p>
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
                                                                    <form:radiobutton path="reportType" value="Others"/> <spring:message code="Report.Other"/>
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
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <div>
                                <button id="reportCommentButtonNoLogin" type="button" class="btn btn-light" data-bs-toggle="modal" data-bs-target="#reportCommentModalNoLogin">
                                    <svg data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="<spring:message code="Report.Add"/>" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#b21e26" class="bi bi-exclamation-circle" viewBox="0 0 16 16">
                                        <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                        <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
                                    </svg>
                                </button>
                            </div>

                            <div class="modal fade" id="reportCommentModalNoLogin" tabindex="-1" aria-labelledby="reportCommentModalNoLoginLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="reportCommentModalNoLoginLabel"><spring:message code="Report.CommentTitle"/></h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <span><spring:message code="Report.CommentWarningAdd"/></span>
                                            <span><spring:message code="Review.WarningAddMessage"/></span>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><spring:message code="Close"/></button>
                                            <a href="<c:url value="/login/sign-in"/>"><button type="button" class="btn btn-success" onclick="this.form.submit(); (this).className += ' spinner-border'; (this).innerText = '|'"><spring:message code="Login.LoginMessage"/></button></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="W-comment-text">
                <p id="floatingTextarea2"><c:out value="${comment.text}"/></p>
            </div>
        </div>
    </div>
</div>
<script>
    const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
    const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
</script>