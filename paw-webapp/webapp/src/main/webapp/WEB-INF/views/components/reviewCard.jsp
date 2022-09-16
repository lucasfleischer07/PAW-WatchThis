<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>


<div class="accordion W-accordion-margin" id="<c:out value="accordion${param.reviewId}"/>">
    <div class="accordion-item">
        <div class="accordion-header" id="<c:out value="heading${param.reviewId}"/>">
            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="<c:out value="#collapse${param.reviewId}"/>" aria-expanded="false" aria-controls="<c:out value="#collapse${param.reviewId}"/>">
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
                <h2 class="W-title-review"><c:out value="${param.reviewTitle}"/></h2>
                <h5 class="W-creator-review"><c:out value="${param.userName}"/></h5>
            </button>
        </div>
        <div id="<c:out value="collapse${param.reviewId}"/>" class="accordion-collapse collapse" aria-labelledby="<c:out value="heading${param.reviewId}"/>" data-bs-parent="<c:out value="#accordion${param.reviewId}"/>">
            <div class="accordion-body">
                <jsp:include page="comment.jsp">
                    <jsp:param name="commentText" value="${param.reviewDescription}"/>
                </jsp:include>
            </div>
        </div>
    </div>
</div>