<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

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
        <link href="<c:url value="/resources/css/reviewRegistrationStyles.css"/>" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">

        <title>Review Registration</title>
    </head>

    <body>
        <jsp:include page="components/header.jsp">
            <jsp:param name="type" value="${details.type}"/>
            <jsp:param name="userName" value="${userName}"/>
            <jsp:param name="userId" value="${userId}"/>
        </jsp:include>

        <c:url value="/reviewForm/${details.type}/${details.id}/${userId}" var="postPath"/>
        <form:form modelAttribute="registerForm" action="${postPath}" method="post">
            <div class="W-general-div-review-info">
                <div class="W-review-registration-img-and-name">
                    <img class="W-review-registration-img" src="<c:url value="${details.image}"/>" alt="<c:out value="${details.name}"/>">
                    <h4 class="W-review-registration-name"><c:out value="${details.name}"/></h4>
                </div>
                <div class="card W-review-card">
                    <c:if test="${errorMsg!=null && errorMsg != ''}">
                        <div class="alert alert-danger d-flex align-items-center" role="alert">
                            <div class="W-register-errorMsg">
                                <c:out value="${errorMsg}"/>
                            </div>
                        </div>
                    </c:if>
                    <div class="mb-3 W-input-label-review-info">
                        <form:errors path="name" element="p" cssStyle="color: red"/>
                        <form:label path="name" class="form-label">Review name <span class="W-red-asterisco">*</span></form:label>
                        <p class="W-review-registration-text">(Must be between 6 and 200 characters)</p>
                        <form:input type="text" class="form-control" path="name" placeholder="My review"/>
                    </div>
                    <div class="mb-3 W-input-label-review-info">
                        <form:errors path="description" element="p" cssStyle="color: red"/>
                        <form:label path="description" class="form-label">Review description <span class="W-red-asterisco">*</span></form:label>
                        <p class="W-review-registration-text">(Must be between 20 and 2000 characters)</p>
                        <form:textarea class="form-control" id="MyTextArea" path="description" rows="3"/>
                    </div>
                    <div class="mb-3 W-input-label-review-info">
                        <form:errors path="rating" element="p" cssStyle="color: red"/>
                        <form:label path="rating" class="form-label">Rating <span class="W-red-asterisco">*</span></form:label>
                        <form:select path="rating" class="form-select" aria-label="Default select example">
                            <form:option value="1">1 star</form:option>
                            <form:option value="2">2 stars</form:option>
                            <form:option value="3">3 stars</form:option>
                            <form:option value="4">4 stars</form:option>
                            <form:option selected="true" value="5">5 stars</form:option>
                        </form:select>
                    </div>
                </div>
            </div>
            <div class="W-submit-cancel-buttons">
                <a href="<c:url value="/${details.type}/${details.id}"/>"><button type="button" class="btn btn-danger" id="cancelButton" onclick="(this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('submitButton').disabled=true">Cancel</button></a>
                <button id="submitButton" type="submit" class="btn btn-success" onclick="this.form.submit(); (this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('cancelButton').disabled=true">Submit</button>
            </div>
        </form:form>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
        <script>const simplemde = new SimpleMDE({ element: document.getElementById("MyTextArea")});</script>
    </body>
</html>