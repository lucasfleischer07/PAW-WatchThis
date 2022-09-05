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

    <p class="W-filter-title">Genre</p>
    <div class="list-group">
        <a class="list-group-item border-0  W-filter-items-text-style" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Action"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>">Action</a>
        <a class="list-group-item border-0  W-filter-items-text-style" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Sci-Fi"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                             <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>">Science Fiction</a>
        <a class="list-group-item border-0  W-filter-items-text-style" href="<c:url value="/${param.type}/filters">
                                                                             <c:param name="genre" value="Comedy"/>
                                                                             <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                             <c:param name="durationTo" value="${param.durationTo}"/>
                                                                             </c:url>">Comedy</a>
        <a class="list-group-item border-0  W-filter-items-text-style" href="<c:url value="/${param.type}/filters">
                                                                             <c:param name="genre" value="Adventure"/>
                                                                             <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                             <c:param name="durationTo" value="${param.durationTo}"/>
                                                                             </c:url>">Adventure</a>
        <a class="list-group-item border-0  W-filter-items-text-style" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Drama"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>">Drama</a>
        <a class="list-group-item border-0  W-filter-items-text-style" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Horror"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>">Horror</a>
        <a class="list-group-item border-0  W-filter-items-text-style" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Animation"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>">Animation</a>
        <a class="list-group-item border-0  W-filter-items-text-style" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Thrillers"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>">Thrillers</a>
        <a class="list-group-item border-0  W-filter-items-text-style" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Mystery"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>">Mystery</a>
        <a class="list-group-item border-0  W-filter-items-text-style" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Crime"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>">Crime</a>
        <a class="list-group-item border-0  W-filter-items-text-style" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Fantasy"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>">Fantasy</a>
        <a class="list-group-item border-0  W-filter-items-text-style" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="Romance"/>
                                                                            <c:param name="durationFrom" value="${param.durationFrom}"/>
                                                                            <c:param name="durationTo" value="${param.durationTo}"/>
                                                                            </c:url>">Romance</a>
    </div>

    <p class="W-filter-title">Duration</p>
    <div class="list-group">

        <a class="list-group-item border-0  W-filter-items-text-style" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="${param.genre}"/>
                                                                            <c:param name="durationFrom" value="0"/>-<c:param name="durationTo" value="90"/>
                                                                            </c:url>">0-90 minutes</a>
        <a class="list-group-item border-0  W-filter-items-text-style" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="${param.genre}"/>
                                                                            <c:param name="durationFrom" value="90"/>-<c:param name="durationTo" value="120"/>
                                                                            </c:url>">90-120 minutes</a>
        <a class="list-group-item border-0  W-filter-items-text-style" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="${param.genre}"/>
                                                                            <c:param name="durationFrom" value="120"/>-<c:param name="durationTo" value="150"/>
                                                                            </c:url>">120-150 minutes</a>
        <a class="list-group-item border-0  W-filter-items-text-style" href="<c:url value="/${param.type}/filters">
                                                                            <c:param name="genre" value="${param.genre}"/>
                                                                            <c:param name="durationFrom" value="150"/>-<c:param name="durationTo" value="1000"/>
                                                                            </c:url>">150 or more</a>
    </div>

<%-- TODO: VER SI DESPUES LO VAMOS A AGREGAR--%>
<%--    <div class="W-confirm-filters-button">--%>
<%--        <button type="button" class="btn btn-success">Apply</button>--%>
<%--    </div>--%>

</div>

<div class="d-flex" >
    <div class="vr W-line-style"></div>
</div>