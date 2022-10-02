<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

        <title><spring:message code="CreateContentMessage"/></title>
    </head>
    <body>
        <jsp:include page="components/header.jsp">
            <jsp:param name="type" value="${details.type}"/>
            <jsp:param name="userName" value="${userName}"/>
            <jsp:param name="userId" value="${userId}"/>
            <jsp:param name="admin" value="${admin}"/>
        </jsp:include>




            <c:url value="/content/editInfo/${contentId}" var="postPath"/>
        <c:choose>
            <c:when test="${create==true}">
                <c:url value="/content/create" var="postPath"/>
                <form:form modelAttribute="contentCreate" action="${postPath}" method="post" enctype="multipart/form-data">
                    <div class="W-general-div-review-info">
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
                                <form:label path="name" class="form-label"><spring:message code="CreateContent.ContentName"/> <span class="W-red-asterisco">*</span></form:label>
                                <p class="W-review-registration-text"><spring:message code="CreateContent.CharacterLimits" arguments="1,100"/></p>
                                <form:input type="text" class="form-control" path="name" placeholder="Lord of The Rings"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="description" element="p" cssStyle="color: red"/>
                                <form:label path="description" class="form-label"><spring:message code="CreateContent.ContentDescription"/> <span class="W-red-asterisco">*</span></form:label>
                                <p class="W-review-registration-text"><spring:message code="CreateContent.CharacterLimits" arguments="20,500"/></p>
                                <form:textarea class="form-control" id="MyTextArea" path="description" rows="3"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="releaseDate" element="p" cssStyle="color: red"/>
                                <form:label path="releaseDate" class="form-label"><spring:message code="CreateContent.ContentReleaseDate"/> <span class="W-red-asterisco">*</span></form:label>
                                <p class="W-review-registration-text"><spring:message code="CreateContent.CharacterLimits"/></p>
                                <form:input type="text" class="form-control" path="releaseDate" placeholder="2002"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="creator" element="p" cssStyle="color: red"/>
                                <form:label path="creator" class="form-label"><spring:message code="CreateContent.ContentCreator"/> <span class="W-red-asterisco">*</span></form:label>
                                <p class="W-review-registration-text"><spring:message code="CreateContent.CharacterLimits" arguments="10,100"/></p>
                                <form:input type="text" class="form-control" path="creator" placeholder="Tarantino"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="duration" element="p" cssStyle="color: red"/>
                                <form:label path="duration" class="form-label"><spring:message code="CreateContent.ContentDuration"/> <span class="W-red-asterisco">*</span></form:label>
                                <form:input type="number" class="form-control" path="duration" placeholder="120"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="genre" element="p" cssStyle="color: red"/>
                                <form:label path="genre" class="form-label"><spring:message code="CreateContent.ContentGenre"/> <span class="W-red-asterisco">*</span></form:label>
                                <form:select path="genre" class="form-select" aria-label="Default select example">
                                    <form:option value="Action"><spring:message code="Genre.Action"/></form:option>
                                    <form:option value="Science Fiction"><spring:message code="Genre.Science"/></form:option>
                                    <form:option value="Comedy"><spring:message code="Genre.Comedy"/></form:option>
                                    <form:option value="Adventure"><spring:message code="Genre.Adventure"/></form:option>
                                    <form:option value="Drama"><spring:message code="Genre.Drama"/></form:option>
                                    <form:option value="Horror"><spring:message code="Genre.Horror"/></form:option>
                                    <form:option value="Animation"><spring:message code="Genre.Animation"/></form:option>
                                    <form:option value="Thriller"><spring:message code="Genre.Thriller"/></form:option>
                                    <form:option value="Mistery"><spring:message code="Genre.Mystery"/></form:option>
                                    <form:option value="Crime"><spring:message code="Genre.Crime"/></form:option>
                                    <form:option value="Fantasy"><spring:message code="Genre.Fantasy"/></form:option>
                                    <form:option value="Romance"><spring:message code="Genre.Romance"/></form:option>
                                </form:select>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="type" element="p" cssStyle="color: red"/>
                                <form:label path="type" class="form-label"><spring:message code="CreateContent.ContentType"/> <span class="W-red-asterisco">*</span></form:label>
                                <form:select path="type" class="form-select" aria-label="Default select example">
                                    <form:option value="movie"><spring:message code="CreateContent.ContentType.Movie"/></form:option>
                                    <form:option value="serie"><spring:message code="CreateContent.ContentType.Serie"/></form:option>
                                </form:select>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="contentPicture" element="p" cssClass="form-error-label" cssStyle="color: red"/>
                                <form:label path="type" class="form-label"><span class="W-red-asterisco">*</span></form:label>
                                <form:input type="file" accept="image/gif, image/jpeg, image/jpg,  image/png" class="form-control" path="contentPicture" cssClass="W-input-width"/>
                            </div>
                        </div>
                    </div>
                    <div class="W-submit-cancel-buttons">
                        <a href="<c:url value="/"/>"><button type="button" class="btn btn-danger" id="cancelButton" onclick="(this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('submitButton').disabled=true"><spring:message code="Form.Cancel"/></button></a>
                        <button id="submitButton" type="submit" class="btn btn-success" onclick="this.form.submit(); (this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('cancelButton').disabled=true"><spring:message code="Form.Submit"/></button>
                    </div>
                </form:form>
            </c:when>
            <c:when test="${create==false}">
                <c:url value="/content/editInfo/${contentId}" var="postPath"/>
                <form:form modelAttribute="contentEditForm" action="${postPath}" method="post" enctype="multipart/form-data">
                    <div class="W-general-div-review-info">
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
                                <form:label path="name" class="form-label"><spring:message code="CreateContent.ContentName"/> <span class="W-red-asterisco">*</span></form:label>
                                <p class="W-review-registration-text"><spring:message code="CreateContent.CharacterLimits" arguments="1,100"/></p>
                                <form:input type="text" class="form-control" path="name" placeholder="Lord of The Rings"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="description" element="p" cssStyle="color: red"/>
                                <form:label path="description" class="form-label"><spring:message code="CreateContent.ContentDescription"/> <span class="W-red-asterisco">*</span></form:label>
                                <p class="W-review-registration-text"><spring:message code="CreateContent.CharacterLimits" arguments="20,500"/></p>
                                <form:textarea class="form-control" id="MyTextArea" path="description" rows="3"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="releaseDate" element="p" cssStyle="color: red"/>
                                <form:label path="releaseDate" class="form-label"><spring:message code="CreateContent.ContentReleaseDate"/> <span class="W-red-asterisco">*</span></form:label>
                                <p class="W-review-registration-text"><spring:message code="CreateContent.CharacterLimitsRelease"/></p>
                                <form:input type="text" class="form-control" path="releaseDate" placeholder="2002"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="creator" element="p" cssStyle="color: red"/>
                                <form:label path="creator" class="form-label"><spring:message code="CreateContent.ContentCreator"/> <span class="W-red-asterisco">*</span></form:label>
                                <p class="W-review-registration-text"><spring:message code="CreateContent.CharacterLimits" arguments="10,100"/></p>
                                <form:input type="text" class="form-control" path="creator" placeholder="Tarantino"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="duration" element="p" cssStyle="color: red"/>
                                <form:label path="duration" class="form-label"><spring:message code="CreateContent.ContentDuration"/> <span class="W-red-asterisco">*</span></form:label>
                                <form:input type="number" class="form-control" path="duration" placeholder="120"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="genre" element="p" cssStyle="color: red"/>
                                <form:label path="genre" class="form-label"><spring:message code="CreateContent.ContentGenre"/> <span class="W-red-asterisco">*</span></form:label>
                                <form:select path="genre" class="form-select" aria-label="Default select example">
                                    <form:option value="Action"><spring:message code="Genre.Action"/></form:option>
                                    <form:option value="Science Fiction"><spring:message code="Genre.Science"/></form:option>
                                    <form:option value="Comedy"><spring:message code="Genre.Comedy"/></form:option>
                                    <form:option value="Adventure"><spring:message code="Genre.Adventure"/></form:option>
                                    <form:option value="Drama"><spring:message code="Genre.Drama"/></form:option>
                                    <form:option value="Horror"><spring:message code="Genre.Horror"/></form:option>
                                    <form:option value="Animation"><spring:message code="Genre.Animation"/></form:option>
                                    <form:option value="Thriller"><spring:message code="Genre.Thriller"/></form:option>
                                    <form:option value="Mistery"><spring:message code="Genre.Mystery"/></form:option>
                                    <form:option value="Crime"><spring:message code="Genre.Crime"/></form:option>
                                    <form:option value="Fantasy"><spring:message code="Genre.Fantasy"/></form:option>
                                    <form:option value="Romance"><spring:message code="Genre.Romance"/></form:option>
                                </form:select>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="type" element="p" cssStyle="color: red"/>
                                <form:label path="type" class="form-label"><spring:message code="CreateContent.ContentType"/> <span class="W-red-asterisco">*</span></form:label>
                                <form:select path="type" class="form-select" aria-label="Default select example">
                                    <form:option value="movie"><spring:message code="CreateContent.ContentType.Movie"/></form:option>
                                    <form:option value="serie"><spring:message code="CreateContent.ContentType.Serie"/></form:option>
                                </form:select>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="contentPicture" element="p" cssClass="form-error-label" cssStyle="color: red"/>
                                <form:input type="file" accept="image/gif, image/jpeg, image/jpg,  image/png" class="form-control" path="contentPicture" cssClass="W-input-width"/>
                            </div>
                        </div>
                    </div>
                    <div class="W-submit-cancel-buttons">
                        <a href="<c:url value="/"/>"><button type="button" class="btn btn-danger" id="cancelButton" onclick="(this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('submitButton').disabled=true"><spring:message code="Form.Cancel"/></button></a>
                        <button id="submitButton" type="submit" class="btn btn-success" onclick="this.form.submit(); (this).disabled = true; (this).className += ' spinner-border'; (this).innerText = '|'; document.getElementById('cancelButton').disabled=true"><spring:message code="Form.Submit"/></button>
                    </div>
                </form:form>
            </c:when>
        </c:choose>



        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
        <script>const simplemde = new SimpleMDE({ element: document.getElementById("MyTextArea")});</script>

    </body>
</html>
