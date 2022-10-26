<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
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
                                                                            </c:url>" onclick="showGenre(this)"><spring:message code="Genre.Clear"/></a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Action"/>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
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
                                                                            </c:url>" onclick="showGenre(this)"><spring:message code="Genre.Action"/></a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Sci-Fi"/>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
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
                                                                            </c:url>" onclick="showGenre(this)"><spring:message code="Genre.Science"/></a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Comedy"/>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
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
                                                                            </c:url>" onclick="showGenre(this)"><spring:message code="Genre.Comedy"/></a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Adventure"/>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
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
                                                                            </c:url>" onclick="showGenre(this)"><spring:message code="Genre.Adventure"/></a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Drama"/>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
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
                                                                            </c:url>" onclick="showGenre(this)"><spring:message code="Genre.Drama"/></a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Horror"/>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
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
                                                                            </c:url>" onclick="showGenre(this)"><spring:message code="Genre.Horror"/></a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Animation"/>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
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
                                                                            </c:url>" onclick="showGenre(this)"><spring:message code="Genre.Animation"/></a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Thriller"/>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
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
                                                                            </c:url>" onclick="showGenre(this)"><spring:message code="Genre.Thriller"/></a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Mystery"/>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
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
                                                                            </c:url>" onclick="showGenre(this)"><spring:message code="Genre.Mystery"/></a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Crime"/>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
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
                                                                            </c:url>" onclick="showGenre(this)"><spring:message code="Genre.Crime"/></a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Fantasy"/>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
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
                                                                            </c:url>" onclick="showGenre(this)"><spring:message code="Genre.Fantasy"/></a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Romance"/>
                                                                            <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                <c:param name="query" value="${param.query}"/>
                                                                            </c:if>
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
                                                                            </c:url>" onclick="showGenre(this)"><spring:message code="Genre.Romance"/></a></li>

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