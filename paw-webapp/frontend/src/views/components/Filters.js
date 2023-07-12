import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import {updateUrlVariable} from "../../scripts/validateParam";
import {checkIsGenre, checkIsFrom, checkIsTo, checkIsSort} from "../../scripts/filtersValidations"


export default function Filters(props) {

    const {t} = useTranslation()
    const navigate = useNavigate();
    const { search } = useLocation();

    const type = props.type
    const [genre, setGenre] = useState('');
    const [durationFrom, setDurationFrom] = useState('');
    const [durationTo, setDurationTo] = useState('');
    const [sorting, setSorting] = useState('');

    const [genreState, setGenreState] = useState({
        Action: false,
        "Sci-Fi": false,
        Comedy: false,
        Adventure: false,
        Drama: false,
        Horror: false,
        Animation: false,
        Thriller: false,
        Mystery: false,
        Crime: false,
        Fantasy: false,
        Romance: false,
    });

    const sortingTypes = ["OlderReleased", "NewestReleased", "MostRated", "NameAsc", "NameDesc"]


    useEffect(() => {
        const queryParams = new URLSearchParams(search);
        updateUrlVariable(genre, checkIsGenre(queryParams.get('genre')),(x) => setGenre(x))
        updateUrlVariable(durationFrom, checkIsFrom(queryParams.get('durationFrom')),(x) => setDurationFrom(x))
        updateUrlVariable(durationTo, checkIsTo(queryParams.get('durationTo')),(x) => setDurationTo(x))
        updateUrlVariable(sorting, checkIsSort(queryParams.get('sorting')),(x) => setSorting(x))
        let genres=queryParams.get('genre')
        if(genres!==null) {
            genreState.Action = genres.includes("Action")
            genreState["Sci-Fi"] = genres.includes("Sci-Fi")
            genreState.Comedy = genres.includes("Comedy")
            genreState.Adventure = genres.includes("Adventure")
            genreState.Animation = genres.includes("Animation")
            genreState.Drama = genres.includes("Drama")
            genreState.Horror = genres.includes("Horror")
            genreState.Thriller = genres.includes("Thriller")
            genreState.Mystery = genres.includes("Mystery")
            genreState.Crime = genres.includes("Crime")
            genreState.Fantasy = genres.includes("Fantasy")
            genreState.Romance = genres.includes("Romance")
        }
    }, [search]);

    const dropdownHandle = (event) => {
        event.stopPropagation();
    }

    const onGenreSubmit = () => {
        const queryParams = new URLSearchParams(window.location.search);
        if (genre.length === 0) {
            queryParams.delete('genre');
        } else {
            let aux = []
            aux = genre.split(" ")
            aux = aux.filter((element) => element !== '');
            queryParams.set('genre', aux);
        }
        const currentPath = window.location.pathname.substring('/paw-2022b-3'.length);
        navigate((type === 'all' ? '/content/all' : currentPath) + '?' + queryParams.toString());
    }

    const handleGenreFormChange = (e) => {
        const genreChecked = e.target.value;
        setGenreState((prevGenre) => ({
            ...prevGenre,
            [genreChecked]: !prevGenre[genreChecked],
        }));
        if (e.target.checked) {
            const aux = genre + " " + genreChecked
            setGenre(aux)
        } else {
            // Escapar caracteres especiales en la palabra buscada armo la regExp y reemplazo
            const genreEscaped = genreChecked.replace(/[.*+?^${}()|[\]\\]/g, "\\$&");
            const regularExp = new RegExp("(,\\s*)?" + genreEscaped + "\\s?", "gi");
            const finalGenre = genre.replace(regularExp, "");
            setGenre(finalGenre)
        }
    }

    const changeDuration = (durationFrom, durationTo) => {
        const searchParams = new URLSearchParams(window.location.search);
        if( durationFrom === '0' && durationTo === '0' ){
            searchParams.delete('durationFrom');
            searchParams.delete('durationTo');
        } else {
            searchParams.set('durationFrom', durationFrom);
            searchParams.set('durationTo', durationTo);
        }
        const currentPath = window.location.pathname.substring('/paw-2022b-3'.length);
        navigate((type === 'all' ? '/content/all' : currentPath) + '?' + searchParams.toString());
    }

    const changeSorting = (sorting) => {
        const searchParams = new URLSearchParams(window.location.search);
        searchParams.set('sorting', sorting);
        const currentPath = window.location.pathname.substring('/paw-2022b-3'.length);
        navigate((type === 'all' ? '/content/all' : currentPath) + '?' + searchParams.toString());
    }


    return(
        <div className="W-filter-div">
            <div className="W-genre-duration-div">
                <div className="list-group">
                    <div className="dropdown W-dropdown-button">
                        {genre !== '' && genre !== 'ANY' && genre != null ? (
                            <button id="genreGroupDrop" type="button" className="W-filter-title W-genre-filter btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                {genre}
                            </button>
                        ) : (
                            <button id="genreGroupDrop" type="button" className="W-filter-title W-genre-filter btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                {t('GenreMessage')}
                            </button>
                        )}

                        <ul className="dropdown-menu" id="drop1" onClick={dropdownHandle}>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Action" value="Action" checked={genreState.Action} onChange={(e) => handleGenreFormChange(e)}/>
                                    {' '} {t('Genre.Action')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Sci-Fi" value="Sci-Fi" checked={genreState["Sci-Fi"]} onChange={(e) => handleGenreFormChange(e)}/>
                                    {' '} {t('Genre.Science')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Comedy" value="Comedy" checked={genreState.Comedy} onChange={(e) => handleGenreFormChange(e)}/>
                                    {' '} {t('Genre.Comedy')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Adventure" value="Adventure" checked={genreState.Adventure} onChange={(e) => handleGenreFormChange(e)}/>
                                    {' '} {t('Genre.Adventure')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Drama" value="Drama" checked={genreState.Drama} onChange={(e) => handleGenreFormChange(e)}/>
                                    {' '} {t('Genre.Drama')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Horror" value="Horror" checked={genreState.Horror} onChange={(e) => handleGenreFormChange(e)}/>
                                    {' '} {t('Genre.Horror')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Animation" value="Animation" checked={genreState.Animation} onChange={(e) => handleGenreFormChange(e)}/>
                                    {' '} {t('Genre.Animation')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Thriller" value="Thriller" checked={genreState.Thriller} onChange={(e) => handleGenreFormChange(e)}/>
                                    {' '} {t('Genre.Thriller')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Mystery" value="Mystery" checked={genreState.Mystery} onChange={(e) => handleGenreFormChange(e)}/>
                                    {' '} {t('Genre.Mystery')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Crime" value="Crime" checked={genreState.Crime} onChange={(e) => handleGenreFormChange(e)}/>
                                    {' '} {t('Genre.Crime')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Fantasy" value="Fantasy" checked={genreState.Fantasy} onChange={(e) => handleGenreFormChange(e)}/>
                                    {' '} {t('Genre.Fantasy')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Romance" value="Romance" checked={genreState.Romance} onChange={(e) => handleGenreFormChange(e)}/>
                                    {' '} {t('Genre.Romance')}
                                </label>
                            </li>

                            <div className="W-apply-button">
                                <button type="submit" className="btn btn-danger mb-1 px-2" onClick={() => onGenreSubmit()}>
                                    {t('Apply')}
                                </button>
                            </div>
                        </ul>
                    </div>
                </div>

                <div className="list-group">
                    <div className="dropdown W-dropdown-button">
                        <button id="genreGroupDrop" type="button" className="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                            { durationFrom !== '' && durationTo !== '0' ?(
                                t('Duration.From.To',{"durationFromArgument":durationFrom,"durationToArgument":durationTo})
                            ) : (
                                t('DurationMessage')
                            )}
                        </button>
                        <ul className="dropdown-menu">
                            <li>
                                <p className="dropdown-item" onClick={() => changeDuration('0','0')}>
                                    {t('Duration.Clear')}
                                </p>
                            </li>
                            <li>
                                <p className="dropdown-item" onClick={() => changeDuration('0','90')}>
                                    {t('Duration.0_90')}
                                </p>
                            </li>
                            <li>
                                <p className="dropdown-item" onClick={() => changeDuration('90','120')}>
                                    {t('Duration.90_120')}
                                </p>
                            </li>
                            <li>
                                <p className="dropdown-item" onClick={() => changeDuration('120','150')}>
                                    {t('Duration.120_150')}
                                </p>
                            </li>
                            <li>
                                <p className="dropdown-item" onClick={() => changeDuration('150','1000')}>
                                    {t('Duration.150_more')}
                                </p>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div className="list-group">
                <div className="dropdown W-dropdown-button-sorting">
                    <button id="genreGroupDrop" type="button" className="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        { sorting !== '' &&  sorting !== 'ANY' && sorting != null ? (
                            t('Sort.' + sorting )
                        ) : (
                            t('SortMessage')
                        )}
                    </button>
                    <ul className="dropdown-menu">
                        {sortingTypes.map((sortingType) => (
                            <li key={sortingType}>
                                <p className="dropdown-item" onClick={() => changeSorting(sortingType)}>
                                    {t(`Sort.${sortingType}`)}
                                </p>
                            </li>
                        ))}
                    </ul>


                </div>
            </div>
        </div>
    )
}