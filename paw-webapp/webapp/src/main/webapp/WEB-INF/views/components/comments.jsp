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
        <div class="W-comment-image-text">
            <div class="W-comment-image">
                <a href="<c:url value="/"/>" class="W-creator-review">Holaaaa</a>
                <%--                <a href="<c:url value="/"/>" class="W-creator-review"><c:out value="${param.userName}"/></a>--%>
            </div>
            <div class="W-comment-text">
                <p id="floatingTextarea2"> hola este es un comentario</p>
            </div>
        </div>
    </div>
</div>