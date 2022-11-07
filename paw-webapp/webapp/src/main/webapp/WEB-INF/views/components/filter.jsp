<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="W-filter-div">
    <div class="W-genre-duration-div">
        <div class="list-group">
            <div class="dropdown W-dropdown-button">
                    <c:choose>
                        <c:when test="${param.genre != '' && param.genre != 'ANY'}">
                            <button id="genreGroupDrop" type="button" onclick="dropDownStay()" class="W-filter-title W-genre-filter btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                <c:out value="${param.genre}"/>
                            </button>
                        </c:when>
                        <c:otherwise>
                            <button id="genreGroupDrop" type="button" onclick="dropDownStay()" class="W-filter-title W-genre-filter btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false" >
                                <spring:message code="GenreMessage"/>
                            </button>
                        </c:otherwise>
                    </c:choose>
                    <ul class="dropdown-menu" id="drop1">
                        <c:url value="/${param.type}/filters" var="postPath">
                            <c:if test="${query != 'ANY' && query!=null}">
                                <c:param name="query" value="${query}"/>
                            </c:if>
                            <c:if test="${durationFrom != 'ANY' && durationFrom!=null}">
                                <c:param name="durationFrom" value="${durationFrom}"/>
                                <c:param name="durationTo" value="${durationTo}"/>
                            </c:if>
                            <c:if test="${sorting != 'ANY' && sorting!=null}">
                                <c:param name="sorting" value="${sorting}"/>
                            </c:if>
                        </c:url>
                        <form:form modelAttribute="genreFilterForm" action="${postPath}" method="post" enctype="multipart/form-data">
                            <li class="mb-1 px-2">
                                <label><form:checkbox cssClass="px-2" path="formGenre" value="Action"/> <spring:message code="Genre.Action"/></label>
                            </li>

                            <li class="mb-1 px-2">
                                <label><form:checkbox cssClass="px-2" path="formGenre" value="Sci-Fi"/> <spring:message code="Genre.Science"/></label>
                            </li>

                            <li class="mb-1 px-2">
                                <label><form:checkbox cssClass="px-2" path="formGenre" value="Comedy"/> <spring:message code="Genre.Comedy"/></label>
                            </li>

                            <li class="mb-1 px-2">
                                <label><form:checkbox cssClass="px-2" path="formGenre" value="Adventure"/> <spring:message code="Genre.Adventure"/></label>
                            </li>

                            <li class="mb-1 px-2">
                                <label><form:checkbox cssClass="px-2" path="formGenre" value="Drama"/> <spring:message code="Genre.Drama"/></label>
                            </li>

                            <li class="mb-1 px-2">
                                <label><form:checkbox cssClass="px-2" path="formGenre" value="Horror"/> <spring:message code="Genre.Horror"/></label>
                            </li>

                            <li class="mb-1 px-2">
                                <label><form:checkbox cssClass="px-2" path="formGenre" value="Animation"/> <spring:message code="Genre.Animation"/></label>
                            </li>

                            <li class="mb-1 px-2">
                                <label><form:checkbox cssClass="px-2" path="formGenre" value="Thriller"/> <spring:message code="Genre.Thriller"/></label>
                            </li>

                            <li class="mb-1 px-2">
                                <label><form:checkbox cssClass="px-2" path="formGenre" value="Mystery"/> <spring:message code="Genre.Mystery"/></label>
                            </li>

                            <li class="mb-1 px-2">
                                <label><form:checkbox cssClass="px-2" path="formGenre" value="Crime"/> <spring:message code="Genre.Crime"/></label>
                            </li>

                            <li class="mb-1 px-2">
                                <label><form:checkbox cssClass="px-2" path="formGenre" value="Fantasy"/> <spring:message code="Genre.Fantasy"/></label>
                            </li>

                            <li class="mb-1 px-2">
                                <label><form:checkbox cssClass="px-2" path="formGenre" value="Romance"/> <spring:message code="Genre.Romance"/></label>
                            </li>

                            <div class="W-apply-button">
                                <button type="submit" class="btn btn-danger mb-1 px-2"> <spring:message code="Apply"/></button>
                            </div>
                        </form:form>
                    </ul>
            </div>
        </div>

        <div class="list-group">
            <div class="dropdown W-dropdown-button">
                <c:choose>
                    <c:when test="${param.durationTo == '1000'}">
                        <button id="genreGroupDrop" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                            <spring:message code="Duration.Or_more" arguments="${param.durationFrom}"/>
                        </button>
                    </c:when>
                    <c:when test="${param.durationFrom != '' &&  param.durationFrom != 'ANY'}">
                        <button id="genreGroupDrop" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                            <spring:message code="Duration.From.To" arguments="${param.durationFrom},${param.durationTo}"/>
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button id="genreGroupDrop" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                            <spring:message code="DurationMessage"/>
                        </button>
                    </c:otherwise>
                </c:choose>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:choose>
                                                                                <c:when test="${param.genre!= 'ANY' && param.sorting!='ANY'}">
                                                                                    <c:param name="genre" value="${param.genre}"/>
                                                                                    <c:param name="sorting" value="${param.sorting}"/>
                                                                                </c:when>
                                                                                <c:when test="${param.genre== 'ANY' && param.sorting!='ANY'}">
                                                                                    <c:param name="sorting" value="${param.sorting}"/>
                                                                                </c:when>
                                                                                <c:when test="${param.genre!= 'ANY' && param.sorting =='ANY'}">
                                                                                    <c:param name="genre" value="${param.genre}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
                                                                            </c:url>" onclick="showDuration(this)"><spring:message code="Duration.Clear"/></a></li>
                    <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:choose>
                                                                                <c:when test="${param.genre!= 'ANY' && param.sorting!='ANY'}">
                                                                                    <c:param name="genre" value="${param.genre}"/>
                                                                                    <c:param name="sorting" value="${param.sorting}"/>
                                                                                </c:when>
                                                                                <c:when test="${param.genre== 'ANY' && param.sorting!='ANY'}">
                                                                                    <c:param name="sorting" value="${param.sorting}"/>
                                                                                </c:when>
                                                                                <c:when test="${param.genre!= 'ANY' && param.sorting =='ANY'}">
                                                                                    <c:param name="genre" value="${param.genre}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
                                                                            <c:param name="durationFrom" value="0"/>
                                                                            <c:param name="durationTo" value="90"/>
                                                                            </c:url>" onclick="showDuration(this)"><spring:message code="Duration.0_90"/></a></li>
                    <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:choose>
                                                                                <c:when test="${param.genre!= 'ANY' && param.sorting!='ANY'}">
                                                                                    <c:param name="genre" value="${param.genre}"/>
                                                                                    <c:param name="sorting" value="${param.sorting}"/>
                                                                                </c:when>
                                                                                <c:when test="${param.genre== 'ANY' && param.sorting!='ANY'}">
                                                                                    <c:param name="sorting" value="${param.sorting}"/>
                                                                                </c:when>
                                                                                <c:when test="${param.genre!= 'ANY' && param.sorting =='ANY'}">
                                                                                    <c:param name="genre" value="${param.genre}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
                                                                            <c:param name="durationFrom" value="90"/>
                                                                            <c:param name="durationTo" value="120"/>
                                                                            </c:url>" onclick="showDuration(this)"><spring:message code="Duration.90_120"/></a></li>
                    <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:choose>
                                                                                <c:when test="${param.genre!= 'ANY' && param.sorting!='ANY'}">
                                                                                    <c:param name="genre" value="${param.genre}"/>
                                                                                    <c:param name="sorting" value="${param.sorting}"/>
                                                                                </c:when>
                                                                                <c:when test="${param.genre== 'ANY' && param.sorting!='ANY'}">
                                                                                    <c:param name="sorting" value="${param.sorting}"/>
                                                                                </c:when>
                                                                                <c:when test="${param.genre!= 'ANY' && param.sorting =='ANY'}">
                                                                                    <c:param name="genre" value="${param.genre}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
                                                                            <c:param name="durationFrom" value="120"/>
                                                                            <c:param name="durationTo" value="150"/>
                                                                            </c:url>" onclick="showDuration(this)"><spring:message code="Duration.120_150"/></a></li>
                    <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:choose>
                                                                                <c:when test="${param.genre!= 'ANY' && param.sorting!='ANY'}">
                                                                                    <c:param name="genre" value="${param.genre}"/>
                                                                                    <c:param name="sorting" value="${param.sorting}"/>
                                                                                </c:when>
                                                                                <c:when test="${param.genre== 'ANY' && param.sorting!='ANY'}">
                                                                                    <c:param name="sorting" value="${param.sorting}"/>
                                                                                </c:when>
                                                                                <c:when test="${param.genre!= 'ANY' && param.sorting =='ANY'}">
                                                                                    <c:param name="genre" value="${param.genre}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
                                                                            <c:param name="durationFrom" value="150"/>
                                                                            <c:param name="durationTo" value="1000"/>
                                                                            </c:url>" onclick="showDuration(this)"><spring:message code="Duration.150_more"/></a></li>
                </ul>
            </div>
        </div>
    </div>

    <div class="list-group">
        <div class="dropdown W-dropdown-button-sorting">
            <c:choose>
                <c:when test="${param.sorting != '' && param.sorting != 'ANY'}">
                    <button id="sortingGroupDrop" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        <spring:message code="Sort.${param.sorting}"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button id="sortingGroupDrop" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        <spring:message code="SortMessage"/>
                    </button>
                </c:otherwise>
            </c:choose>
            <ul class="dropdown-menu">
                <jsp:useBean id="sortingTypes" scope="request" type="ar.edu.itba.paw.models.Sorting[]"/>
                <c:forEach var="sortingType" items="${sortingTypes}">
                    <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                        <c:param name="sorting" value="${sortingType.toString()}"/>
                        <c:if test="${param.query != 'ANY' && param.query!=null}">
                            <c:param name="query" value="${param.query}"/>
                        </c:if>
                        <c:choose>
                            <c:when test="${param.genre!= 'ANY' && param.durationFrom!='ANY'}">
                                <c:param name="genre" value="${param.genre}"/>
                                <c:param name="durationFrom" value="${param.durationFrom}"/>
                                <c:param name="durationTo" value="${param.durationTo}"/>
                            </c:when>
                            <c:when test="${param.genre== 'ANY' && param.durationFrom!='ANY'}">
                                <c:param name="durationFrom" value="${param.durationFrom}"/>
                                <c:param name="durationTo" value="${param.durationTo}"/>
                            </c:when>
                            <c:when test="${param.genre!= 'ANY' && param.durationFrom =='ANY'}">
                                <c:param name="genre" value="${param.genre}"/>
                            </c:when>
                        </c:choose>
                        </c:url>" onclick="showSorting(this)"><spring:message code="Sort.${sortingType.toString()}"/></a></li>
                </c:forEach>
            </ul>
        </div>
    </div>

</div>

<script src="<c:url value="/resources/js/dropDownBehaviour.js"/>"></script>


