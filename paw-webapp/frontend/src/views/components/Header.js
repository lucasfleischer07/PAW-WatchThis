import {Link, useLocation, useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";
import { useState, useEffect } from 'react';

export default function Header(props) {
    const {t} = useTranslation()

    const navigate = useNavigate();

    const [isDropdownOpen, setIsDropdownOpen] = useState(false);

    const handleDropdownToggle = () => {
        setIsDropdownOpen((prevState) => !prevState);
    };

    const [genreProp, setGenre] = useState("ANY")
    const [durationFromProp, setDurFrom] = useState("ANY")
    const [durationToProp, setDurTo] = useState("ANY")
    const [sortingProp, setSorting] = useState("ANY")
    const [queryProp, setQuery] = useState("ANY")
    const [type, setType] = useState(props.type)
    const [admin, setAdmin] = useState(props.admin)
    const [userName, setUserName] = useState(props.userName)

    const { search } = useLocation();

    useEffect(() => {
        const queryParams = new URLSearchParams(search);
        const genre = queryParams.get('genre')
        const durationFrom = queryParams.get('durationFrom')
        const durationTo = queryParams.get('durationTo')
        const sorting = queryParams.get('sorting')
        const query = queryParams.get('query')
        if (genre !== genreProp && genre !== null ) {
            setGenre(genre);
        }
        if (durationFrom !== durationFromProp && durationFrom !== null){
            setDurFrom(durationFrom)
        }
        if (durationTo !== durationToProp  && durationTo !== null){
            setDurTo(durationTo)
        }
        if (sorting !== sortingProp  && sorting !== null){
            setSorting(sorting)
        }
        if (query !== queryProp && query !== null){
            setQuery(query)
        }
    }, [search ]);




    const [queryForm, setQueryForm] = useState("ALL");

    const [contentType, setContentType] = useState("all")

    const [currentPage, setCurrentPage] = useState(1)

    //RECORDAR QUE DEPENDIENDO DEL TYPE, EL PATH DEBE CAMBIAR, EL TYPE SE DEBE RECIBIR POR PARAM
    const handleSubmit = (event) => {
        event.preventDefault();
        props.handleQuery(contentType,queryForm)

    }

    const handleChange = (e) => {
        const {name, value} = e.target
        setQueryForm((prev) => {
            return {...prev, [name]: value}
        })
    }
    //Tomo la url actual, y le seteo el parametro deseado
    const updateUrl = () => {
        const searchParams = new URLSearchParams(window.location.search);
        searchParams.set('genre', 'xd');
        navigate(window.location.pathname + '?' + searchParams.toString());
    }

    return(
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark sticky-top W-header-height">
            <div className="W-container-fluid">
                <Link className="navbar-brand" to="/">
                    <div className="W-logo-div">
                        <img src={"/images/WatchThisLogo.png"} alt="WatchThisLogo" class="W-img-size2"/>
                    </div>
                </Link>
                <div>
                    <button className="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasDarkNavbar" aria-controls="offcanvasDarkNavbar">
                        <span className="navbar-toggler-icon"/>
                    </button>
                </div>

                <div className="offcanvas offcanvas-end text-bg-dark W-header-accomodation" tabIndex="-1" id="offcanvasDarkNavbar" aria-labelledby="offcanvasDarkNavbarLabel">
                    <div className="offcanvas-header">
                        <h3 className="offcanvas-title W-hamburger-button-title" id="offcanvasDarkNavbarLabel">{t('WatchThisMessage')}</h3>
                        <button type="button" className="btn-close btn-close-white" data-bs-dismiss="offcanvas" aria-label="Close"/>
                    </div>

                    <div className="offcanvas-body W-navbar-buttons-acomodation">

                        <div className="W-navbar-hamburger-postion">
                            <ul className="navbar-nav justify-content-between flex-grow-1 pe-3 W-navitem-list">
                                {type === 'movies' || type === 'movie' ? (
                                    <>
                                        <li className="nav-item W-home-button-hamburger-button W-display-none-header">
                                            <Link className="nav-link" aria-current="page" to="/">{t('HomeMessage')}</Link>
                                        </li>
                                        <li className="nav-item W-nav-item">
                                            <Link className="nav-link active" aria-current="page" to="/movies">{t('MovieMessage')}</Link>
                                        </li>
                                        <li className="nav-item W-nav-item">
                                            <Link className="nav-link" to="/series">{t('SerieMessage')}</Link>
                                        </li>
                                    </>
                                ) : type === 'series' || type === 'serie' ? (
                                    <>
                                        <li className="nav-item W-home-button-hamburger-button">
                                            <Link className="nav-link" aria-current="page" to="/">{t('HomeMessage')}</Link>
                                        </li>
                                        <li className="nav-item W-nav-item">
                                            <Link className="nav-link" aria-current="page" to="/movies">{t('MovieMessage')}</Link>
                                        </li>
                                        <li className="nav-item W-nav-item">
                                            <Link className="nav-link active" to="/series">{t('SerieMessage')}</Link>
                                        </li>
                                    </>
                                ) : (
                                    <>
                                        <li className="nav-item W-home-button-hamburger-button">
                                            <Link className="nav-link" aria-current="page" to="/">{t('HomeMessage')}</Link>
                                        </li>
                                        <li className="nav-item W-nav-item">
                                            <Link className="nav-link" aria-current="page" to="/movies">{t('MovieMessage')}</Link>
                                        </li>
                                        <li className="nav-item W-nav-item">
                                            <Link className="nav-link" to="/series">{t('SerieMessage')}</Link>
                                        </li>
                                    </>
                                )}
                            </ul>
                        </div>

                        <div className="d-flex W-navbar-search">
                            <form className="form-inline my-2 my-lg-0 W-searchbar" onSubmit={handleSubmit}>
                                { queryProp !== 'ANY' ? (
                                    <input name="query" className="form-control me-2" type="search" placeholder={t('SearchMessage')} aria-label="Search" value={queryProp} onChange={handleChange} />
                                ) : (
                                    <input name="query" className="form-control me-2" type="search" placeholder={t('SearchMessage')} aria-label="Search"  onChange={handleChange} />
                                )}
                                {genreProp !== 'ANY' && genreProp !== null &&
                                    <input type="hidden" name="genreProp" value={genreProp} />
                                }
                                {durationFromProp !== 'ANY' && durationFromProp !== null &&
                                    <>
                                        <input type="hidden" name="durationFrom" value={durationFromProp} />
                                        <input type="hidden" name="durationTo" value={durationToProp} />
                                    </>
                                }
                                {sortingProp !== 'ANY' && sortingProp !== null &&
                                    <input type="hidden" name="sorting" value={sortingProp} />
                                }
                                <button className="btn btn-success W-search-button-color" type="submit">
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-search W-search-icon" viewBox="0 0 16 16">
                                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
                                    </svg>
                                </button>
                            </form>
                        </div>

                        <div className="W-nav-login-button">
                            {userName !== "null" && userName !== "" ? (
                                <div className="btn-group">
                                    <button type="button" className="btn btn-dark dropdown-toggle W-border-color-user-btn" onClick={handleDropdownToggle} aria-expanded={isDropdownOpen}>
                                        {userName}
                                    </button>
                                    <ul className={`dropdown-menu ${isDropdownOpen ? "show" : ""}`}>
                                        <li>
                                            <Link className="dropdown-item" to="/profile">{t('Profile')}</Link>
                                        </li>
                                        <li>
                                            <Link className="dropdown-item" to="/profile/watchList">{t('WatchList')}</Link>
                                        </li>
                                        <li>
                                            <Link className="dropdown-item" to="/profile/viewedList">{t('ViewedList.Title')}</Link>
                                        </li>
                                        {admin === true || admin === "true" ? (
                                            <>
                                                <li>
                                                    <Link className="dropdown-item" to="/profile/create">{t('CreateContent.Message')}</Link>
                                                </li>
                                                <li>
                                                    <Link className="dropdown-item" to="/reports">{t('Report.ReportedContent')}</Link>
                                                </li>
                                            </>
                                        ) : null}
                                        <li>
                                            <hr className="dropdown-divider" />
                                        </li>
                                        <li>
                                            <Link className="dropdown-item" to="/login/sign-out">{t('LogOutMessage')}</Link>
                                        </li>
                                    </ul>
                                </div>
                            ) : (
                                <Link className="dropdown-item" to="/login/sign-in">{t('LoginMessage')}</Link>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </nav>

    );



}