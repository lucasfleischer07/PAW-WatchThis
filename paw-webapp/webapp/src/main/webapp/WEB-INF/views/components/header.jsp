<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top W-header-height">
    <div class="container-fluid">
        <div class="W-logo-div">
            <a class="navbar-brand W-logo-style" href="<c:url value="/"/>">
                <img src="<c:url value="/resources/img/WatchThisLogo.png"/>" alt="WatchThisLogo" style="width:100%;height:100%;">
            </a>
        </div>

        <div>
            <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasDarkNavbar" aria-controls="offcanvasDarkNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>

        <div class="offcanvas offcanvas-end text-bg-dark W-header-accomodation" tabindex="-1" id="offcanvasDarkNavbar" aria-labelledby="offcanvasDarkNavbarLabel">

            <div class="offcanvas-header">
                <h5 class="offcanvas-title" id="offcanvasDarkNavbarLabel">Watch This</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas" aria-label="Close"></button>
            </div>

            <div class="offcanvas-body W-navbar-buttons-acomodation">

                <div class="W-navbar-hamburger-postion">
                    <ul class="navbar-nav justify-content-between flex-grow-1 pe-3 W-navitem-list">
                        <c:choose>
                            <c:when test="${param.type == 'movies' || param.type == 'movie'}">
                                <li class="nav-item W-home-button-hamburger-button" style="display: none">
                                    <a class="nav-link" aria-current="page" href="<c:url value="/"/>">Home</a>
                                </li>
                                <li class="nav-item W-nav-item">
                                    <a class="nav-link active" aria-current="page" href="<c:url value="/movies"/>">Movies</a>
                                </li>
                                <li class="nav-item W-nav-item">
                                    <a class="nav-link" href="<c:url value="/series"/>">Tv Shows</a>
                                </li>
                            </c:when>
                            <c:when test="${param.type == 'series' || param.type == 'serie'}">
                                <li class="nav-item W-home-button-hamburger-button" style="display: none">
                                    <a class="nav-link" aria-current="page" href="<c:url value="/"/>">Home</a>
                                </li>
                                <li class="nav-item W-nav-item">
                                    <a class="nav-link" aria-current="page" href="<c:url value="/movies"/>">Movies</a>
                                </li>
                                <li class="nav-item W-nav-item">
                                    <a class="nav-link active" href="<c:url value="/series"/>">Tv Shows</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="nav-item W-home-button-hamburger-button" style="display: none">
                                    <a class="nav-link" aria-current="page" href="<c:url value="/"/>">Home</a>
                                </li>
                                <li class="nav-item W-nav-item">
                                    <a class="nav-link" aria-current="page" href="<c:url value="/movies"/>">Movies</a>
                                </li>
                                <li class="nav-item W-nav-item">
                                    <a class="nav-link" href="<c:url value="/series"/>">Tv Shows</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>

                <div class="d-flex W-navbar-search">
                    <form class="form-inline my-2 my-lg-0 W-searchbar" method="GET" action="<c:url value="/search"/>">
                        <spring:message var="searchPlaceholder" />
                        <input name="query" class="form-control me-2" type="search" placeholder="Search movies or tv shows" aria-label="Search" >
                        <button class="btn btn-success W-search-button-color" type="submit">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                            </svg>
                        </button>
                    </form>
                </div>

                <div class="W-nav-login-button">
                    <a href="<c:url value="/login/sign-in"/>"><button class="btn btn-success" type="button">Login</button></a>
                </div>

                <div class="W-filters-hamburger-buttons">
                    <div class="btn-group-vertical" role="group" aria-label="Button group with nested dropdown">
<%--                        TODO: Agregarlo despues--%>
<%--                        <div class="btn-group" role="group">--%>
<%--                            <button id="ratingGroupDrop" type="button" class="btn btn-dark dropdown-toggle W-filter-hamburger-button" data-bs-toggle="dropdown" aria-expanded="false">--%>
<%--                                Rating--%>
<%--                            </button>--%>
<%--                            <ul class="dropdown-menu" aria-labelledby="ratingGroupDrop">--%>
<%--                                <li><a class="dropdown-item" href="#" onclick="showRating(this)">1 star</a></li>--%>
<%--                                <li><a class="dropdown-item" href="#" onclick="showRating(this)">2 stars</a></li>--%>
<%--                                <li><a class="dropdown-item" href="#" onclick="showRating(this)">3 stars</a></li>--%>
<%--                                <li><a class="dropdown-item" href="#" onclick="showRating(this)">4 stars</a></li>--%>
<%--                                <li><a class="dropdown-item" href="#" onclick="showRating(this)">5 stars</a></li>--%>
<%--                            </ul>--%>
<%--                        </div>--%>

                        <div class="btn-group" role="group">
                            <c:choose>
                                <c:when test="${param.genre != '' && param.genre != 'ANY'}">
                                    <button id="genreGroupDrop" type="button" class="btn btn-dark dropdown-toggle W-filter-hamburger-button" data-bs-toggle="dropdown" aria-expanded="false">
                                        <c:out value="${param.genre}"/>
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button id="genreGroupDrop" type="button" class="btn btn-dark dropdown-toggle W-filter-hamburger-button" data-bs-toggle="dropdown" aria-expanded="false">
                                        Genre
                                    </button>
                                </c:otherwise>
                            </c:choose>
                            <ul class="dropdown-menu" aria-labelledby="genreGroupDrop">
                                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="ANY"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>" onclick="showGenre(this)">Clear filter</a></li>
                                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Action"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>" onclick="showGenre(this)">Action</a></li>
                                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Sci-Fi"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>" onclick="showGenre(this)">Science Fiction</a></li>
                                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Comedy"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>" onclick="showGenre(this)">Comedy</a></li>
                                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Adventure"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>" onclick="showGenre(this)">Adventure</a></li>
                                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Drama"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>" onclick="showGenre(this)">Drama</a></li>
                                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Horror"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>" onclick="showGenre(this)">Horror</a></li>
                                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Animation"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>" onclick="showGenre(this)">Animation</a></li>
                                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Thriller"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>" onclick="showGenre(this)">Thriller</a></li>
                                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Mystery"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>" onclick="showGenre(this)">Mystery</a></li>
                                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Crime"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>" onclick="showGenre(this)">Crime</a></li>
                                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Fantasy"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>" onclick="showGenre(this)">Fantasy</a></li>
                                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Romance"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>" onclick="showGenre(this)">Romance</a></li>
                            </ul>
                        </div>
                        <div class="list-group">
                            <div class="dropdown W-dropdown-button">
                                <c:choose>
                                    <c:when test="${param.durationTo == '1000'}">
                                        <button id="genreGroupDrop" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                            <c:out value="${param.durationFrom}"/> or more
                                        </button>
                                    </c:when>
                                    <c:when test="${param.durationFrom != '' &&  param.durationFrom != 'ANY'}">
                                        <button id="genreGroupDrop" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                            <c:out value="${param.durationFrom}"/>-<c:out value="${param.durationTo}"/> minutes
                                        </button>
                                    </c:when>

                                    <c:otherwise>
                                        <button id="genreGroupDrop" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                            Duration
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                                                <c:choose>
                                                                                                    <c:when test="${param.genre != 'ANY'}">
                                                                                                        <c:param name="genre" value="${param.genre}"/>
                                                                                                    </c:when>
                                                                                                </c:choose>
                                                                                                </c:url>" onclick="showDuration(this)">Clear filter</a></li>
                                    <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                                                <c:choose>
                                                                                                    <c:when test="${param.genre != 'ANY'}">
                                                                                                        <c:param name="genre" value="${param.genre}"/>
                                                                                                    </c:when>
                                                                                                </c:choose>
                                                                                                <c:param name="durationFrom" value="0"/>-<c:param name="durationTo" value="90"/>
                                                                                                </c:url>" onclick="showDuration(this)">0-90 minutes</a></li>
                                    <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                                                <c:choose>
                                                                                                    <c:when test="${param.genre != 'ANY'}">
                                                                                                        <c:param name="genre" value="${param.genre}"/>
                                                                                                    </c:when>
                                                                                                </c:choose>
                                                                                                <c:param name="durationFrom" value="90"/>-<c:param name="durationTo" value="120"/>
                                                                                                </c:url>" onclick="showDuration(this)">90-120 minutes</a></li>
                                    <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                                                <c:choose>
                                                                                                    <c:when test="${param.genre != 'ANY'}">
                                                                                                        <c:param name="genre" value="${param.genre}"/>
                                                                                                    </c:when>
                                                                                                </c:choose>
                                                                                                <c:param name="durationFrom" value="120"/>-<c:param name="durationTo" value="150"/>
                                                                                                </c:url>" onclick="showDuration(this)">120-150 minutes</a></li>
                                    <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                                                <c:choose>
                                                                                                    <c:when test="${param.genre != 'ANY'}">
                                                                                                        <c:param name="genre" value="${param.genre}"/>
                                                                                                    </c:when>
                                                                                                </c:choose>
                                                                                                <c:param name="durationFrom" value="150"/>-<c:param name="durationTo" value="1000"/>
                                                                                                </c:url>" onclick="showDuration(this)">150 or more</a></li>
                                </ul>
                            </div>
                        </div>

                        <div class="d-flex W-navbar-search">
                            <form class="form-inline my-2 my-lg-0 W-searchbar" method="GET" action="<c:url value="/search"/>">
                                <spring:message var="searchPlaceholder" />
                                <input name="query" class="form-control me-2" type="search" placeholder="Search movies or tv shows" aria-label="Search" >
                                <button class="btn btn-success W-search-button-color" type="submit">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                                    </svg>
                                </button>
                            </form>
                        </div>

                        <div class="W-nav-login-button">
                            <a href="<c:url value="/login/sign-in"/>"><button class="btn btn-success" type="button">Login</button></a>
                        </div>
                    </div>
                </div>


            </div>
        </div>
    </div>
    <script src="<c:url value="/resources/js/dropDownBehaviour.js"/>"></script>
</nav>

