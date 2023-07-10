import {Link, useLocation, useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {useState, useEffect, useContext} from 'react';
import {toast} from "react-toastify";
import {AuthContext} from "../../context/AuthContext";

export default function Header(props) {
    const {t} = useTranslation()
    const navigate = useNavigate();
    let {signOut} = useContext(AuthContext)

    // const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    //
    // const handleDropdownToggle = () => {
    //     setIsDropdownOpen((prevState) => !prevState);
    // };

    const [queryProp, setQuery] = useState("")
    const type = props.type
    const admin = props.admin
    const userName = props.userName
    const userId = props.userId

    const { search } = useLocation();

    const [queryForm, setQueryForm] = useState({
        query: ""
    });

    const [contentType, setContentType] = useState("all")

    const [currentPage, setCurrentPage] = useState(1)

    const validateSearch = () => {
        return ( queryForm.query != null && queryForm.query.length > 0 )
    }

    //RECORDAR QUE DEPENDIENDO DEL TYPE, EL PATH DEBE CAMBIAR, EL TYPE SE DEBE RECIBIR POR PARAM
    const handleSubmit = (event) => {
        event.preventDefault();
        const searchParams = new URLSearchParams(window.location.search);
        if( validateSearch() ){
            searchParams.set('query', queryForm.query);
            if (type === 'all'){
                navigate('/content/all' + '?' + searchParams.toString());
            }else if ( type === 'movie'){
                navigate('/content/movie' + '?' + searchParams.toString());
            }else{
                navigate('/content/serie' + '?' + searchParams.toString());
            }
        }
    }

    const handleLogOut = (e) => {
        e.preventDefault();
        signOut()
        navigate("/",{replace:true})
        toast.success(t('Logout.Success'))
    }

    const handleChange = (e) => {
        const {name, value} = e.target
        setQueryForm((prev) => {
            return {...prev, [name]: value}
        })
    }

    useEffect(() => {
        const queryParams = new URLSearchParams(search);
        const query = queryParams.get('query')
        if (query !== queryProp && query !== null){
            setQuery(query)
        }
    }, [search]);

    return(
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark sticky-top W-header-height">
            <div className="W-container-fluid">
                <Link className="navbar-brand" to="/">
                    <div className="W-logo-div">
                        <img src={"/images/WatchThisLogo.png"} alt="WatchThisLogo" className="W-img-size2"/>
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
                                            <Link className="nav-link active" aria-current="page" to="/content/movie">{t('MovieMessage')}</Link>
                                        </li>
                                        <li className="nav-item W-nav-item">
                                            <Link className="nav-link" to="/content/serie">{t('SerieMessage')}</Link>
                                        </li>
                                    </>
                                ) : type === 'series' || type === 'serie' ? (
                                    <>
                                        <li className="nav-item W-home-button-hamburger-button">
                                            <Link className="nav-link" aria-current="page" to="/">{t('HomeMessage')}</Link>
                                        </li>
                                        <li className="nav-item W-nav-item">
                                            <Link className="nav-link" aria-current="page" to="/content/movie">{t('MovieMessage')}</Link>
                                        </li>
                                        <li className="nav-item W-nav-item">
                                            <Link className="nav-link active" to="/content/serie">{t('SerieMessage')}</Link>
                                        </li>
                                    </>
                                ) : (
                                    <>
                                        <li className="nav-item W-home-button-hamburger-button">
                                            <Link className="nav-link" aria-current="page" to="/">{t('HomeMessage')}</Link>
                                        </li>
                                        <li className="nav-item W-nav-item">
                                            <Link className="nav-link" aria-current="page" to="/content/movie">{t('MovieMessage')}</Link>
                                        </li>
                                        <li className="nav-item W-nav-item">
                                            <Link className="nav-link" to="/content/serie">{t('SerieMessage')}</Link>
                                        </li>
                                    </>
                                )}
                            </ul>
                        </div>

                        <div className="d-flex W-navbar-search">
                            <form className="form-inline my-2 my-lg-0 W-searchbar" onSubmit={handleSubmit}>
                                { queryProp !== '' ? (
                                    <input name="query" className="form-control me-2" type="search" placeholder={t('SearchMessage')} aria-label="Search" value={queryForm.query} onChange={handleChange} />
                                ) : (
                                    <input name="query" className="form-control me-2" type="search" placeholder={t('SearchMessage')} aria-label="Search"  onChange={handleChange} />
                                )}

                                <button className="btn btn-success W-search-button-color" type="submit">
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-search W-search-icon" viewBox="0 0 16 16">
                                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
                                    </svg>
                                </button>
                            </form>
                        </div>

                        <div className="W-nav-login-button">
                            {userName !== null && userName !== "" && userName !== undefined ? (
                                <div className="dropdown">
                                        <button type="button"
                                                className="btn btn-dark dropdown-toggle W-border-color-user-btn"
                                                data-bs-toggle="dropdown"
                                                aria-expanded="false">

                                        {userName}
                                    </button>

                                    <ul className="dropdown-menu">

                                        <li>
                                            <Link className="dropdown-item" to={`/user/profile/${userId}`}>{t('Profile')}</Link>
                                        </li>
                                        <li>
                                            <Link className="dropdown-item" to="/user/watchList">{t('WatchList')}</Link>
                                        </li>
                                        <li>
                                            <Link className="dropdown-item" to="/user/viewedList">{t('ViewedList.Title')}</Link>
                                        </li>
                                        {admin === true || admin === "true" ? (
                                            <>
                                                <li>
                                                    <Link className="dropdown-item" to="/content/form/create">{t('CreateContent.Message')}</Link>
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
                                            <Link className="dropdown-item" to="/" onClick={handleLogOut}>{t('LogOutMessage')}</Link>
                                        </li>
                                    </ul>
                                </div>
                            ) : (
                                <Link className="dropdown-item" to="/login">{t('LoginMessage')}</Link>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </nav>
    );
}