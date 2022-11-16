<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top W-header-height">
    <div class="W-container-fluid">

        <a class="navbar-brand" href="<c:url value="/"/>">
            <div class="W-logo-div">
                <img src="<c:url value="/resources/img/WatchThisLogo.png"/>" alt="WatchThisLogo" class="W-img-size2">
            </div>
        </a>

        <div>
            <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasDarkNavbar" aria-controls="offcanvasDarkNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>

        <div class="offcanvas offcanvas-end text-bg-dark W-header-accomodation" tabindex="-1" id="offcanvasDarkNavbar" aria-labelledby="offcanvasDarkNavbarLabel">

            <div class="offcanvas-header">
                <h3 class="offcanvas-title W-hamburger-button-title" id="offcanvasDarkNavbarLabel"><spring:message code="WatchThisMessage"/></h3>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas" aria-label="Close"></button>
            </div>

            <div class="offcanvas-body W-navbar-buttons-acomodation">

                <div class="W-navbar-hamburger-postion">
                    <ul class="navbar-nav justify-content-between flex-grow-1 pe-3 W-navitem-list">
                        <c:choose>
                            <c:when test="${param.type == 'movies' || param.type == 'movie'}">
                                <li class="nav-item W-home-button-hamburger-button W-display-none-header">
                                    <a class="nav-link" aria-current="page" href="<c:url value="/"/>"><spring:message code="HomeMessage"/></a>
                                </li>
                                <li class="nav-item W-nav-item">
                                    <a class="nav-link active" aria-current="page" href="<c:url value="/movies"/>"><spring:message code="MovieMessage"/></a>
                                </li>
                                <li class="nav-item W-nav-item">
                                    <a class="nav-link" href="<c:url value="/series"/>"><spring:message code="SerieMessage"/></a>
                                </li>
                            </c:when>
                            <c:when test="${param.type == 'series' || param.type == 'serie'}">
                                <li class="nav-item W-home-button-hamburger-button">
                                    <a class="nav-link" aria-current="page" href="<c:url value="/"/>"><spring:message code="HomeMessage"/></a>
                                </li>
                                <li class="nav-item W-nav-item">
                                    <a class="nav-link" aria-current="page" href="<c:url value="/movies"/>"><spring:message code="MovieMessage"/></a>
                                </li>
                                <li class="nav-item W-nav-item">
                                    <a class="nav-link active" href="<c:url value="/series"/>"><spring:message code="SerieMessage"/></a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="nav-item W-home-button-hamburger-button">
                                    <a class="nav-link" aria-current="page" href="<c:url value="/"/>"><spring:message code="HomeMessage"/></a>
                                </li>
                                <li class="nav-item W-nav-item">
                                    <a class="nav-link" aria-current="page" href="<c:url value="/movies"/>"><spring:message code="MovieMessage"/></a>
                                </li>
                                <li class="nav-item W-nav-item">
                                    <a class="nav-link" href="<c:url value="/series"/>"><spring:message code="SerieMessage"/></a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>


                <div class="d-flex W-navbar-search">
                    <c:choose>
                        <c:when test="${param.type == 'profile'}">
                            <form class="form-inline my-2 my-lg-0 W-searchbar" method="GET" action="<c:url value="/all/filters"/>">
                                <spring:message code="SearchMessage" var="searchPlaceholder"/>
                                <input name="query" class="form-control me-2" type="search" placeholder="${searchPlaceholder}" aria-label="Search" >
                                <c:if test="${param.genre != 'ANY' && param.genre!=null}">
                                    <input type="hidden" id="thisField" name="genre" value="${param.genre}">
                                </c:if>
                                <c:if test="${param.durationFrom != 'ANY' && param.durationFrom!=null}">
                                    <input type="hidden" id="thisField" name="durationFrom" value="${param.durationFrom}">
                                    <input type="hidden" id="thisField" name="durationTo" value="${param.durationTo}">
                                </c:if>
                                <c:if test="${param.sorting != 'ANY' && param.sorting!=null}">
                                    <input type="hidden" id="thisField" name="sorting" value="${param.sorting}">
                                </c:if>
                                <button class="btn btn-success W-search-button-color" type="submit">
                                    <svg xmlns="http://www.w3.org/2000/svg"  fill="currentColor" class="bi bi-search W-search-icon" viewBox="0 0 16 16">
                                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                                    </svg>
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${param.type == 'movie'}">
                                    <form class="form-inline my-2 my-lg-0 W-searchbar" method="GET" action="<c:url value="/movies/filters"/>">
                                        <spring:message code="SearchMessage" var="searchPlaceholder"/>
                                        <c:choose>
                                            <c:when test="${param.query != 'ANY'}">
                                                <input name="query" class="form-control me-2" type="search" placeholder="${searchPlaceholder}" aria-label="Search" value="<c:out value="${param.query}"/>" >
                                            </c:when>
                                            <c:otherwise>
                                                <input name="query" class="form-control me-2" type="search" placeholder="${searchPlaceholder}" aria-label="Search" >
                                            </c:otherwise>
                                        </c:choose>
                                        <c:if test="${param.genre != 'ANY' && param.genre!=null}">
                                            <input type="hidden" id="thisField" name="genre" value="${param.genre}">
                                        </c:if>
                                        <c:if test="${param.durationFrom != 'ANY' && param.durationFrom!=null}">
                                            <input type="hidden" id="thisField" name="durationFrom" value="${param.durationFrom}">
                                            <input type="hidden" id="thisField" name="durationTo" value="${param.durationTo}">
                                        </c:if>
                                        <c:if test="${param.sorting != 'ANY' && param.sorting!=null}">
                                            <input type="hidden" id="thisField" name="sorting" value="${param.sorting}">
                                        </c:if>
                                        <button class="btn btn-success W-search-button-color" type="submit">
                                            <svg xmlns="http://www.w3.org/2000/svg"  fill="currentColor" class="bi bi-search W-search-icon" viewBox="0 0 16 16">
                                                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                                            </svg>
                                        </button>
                                    </form>
                                </c:when>
                                <c:when test="${param.type == 'serie'}">
                                    <form class="form-inline my-2 my-lg-0 W-searchbar" method="GET" action="<c:url value="/series/filters"/>">
                                        <spring:message code="SearchMessage" var="searchPlaceholder"/>
                                        <c:choose>
                                            <c:when test="${param.query != 'ANY'}">
                                                <input name="query" class="form-control me-2" type="search" placeholder="${searchPlaceholder}" aria-label="Search" value="<c:out value="${param.query}"/>">
                                            </c:when>
                                            <c:otherwise>
                                                <input name="query" class="form-control me-2" type="search" placeholder="${searchPlaceholder}" aria-label="Search" >
                                            </c:otherwise>
                                        </c:choose>
                                        <c:if test="${param.genre != 'ANY' && param.genre!=null}">
                                            <input type="hidden" id="thisField" name="genre" value="${param.genre}">
                                        </c:if>
                                        <c:if test="${param.durationFrom != 'ANY' && param.durationFrom!=null}">
                                            <input type="hidden" id="thisField" name="durationFrom" value="${param.durationFrom}">
                                            <input type="hidden" id="thisField" name="durationTo" value="${param.durationTo}">
                                        </c:if>
                                        <c:if test="${param.sorting != 'ANY' && param.sorting!=null}">
                                            <input type="hidden" id="thisField" name="sorting" value="${param.sorting}">
                                        </c:if>
                                        <button class="btn btn-success W-search-button-color" type="submit">
                                            <svg xmlns="http://www.w3.org/2000/svg"  fill="currentColor" class="bi bi-search W-search-icon" viewBox="0 0 16 16">
                                                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                                            </svg>
                                        </button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form class="form-inline my-2 my-lg-0 W-searchbar" method="GET" action="<c:url value="/${param.type2}/filters"/>">
                                        <spring:message code="SearchMessage" var="searchPlaceholder"/>
                                        <c:choose>
                                            <c:when test="${param.query != 'ANY'}">
                                                <input name="query" class="form-control me-2" type="search" placeholder="${searchPlaceholder}" aria-label="Search" value="<c:out value="${param.query}"/> " >
                                            </c:when>
                                            <c:otherwise>
                                                <input name="query" class="form-control me-2" type="search" placeholder="${searchPlaceholder}" aria-label="Search" >
                                            </c:otherwise>
                                        </c:choose>
                                        <c:if test="${param.genre != 'ANY' && param.genre!=null}">
                                            <input type="hidden" id="thisField" name="genre" value="${param.genre}">
                                        </c:if>
                                        <c:if test="${param.durationFrom != 'ANY' && param.durationFrom!=null}">
                                            <input type="hidden" id="thisField" name="durationFrom" value="${param.durationFrom}">
                                            <input type="hidden" id="thisField" name="durationTo" value="${param.durationTo}">
                                        </c:if>
                                        <c:if test="${param.sorting != 'ANY' && param.sorting!=null}">
                                            <input type="hidden" id="thisField" name="sorting" value="${param.sorting}">
                                        </c:if>
                                        <button class="btn btn-success W-search-button-color" type="submit">
                                            <svg xmlns="http://www.w3.org/2000/svg"  fill="currentColor" class="bi bi-search W-search-icon" viewBox="0 0 16 16">
                                                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                                            </svg>
                                        </button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="W-nav-login-button">
                    <c:choose>
                        <c:when test="${param.userName != 'null' && param.userName!= ''}">
                            <div class="btn-group">
                                <button type="button" class="btn btn-dark dropdown-toggle W-border-color-user-btn" data-bs-toggle="dropdown" aria-expanded="false">
                                        ${param.userName}
                                </button>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="<c:url value="/profile"/>"><spring:message code="Profile"/></a></li>
                                    <li><a class="dropdown-item" href="<c:url value="/profile/watchList"/>"><spring:message code="WatchList"/></a></li>
                                    <li><a class="dropdown-item" href="<c:url value="/profile/viewedList"/>"><spring:message code="ViewedList.Title"/></a></li>
                                    <c:if test="${param.admin == true || param.admin == 'true'}">
                                        <li><a class="dropdown-item" href="<c:url value="/content/create"/>"><spring:message code="CreateContent.Message"/></a></li>
                                        <li><a class="dropdown-item" href="<c:url value="/report/reportedContent/reviews"/>"><spring:message code="Report.ReportedContent"/></a></li>
                                    </c:if>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="<c:url value="/login/sign-out"/>"><spring:message code="LogOutMessage"/></a></li>
                                </ul>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="/login/sign-in"/>"><button class="btn btn-success" type="button"><spring:message code="LoginMessage"/></button></a>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="W-filters-hamburger-buttons">
                    <c:choose>
                        <c:when test="${param.type == 'movie' || param.type == 'serie' || param.type == 'all'}">
                            <div class="btn-group-vertical" role="group" aria-label="Button group with nested dropdown">
                                <div class="list-group">
                                    <div class="dropdown">
                                        <c:choose>
                                            <c:when test="${param.genre != '' && param.genre != 'ANY'}">
                                                <button id="genreGroupDrop" type="button" onclick="dropDownStayGenreFiltersHamburger()" class="W-filter-title W-dropdown-button-genre-duration-sort btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                                    <span><c:out value="${param.genre}"/></span>
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button id="genreGroupDrop" type="button" onclick="dropDownStayGenreFiltersHamburger()" class="W-filter-title W-dropdown-button-genre-duration-sort btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                                    <spring:message code="GenreMessage"/>
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                        <ul class="dropdown-menu" id="drop2">
                                            <c:url value="/${param.type2}/filters" var="postPath">
                                                <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                    <c:param name="query" value="${param.query}"/>
                                                </c:if>
                                                <c:if test="${param.durationFrom != 'ANY' && param.durationFrom!=null}">
                                                    <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                    <c:param name="durationTo" value="${param.durationTo}"/>
                                                </c:if>
                                                <c:if test="${param.sorting != 'ANY' && param.sorting!=null}">
                                                    <c:param name="sorting" value="${param.sorting}"/>
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
                                    <div class="dropdown">
                                        <c:choose>
                                            <c:when test="${param.durationTo == '1000'}">
                                                <button id="genreGroupDrop" type="button" class="W-filter-title W-dropdown-button-genre-duration-sort  btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                                    <spring:message code="Duration.Or_more" arguments="${param.durationFrom}"/>
                                                </button>
                                            </c:when>
                                            <c:when test="${param.durationFrom != '' &&  param.durationFrom != 'ANY'}">
                                                <button id="genreGroupDrop" type="button" class="W-filter-title W-dropdown-button-genre-duration-sort  btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                                    <spring:message code="Duration.From.To" arguments="${param.durationFrom},${param.durationTo}"/>
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button id="genreGroupDrop" type="button" class="W-filter-title W-dropdown-button-genre-duration-sort  btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                                    <spring:message code="DurationMessage"/>
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                        <ul class="dropdown-menu">
                                            <li><a class="dropdown-item" href="<c:url value="/${param.type2}/filters">
                                                                                                <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                                    <c:param name="query" value="${param.query}"/>
                                                                                                </c:if>
                                                                                                <c:if test="${param.genre != 'ANY' && param.genre!=null}">
                                                                                                    <c:param name="genre" value="${param.genre}"/>
                                                                                                </c:if>
                                                                                                <c:if test="${param.sorting != 'ANY' && param.sorting!=null}">
                                                                                                    <c:param name="sorting" value="${param.sorting}"/>
                                                                                                </c:if>
                                                                                                </c:url>" onclick="showDuration(this)"><spring:message code="Duration.Clear"/>
                                            </a></li>
                                            <li><a class="dropdown-item" href="<c:url value="/${param.type2}/filters">
                                                                                                <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                                    <c:param name="query" value="${param.query}"/>
                                                                                                </c:if>
                                                                                                <c:if test="${param.genre != 'ANY' && param.genre!=null}">
                                                                                                    <c:param name="genre" value="${param.genre}"/>
                                                                                                </c:if>
                                                                                                <c:if test="${param.sorting != 'ANY' && param.sorting!=null}">
                                                                                                    <c:param name="sorting" value="${param.sorting}"/>
                                                                                                </c:if>
                                                                                                <c:param name="durationFrom" value="0"/>-<c:param name="durationTo" value="90"/>
                                                                                                </c:url>" onclick="showDuration(this)"><spring:message code="Duration.0_90"/>
                                            </a></li>
                                            <li><a class="dropdown-item" href="<c:url value="/${param.type2}/filters">
                                                                                                <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                                    <c:param name="query" value="${param.query}"/>
                                                                                                </c:if>
                                                                                                <c:if test="${param.genre != 'ANY' && param.genre!=null}">
                                                                                                    <c:param name="genre" value="${param.genre}"/>
                                                                                                </c:if>
                                                                                                <c:if test="${param.sorting != 'ANY' && param.sorting!=null}">
                                                                                                    <c:param name="sorting" value="${param.sorting}"/>
                                                                                                </c:if>
                                                                                                <c:param name="durationFrom" value="90"/>-<c:param name="durationTo" value="120"/>
                                                                                                </c:url>" onclick="showDuration(this)"><spring:message code="Duration.90_120"/>
                                            </a></li>
                                            <li><a class="dropdown-item" href="<c:url value="/${param.type2}/filters">
                                                                                                <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                                    <c:param name="query" value="${param.query}"/>
                                                                                                </c:if>
                                                                                                <c:if test="${param.genre != 'ANY' && param.genre!=null}">
                                                                                                    <c:param name="genre" value="${param.genre}"/>
                                                                                                </c:if>
                                                                                                <c:if test="${param.sorting != 'ANY' && param.sorting!=null}">
                                                                                                    <c:param name="sorting" value="${param.sorting}"/>
                                                                                                </c:if>
                                                                                                <c:param name="durationFrom" value="120"/>-<c:param name="durationTo" value="150"/>
                                                                                                </c:url>" onclick="showDuration(this)"><spring:message code="Duration.120_150"/>
                                            </a></li>
                                            <li><a class="dropdown-item" href="<c:url value="/${param.type2}/filters">
                                                                                                <c:if test="${param.query != 'ANY' && param.query!=null}">
                                                                                                    <c:param name="query" value="${param.query}"/>
                                                                                                </c:if>
                                                                                                <c:if test="${param.genre != 'ANY' && param.genre!=null}">
                                                                                                    <c:param name="genre" value="${param.genre}"/>
                                                                                                </c:if>
                                                                                                <c:if test="${param.sorting != 'ANY' && param.sorting!=null}">
                                                                                                    <c:param name="sorting" value="${param.sorting}"/>
                                                                                                </c:if>
                                                                                                <c:param name="durationFrom" value="150"/>-<c:param name="durationTo" value="1000"/>
                                                                                                </c:url>" onclick="showDuration(this)"><spring:message code="Duration.150_more"/>
                                            </a></li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="list-group">
                                    <div class="dropdown W-dropdown-button">
                                        <c:choose>
                                            <c:when test="${param.sorting != '' && param.sorting != 'ANY'}">
                                                <button id="sortingGroupDrop" type="button" class="W-dropdown-button-genre-duration-sort  W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                                    <spring:message code="Sort.${param.sorting}"/>
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button id="sortingGroupDrop" type="button" class="W-dropdown-button-genre-duration-sort  W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                                    <spring:message code="SortMessage"/>
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                        <ul class="dropdown-menu W-dropdown-sorting">
                                            <jsp:useBean id="sortingTypes" scope="request" type="ar.edu.itba.paw.models.Sorting[]"/>
                                            <c:forEach var="sortingType" items="${sortingTypes}">
                                                <li><a class="dropdown-item" href="<c:url value="/${param.type2}/filters">
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
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

    <script src="<c:url value="/resources/js/dropDownBehaviour.js"/>"></script>
</nav>

