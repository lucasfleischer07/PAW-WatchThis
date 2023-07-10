import React, {useContext, useEffect, useState} from "react";
import {AuthContext} from "../context/AuthContext";
import {Link, useLocation, useNavigate, useParams} from "react-router-dom";
import {Button, Modal} from "react-bootstrap";
import {useTranslation} from "react-i18next";
import Markdown from "marked-react";
import ReviewCard from "./components/ReviewCard";
import {contentService, listsService, reviewService, userService} from "../services";
import {toast} from "react-toastify";
import TooltipComponent from './components/Tooltip';
import Header from "./components/Header";
import ExpiredCookieModal from "./components/ExpiredCookieModal";
import {type} from "@testing-library/user-event/dist/type";
import {updateUrlVariable, validateParam} from "../scripts/validateParam";


export default function InfoPage() {
    const {t} = useTranslation()
    const { search } = useLocation();
    let navigate = useNavigate()
    let {isLogged, signOut} = useContext(AuthContext)
    const { contentType, contentId} = useParams();
    const [showExpiredCookiesModal, setShowExpiredCookiesModal] = useState(false)

    const [user, setUser]= useState(localStorage.hasOwnProperty("user")? JSON.parse(localStorage.getItem("user")) : null)

    const [content, setContent] = useState({})
    const [reviews, setReviews] = useState({})
    const [actualPage, setActualPage] = useState(1)
    const [amountPages, setAmountPages] = useState(1)
    const [isLikeReviewsList, setIsLikeReviewsList] = useState(false);
    const [isDislikeReviewsList, setIsDislikeReviewsList] = useState(false);

    const [showDeleteContentModal, setShowDeleteContentModal] = useState(false)
    const [showWatchListModal, setShowWatchListModal] = useState(false)
    const [showViewedListModal, setShowViewedListModal] = useState(false)
    const [showAddReviewLoginModal, setShowAddReviewLoginModal] = useState(false)

    const [isInWatchList, setIsInWatchList] = useState(false)
    const [isInViewedList, setIsInViewedList] = useState(false)
    const [alreadyReviewed, setAlreadyReviewed] = useState(false)
    const [reviewsChange,setReviewsChange]=useState(false)
    const handleShowDeleteContentModal = () => {
        setShowDeleteContentModal(true)
    }
    const handleCloseDeleteContentModal = () => {
        setShowDeleteContentModal(false)
    }

    const handleShowWatchListModal = () => {
        setShowWatchListModal(true)
    }
    const handleCloseWatchListModal = () => {
        setShowWatchListModal(false)
    }

    const handleShowViewedListModal = () => {
        setShowViewedListModal(true)
    }
    const handleCloseViewedListModal = () => {
        setShowViewedListModal(false)
    }

    const handleShowAddReviewLoginModal = () => {
        setShowAddReviewLoginModal(true)
    }
    const handleCloseAddReviewLoginModal = () => {
        setShowAddReviewLoginModal(false)
    }

    const prevPage = () => {
        setActualPage(actualPage - 1)
        changeUrlPage(actualPage - 1)
    }

    const nextPage = () => {
        setActualPage(actualPage + 1)
        changeUrlPage(actualPage + 1)
    }

    const changePage = ( newPage) => {
        setActualPage(newPage)
        changeUrlPage(newPage)
    }

    const changeUrlPage = (page) => {
        const searchParams = new URLSearchParams(window.location.search);
        searchParams.set('page', page);
        navigate(window.location.pathname + '?' + searchParams.toString());
    }

    const handleSubmitDeleteContent = (e) => {
        e.preventDefault()
        if(user?.role === 'admin') {
            contentService.deleteContent(parseInt(contentId))
                .then(data => {
                    if(!data.error) {
                        // TODO: Meter toast de contenido eliminado correctamente
                        handleCloseDeleteContentModal()
                        navigate("/", {replace: true})
                    } else {
                        navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                    }
                })
                .catch(() => {
                    navigate("/error", { replace: true, state: {errorCode: 404} })
                })
        } else {
            navigate("/error", { replace: true, state: {errorCode: 401} })
        }
    }

    const handleAddToWatchList = (e) => {
        e.preventDefault();
        listsService.addUserWatchList(parseInt(contentId))
            .then(data => {
                if(!data.error) {
                    setIsInWatchList(true);
                    toast.success(t('WatchList.Added'))
                } else {
                    if(data.errorCode === 404) {
                        setShowExpiredCookiesModal(true)
                    } else {
                        navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                    }
                }
            })
            .catch(() => {
                navigate("/error", { replace: true, state: {errorCode: 404} })
            })
    }
    const handleDeleteFromWatchList = (e) => {
        e.preventDefault()
        listsService.deleteUserWatchList(parseInt(contentId))
            .then(data => {
                if(!data.error) {
                    setIsInWatchList(false);
                    toast.success(t('WatchList.Removed'))
                } else {
                    if(data.errorCode === 404) {
                        setShowExpiredCookiesModal(true)
                    } else {
                        navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                    }
                }
            })
            .catch(() => {
                navigate("/error", { replace: true, state: {errorCode: 404} })
            })
    }
    const handleAddToViewedList = (e) => {
        e.preventDefault();
        listsService.addUserViewedList(parseInt(contentId))
            .then(data => {
                if(!data.error) {
                    setIsInViewedList(true);
                    toast.success(t('ViewedList.Added'))
                } else {
                    if(data.errorCode === 404) {
                        setShowExpiredCookiesModal(true)
                    } else {
                        navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                    }
                }
            })
            .catch(() => {
                navigate("/error", { replace: true, state: {errorCode: 404} })
            })
    }
    const handleDeleteFromViewedList = (e) => {
        e.preventDefault();
        listsService.deleteUserViewedList(parseInt(contentId))
            .then(data => {
                if(!data.error) {
                    setIsInViewedList(false);
                    toast.success(t('ViewedList.Removed'))
                } else {
                    if(data.errorCode === 404) {
                        setShowExpiredCookiesModal(true)
                    } else {
                        navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                    }
                }
            })
            .catch(() => {
                navigate("/error", { replace: true, state: {errorCode: 404} })
            })
    }

    const handleGoToLogin = () => {
        navigate("/login", {replace: true})
    }


    useEffect(() => {
        contentService.getSpecificContent(parseInt(contentId))
            .then(data => {
                if(!data.error) {
                    setContent(data.data)
                } else {
                    navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                }
            })
            .catch(() => {
                navigate("/error", { replace: true, state: {errorCode: 404} })
            })
    }, [])

    useEffect(() => {
        const queryParams = new URLSearchParams(search);
        updateUrlVariable(actualPage, queryParams.get('page'), (x) =>setActualPage(x))
    }, [search]);


    useEffect(() => {
        if(isLogged()) {
            listsService.getUserWatchListContentIds(user.id)
                .then(watchList => {
                    if(!watchList.error) {
                        setIsInWatchList(watchList.data.some(item => item.id === parseInt(contentId)))
                    } else if(watchList.errorCode === 404) {
                        setShowExpiredCookiesModal(true)
                    } else {
                        navigate("/error", { replace: true, state: {errorCode: watchList.errorCode} })
                    }
                })
                .catch(() => {
                    navigate("/error", { replace: true, state: {errorCode: 404} })
                })

            listsService.getUserViewedListContentIds(user.id)
                .then(viewedList => {
                    if(!viewedList.error) {
                        setIsInViewedList(viewedList.data.some(item => item.id === parseInt(contentId)))
                    } else {
                        if(viewedList.errorCode === 404 && !showExpiredCookiesModal) {
                            setShowExpiredCookiesModal(true)
                        } else {
                            navigate("/error", { replace: true, state: {errorCode: viewedList.errorCode} })
                        }
                    }
                })
                .catch(() => {
                    navigate("/error", { replace: true, state: {errorCode: 404} })
                })

            userService.getReviewsLike(user?.id)
                .then(data => {
                    if(!data.error) {
                        setIsLikeReviewsList(data.data)
                    } else {
                        if(data.errorCode === 404 && !showExpiredCookiesModal) {
                            setShowExpiredCookiesModal(true)
                        } else {
                            navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                        }
                    }
                })
                .catch(() => {
                    navigate("/error", { replace: true, state: {errorCode: 404} })
                })

            userService.getReviewsDislike(user?.id)
                .then(data => {
                    if(!data.error) {
                        setIsDislikeReviewsList(data.data)
                    } else {
                        if(data.errorCode === 404 && !showExpiredCookiesModal) {
                            setShowExpiredCookiesModal(true)
                        } else {
                            navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                        }
                    }
                })
                .catch(() => {
                    navigate("/error", { replace: true, state: {errorCode: 404} })
                })}
        reviewService.reviews(parseInt(contentId), actualPage)
            .then(data => {
                if(!data.error) {
                    setReviews(data.data)
                    for(let i = 0; i < data.data.length; i++) {
                        if(data.data[i].user.username === user?.username) {
                            setAlreadyReviewed(true)
                        }
                    }
                } else {
                    navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                }
            })
            .catch(() => {
                navigate("/error", { replace: true, state: {errorCode: 404} })
            })


    }, [actualPage])

    return(
        <>
            {showExpiredCookiesModal && (
                <ExpiredCookieModal/>
            )}

            <Header type={contentType} admin={user?.role === 'admin'} userName={user?.username} userId={user?.id}/>

            {user?.role === 'admin' ? (
                <div className="W-delete-edit-buttons-content">
                    <form className="W-delete-form" id="formDeleteContent" method="post">
                        <button className="btn btn-danger text-nowrap" type="button" data-bs-toggle="modal" data-bs-target="#modalDelete" onClick={handleShowDeleteContentModal}>
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-trash3" viewBox="0 0 16 16">
                                <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
                            </svg>
                        </button>
                    </form>

                    <Modal show={showDeleteContentModal} onHide={handleCloseDeleteContentModal} aria-labelledby={`modalDelete`} aria-hidden="true">
                        <Modal.Header closeButton>
                            <Modal.Title id={`modalLabel${content.id}`}>
                                {t('Delete.Confirmation')}
                            </Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            {t('DeleteContent')}
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="secondary" onClick={handleCloseDeleteContentModal}>
                                {t('No')}
                            </Button>
                            <Button variant="success" onClick={handleSubmitDeleteContent}>
                                {t('Yes')}
                            </Button>
                        </Modal.Footer>
                    </Modal>

                    <Link to={`/content/form/edition/${content.id}`} className="W-edit-button-review">
                        <button id="editReviewButton" className="btn btn-dark text-nowrap">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-pencil-square" viewBox="0 0 16 16">
                                <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                <path fillRule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                            </svg>
                        </button>
                    </Link>
                </div>
            ) : (
                <></>
            )}

            <div className="W-column-display W-word-break">
                <div className="W-review-div-sizing">
                    <div className="card W-inv-film-card-margin">
                        <div className="W-general-div-infoPage-watchlistButton-and-review">
                            <div className="W-watchList-and-ViewedList-div">
                                <div className="W-watchlist-button-div-infopage">
                                    {isLogged() && isInWatchList === false ? (
                                        <form id={`form${content.id}`} method="post">
                                            <TooltipComponent text={t('WatchList.Add')}>
                                                <button id="watchListButton" className="btn btn-white W-watchlist-button-infopage" type="submit" onClick={handleAddToWatchList}>
                                                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                                                        <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"/>
                                                        <path d="M8 4a.5.5 0 0 1 .5.5V6H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V7H6a.5.5 0 0 1 0-1h1.5V4.5A.5.5 0 0 1 8 4z"/>
                                                    </svg>
                                                </button>
                                            </TooltipComponent>
                                        </form>
                                    ) : isLogged() && isInWatchList === true ? (
                                        <form id={`form${content.id}`} method="post">
                                            <TooltipComponent text={t('WatchList.Remove')}>
                                                <button id="watchListButton" className="btn btn-white W-watchlist-button-infopage" type="submit" onClick={handleDeleteFromWatchList}>
                                                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                                                        <path fillRule="evenodd" d="M2 15.5V2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.74.439L8 13.069l-5.26 2.87A.5.5 0 0 1 2 15.5zM6.854 5.146a.5.5 0 1 0-.708.708L7.293 7 6.146 8.146a.5.5 0 1 0 .708.708L8 7.707l1.146 1.147a.5.5 0 1 0 .708-.708L8.707 7l1.147-1.146a.5.5 0 0 0-.708-.708L8 6.293 6.854 5.146z"/>
                                                    </svg>
                                                </button>
                                            </TooltipComponent>
                                        </form>
                                    ) : (
                                        <>
                                            <TooltipComponent text={t('WatchList.Add')}>
                                                <button id="watchListButton" className="btn btn-white W-watchlist-button-infopage" type="button" data-bs-toggle="modal" data-bs-target="#watchListModal" onClick={handleShowWatchListModal}>
                                                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                                                        <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"/>
                                                        <path d="M8 4a.5.5 0 0 1 .5.5V6H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V7H6a.5.5 0 0 1 0-1h1.5V4.5A.5.5 0 0 1 8 4z"/>
                                                    </svg>
                                                </button>
                                            </TooltipComponent>
                                            <Modal show={showWatchListModal} onHide={handleCloseWatchListModal} aria-labelledby={`watchListModal`} aria-hidden="true">
                                                <Modal.Header closeButton>
                                                    <Modal.Title id={`modalLabel${content.id}`}>
                                                        {t('WatchList.Title')}
                                                    </Modal.Title>
                                                </Modal.Header>
                                                <Modal.Body>
                                                    {t('WatchList.WarningAdd')}
                                                    {t('Review.WarningAddMessage')}
                                                </Modal.Body>
                                                <Modal.Footer>
                                                    <Button variant="secondary" onClick={handleCloseWatchListModal}>
                                                        {t('No')}
                                                    </Button>
                                                    <Button variant="success" onClick={handleGoToLogin}>
                                                        {t('Yes')}
                                                    </Button>
                                                </Modal.Footer>
                                            </Modal>
                                        </>
                                    )}
                                </div>

                                <div className="W-viewedlist-button-div-infopage">
                                    {isLogged() && isInViewedList === false ? (
                                        <form id={`form${content.id}`} method="post" className="W-form-zero-margin">
                                            <TooltipComponent text={t('ViewedList.Add')}>
                                                <button id="viewedListButton" className="btn btn-white W-watchlist-button-infopage" type="submit" onClick={handleAddToViewedList}>
                                                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                                                        <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                                                        <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
                                                    </svg>
                                                </button>
                                            </TooltipComponent>
                                        </form>
                                    ) : isLogged() && isInViewedList === true ? (
                                        <form id={`form${content.id}`} className="W-form-zero-margin">
                                            <TooltipComponent text={t('ViewedList.Remove')}>
                                                <button id="viewedListButton" className="btn btn-white W-watchlist-button-infopage" type="submit" onClick={handleDeleteFromViewedList}>
                                                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                                                        <path d="m10.79 12.912-1.614-1.615a3.5 3.5 0 0 1-4.474-4.474l-2.06-2.06C.938 6.278 0 8 0 8s3 5.5 8 5.5a7.029 7.029 0 0 0 2.79-.588zM5.21 3.088A7.028 7.028 0 0 1 8 2.5c5 0 8 5.5 8 5.5s-.939 1.721-2.641 3.238l-2.062-2.062a3.5 3.5 0 0 0-4.474-4.474L5.21 3.089z"/>
                                                        <path d="M5.525 7.646a2.5 2.5 0 0 0 2.829 2.829l-2.83-2.829zm4.95.708-2.829-2.83a2.5 2.5 0 0 1 2.829 2.829zm3.171 6-12-12 .708-.708 12 12-.708.708z"/>
                                                    </svg>
                                                </button>
                                            </TooltipComponent>
                                        </form>
                                    ) : (
                                        <>
                                            <TooltipComponent text={t('ViewedList.Add')}>
                                                <button id="viewedListButton" className="btn btn-white W-watchlist-button-infopage" type="button" data-bs-toggle="modal" data-bs-target="#viewedListModal" onClick={handleShowViewedListModal}>
                                                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                                                        <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                                                        <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
                                                    </svg>
                                                </button>
                                            </TooltipComponent>

                                            <Modal show={showViewedListModal} onHide={handleCloseViewedListModal} aria-labelledby={`viewedListModal`} aria-hidden="true">
                                                <Modal.Header closeButton>
                                                    <Modal.Title id={`modalLabel${content.id}`}>
                                                        {t('ViewedList.Title')}
                                                    </Modal.Title>
                                                </Modal.Header>
                                                <Modal.Body>
                                                    {t('ViewedList.WarningAdd')}
                                                    {t('Review.WarningAddMessage')}
                                                </Modal.Body>
                                                <Modal.Footer>
                                                    <Button variant="secondary" onClick={handleCloseViewedListModal}>
                                                        {t('No')}
                                                    </Button>
                                                    <Button variant="success" onClick={handleGoToLogin}>
                                                        {t('Yes')}
                                                    </Button>
                                                </Modal.Footer>
                                            </Modal>
                                        </>
                                    )}
                                </div>
                            </div>


                            <div className="W-div-img-and-reviewInfo">
                                <div className="col-md-4 W-img-aligment">
                                    <img src={content.contentPictureUrl} className="W-img-size" alt={`Image ${content.name}`}/>
                                </div>
                                <div className="col-md-8">
                                    <div className="card-body W-card-body-aligment">
                                        <h6 className="card-title W-card-title">{content.name}</h6>
                                        <Markdown id={`descriptionParagraphInfoPage${content.id}`} className="W-report-review-paragraph">{content.description}</Markdown>
                                        <p className="card-text W-subTitles-font-size"><span className="W-span-text-info-card-movie">{t('Content.Duration')}</span>{content.duration}</p>
                                        <p className="card-text W-subTitles-font-size"><span className="W-span-text-info-card-movie">{t('Content.Genre')}</span>{content.genre}</p>
                                        <p className="card-text W-subTitles-font-size"><span className="W-span-text-info-card-movie">{t('Content.Released')}</span>{content.releaseDate}</p>
                                        <p className="card-text W-subTitles-font-size"><span className="W-span-text-info-card-movie">{t('Content.Creator')}</span>{content.creator}</p>

                                        <div>
                                            {[...Array(5)].map((_, index) => {
                                                if (content.rating >= index + 1) {
                                                    return <svg key={index} xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-star-fill W-star-style-info-page" viewBox="0 0 16 16"><path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/></svg>;
                                                } else {
                                                    return <svg key={index} xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-star W-star-style-info-page" viewBox="0 0 16 16"><path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/></svg>;
                                                }
                                            })}
                                        </div>

                                        {content.reviewsAmount === 1 ? (
                                            <p className="card-text W-subTitles-font-size">{t('Content.Review.Amount.One', {reviewsAmount: content.reviewsAmount})}</p>
                                        ) : (
                                            <p className="card-text W-subTitles-font-size">{t('Content.ReviewAmount', {reviewsAmount: content.reviewsAmount})}</p>
                                        )}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="card W-reviews-card-margins W-inv-film-card-margin">
                    <div className="card-header W-card-header">
                        <h3 className="W-title-review">{t('Content.Review')}</h3>
                        <div className="W-add-review">
                            {user?.username == null ? (
                                <>
                                    <button type="button" className="btn btn-dark W-add-review-button W-reviewText" data-bs-toggle="modal" data-bs-target="#reviewLoginModal" onClick={handleShowAddReviewLoginModal}>{t('Content.AddReview')}</button>

                                    <Modal show={showAddReviewLoginModal} onHide={handleCloseAddReviewLoginModal} aria-labelledby={`reviewLoginModal`} aria-hidden="true">
                                        <Modal.Header closeButton>
                                            <Modal.Title id={`modalLabel${content.id}`}>
                                                {t('Profile.Reviews')}
                                            </Modal.Title>
                                        </Modal.Header>
                                        <Modal.Body>
                                            {t('Review.WarningAdd')}
                                            {t('Review.WarningAddMessage')}
                                        </Modal.Body>
                                        <Modal.Footer>
                                            <Button variant="secondary" onClick={handleCloseAddReviewLoginModal}>
                                                {t('No')}
                                            </Button>
                                            <Button variant="success" onClick={handleGoToLogin}>
                                                {t('Yes')}
                                            </Button>
                                        </Modal.Footer>
                                    </Modal>
                                </>

                            ) : alreadyReviewed === false ? (
                                <Link to={`/content/${contentType}/${contentId}/reviewRegistration`}>
                                    <button type="button" className="btn btn-dark W-add-review-button W-reviewText">{t('Content.AddReview')}</button>
                                </Link>
                            ) : (
                                <></>
                            )}
                        </div>
                    </div>

                    <div className="card-body">
                        {reviews.length > 0 ? (
                            <>
                                {reviews.map((review) => {
                                    return (
                                        <div key={review.id}>
                                            <ReviewCard
                                                id={`reviewCardUser${review.id}`}
                                                key ={isLikeReviewsList && isDislikeReviewsList}
                                                reviewTitle={review.name}
                                                reviewDescription={review.description}
                                                reviewRating={review.rating}
                                                reviewId={review.id}
                                                reviewReputation={review.reputation}
                                                reviewUser={review.user.username}
                                                reviewUserId={review.user.id}
                                                contentId={content.id}
                                                contentType={review.type}
                                                loggedUserName={user?.username}
                                                loggedUserId={user?.id}
                                                isAdmin={user?.role === 'admin'}
                                                isLikeReviews={isLikeReviewsList.length > 0 ? isLikeReviewsList.some(item => item.id === parseInt(review.id)) : false}
                                                isDislikeReviews={isDislikeReviewsList.length > 0 ? isDislikeReviewsList.some(item => item.id === parseInt(review.id)) : false}
                                                alreadyReport={review.reviewReporters.includes(user?.username)}
                                                canComment={true}
                                                seeComments={true}
                                                reviewsChange={reviewsChange}
                                                setReviewsChange={setReviewsChange}
                                            />
                                        </div>
                                    );
                                })}

                                {amountPages > 1 && (
                                    <ul className="pagination justify-content-center W-pagination">
                                        {actualPage > 1 ? (
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
                                                index + 1 === parseInt(actualPage) ? (
                                                    <li className="page-item active">
                                                        <p className="page-link W-pagination-color">{index + 1}</p>
                                                    </li>
                                                ): index + 1 === parseInt(actualPage) + 4 ? (
                                                    <li className="page-item">
                                                        <p className="page-link W-pagination-color" onClick={() => changePage(index + 1)}>
                                                            ...
                                                        </p>
                                                    </li>
                                                ): index + 1 === parseInt(actualPage) - 4 ? (
                                                    <li className="page-item">
                                                        <p className="page-link W-pagination-color" onClick={() => changePage(index + 1)}>
                                                            ...
                                                        </p>
                                                    </li>
                                                ) : ( index + 1 > parseInt(actualPage) - 4 && index + 1 < parseInt(actualPage) + 4 ) && (
                                                    <li className="page-item">
                                                        <p className="page-link W-pagination-color" onClick={() => changePage(index + 1)}>
                                                            {index + 1}
                                                        </p>
                                                    </li>
                                                )
                                            ))
                                        ) : (
                                            Array.from({ length: amountPages }, (_, index) => (
                                                index + 1 === actualPage ? (
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
                                        {actualPage < amountPages ? (
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
                                )}
                            </>
                        ) : (
                            <div>
                                <div className="W-no-reviews-icon">
                                    <img className={"W-img-not-review"} src={"/paw-2022b-3/images/noReviews.png"} alt="No_Review_Img"/>
                                </div>
                                {contentType === 'movie' ? (
                                    <h3 className="W-no-reviews-text">{t('Content.NoReviewMessage.Movie')}</h3>
                                ) : (
                                    <h3 className="W-no-reviews-text">{t('Content.NoReviewMessage.Serie')}</h3>
                                )}

                            </div>
                        )}
                    </div>
                </div>
            </div>
        </>

    )
}