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
                                <li class="nav-item W-home-button-hamburger-button W-display-none-header">
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
                                <li class="nav-item W-home-button-hamburger-button W-display-none-header">
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
                                <spring:message var="searchPlaceholder" />
                                <input name="query" class="form-control me-2" type="search" placeholder="<spring:message code="SearchMessage"/>" aria-label="Search" >
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
                        <c:when test="${param.type == 'movie'}">
                            <form class="form-inline my-2 my-lg-0 W-searchbar" method="GET" action="<c:url value="/movies/filters"/>">
                                <spring:message var="searchPlaceholder" />
                                <input name="query" class="form-control me-2" type="search" placeholder="<spring:message code="SearchMessage"/>" aria-label="Search" >
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
                                <spring:message var="searchPlaceholder" />
                                <input name="query" class="form-control me-2" type="search" placeholder="<spring:message code="SearchMessage"/>" aria-label="Search" >
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
                            <form class="form-inline my-2 my-lg-0 W-searchbar" method="GET" action="<c:url value="/${param.type}/filters"/>">
                                <spring:message var="searchPlaceholder" />
                                <c:choose>
                                    <c:when test="${param.query != 'ANY'}">
                                        <input name="query" class="form-control me-2" type="search" placeholder="<spring:message code="SearchMessage"/>" aria-label="Search" value="<c:out value="${param.query}"/>" >
                                    </c:when>
                                    <c:otherwise>
                                        <input name="query" class="form-control me-2" type="search" placeholder="<spring:message code="SearchMessage"/>" aria-label="Search" >
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
            </div>
        </div>
    </div>

    <script src="<c:url value="/resources/js/dropDownBehaviour.js"/>"></script>
</nav>
