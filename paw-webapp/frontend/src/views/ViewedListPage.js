import React, {useContext, useEffect, useState} from "react";
import ContentCard from "./components/ContentCard";
import {useTranslation} from "react-i18next";
import {AuthContext} from "../context/AuthContext";
import {Link, useLocation, useNavigate} from "react-router-dom";
import Header from "./components/Header";
import ExpiredCookieModal from "./components/ExpiredCookieModal";
import {contentService, listsService, userService} from "../services";
import {updateUrlVariable} from "../scripts/validateParam";
import {checkIsNumber} from "../scripts/filtersValidations";

export default function ViewedListPage(props) {
    const {t} = useTranslation()
    const { search } = useLocation();
    let navigate = useNavigate()
    let {isLogged} = useContext(AuthContext)
    const [showExpiredCookiesModal, setShowExpiredCookiesModal] = useState(false)

    const [user, setUser] = useState(localStorage.hasOwnProperty("user")? JSON.parse(localStorage.getItem("user")) : null)

    const [totalContent, setTotalContent] = useState(-1)
    const [page, setPage] = useState(1)
    const [amountPages, setAmountPages] = useState(1)
    const [viewedList, setViewedList] = useState([])
    const [watchList, setWatchList] = useState([])
    const [added, setAdded] = useState(false)
    const [loaded, setLoaded] = useState(false);


    useEffect(() => {
        const queryParams = new URLSearchParams(search);
        updateUrlVariable(page, checkIsNumber(queryParams.get('page')), (x) =>setPage(x))
    }, [search]);

    const prevPage = () => {
        changeUrlPage(page - 1)
    }

    const nextPage = () => {
        changeUrlPage(page + 1)
    }

    const changePage = ( newPage) => {
        changeUrlPage(newPage)
    }

    const changeUrlPage = (page) => {
        const searchParams = new URLSearchParams(window.location.search);
        searchParams.set('page', page);
        const currentPath = window.location.pathname.substring('/paw-2022b-3'.length);
        navigate(currentPath + '?' + searchParams.toString());
    }

    const getUserViewedList = async () => {
        if(isLogged()) {
            const queryParams = new URLSearchParams(search);
            const currentPage = checkIsNumber(queryParams.get('page'));

            if(currentPage !== page) {
                setPage(currentPage)
            } else {
                const userData = await userService.getUserInfo(user.id)
                if(!userData.error) {
                    const viewedListData = await contentService.getLists(userData.data.userViewedListURL);
                    if (!viewedListData.error) {
                        setViewedList(viewedListData.data);
                        setAmountPages(viewedListData.totalPages);
                        setTotalContent(viewedListData.totalContent);
                    } else {
                        if (viewedListData.errorCode === 404) {
                            setShowExpiredCookiesModal(true);
                        } else {
                            navigate("/error", { replace: true, state: { errorCode: viewedListData.errorCode } });
                        }
                    }

                    const watchListData = await contentService.getLists(userData.data.userWatchListURL);
                    if (!watchListData.error) {
                        setWatchList(watchListData.data);
                    } else {
                        if (watchListData.errorCode === 404) {
                            setShowExpiredCookiesModal(true);
                        } else {
                            navigate("/error", { replace: true, state: { errorCode: watchListData.errorCode } });
                        }
                    }

                } else {
                    navigate("/error", { replace: true, state: { errorCode: userData.errorCode } });
                }
            }




        } else {
            navigate("/error", { replace: true, state: {errorCode: 401} })
        }
    }

    useEffect(() => {
        const fetchData = async () => {
            await getUserViewedList()
            setLoaded(true);
        };

        fetchData();
    }, [added, page])

    useEffect(() => {
        document.title = t('ViewedList.Title')
    })

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
                                <h2 className="W-watch-viewed-list-title">{t('ViewedList.Your')}</h2>
                            </div>
                        </div>
                        <div className="bg-light p-4 d-flex text-center">
                            <h4>{t('WatchList.Titles', {titlesAmount: totalContent})}</h4>
                        </div>
                        {viewedList.length === 0 ? (
                            <div className="W-watchlist-div-info-empty">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-x-fill W-watchlist-empty-icon" viewBox="0 0 16 16">
                                    <path d="m10.79 12.912-1.614-1.615a3.5 3.5 0 0 1-4.474-4.474l-2.06-2.06C.938 6.278 0 8 0 8s3 5.5 8 5.5a7.029 7.029 0 0 0 2.79-.588zM5.21 3.088A7.028 7.028 0 0 1 8 2.5c5 0 8 5.5 8 5.5s-.939 1.721-2.641 3.238l-2.062-2.062a3.5 3.5 0 0 0-4.474-4.474L5.21 3.089z"/>
                                    <path d="M5.525 7.646a2.5 2.5 0 0 0 2.829 2.829l-2.83-2.829zm4.95.708-2.829-2.83a2.5 2.5 0 0 1 2.829 2.829zm3.171 6-12-12 .708-.708 12 12-.708.708z"/>
                                </svg>
                                <div>
                                    <p>{t('ViewedList.Empty')}</p>
                                </div>
                                <div>
                                    <p>{t('ViewedList.Empty2')}</p>
                                </div>
                                <div>
                                    <Link to="/">{t('WatchList.Recomendation')}</Link>
                                </div>
                            </div>
                        ) : (
                            <div className="W-films-div">
                                <div className="row row-cols-1 row-cols-md-2 g-2 W-content-alignment">
                                    {loaded && viewedList.map((content) => (
                                        <ContentCard
                                            key={`viewedListContentCard${content.id}`}
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
                                            watchList={watchList}
                                            isInWatchList={watchList.length > 0 ? watchList.some(item => item.id === parseInt(content.id)) : false}
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
                                index + 1 === page ? (
                                    <li className="page-item active">
                                        <p className="page-link W-pagination-color">{index + 1}</p>
                                    </li>
                                ): index + 1 === page + 4 ? (
                                    <li className="page-item">
                                        <p className="page-link W-pagination-color" onClick={() => changePage(index + 1)}>
                                            ...
                                        </p>
                                    </li>
                                ): index + 1 === page - 4 ? (
                                    <li className="page-item">
                                        <p className="page-link W-pagination-color" onClick={() => changePage(index + 1)}>
                                            ...
                                        </p>
                                    </li>
                                ) : ( index + 1 > page - 4 && index + 1 < page + 4 ) && (
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