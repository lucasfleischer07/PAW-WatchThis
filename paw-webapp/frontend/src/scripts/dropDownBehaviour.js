
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

    function dropDownStayGenreFilters() {
        const genreFilters = document.getElementById('drop1');

        genreFilters.addEventListener('click', function (event) {
        event.stopPropagation();
        });
    }

    function dropDownStayGenreFiltersHamburger() {
        const filterHamburger= document.getElementById('drop2');

        filterHamburger.addEventListener('click', function (event) {
            event.stopPropagation();
        });
    }

    function dropDownStayGenreCreate() {
        const genreCreateContent = document.getElementById('drop3');

        genreCreateContent.addEventListener('click', function (event) {
            event.stopPropagation();
        });
    }


    function update(feature) {
        const genreButton = document.getElementById('createGenre').innerHTML;

        if(feature.checked === true){
            document.getElementById('createGenre').innerHTML += feature.value + " ";
        }
        if(feature.checked === false){
            if(genreButton.valueOf().includes(feature.value)) {
                document.getElementById('createGenre').innerHTML = document.getElementById('createGenre').innerHTML.replaceAll(feature.value + " ", "")
            }
        }
    }

    export {update, dropDownStayGenreCreate, showGenre, showDuration, showSorting, dropDownStayGenreFilters, dropDownStayGenreFiltersHamburger, showRating}
