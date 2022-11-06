<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="card" style="width: 80%">
    <div class="card-body W-general-div-comment">
        <div>
            <%--            <c:choose>--%>
            <%--                <c:when test="${user.image == null}">--%>
            <img src="<c:url value="/resources/img/backgorundImage.png"/> " alt="User_img" class="W-comment-profile-picture">
            <%--                </c:when>--%>
            <%--                <c:otherwise>--%>
            <%--                    <img src="<c:url value="/profile/${user.userName}/profileImage"/> " alt="User_img" class="W-edit-profile-picture">--%>
            <%--                </c:otherwise>--%>
            <%--            </c:choose>        --%>
        </div>
        <div class="W-comment-username-report-description-div ">
            <div class="W-comment-username-and-report">
                <div>
                    <a href="<c:url value="/"/>" class="W-creator-review">Holaaaa</a>
                    <%--                <a href="<c:url value="/"/>" class="W-creator-review"><c:out value="${param.userName}"/></a>--%>
                </div>
                <div>
                    <c:choose>
                        <c:when test="${param.loggedUserName != 'null'}">
                            <button id="reportCommentButton" type="button" class="btn btn-light W-background-color-report" data-bs-toggle="modal" data-bs-target="#reportCommentModal">
                                <svg data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="<spring:message code="Report.Add"/>" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#b21e26" class="bi bi-exclamation-circle" viewBox="0 0 16 16">
                                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                    <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
                                </svg>
                            </button>
                            <div class="modal fade" id="reportCommentModal" tabindex="-1" aria-labelledby="reportCommentModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <c:url value="/report/comment/${param.reviewId}" var="postPath"/>
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
                                                            <form:radiobutton path="reportType" value="Spam"/> <spring:message code="Report.Spam"/>
                                                        </li>
                                                        <li>
                                                            <form:radiobutton path="reportType" value="Insult"/> <spring:message code="Report.Insult"/>
                                                        </li>
                                                        <li>
                                                            <form:radiobutton path="reportType" value="Inappropiate"/> <spring:message code="Report.Inappropriate"/>
                                                        </li>
                                                        <li>
                                                            <form:radiobutton path="reportType" value="Unrelated"/> <spring:message code="Report.Unrelated"/>
                                                        </li>
                                                        <li>
                                                            <form:radiobutton path="reportType" value="Others"/> <spring:message code="Report.Other"/>
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
                        </c:when>
                        <c:otherwise>
                            <button id="reportCommentButtonNoLogin" type="button" class="btn btn-light" data-bs-toggle="modal" data-bs-target="#reportCommentModalNoLogin">
                                <svg data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title="<spring:message code="Report.Add"/>" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#b21e26" class="bi bi-exclamation-circle" viewBox="0 0 16 16">
                                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                    <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
                                </svg>
                            </button>
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
                                            <a href="<c:url value="/login/sign-in"/>"><button type="button" class="btn btn-success"><spring:message code="Login.LoginMessage"/></button></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="W-comment-text">
                <p id="floatingTextarea2"> hola este es un comentario</p>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
<script>
    const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
    const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
</script>