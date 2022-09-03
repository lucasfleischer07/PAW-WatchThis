<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top W-header-height" style="z-index: 1;">
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
                        <li class="nav-item W-home-button-hamburger-button">
                            <a class="nav-link" data-bs-dismiss="offcanvas" aria-current="page" href="<c:url value="/"/>">Home</a>
                        </li>
                        <li class="nav-item W-nav-item">
                            <a class="nav-link" data-bs-dismiss="offcanvas" aria-current="page" href="<c:url value="/"/>">Movies</a>
<%--                            <a class="nav-link" data-bs-dismiss="offcanvas" aria-current="page" href="<c:url value='/movie/${param.movieId}'/>">Movies</a>--%>
                        </li>
                        <li class="nav-item W-nav-item">
                        <a class="nav-link" data-bs-dismiss="offcanvas" href="<c:url value="/series"/>">Series</a>
<%--                            <a class="nav-link" data-bs-dismiss="offcanvas"  href="<c:url value='/serie/${param.serieId}'/>">Series</a>--%>

                        </li>
                    </ul>
                </div>
                <div class="W-navbar-search">
                    <form class="d-flex W-searchbar" role="search">
                        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                        <button class="btn btn-success W-search-button-color" type="submit">Search</button>
                    </form>
                </div>
                <div class="W-nav-login-button" style="display: none">
                    <button class="btn btn-success" type="submit">Login</button>
                </div>
            </div>
        </div>
    </div>
</nav>