<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<div class="W-filter-div">
    <%-- <p class="W-filter-title">Rating</p>
    <div class="list-group ">
        <a href="#" class="list-group-item border-0 W-filter-padding">
            <div class="W-star-filter-location">
                <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star-fill W-star-style" viewBox="0 0 16 16">
                    <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                </svg>
                <svg xmlns="http://www.w3.org/2000/svg"  fill="currentColor" class="bi bi-star-fill W-star-style" viewBox="0 0 16 16">
                    <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                </svg>
                <svg xmlns="http://www.w3.org/2000/svg"  fill="currentColor" class="bi bi-star-fill W-star-style" viewBox="0 0 16 16">
                    <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                </svg>
                <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star-fill W-star-style" viewBox="0 0 16 16">
                    <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                </svg>
                <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star-fill W-star-style" viewBox="0 0 16 16">
                    <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                </svg>

            </div>
        </a>
    </div>--%>

<%--    <p class="W-filter-title">Genre</p>--%>
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
                        Genre
                    </button>
                </c:otherwise>
            </c:choose>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:choose>
                                                                                <c:when test="${param.durationFrom != 'ANY'}">
                                                                                    <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                                    <c:param name="durationTo" value="${param.durationTo}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            </c:url>" onclick="showGenre(this)">Clear filter</a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Action"/>
                                                                            <c:choose>
                                                                                <c:when test="${param.durationFrom != 'ANY'}">
                                                                                    <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                                    <c:param name="durationTo" value="${param.durationTo}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            </c:url>" onclick="showGenre(this)">Action</a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Sci-Fi"/>
                                                                            <c:choose>
                                                                                <c:when test="${param.durationFrom != 'ANY'}">
                                                                                    <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                                    <c:param name="durationTo" value="${param.durationTo}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            </c:url>" onclick="showGenre(this)">Science Fiction</a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Comedy"/>
                                                                            <c:choose>
                                                                                <c:when test="${param.durationFrom != 'ANY'}">
                                                                                    <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                                    <c:param name="durationTo" value="${param.durationTo}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            </c:url>" onclick="showGenre(this)">Comedy</a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Adventure"/>
                                                                            <c:choose>
                                                                                <c:when test="${param.durationFrom != 'ANY'}">
                                                                                    <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                                    <c:param name="durationTo" value="${param.durationTo}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            </c:url>" onclick="showGenre(this)">Adventure</a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Drama"/>
                                                                            <c:choose>
                                                                                <c:when test="${param.durationFrom != 'ANY'}">
                                                                                    <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                                    <c:param name="durationTo" value="${param.durationTo}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            </c:url>" onclick="showGenre(this)">Drama</a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Horror"/>
                                                                            <c:choose>
                                                                                <c:when test="${param.durationFrom != 'ANY'}">
                                                                                    <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                                    <c:param name="durationTo" value="${param.durationTo}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            </c:url>" onclick="showGenre(this)">Horror</a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Animation"/>
                                                                            <c:choose>
                                                                                <c:when test="${param.durationFrom != 'ANY'}">
                                                                                    <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                                    <c:param name="durationTo" value="${param.durationTo}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            </c:url>" onclick="showGenre(this)">Animation</a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Thriller"/>
                                                                            <c:choose>
                                                                                <c:when test="${param.durationFrom != 'ANY'}">
                                                                                    <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                                    <c:param name="durationTo" value="${param.durationTo}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            </c:url>" onclick="showGenre(this)">Thriller</a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Mystery"/>
                                                                            <c:choose>
                                                                                <c:when test="${param.durationFrom != 'ANY'}">
                                                                                    <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                                    <c:param name="durationTo" value="${param.durationTo}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            </c:url>" onclick="showGenre(this)">Mystery</a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Crime"/>
                                                                            <c:choose>
                                                                                <c:when test="${param.durationFrom != 'ANY'}">
                                                                                    <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                                    <c:param name="durationTo" value="${param.durationTo}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            </c:url>" onclick="showGenre(this)">Crime</a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Fantasy"/>
                                                                            <c:choose>
                                                                                <c:when test="${param.durationFrom != 'ANY'}">
                                                                                    <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                                    <c:param name="durationTo" value="${param.durationTo}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            </c:url>" onclick="showGenre(this)">Fantasy</a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Romance"/>
                                                                            <c:choose>
                                                                                <c:when test="${param.durationFrom != 'ANY'}">
                                                                                    <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                                    <c:param name="durationTo" value="${param.durationTo}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            </c:url>" onclick="showGenre(this)">Romance</a></li>

            </ul>
        </div>
    </div>

<%--    <p class="W-filter-title">Duration</p>--%>
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
    <div class="list-group">
        <div class="dropdown W-dropdown-button-sorting">
            <c:choose>
                <c:when test="${param.sorting == '1000'}">
                    <button id="sortingGroupDrop" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        <c:out value="${param.durationFrom}"/>
                    </button>
                </c:when>
                <c:when test="${param.sorting != '' &&  param.durationFrom != 'ANY'}">
                    <button id="sortingGroupDrop" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        <c:out value="${param.durationFrom}"/>
                    </button>
                </c:when>
                <c:otherwise>
                    <button id="sortingGroupDrop" type="button" class="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        Sort by
                    </button>
                </c:otherwise>
            </c:choose>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                                        <c:choose>
                                                                                            <c:when test="${param.sorting != 'ANY'}">
                                                                                                <c:param name="genre" value="${param.sorting}"/>
                                                                                            </c:when>
                                                                                        </c:choose>
                                                                                        </c:url>" onclick="showSorting(this)">Last released</a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                                        <c:choose>
                                                                                            <c:when test="${param.genre != 'ANY'}">
                                                                                                <c:param name="sorting" value="${param.sorting}"/>
                                                                                            </c:when>
                                                                                        </c:choose>                                                                      <c:param name="durationFrom" value="0"/>-<c:param name="durationTo" value="90"/>
                                                                                        </c:url>" onclick="showSorting(this)">Older released</a></li>
                <li><a class="dropdown-item" href="<c:url value="/${param.type}/filters">
                                                                                        <c:choose>
                                                                                            <c:when test="${param.genre != 'ANY'}">
                                                                                                <c:param name="sorting" value="${param.sorting}"/>
                                                                                            </c:when>
                                                                                        </c:choose>
                                                                                        <c:param name="sorting" value="90"/>
                                                                                        </c:url>" onclick="showSorting(this)">Best ratings</a></li>

            </ul>
        </div>
    </div>
</div>

<script src="<c:url value="/resources/js/dropDownBehaviour.js"/>"></script>
<hr class="d-flex W-line-style"/>
