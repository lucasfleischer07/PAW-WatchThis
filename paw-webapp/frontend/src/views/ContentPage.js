import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {contentService, listsService} from "../services";
import ContentCard from "./components/ContentCard";
import {Link, useLocation, useNavigate, useParams} from "react-router-dom";
import Header from "./components/Header";
import Filters from "./components/Filters";
import ExpiredCookieModal from "./components/ExpiredCookieModal";
import {updateUrlVariable} from "../scripts/validateParam";
import {checkIsFrom, checkIsGenre, checkIsNumber, checkIsSort, checkIsTo} from "../scripts/filtersValidations";

export default function ContentPage(props) {
    const {t} = useTranslation()
    let navigate = useNavigate()
    const { contentType } = useParams();
    const { search } = useLocation();
    const [showExpiredCookiesModal, setShowExpiredCookiesModal] = useState(false)

    const [allContent, setAllContent] = useState([])
    const [actualPage, setActualPage] = useState(1)
    const [amountPages, setAmountPages] = useState(1)
    const [userWatchListIds, setUserWatchListIds] = useState([])
    const [user, setUser] = useState(localStorage.hasOwnProperty("user")? JSON.parse(localStorage.getItem("user")) : null)
    const [genre, setGenre] = useState('');
    const [durationFrom, setDurationFrom] = useState('');
    const [durationTo, setDurationTo] = useState('');
    const [sorting, setSorting] = useState('');
    const [query, setQuery] = useState('')
    const [settedParams,isSettedParams]=useState(false)


    useEffect(() => {
        if(user === undefined) {
            setUser(JSON.parse(localStorage.getItem("user")))
        }
    }, [user])

    useEffect(() => {
        setActualPage(1)
    }, [contentType])


    useEffect(() => {
        if(settedParams === true) {
            contentService.getContentByType(contentType, actualPage, genre, durationFrom, durationTo, sorting, query)
                .then(data => {
                    if(!data.error) {
                        setAllContent(data.data)
                        const aux = data.totalPages
                        setAmountPages(aux);
                    } else {
                        navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                    }
                })
                .catch(() => {
                    navigate("/error", { replace: true, state: {errorCode: 404} })
                })
        }
    }, [actualPage, contentType,genre,durationFrom,durationTo,sorting,query,settedParams])


    useEffect(() => {
        user !== null || undefined?
            contentService.getContentByType(null, 1, '', '', '', '', '', user.id, true, false, false)
                .then(watchList => {
                    if(!watchList.error) {
                        setUserWatchListIds(watchList.data)
                    } else {
                        if(watchList.errorCode === 404) {
                            setShowExpiredCookiesModal(true)
                        } else {
                            navigate("/error", { replace: true, state: {errorCode: watchList.errorCode} })
                        }
                    }
                })
                .catch(() => {
                    navigate("/error", { replace: true, state: {errorCode: 404} })
                })

            : setUserWatchListIds(null)
    }, [user])

    useEffect(() => {
        const queryParams = new URLSearchParams(search);
        updateUrlVariable(genre, checkIsGenre(queryParams.get('genre')),(x) => setGenre(x))
        updateUrlVariable(durationFrom, checkIsFrom(queryParams.get('durationFrom')),(x) => setDurationFrom(x))
        updateUrlVariable(durationTo, checkIsTo(queryParams.get('durationTo')),(x) => setDurationTo(x))
        updateUrlVariable(sorting, checkIsSort(queryParams.get('sorting')),(x) => setSorting(x))
        updateUrlVariable(actualPage, checkIsNumber(queryParams.get('page')), (x) =>setActualPage(x))
        updateUrlVariable(query, queryParams.get('query'), (x) =>setQuery(x))
        isSettedParams(true)
    }, [search]);

    const prevPage = () => {
        changeUrlPage(actualPage - 1)
    }

    const nextPage = () => {
        changeUrlPage(actualPage + 1)
    }

    const changePage = ( newPage) => {
        changeUrlPage(newPage)
    }

    const changeUrlPage = (page) => {
        const searchParams = new URLSearchParams(search);
        searchParams.set('page', page);
        const currentPath = window.location.pathname.substring('/paw-2022b-3'.length);
        navigate(currentPath + '?' + searchParams.toString());
    }

    useEffect(() => {
        if(contentType === "movie") {
            document.title = t('MovieMessage')
        } else if(contentType === "serie"){
            document.title = t('SerieMessage')
        } else {
            document.title = t('WatchThisMessage')
        }
    })

    return (
        <div>
            {showExpiredCookiesModal && (
                <ExpiredCookieModal/>
            )}

            <Header type={contentType} admin={user?.role === 'admin'} userName={user?.username} userId={user?.id}/>
            <Filters type={contentType}/>


            {(props.query != null && props.query !== '' && props.query !== 'ANY' && allContent.length > 0) && (
                <h3 className="W-search-context-title">
                    <span>{t('Search.Title', {query: query})}</span>
                </h3>
            )}
            <div className="W-films-div">
                <div className="row row-cols-1 row-cols-md-2 g-2 W-content-alignment">
                    {allContent.map((content) => (
                        <ContentCard
                            key={content.id}
                            contentName={content.name}
                            contentReleased={content.releaseDate}
                            contentCreator={content.creator}
                            contentGenre={content.genre}
                            contentImage={content.contentPictureUrl}
                            contentId={content.id}
                            contentType={content.type}
                            contentRating={content.rating}
                            reviewsAmount={content.reviewsAmount}
                            user={user}
                            isInWatchList={userWatchListIds !== null ? userWatchListIds.some(item => item.id === content.id) : false}
                        />
                    ))}
                </div>
            </div>
            {amountPages > 1 && (
                <div>
                    {(allContent.length > 0) && (
                        <ul className="pagination justify-content-center W-pagination">
                            {actualPage > 1 ? (
                                <li key={"prev"} className="page-item">
                                    <p className="page-link W-pagination-color" onClick={() => prevPage()}>
                                        {t('Pagination.Prev')}
                                    </p>
                                </li>
                            ) : (
                                <li key={"prev"} className="page-item disabled">
                                    <p className="page-link W-pagination-color">{t('Pagination.Prev')}</p>
                                </li>
                            )}
                            {amountPages > 10 ? (
                                Array.from({ length: amountPages }, (_, index) => (
                                    index + 1 === parseInt(actualPage) ? (
                                        <li key={index+1} className="page-item active">
                                            <p className="page-link W-pagination-color">{index + 1}</p>
                                        </li>
                                    ): index + 1 === parseInt(actualPage) + 4 ? (
                                        <li key={index+1} className="page-item">
                                            <p className="page-link W-pagination-color" onClick={() => changePage(index + 1)}>
                                                ...
                                            </p>
                                        </li>
                                    ): index + 1 === parseInt(actualPage) - 4 ? (
                                        <li key={index+1} className="page-item">
                                            <p className="page-link W-pagination-color" onClick={() => changePage(index + 1)}>
                                                ...
                                            </p>
                                        </li>
                                    ) : ( index + 1 > parseInt(actualPage) - 4 && index + 1 < parseInt(actualPage) + 4 ) && (
                                        <li key={index+1} className="page-item">
                                            <p className="page-link W-pagination-color" onClick={() => changePage(index + 1)}>
                                                {index + 1}
                                            </p>
                                        </li>
                                    )
                                ))
                            ) : (
                                Array.from({ length: amountPages }, (_, index) => (
                                    index + 1 === actualPage ? (
                                        <li key={index+1} className="page-item active">
                                            <p className="page-link W-pagination-color">{index + 1}</p>
                                        </li>
                                    ) : (
                                        <li key={index+1} className="page-item">
                                            <p className="page-link W-pagination-color" onClick={() => changePage(index + 1)}>
                                                {index + 1}
                                            </p>
                                        </li>
                                    )
                                ))
                            )}
                            {actualPage < amountPages ? (
                                <li key={"next"} className="page-item">
                                    <p className="page-link W-pagination-color" onClick={() => nextPage()}>
                                        {t('Pagination.Next')}
                                    </p>
                                </li>
                            ) : (
                                <li key={"next"} className="page-item disabled">
                                    <p className="page-link W-pagination-color">{t('Pagination.Next')}</p>
                                </li>
                            )}
                        </ul>)}
                </div>
            )
            }



            {(allContent.length === 0) && (
                <div className="card W-not-found-card">
                    <div className="card-body W-row-display">
                        <div className="W-search-notFound-image">
                            <img className="W-not-found" src={"/paw-2022b-3/images/noResults.png"} alt="Not_Found_Img"/>
                        </div>
                        <div className="W-search-notFound-text">
                            {((query == null || query === '' || query === 'ANY') && (genre != null || durationFrom !== 'ANY')) ? (
                                <p className="W-not-found-message">{t('Content.NoContent.Filters')}</p>
                            ) : (
                                <p className="W-not-found-message">
                                    <span>{t('Content.NoContent', {query: query})}</span>
                                </p>
                            )}
                            <div>
                                <h5 className="W-search-notFound-text2">
                                    <Link to="/">{t('WatchList.Recomendation')}</Link>
                                </h5>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}