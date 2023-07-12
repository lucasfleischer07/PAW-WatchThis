const validGenre = ["Action", "Sci-Fi", "Comedy", "Adventure", "Drama", "Horror", "Animation", "Thriller", "Mystery", "Crime", "Fantasy", "Romance"]
const validFrom = ["0", "90", "120", "150"]
const validTo = [ "90", "120", "150", "1000"]
const validSort = [ "OlderReleased", "NewestReleased", "MostRated", "NameAsc","NameDesc"]
const validReason = ["Spam", "Insult", "Inappropriate", "Unrelated", "Other"]

function checkIsGenre(genreVal) {
    if (genreVal !== null){
        let genreList = genreVal.split(',')
        genreList = genreList.filter(value => validGenre.includes(value));
        return genreList.length > 0 ? genreList.join(',') : ""
    }
    return null
}

function checkIsFrom(fromVal){
    return fromVal !== null? (validFrom.includes(fromVal) ? fromVal : "") : null
}

function checkIsTo(toVal){
    return toVal !== null ? (validTo.includes(toVal) ? toVal : "") : null
}

function checkIsSort(sortVal){
    return sortVal !== null ? (validSort.includes(sortVal) ? sortVal : "") : null
}

function checkIsNumber(pageVal){
    return isNaN(pageVal) ? parseInt(pageVal) : 1
}

function checkIsReason(reasonVal){
    return reasonVal !== null ? (validReason.includes(reasonVal) ? reasonVal : "") : null
}

export {checkIsGenre, checkIsFrom, checkIsTo, checkIsSort, checkIsNumber, checkIsReason}
