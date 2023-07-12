import React, {useContext, useEffect, useState} from "react";
import ContentCard from "./components/ContentCard";
import {useTranslation} from "react-i18next";
import {AuthContext} from "../context/AuthContext";
import {Link, useLocation, useNavigate} from "react-router-dom";
import Header from "./components/Header";
import ExpiredCookieModal from "./components/ExpiredCookieModal";
import {listsService} from "../services";
import {updateUrlVariable} from "../scripts/validateParam";
import {checkIsNumber} from "../scripts/filtersValidations";

export default function WatchListPage(props) {
    const {t} = useTranslation()
    const { search } = useLocation();

    let navigate = useNavigate()
    let {isLogged} = useContext(AuthContext)
    const [showExpiredCookiesModal, setShowExpiredCookiesModal] = useState(false)

    const [user, setUser] = useState(localStorage.hasOwnProperty("user")? JSON.parse(localStorage.getItem("user")) : null)
    const [page, setPage] = useState(1)
    const [amountPages, setAmountPages] = useState(1)
    const [watchList, setWatchList] = useState([])
    const [added, setAdded] = useState(false)

    useEffect(() => {
        const queryParams = new URLSearchParams(search);
        updateUrlVariable(page,(typeof queryParams.get('page') === 'string' ?  checkIsNumber(queryParams.get('page')) : queryParams.get('page')), (x) =>setPage(x))
    }, [search]);

    const prevPage = () => {
        setAmountPages(page - 1)
        changeUrlPage(page - 1)
    }

    const nextPage = () => {
        setAmountPages(page + 1)
        changeUrlPage(page + 1)
    }

    const changePage = ( newPage) => {
        setPage(newPage)
        changeUrlPage(newPage)
    }

    const changeUrlPage = (page) => {
        const searchParams = new URLSearchParams(window.location.search);
        searchParams.set('page', page);
        const currentPath = window.location.pathname.substring('/paw-2022b-3'.length);
        navigate(currentPath + '?' + searchParams.toString());
    }

    const getUserWatchList = () => {
        if(isLogged()) {
            listsService.getUserWatchList(user?.id, page)
                .then(watchList => {
                    if(!watchList.error) {
                        setWatchList(watchList.data)
                        setAmountPages(watchList.totalPages)
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
        } else {
            navigate("/error", { replace: true, state: {errorCode: 401} })
        }
    }

    useEffect(() => {
        getUserWatchList()
    }, [])

    useEffect(() => {
        getUserWatchList()
    }, [added, page])

    return (
        <>
            {showExpiredCookiesModal && (
                <ExpiredCookieModal/>
            )}

            <Header type="all" admin={user?.role === 'admin'} userName={user?.username} userId={user?.id}/>

            <div className="row px-4">
                <div className="W-profile-general-div-display">
                    <div className="bg-white shadow rounded overflow-hidden W-viewed-watch-list-general-div">
                        <div className="W-profile-background-color">
                            <div>
                                <h2 className="W-watch-viewed-list-title">{t('WatchList.Your')}</h2>
                            </div>
                        </div>
                        <div className="bg-light p-4 d-flex text-center">
                            <h4>{t('WatchList.Titles', {titlesAmount: watchList.length})}</h4>
                        </div>

                        {watchList.length === 0 ? (
                            <div className="W-watchlist-div-info-empty">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-x-fill W-watchlist-empty-icon" viewBox="0 0 16 16">
                                    <path fillRule="evenodd" d="M2 15.5V2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.74.439L8 13.069l-5.26 2.87A.5.5 0 0 1 2 15.5zM6.854 5.146a.5.5 0 1 0-.708.708L7.293 7 6.146 8.146a.5.5 0 1 0 .708.708L8 7.707l1.146 1.147a.5.5 0 1 0 .708-.708L8.707 7l1.147-1.146a.5.5 0 0 0-.708-.708L8 6.293 6.854 5.146z"/>
                                </svg>
                                <div>
                                    <p>{t('WatchList.Empty')}</p>
                                </div>
                                <div>
                                    <p>{t('WatchList.Empty2')}</p>
                                </div>
                                <div>
                                    <Link to="/">{t('WatchList.Recomendation')}</Link>
                                </div>
                            </div>
                        ) : (
                            <div className="W-films-div">
                                <div className="row row-cols-1 row-cols-md-2 g-2 W-content-alignment">
                                    {watchList.map((content) => (
                                        <ContentCard
                                            key={`watchListContentCard${content.id}`}
                                            contentName={content.name}
                                            contentReleased={content.releaseDate}
                                            contentCreator={content.creator}
                                            contentGenre={content.genre}
                                            contentImage={content.contentPictureUrl}
                                            contentId={content.id}
                                            contentType={content.type}
                                            contentRating={content.rating}
                                            reviewsAmount={content.reviewsAmount}
                                            added={added}
                                            setAdded={setAdded}
                                            isInWatchList={watchList.length > 0 ? watchList.some(item => item.id === content.id) : false}
                                        />
                                    ))}
                                </div>
                            </div>
                        )}

                    </div>
                </div>
            </div>
            {amountPages > 1 && (
                <div>
                    <ul className="pagination justify-content-center W-pagination">
                        {page > 1 ? (
                            <li className="page-item">
                                <p className="page-link W-pagination-color" onClick={() => prevPage()}>
                                    {t('Pagination.Prev')}
                                </p>
                            </li>
                        ) : (
                            <li className="page-item disabled">
                                <p className="page-link W-pagination-color">{t('Pagination.Prev')}</p>
                            </li>
                        )}
                        {amountPages > 10 ? (
                            Array.from({ length: amountPages }, (_, index) => (
                                index + 1 === parseInt(page) ? (
                                    <li className="page-item active">
                                        <p className="page-link W-pagination-color">{index + 1}</p>
                                    </li>
                                ): index + 1 === parseInt(page) + 4 ? (
                                    <li className="page-item">
                                        <p className="page-link W-pagination-color" onClick={() => changePage(index + 1)}>
                                            ...
                                        </p>
                                    </li>
                                ): index + 1 === parseInt(page) - 4 ? (
                                    <li className="page-item">
                                        <p className="page-link W-pagination-color" onClick={() => changePage(index + 1)}>
                                            ...
                                        </p>
                                    </li>
                                ) : ( index + 1 > parseInt(page) - 4 && index + 1 < parseInt(page) + 4 ) && (
                                    <li className="page-item">
                                        <p className="page-link W-pagination-color" onClick={() => changePage(index + 1)}>
                                            {index + 1}
                                        </p>
                                    </li>
                                )
                            ))
                        ) : (
                            Array.from({ length: amountPages }, (_, index) => (
                                index + 1 === page ? (
                                    <li className="page-item active">
                                        <p className="page-link W-pagination-color">{index + 1}</p>
                                    </li>
                                ) : (
                                    <li className="page-item">
                                        <p className="page-link W-pagination-color" onClick={() => changePage(index + 1)}>
                                            {index + 1}
                                        </p>
                                    </li>
                                )
                            ))
                        )}
                        {page < amountPages ? (
                            <li className="page-item">
                                <p className="page-link W-pagination-color" onClick={() => nextPage()}>
                                    {t('Pagination.Next')}
                                </p>
                            </li>
                        ) : (
                            <li className="page-item disabled">
                                <p className="page-link W-pagination-color">{t('Pagination.Next')}</p>
                            </li>
                        )}
                    </ul>
                </div>
            )}
        </>
    )
}