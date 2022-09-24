<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="accordion W-accordion-margin" id="<c:out value="accordion${param.reviewId}"/>">
    <div class="accordion-item">
        <div class="accordion-header" id="<c:out value="heading${param.reviewId}"/>">
            <div class="card">
                <div class="card-header W-accordion-card-header">
                    <a href="<c:url value="/profile/${param.userName}"/>" class="W-creator-review"><c:out value="${param.userName}"/></a>
                    <c:choose>
                        <c:when test="${param.userName.equals(param.loggedUserName)}">
                            <div >
                                <div class="W-delete-edit-buttons">
                                    <a href="<c:url value="/reviewForm/edit/${param.contentType}/${param.contentId}/${param.reviewId}"/>">
                                        <button class="btn btn-dark text-nowrap" >
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                                                <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                                <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                            </svg>
                                        </button>
                                    </a>
                                    <form  class="W-delete-form" id="<c:out value="form${param.reviewId}"/>" method="post" action="<c:url value="/review/${param.reviewId}/delete"/>">
                                        <button class="btn btn-danger text-nowrap"  type="button" data-bs-toggle="modal" data-bs-target="#<c:out value="modal${param.reviewId}"/>" >
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                                                <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
                                            </svg>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </c:when>
                        <c:when test="${param.isAdmin}">
                            <div >
                                <div class="W-delete-edit-buttons">
                                    <form  class="W-delete-form" id="<c:out value="form${param.reviewId}"/>" method="post" action="<c:url value="/review/${param.reviewId}/delete"/>">
                                        <button class="btn btn-danger text-nowrap"  type="button" data-bs-toggle="modal" data-bs-target="#<c:out value="modal${param.reviewId}"/>" >
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                                                <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
                                            </svg>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </c:when>
                    </c:choose>
                    <c:if test="${param.userName.equals(param.loggedUserName)}">

                    </c:if>
                </div>
                <div class="card-body W-accordion-card-body">
                    <button id="<c:out value="button${param.reviewId}"/>" class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="<c:out value="#collapse${param.reviewId}"/>" aria-expanded="false" aria-controls="<c:out value="#collapse${param.reviewId}"/>">
                        <%--<c:if test="${param.reviewScore!=null}">
                        <p class="W-reviewScore"><c:out value="${param.reviewScore==null?0:param.reviewScore}"/></p>
                        </c:if>--%>
                        <div class="W-stars">
                            <c:if test="${param.reviewRating!= null && param.reviewRating != 0}">
                                <c:forEach  begin="1" step="1" end="5" var="var">
                                    <c:choose>
                                        <c:when test="${param.reviewRating >= var}">
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
                <jsp:include page="comment.jsp">
                    <jsp:param name="commentText" value="${param.reviewDescription}"/>
                </jsp:include>
            </div>
        </div>
    </div>
    <div class="modal fade" id="<c:out value="modal${param.reviewId}"/>" tabindex="-1" aria-labelledby="<c:out value="modalLabel${param.reviewId}"/>" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="<c:out value="modalLabel${param.reviewId}"/>">Delete confirmation</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>Do you want to delete this review?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No</button>
                    <button type="submit" form="<c:out value="form${param.reviewId}"/>" class="btn btn-primary">Yes</button>
                </div>
            </div>
        </div>
    </div>
</div>