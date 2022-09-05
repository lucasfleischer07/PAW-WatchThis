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
                        <li class="nav-item W-home-button-hamburger-button" style="display: none">
                            <a class="nav-link" aria-current="page" href="<c:url value="/"/>">Home</a>
                        </li>
                        <li class="nav-item W-nav-item">
                            <a class="nav-link" aria-current="page" href="<c:url value="/"/>">Movies</a>
                        </li>
                        <li class="nav-item W-nav-item">
                            <a class="nav-link" href="<c:url value="/series"/>">Series</a>
                        </li>
                    </ul>
                </div>

                <div class="W-filters-hamburger-buttons">
                    <div class="btn-group-vertical" role="group" aria-label="Button group with nested dropdown">
                        <div class="btn-group" role="group">
                            <button id="ratingGroupDrop" type="button" class="btn btn-dark dropdown-toggle W-filter-hamburger-button" data-bs-toggle="dropdown" aria-expanded="false">
                                Rating
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="ratingGroupDrop">
                                <li><a class="dropdown-item" href="#" onclick="showRating(this)">1 star</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showRating(this)">2 stars</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showRating(this)">3 stars</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showRating(this)">4 stars</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showRating(this)">5 stars</a></li>
                            </ul>
                        </div>

                        <div class="btn-group" role="group">
                            <button id="genreGroupDrop" type="button" class="btn btn-dark dropdown-toggle W-filter-hamburger-button" data-bs-toggle="dropdown" aria-expanded="false">
                                Genre
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="genreGroupDrop">
                                <li><a class="dropdown-item" href="#" onclick="showGenre(this)">Action</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showGenre(this)">Science Fiction</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showGenre(this)">Comedy</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showGenre(this)">Adventure</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showGenre(this)">Drama</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showGenre(this)">Horror</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showGenre(this)">Animation</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showGenre(this)">Thrillers</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showGenre(this)">Mystery</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showGenre(this)">Crime</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showGenre(this)">Fantasy</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showGenre(this)">Romance</a></li>
                            </ul>
                        </div>

                        <div class="btn-group" role="group">
                            <button id="durationGroupDrop" type="button" class="btn btn-dark dropdown-toggle W-filter-hamburger-button" data-bs-toggle="dropdown" aria-expanded="false">
                                Duration
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="durationGroupDrop">
                                <li><a class="dropdown-item" href="#" onclick="showDuration(this)">0-90 minutes</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showDuration(this)">90-120 minutes</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showDuration(this)">120-150 minutes</a></li>
                                <li><a class="dropdown-item" href="#" onclick="showDuration(this)">150 or more</a></li>
                            </ul>
                        </div>
                        <div class="W-confirm-filters-button">
                            <button type="button" class="btn btn-success">Apply</button>
                        </div>
                    </div>

                    <script>
                        function showRating(item) {
                            document.getElementById("ratingGroupDrop").innerHTML = item.innerHTML;
                        }

                        function showGenre(item) {
                            document.getElementById("genreGroupDrop").innerHTML = item.innerHTML;
                        }

                        function showDuration(item) {
                            document.getElementById("durationGroupDrop").innerHTML = item.innerHTML;
                        }

                    </script>
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