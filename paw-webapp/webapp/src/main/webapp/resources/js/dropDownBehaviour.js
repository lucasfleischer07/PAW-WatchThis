
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
        const ul = document.getElementById('drop1');
        const ul2= document.getElementById('drop2');
        ul.addEventListener('click', function (event) {
            event.stopPropagation();
        });
        ul2.addEventListener('click', function (event) {
            event.stopPropagation();
        });
    }




