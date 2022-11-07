
    function showRating(item) {
    document.getElementById("ratingGroupDrop").innerHTML = item.innerHTML;
}

    function showGenre(item) {
    document.getElementById("genreGroupDrop").innerHTML = item.innerHTML;
}

    function showDuration(item) {
    document.getElementById("durationGroupDrop").innerHTML = item.innerHTML;
}

    function showSorting(item) {
        document.getElementById("sortingGroupDrop").innerHTML = item.innerHTML;
    }

    function dropDownStay() {
        const ul = document.getElementById('test');
        ul.addEventListener('click', function (event) {
            event.stopPropagation();
        });
    }




