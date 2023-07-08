import {useTranslation} from "react-i18next";
import {useContext, useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import PropTypes from 'prop-types';
import {updateUrlVariable, validateParam} from "../../scripts/validateParam";


export default function Filters(props) {

    const {t} = useTranslation()
    const navigate = useNavigate();
    const { search } = useLocation();

    const type = props.type
    const [genre, setGenre] = useState('');
    const [durationFrom, setDurationFrom] = useState('');
    const [durationTo, setDurationTo] = useState('');
    const [sorting, setSorting] = useState('');

    const [selectedGenresForm, setSelectedGenresForm] = useState([] );

    const sortingTypes = ["OlderReleased","NewestReleased","MostRated","NameAsc","NameDesc"]





    useEffect(() => {
        const queryParams = new URLSearchParams(search);
        updateUrlVariable(genre, queryParams.get('genre'),(x) => setGenre(x))
        updateUrlVariable(durationFrom, queryParams.get('durationFrom'),(x) => setDurationFrom(x))
        updateUrlVariable(durationTo, queryParams.get('durationTo'),(x) => setDurationTo(x))
        updateUrlVariable(sorting, queryParams.get('sorting'),(x) => setSorting(x))
    }, [search]);

    const dropdownHandle = (event) => {
        event.stopPropagation();
    }

    const onGenreSubmit = ( genres ) => {
        alert(genres.length)
        const queryParams = new URLSearchParams(window.location.search);
        if (genres.length === 0) {
            queryParams.delete('genre');
        } else {
            queryParams.set('genre', genres.join(','));
        }
        navigate((type === 'all' ? '/content/all' : window.location.pathname) + '?' + queryParams.toString());
    }

    const handleGenreFormChange = (e) => {
        alert(e.target.value)
        const genre = e.target.value;
        if (e.target.checked) {
            setSelectedGenresForm([...selectedGenresForm, genre]);
        } else {
            setSelectedGenresForm(selectedGenresForm.filter((g) => g !== genre));
        }
    }

    const changeDuration = (durationFrom, durationTo) => {
        const searchParams = new URLSearchParams(window.location.search);
        if( durationFrom === '0' && durationTo === '0' ){
            searchParams.delete('durationFrom');
            searchParams.delete('durationTo');
        }else{
            searchParams.set('durationFrom', durationFrom);
            searchParams.set('durationTo', durationTo);
        }
        navigate((type === 'all' ? '/content/all' : window.location.pathname) + '?' + searchParams.toString());
    }

    const changeSorting = (sorting) => {
        const searchParams = new URLSearchParams(window.location.search);
        searchParams.set('sorting', sorting);
        navigate((type === 'all' ? '/content/all' : window.location.pathname) + '?' + searchParams.toString());
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
                                    <input type="checkbox" className="px-2" name="Action"  onChange={() => handleGenreFormChange()}/>
                                    {' '} {t('Genre.Action')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Sci-Fi"  onChange={() => handleGenreFormChange()}/>
                                    {' '} {t('Genre.Science')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Comedy"  onChange={() => handleGenreFormChange()}/>
                                    {' '} {t('Genre.Comedy')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Adventure" onChange={() => handleGenreFormChange()}/>
                                    {' '} {t('Genre.Adventure')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Drama" onChange={() => handleGenreFormChange()}/>
                                    {' '} {t('Genre.Drama')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Horror" onChange={() => handleGenreFormChange()}/>
                                    {' '} {t('Genre.Horror')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Animation" onChange={() => handleGenreFormChange()}/>
                                    {' '} {t('Genre.Animation')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Thriller" onChange={() => handleGenreFormChange()}/>
                                    {' '} {t('Genre.Thriller')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Mystery" onChange={() => handleGenreFormChange()}/>
                                    {' '} {t('Genre.Mystery')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Crime" onChange={() => handleGenreFormChange()}/>
                                    {' '} {t('Genre.Crime')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Fantasy" onChange={() => handleGenreFormChange()}/>
                                    {' '} {t('Genre.Fantasy')}
                                </label>
                            </li>
                            <li className="mb-1 px-2">
                                <label>
                                    <input type="checkbox" className="px-2" name="Romance" onChange={() => handleGenreFormChange()}/>
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
                                <p className="dropdown-item" onClick={() => changeDuration('150','180')}>
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