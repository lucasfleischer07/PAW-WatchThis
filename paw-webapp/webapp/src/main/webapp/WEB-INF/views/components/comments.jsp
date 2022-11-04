<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="card" style="width: 80%">
    <div class="card-body" style="display: flex">
        <div>
            <%--            <c:choose>--%>
            <%--                <c:when test="${user.image == null}">--%>
            <img src="<c:url value="/resources/img/defaultUserImg.png"/> " alt="User_img" class="W-comment-profile-picture">
            <%--                </c:when>--%>
            <%--                <c:otherwise>--%>
            <%--                    <img src="<c:url value="/profile/${user.userName}/profileImage"/> " alt="User_img" class="W-edit-profile-picture">--%>
            <%--                </c:otherwise>--%>
            <%--            </c:choose>        --%>
        </div>
        <div>
            <div>
                <a href="<c:url value="/"/>" class="W-creator-review">Holaaaa</a>
                <%--                <a href="<c:url value="/"/>" class="W-creator-review"><c:out value="${param.userName}"/></a>--%>
            </div>
            <div>
                <p id="floatingTextarea2" ></p>
            </div>
        </div>
    </div>
</div>