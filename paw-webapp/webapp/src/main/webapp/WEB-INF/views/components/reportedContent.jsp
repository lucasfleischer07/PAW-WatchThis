<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="card W-card-reported-width">
    <div class="card-body W-general-div-reports">
        <div class="W-username-report-description-div">
            <div class="W-comment-username-and-report">
                <div>
                    <a href="<c:url value="/"/>" class="W-creator-review W-margin-right-reports">Usuario</a>
                    <a href="<c:url value="/"/>" class="W-creator-review">Contenido donde fue realizado</a>
<%--                <a href="<c:url value="/"/>" class="W-creator-review"><c:out value="${param.userName}"/></a>--%>
                </div>
                <div class="W-amount-reports-and-delete-button">
                    <div class="W-report-margin-zero">
                        <c:choose>
                            <c:when test="${param.reportsAmount == 1}">
                                <p class="W-report-margin-zero"><spring:message code="Report.Report" arguments="${param.reportsAmount}"/></p>
                            </c:when>
                            <c:otherwise>
                                <p class="W-report-margin-zero"><spring:message code="Report.Reports" arguments="${param.reportsAmount}"/></p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="W-delete-button-report">
                        <form class="W-delete-form-reported" id="<c:out value="form${param.contentType}"/>" method="post" action="<c:url value="/${param.contentType}/${param.contentId}/delete"/>">
                            <button class="btn btn-danger text-nowrap"  type="button" data-bs-toggle="modal" data-bs-target="#<c:out value="modal${param.contentId}"/>">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                                    <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
                                </svg>
                            </button>
                        </form>

                        <div class="modal fade" id="<c:out value="modal${param.contentId}"/>" tabindex="-1" aria-labelledby="<c:out value="modalLabel${param.contentId}"/>" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="<c:out value="modalLabel${param.contentId}"/>"><spring:message code="Delete.Confirmation"/></h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <c:choose>
                                            <c:when test="${param.contentType == 'review'}">
                                                <p><spring:message code="DeleteReview"/></p>
                                            </c:when>
                                            <c:otherwise>
                                                <p><spring:message code="DeleteComment"/></p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><spring:message code="No"/></button>
                                        <button type="submit" form="<c:out value="form${param.contentId}"/>" class="btn btn-success" ><spring:message code="Yes"/></button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <div class="W-comment-text">
                <p class="W-report-description-paragraph" id="floatingTextarea2"> hola este es un comentario reportado</p>
            </div>
            <div class="W-type-of-report">
                <p class="W-report-margin-zero W-color-report-type">Comment</p>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
<script>
    const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
    const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
</script>