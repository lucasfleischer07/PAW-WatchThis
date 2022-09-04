<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<div class="W-filter-div">
    <p class="W-filter-title">Rating</p>
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
    </div>

    <p class="W-filter-title">Genre</p>
    <div class="list-group">
        <div class="form-check">
            <input class="form-check-input" type="checkbox" value="" id=genreFilterAction>
            <label class="form-check-label" for="genreFilterAction">
                Action
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="checkbox" value="" id="genreFilterScienceFiction" >
            <label class="form-check-label" for="genreFilterScienceFiction">
                Science Fiction
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="checkbox" value="" id="genreFilterComedy" >
            <label class="form-check-label" for="genreFilterComedy">
                Comedy
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="checkbox" value="" id="genreFilterAdventure" >
            <label class="form-check-label" for="genreFilterAdventure">
                Adventure
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="checkbox" value="" id="genreFilterDrama" >
            <label class="form-check-label" for="genreFilterDrama">
                Drama
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="checkbox" value="" id="genreFilterHorror" >
            <label class="form-check-label" for="genreFilterHorror">
                Horror
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="checkbox" value="" id="genreFilterAnimation" >
            <label class="form-check-label" for="genreFilterAnimation">
                Animation
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="checkbox" value="" id="genreFilterThrillers" >
            <label class="form-check-label" for="genreFilterThrillers">
                Thrillers
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="checkbox" value="" id="genreFilterMystery" >
            <label class="form-check-label" for="genreFilterMystery">
                Mystery
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="checkbox" value="" id="genreFilterCrime" >
            <label class="form-check-label" for="genreFilterCrime">
                Crime
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="checkbox" value="" id="genreFilterFantasy" >
            <label class="form-check-label" for="genreFilterFantasy">
                Fantasy
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="checkbox" value="" id="genreFilterRomance" >
            <label class="form-check-label" for="genreFilterRomance">
                Romance
            </label>
        </div>
    </div>
<%--        <a href="#" class="list-group-item border-0  W-filter-items-text-style">Action</a>--%>
<%--        <a href="#" class="list-group-item border-0  W-filter-items-text-style">Science Fiction</a>--%>
<%--        <a href="#" class="list-group-item border-0  W-filter-items-text-style">Comedy</a>--%>
<%--        <a href="#" class="list-group-item border-0  W-filter-items-text-style">Adventure</a>--%>
<%--        <a href="#" class="list-group-item border-0  W-filter-items-text-style">Drama</a>--%>
<%--        <a href="#" class="list-group-item border-0  W-filter-items-text-style">Horror</a>--%>
<%--        <a href="#" class="list-group-item border-0  W-filter-items-text-style">Animation</a>--%>
<%--        <a href="#" class="list-group-item border-0  W-filter-items-text-style">Thrillers</a>--%>
<%--        <a href="#" class="list-group-item border-0  W-filter-items-text-style">Mystery</a>--%>
<%--        <a href="#" class="list-group-item border-0  W-filter-items-text-style">Crime</a>--%>
<%--        <a href="#" class="list-group-item border-0  W-filter-items-text-style">Fantasy</a>--%>
<%--        <a href="#" class="list-group-item border-0  W-filter-items-text-style">Romance</a>--%>

<%--        <a href="<c:url value="/movies/Action">" class="list-group-item border-0  W-filter-items-text-style">Action</a>--%>
<%--        <a href="<c:url value="/movies/Sfi-Fi">" class="list-group-item border-0  W-filter-items-text-style">Science Fiction</a>--%>
<%--        <a href="<c:url value="/movies/Comedy">" class="list-group-item border-0  W-filter-items-text-style">Comedy</a>--%>
<%--        <a href="<c:url value="/movies/Adventure">" class="list-group-item border-0  W-filter-items-text-style">Adventure</a>--%>
<%--        <a href="<c:url value="/movies/Drama">" class="list-group-item border-0  W-filter-items-text-style">Drama</a>--%>
<%--        <a href="<c:url value="/movies/Horror">" class="list-group-item border-0  W-filter-items-text-style">Horror</a>--%>
<%--        <a href="<c:url value="/movies/Animation">" class="list-group-item border-0  W-filter-items-text-style">Animation</a>--%>
<%--        <a href="<c:url value="/movies/Thriller">" class="list-group-item border-0  W-filter-items-text-style">Thrillers</a>--%>
<%--        <a href="<c:url value="/movies/Mystery">" class="list-group-item border-0  W-filter-items-text-style">Mystery</a>--%>
<%--        <a href="<c:url value="/movies/Crime">" class="list-group-item border-0  W-filter-items-text-style">Crime</a>--%>
<%--        <a href="<c:url value="/movies/Fantasy">" class="list-group-item border-0  W-filter-items-text-style">Fantasy</a>--%>
<%--        <a href="<c:url value="/movies/Romance">" class="list-group-item border-0  W-filter-items-text-style">Romance</a>                                        --%>

    <p class="W-filter-title">Duration</p>
    <div class="list-group">
        <div class="form-check">
            <input class="form-check-input" type="radio" name="flexRadioDefault" id="durationFilter90">
            <label class="form-check-label" for="durationFilter90">
                0-90 minutes
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="radio" name="flexRadioDefault" id="durationFilter120">
            <label class="form-check-label" for="durationFilter120">
                90-120 minutes
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="radio" name="flexRadioDefault" id="durationFilter150">
            <label class="form-check-label" for="durationFilter150">
                120-150 minutes
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="radio" name="flexRadioDefault" id="durationFilterMore">
            <label class="form-check-label" for="durationFilterMore">
                150 or more
            </label>
        </div>
<%--        <a href="#" class="list-group-item border-0 W-filter-items-text-style">0-90 minutes</a>--%>
<%--        <a href="#" class="list-group-item border-0 W-filter-items-text-style">90-120 minutes</a>--%>
<%--        <a href="#" class="list-group-item border-0 W-filter-items-text-style">120-150 minutes</a>--%>
<%--        <a href="#" class="list-group-item border-0 W-filter-items-text-style">150 or more</a>--%>
    </div>

    <div class="W-confirm-filters-button">
        <button type="button" class="btn btn-success">Apply</button>
    </div>

</div>

<div class="d-flex W-filter-line-divider" >
    <div class="vr W-line-style"></div>
</div>