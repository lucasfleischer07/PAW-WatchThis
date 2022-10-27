<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="W-filter-div">
    <div class="list-group">
        <div class="dropdown W-dropdown-button">
            <c:choose>
                <c:when test="${param.genre != '' && param.genre != 'ANY'}">
                    <button id="genreGroupDrop" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        <c:out value="${param.genre}"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button id="genreGroupDrop" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        <spring:message code="GenreMessage"/>
                    </button>
                </c:otherwise>
            </c:choose>
            <ul class="dropdown-menu">
                <c:url value="/${param.type}/filters" var="postPath">
                    <c:choose>
                        <c:when test="${param.durationFrom!= 'ANY' && param.sorting!='ANY'}">
                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                            <c:param name="durationTo" value="${param.durationTo}"/>
                            <c:param name="sorting" value="${param.sorting}"/>
                        </c:when>
                        <c:when test="${param.durationFrom== 'ANY' && param.sorting!='ANY'}">
                            <c:param name="sorting" value="${param.sorting}"/>
                        </c:when>
                        <c:when test="${param.durationFrom!= 'ANY' && param.sorting =='ANY'}">
                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                            <c:param name="durationTo" value="${param.durationTo}"/>
                        </c:when>
                    </c:choose>
                </c:url>
                <form:form modelAttribute="genreFilterForm" action="${postPath}" method="post" enctype="multipart/form-data">
                    <li class="mb-1 px-2">
                        <label><form:checkbox cssClass="px-2" path="genre" value="Action"/> <spring:message code="Genre.Action"/></label>
                    </li>

                    <li class="mb-1 px-2">
                        <label><form:checkbox cssClass="px-2" path="genre" value="Sci-Fi"/> <spring:message code="Genre.Science"/></label>
                    </li>

                    <li class="mb-1 px-2">
                        <label><form:checkbox cssClass="px-2" path="genre" value="Comedy"/> <spring:message code="Genre.Comedy"/></label>
                    </li>

                    <li class="mb-1 px-2">
                        <label><form:checkbox cssClass="px-2" path="genre" value="Adventure"/> <spring:message code="Genre.Adventure"/></label>
                    </li>

                    <li class="mb-1 px-2">
                        <label><form:checkbox cssClass="px-2" path="genre" value="Drama"/> <spring:message code="Genre.Drama"/></label>
                    </li>

                    <li class="mb-1 px-2">
                        <form:checkbox cssClass="px-2" path="genre" value="Horror"/> <spring:message code="Genre.Horror"/>
                    </li>

                    <li class="mb-1 px-2">
                        <label><form:checkbox cssClass="px-2" path="genre" value="Animation"/> <spring:message code="Genre.Animation"/></label>
                    </li>

                    <li class="mb-1 px-2">
                        <label><form:checkbox cssClass="px-2" path="genre" value="Thriller"/> <spring:message code="Genre.Thriller"/></label>
                    </li>

                    <li class="mb-1 px-2">
                        <label><form:checkbox cssClass="px-2" path="genre" value="Mystery"/> <spring:message code="Genre.Mystery"/></label>
                    </li>

                    <li class="mb-1 px-2">
                        <label><form:checkbox cssClass="px-2" path="genre" value="Crime"/> <spring:message code="Genre.Crime"/></label>
                    </li>

                    <li class="mb-1 px-2">
                        <label><form:checkbox cssClass="px-2" path="genre" value="Fantasy"/> <spring:message code="Genre.Fantasy"/></label>
                    </li>

                    <li class="mb-1 px-2">
                        <label><form:checkbox cssClass="px-2" path="genre" value="Romance"/> <spring:message code="Genre.Romance"/></label>
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
    <div class="list-group">
        <div class="dropdown W-dropdown-button-sorting">
            <c:choose>
                <c:when test="${param.sorting != '' && param.sorting != 'ANY'}">
                    <button id="sortingGroupDrop" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        <c:out value="${param.sorting}"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button id="sortingGroupDrop" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        <spring:message code="SortMessage"/>
                    </button>
                </c:otherwise>
            </c:choose>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                                        <c:param name="sorting" value="Last-released"/>
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
                                                                                        </c:url>" onclick="showSorting(this)"><spring:message code="Sort.Last"/></a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                                        <c:param name="sorting" value="Older-released"/>
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
                                                                                        </c:url>" onclick="showSorting(this)"><spring:message code="Sort.Older"/></a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                                        <c:param name="sorting" value="Best-ratings"/>
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
                                                                                        </c:url>" onclick="showSorting(this)"><spring:message code="Sort.Best"/></a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                                        <c:param name="sorting" value="A-Z"/>
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
                                                                                        </c:url>" onclick="showSorting(this)"><spring:message code="Sort.A_Z"/></a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                                        <c:param name="sorting" value="Z-A"/>
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
                                                                                        </c:url>" onclick="showSorting(this)"><spring:message code="Sort.Z-A"/></a></li>

            </ul>
        </div>
    </div>

</div>

<script src="<c:url value="/resources/js/dropDownBehaviour.js"/>"></script>