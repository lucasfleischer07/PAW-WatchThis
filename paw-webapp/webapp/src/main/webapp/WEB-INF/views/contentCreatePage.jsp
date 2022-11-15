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

        <title><spring:message code="CreateContent.Message"/></title>
    </head>
    <body>
        <jsp:include page="components/noFilterHeader.jsp">
            <jsp:param name="type" value="${type}"/>
            <jsp:param name="userName" value="${userName}"/>
            <jsp:param name="userId" value="${userId}"/>
            <jsp:param name="admin" value="${admin}"/>
            <jsp:param name="genre" value="${param.genre}"/>
        </jsp:include>
        <c:choose>
            <c:when test="${create == true}">
                <h3 class="W-creating-content-title"><spring:message code="CreateContent.Title"/></h3>
            </c:when>
            <c:otherwise>
                <h3 class="W-creating-content-title"><spring:message code="EditContent.Title"/></h3>
            </c:otherwise>
        </c:choose>
        <c:url value="/content/editInfo/${contentId}" var="postPath"/>
        <c:choose>
            <c:when test="${create==true}">
                <c:url value="/content/create" var="postPath"/>
                <form:form modelAttribute="contentCreate" action="${postPath}" method="post" enctype="multipart/form-data" id="form">
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
                                <form:errors path="name" element="p" cssStyle="color: #b21e26"/>
                                <form:label path="name" class="form-label"><spring:message code="CreateContent.ContentName"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                <p class="W-review-registration-text"><spring:message code="CreateContent.CharacterLimits" arguments="1,55"/></p>
                                <spring:message code="Placeholder.FilmEx" var="placeholder"/>
                                <form:input type="text" class="form-control" path="name" placeholder="${placeholder}"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="description" element="p" cssStyle="color: #b21e26"/>
                                <form:label path="description" class="form-label"><spring:message code="CreateContent.ContentDescription"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                <p class="W-review-registration-text"><spring:message code="CreateContent.CharacterLimits" arguments="20,2000"/></p>
                                <form:textarea class="form-control" id="MyTextArea" path="description" rows="3"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="releaseDate" element="p" cssStyle="color: #b21e26"/>
                                <form:label path="releaseDate" class="form-label"><spring:message code="CreateContent.ContentReleaseDate"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                <p class="W-review-registration-text"><spring:message code="CreateContent.YearCharacterLimits"/></p>
                                <spring:message code="Placeholder.YearEx" var="placeholder"/>
                                <form:input type="text" class="form-control" path="releaseDate" placeholder="${placeholder}"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="creator" element="p" cssStyle="color: #b21e26"/>
                                <form:label path="creator" class="form-label"><spring:message code="CreateContent.ContentCreator"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                <p class="W-review-registration-text"><spring:message code="CreateContent.CharacterLimits" arguments="4,100"/></p>
                                <spring:message code="Placeholder.CreatorEx" var="placeholder"/>
                                <form:input type="text" class="form-control" path="creator" placeholder="${placeholder}"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="duration" element="p" cssStyle="color: #b21e26"/>
                                <form:label path="duration" class="form-label"><spring:message code="CreateContent.ContentDuration"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                <spring:message code="Placeholder.DurationEx" var="placeholder"/>
                                <form:input type="number" class="form-control" path="duration" placeholder="${placeholder}"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="genre" element="p" cssStyle="color: #b21e26"/>
                                <form:label path="genre" class="form-label"><spring:message code="CreateContent.ContentGenre"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>

                                <button id="createGenre" onclick="dropDownStayGenreCreate()" type="button"class="W-genre-create-button btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"  >
                                    <spring:message code="Genre.Message2"/>
                                </button>

                                <ul class="dropdown-menu" id="drop3">
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Action"/> <spring:message code="Genre.Action"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)"  cssClass="px-2" path="genre" value="Sci-Fi"/> <spring:message code="Genre.Science"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Comedy"/> <spring:message code="Genre.Comedy"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Adventure"/> <spring:message code="Genre.Adventure"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Drama"/> <spring:message code="Genre.Drama"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Horror"/> <spring:message code="Genre.Horror"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Animation"/> <spring:message code="Genre.Animation"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Thriller"/> <spring:message code="Genre.Thriller"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Mystery"/> <spring:message code="Genre.Mystery"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Crime"/> <spring:message code="Genre.Crime"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Fantasy"/> <spring:message code="Genre.Fantasy"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Romance"/> <spring:message code="Genre.Romance"/></label>
                                    </li>
                                </ul>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="type" element="p" cssStyle="color: #b21e26"/>
                                <form:label path="type" class="form-label"><spring:message code="CreateContent.ContentType"/>
                                    <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                <form:select path="type" class="form-select" aria-label="Default select example">
                                    <form:option value="movie"><spring:message code="CreateContent.ContentType.Movie"/></form:option>
                                    <form:option value="serie"><spring:message code="CreateContent.ContentType.Serie"/></form:option>
                                </form:select>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="contentPicture" element="p" cssClass="form-error-label" cssStyle="color: #b21e26"/>
                                <form:label path="type" class="form-label"><span class="W-red-asterisco"><spring:message code="Asterisk"/></span></span></form:label>
                                <form:input type="file" accept="image/gif, image/jpeg, image/jpg,  image/png" class="form-control" path="contentPicture" cssClass="W-input-width"/>
                            </div>
                        </div>
                    </div>
                    <div class="W-submit-cancel-buttons">
                        <a href="<c:url value="/"/>"><button type="button" class="btn btn-danger" onclick="(this).className += ' spinner-border'; (this).innerText = '|'"><spring:message code="Form.Cancel"/></button></a>
                        <button type="submit" class="btn btn-success" onclick="this.form.submit(); (this).className += ' spinner-border'; (this).innerText = '|'"><spring:message code="Form.Submit"/></button>
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
                                <form:errors path="name" element="p" cssStyle="color: #b21e26"/>
                                <form:label path="name" class="form-label"><spring:message code="CreateContent.ContentName"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                <p class="W-review-registration-text"><spring:message code="CreateContent.CharacterLimits" arguments="1,55"/></p>
                                <spring:message code="Placeholder.FilmEx" var="placeholder"/>
                                <form:input type="text" class="form-control" path="name" placeholder="${placeholder}"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="description" element="p" cssStyle="color: #b21e26"/>
                                <form:label path="description" class="form-label"><spring:message code="CreateContent.ContentDescription"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                <p class="W-review-registration-text"><spring:message code="CreateContent.CharacterLimits" arguments="20,500"/></p>
                                <form:textarea class="form-control" id="MyTextArea" path="description" rows="3"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="releaseDate" element="p" cssStyle="color: #b21e26"/>
                                <form:label path="releaseDate" class="form-label"><spring:message code="CreateContent.ContentReleaseDate"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                <p class="W-review-registration-text"><spring:message code="CreateContent.YearCharacterLimits"/></p>
                                <spring:message code="Placeholder.YearEx" var="placeholder"/>
                                <form:input type="text" class="form-control" path="releaseDate" placeholder="${placeholder}"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="creator" element="p" cssStyle="color: #b21e26"/>
                                <form:label path="creator" class="form-label"><spring:message code="CreateContent.ContentCreator"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                <p class="W-review-registration-text"><spring:message code="CreateContent.CharacterLimits" arguments="4,100"/></p>
                                <spring:message code="Placeholder.CreatorEx" var="placeholder"/>
                                <form:input type="text" class="form-control" path="creator" placeholder="${placeholder}"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="duration" element="p" cssStyle="color: #b21e26"/>
                                <form:label path="duration" class="form-label"><spring:message code="CreateContent.ContentDuration"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                <spring:message code="Placeholder.DurationEx" var="placeholder"/>
                                <form:input type="number" class="form-control" path="duration" placeholder="${placeholder}"/>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="genre" element="p" cssStyle="color: #b21e26"/>
                                <form:label path="genre" class="form-label"><spring:message code="CreateContent.ContentGenre"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>

                                <button id="createGenre" onclick="dropDownStayGenreCreate()" type="button" class="W-genre-create-button btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false" >
                                    <spring:message code="Genre.Message2"/>
                                </button>

                                <ul class="dropdown-menu" id="drop3" >
                                    <li class="mb-1 px-2" >
                                        <label><form:checkbox onchange="update(this)" cssClass="px-2" path="genre" value="Action"/> <spring:message code="Genre.Action"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onchange="update(this)" cssClass="px-2" path="genre" value="Sci-Fi"/> <spring:message code="Genre.Science"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Comedy"/> <spring:message code="Genre.Comedy"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Adventure"/> <spring:message code="Genre.Adventure"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Drama"/> <spring:message code="Genre.Drama"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Horror"/> <spring:message code="Genre.Horror"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Animation"/> <spring:message code="Genre.Animation"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Thriller"/> <spring:message code="Genre.Thriller"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Mystery"/> <spring:message code="Genre.Mystery"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Crime"/> <spring:message code="Genre.Crime"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Fantasy"/> <spring:message code="Genre.Fantasy"/></label>
                                    </li>
                                    <li class="mb-1 px-2">
                                        <label><form:checkbox onclick="update(this)" cssClass="px-2" path="genre" value="Romance"/> <spring:message code="Genre.Romance"/></label>
                                    </li>
                                </ul>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="type" element="p" cssStyle="color: #b21e26"/>
                                <form:label path="type" class="form-label"><spring:message code="CreateContent.ContentType"/> <span class="W-red-asterisco"><spring:message code="Asterisk"/></span></form:label>
                                <form:select path="type" class="form-select" aria-label="Default select example">
                                    <form:option value="movie"><spring:message code="CreateContent.ContentType.Movie"/></form:option>
                                    <form:option value="serie"><spring:message code="CreateContent.ContentType.Serie"/></form:option>
                                </form:select>
                            </div>
                            <div class="mb-3 W-input-label-review-info">
                                <form:errors path="contentPicture" element="p" cssClass="form-error-label" cssStyle="color: #b21e26"/>
                                <form:input type="file" accept="image/gif, image/jpeg, image/jpg,  image/png" class="form-control" path="contentPicture" cssClass="W-input-width"/>
                            </div>
                        </div>
                    </div>
                    <div class="W-submit-cancel-buttons">
                        <a href="<c:url value="/"/>" onclick="(this).className += ' spinner-border text-danger'; (this).innerText = ''">
                            <button type="button" class="btn btn-danger" id="cancelButton"><spring:message code="Form.Cancel"/></button></a>
                        <button id="submitButton" type="submit" class="btn btn-success" onclick="this.form.submit(); (this).className += ' spinner-border'; (this).innerText = '|'"><spring:message code="Form.Submit"/></button>
                    </div>
                </form:form>
            </c:when>
        </c:choose>

        <script src="<c:url value="/resources/js/dropDownBehaviour.js"/>"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
        <script>const simplemde = new SimpleMDE({ element: document.getElementById("MyTextArea"),showIcons: ["strikethrough"],hideIcons: ["link", "image","table","preview","fullscreen","guide","side-by-side","quote"]});</script>

    </body>

</html>
