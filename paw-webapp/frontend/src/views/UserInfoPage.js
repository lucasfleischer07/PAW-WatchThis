import {useTranslation} from "react-i18next";
import React, {useContext, useEffect, useState} from "react";
import {Link, useLocation, useNavigate, useParams} from "react-router-dom";
import {toast} from "react-toastify";
import TooltipComponent from "./components/Tooltip";
import Header from "./components/Header";
import ReviewCard from "./components/ReviewCard";
import {AuthContext} from "../context/AuthContext";
import ExpiredCookieModal from "./components/ExpiredCookieModal";
import {contentService, reviewService, userService} from "../services";
import {updateUrlVariable} from "../scripts/validateParam";
import {checkIsNumber} from "../scripts/filtersValidations";
import defaultUserImg from "../images/defaultUserImg.png"
import noReviews from "../images/noReviews.png"

export default function UserInfoPage() {
    const {t} = useTranslation()
    let navigate = useNavigate()
    const { search } = useLocation();
    let {isLogged} = useContext(AuthContext)
    const { userProfileId } = useParams();
    const [showExpiredCookiesModal, setShowExpiredCookiesModal] = useState(false)

    const [user, setUser] = useState(localStorage.hasOwnProperty("user")? JSON.parse(localStorage.getItem("user")) : null)
    const [loggedUserReviewsReported, setLoggedUserReviewsReported] = useState([])

    const [reviewOwnerUser, setReviewOwnerUser] = useState({})
    const [isSameUser, setIsSameUser] = useState(undefined)
    const [canPromote, setCanPromote] = useState(undefined)
    const [page, setPage] = useState(1)
    const [amountPages, setAmountPages] = useState(1)
    const [reviews, setReviews] = useState([])
    const [reputation, setReputation] = useState(0)
    const [amountReviews, setAmountReviews] =useState(0)
    const [totalReviews, setTotalReviews] = useState(0)

    const [isLikeReviewsList, setIsLikeReviewsList] = useState([]);
    const [isDislikeReviewsList, setIsDislikeReviewsList] = useState([]);
    const [contentArray, setContentArray] = useState([])
    const [loaded, setLoaded] = useState(false)

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


    const handlePromoteUser = (e) => {
        e.preventDefault();
        userService.promoteUserToAdmin(reviewOwnerUser.id)
            .then(data => {
                if(!data.error) {
                    setCanPromote(false)
                    toast.success(t('Profile.PromoteUser.Success'))
                } else {
                    if(data.errorCode === 404) {
                        toast.error(t('Profile.PromoteUser.Error'))
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

    useEffect(() => {
        const queryParams = new URLSearchParams(search);
        updateUrlVariable(page, checkIsNumber(queryParams.get('page')), (x) =>setPage(x))
    }, [search]);


    useEffect(() => {
        const queryParams = new URLSearchParams(search);
        const currentPage = checkIsNumber(queryParams.get('page'));

        if(currentPage !== page) {
            setPage(currentPage)
        } else {
            async function fetchData() {
                reviewService.getReviews(parseInt(userProfileId), null, page)
                    .then(async reviewsData => {
                        if (!reviewsData.error) {
                            setReviews([])
                            setContentArray([])
                            setReviews(reviewsData.data)
                            setTotalReviews(reviewsData.totalUserReviews)
                            for (let i = 0; i < reviewsData.data.length; i++) {
                                await contentService.getSpecificContent(reviewsData.data[i].content)
                                    .then(contentData => {
                                        if (!contentData.error) {
                                            setContentArray(prevArray => [...prevArray, contentData.data]);
                                        } else {
                                            navigate("/error", {replace: true, state: {errorCode: contentData.errorCode}})
                                        }
                                    })
                                    .catch(error => {
                                        navigate("/error", {replace: true, state: {errorCode: 404}})
                                    });
                            }

                            userService.getUserInfo(parseInt(userProfileId))
                                .then((data) => {
                                    if (!data.error) {
                                        setReviewOwnerUser(data.data)
                                        if (reviewsData.data.length === 0) {
                                            setReputation(0)
                                        } else {
                                            let reputation = 0
                                            for (let i = 0; i < reviewsData.data.length; i++) {
                                                reputation += reviewsData.data[i].reputation
                                            }
                                            setReputation(reputation)
                                        }

                                        if (user?.role === 'admin' && data.data.role !== 'admin') {
                                            setCanPromote(true)
                                        } else {
                                            setCanPromote(false)
                                        }

                                        setAmountReviews(reviewsData.totalReviews)
                                        setAmountPages(reviewsData.totalPages)

                                        if (user?.id === parseInt(userProfileId)) {
                                            setIsSameUser(true)
                                        } else {
                                            setIsSameUser(false)
                                        }

                                        setLoaded(true)

                                    } else {
                                        navigate("/error", {replace: true, state: {errorCode: data.errorCode}})
                                    }

                                })
                                .catch(() => {
                                    navigate("/error", {replace: true, state: {errorCode: 404}})
                                })

                        } else {
                            navigate("/error", {replace: true, state: {errorCode: reviewsData.errorCode}})
                        }
                    })
                    .catch(() => {
                        navigate("/error", { replace: true, state: {errorCode: 404} })
                    })

                if(isLogged()) {
                    userService.getReviewsLike(user?.id)
                        .then(data => {
                            if(!data.error) {
                                setIsLikeReviewsList(data.data)
                            } else {
                                navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
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
                                navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                            }
                        })
                        .catch(() => {
                            navigate("/error", { replace: true, state: {errorCode: 404} })
                        })

                    reviewService.getReviews(user?.id, null, 1, true)
                        .then(data => {
                            if(!data.error) {
                                setLoggedUserReviewsReported(data.data)
                            } else {
                                navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                            }
                        })
                        .catch(() => {
                            navigate("/error", { replace: true, state: {errorCode: 404} })
                        })
                }
            }
            fetchData()
        }
    }, [page, userProfileId])


    useEffect(() => {
        document.title = t('Profile')
    })


    return(
        <>
            {showExpiredCookiesModal && (
                <ExpiredCookieModal/>
            )}

            <Header type="all" admin={user?.role === 'admin'} userName={user?.username} userId={user?.id}/>

            <div className="row py-5 px-4 W-set-margins">
                <div className="col-md-5 mx-auto W-profile-general-div-display">
                    <div className="bg-white shadow rounded overflow-hidden W-profile-general-div">
                        <div className="W-profile-background-color bg-dark">
                            <div className="media align-items-end">
                                <div className="profile mr-3">
                                    <div className="W-img-and-quote-div">
                                        <div>
                                            {reviewOwnerUser.image == null ? (
                                                <img src={defaultUserImg} alt="User_img" className="W-edit-profile-picture" />
                                            ) : (
                                                <img src={reviewOwnerUser.image} alt="User_img" className="W-edit-profile-picture" />
                                            )}
                                            <div className="W-admin-icon">
                                                <h4 className="W-username-profilepage">{reviewOwnerUser.username}</h4>
                                                {user?.role === 'admin' && (
                                                    <span>
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-award-fill" viewBox="0 0 16 16" style={{color: '#ddbe1b'}}>
                                                            <path d="m8 0 1.669.864 1.858.282.842 1.68 1.337 1.32L13.4 6l.306 1.854-1.337 1.32-.842 1.68-1.858.282L8 12l-1.669-.864-1.858-.282-.842-1.68-1.337-1.32L2.6 6l-.306-1.854 1.337-1.32.842-1.68L6.331.864z"/>
                                                            <path d="M4 11.794V16l4-1 4 1v-4.206l-2.018.306L8 13.126 6.018 12.1z"/>
                                                        </svg>
                                                </span>
                                                )}
                                            </div>
                                        </div>
                                        <div className="W-margin-left-label">
                                            {/*<p className="W-quote-in-profile">{quote}</p>*/}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="bg-light p-4 d-flex text-center W-editProfileButton-and-reviewsCant">
                            <div className="W-edition-and-admin-buttons">
                                {isSameUser && (
                                    <Link to="/user/profile/editProfile" className="btn btn-outline-dark btn-block W-editProfile-button">
                                        {t('Profile.EditProfile')}
                                    </Link>
                                )}
                                {!isSameUser && canPromote && (
                                    <form onSubmit={handlePromoteUser} className="W-delete-form" id={`user${reviewOwnerUser.username}`} method="post">
                                        <button type="submit" className={`btn btn-outline-dark btn-sm btn-block`}>
                                            {t('Profile.PromoteUser')}
                                        </button>
                                    </form>
                                )}
                            </div>
                            <ul className="list-inline mb-0">
                                <li className="list-inline-item">
                                    <h4 className="font-weight-bold mb-0 d-block">{totalReviews}</h4>
                                    {reviews.length === 1 ? (
                                        <span className="text-muted"><i className="fas fa-image mr-1"></i>{t('Profile.Review')}</span>
                                    ) : (
                                        <span className="text-muted"><i className="fas fa-image mr-1"></i>{t('Profile.Reviews')}</span>
                                    )}
                                </li>
                                <TooltipComponent text={t('Reputation.Tooltip')}>
                                    <li className="list-inline-item">
                                        <h4 className="font-weight-bold mb-0 d-block">{reputation}</h4>
                                        <span className="text-muted"><i className="fas fa-image mr-1"></i>{t('Profile.Reputation')}</span>
                                    </li>
                                </TooltipComponent>
                            </ul>
                        </div>

                        <div className="py-4 px-4">
                            <div className="d-flex align-items-center justify-content-between mb-3">
                                <h4 className="mb-0">{t('Profile.RecentReviews')}</h4>
                            </div>
                            <div className="card">
                                <div className="card-body">
                                    {loaded && reviews.length > 0 ? (
                                        reviews.map((review, index) => (
                                            <div key={review.id}>
                                                <Link className="W-movie-title" to={`/content/${contentArray[index]?.type}/${contentArray[index]?.id}`}>
                                                    <h5>{contentArray[index]?.name}</h5>
                                                </Link>
                                                <ReviewCard
                                                    id={`reviewCardUser${review.id}`}
                                                    key={isLikeReviewsList && isDislikeReviewsList }
                                                    reviewTitle={review.name}
                                                    reviewDescription={review.description}
                                                    reviewRating={review.rating}
                                                    reviewId={review.id}
                                                    reviewReputation={review.reputation}
                                                    reviewUserUrl={review.user}
                                                    reviewUser={{id: reviewOwnerUser.id, username: reviewOwnerUser.username}}
                                                    contentUrl={review.content}
                                                    contentId={contentArray[index]?.id}
                                                    contentType={review.type}
                                                    loggedUserName={user?.username}
                                                    loggedUserId={user?.id}
                                                    isAdmin={user?.role === 'admin'}
                                                    isLikeReviews={isLikeReviewsList.length > 0 ? isLikeReviewsList.some(item => parseInt(item.id) === parseInt(review.id)) : false}
                                                    isDislikeReviews={isDislikeReviewsList.length > 0 ? isDislikeReviewsList.some(item => parseInt(item.id) === parseInt(review.id)) : false}
                                                    alreadyReport={loggedUserReviewsReported.length > 0 ? loggedUserReviewsReported.some(item => item.id === parseInt(review.id)) : false}
                                                    canComment={false}
                                                    seeComments={false}
                                                />
                                            </div>
                                        ))
                                    ) : (
                                        <div className="W-no-reviews-icon">
                                            <img className="W-no-reviews-image" src={noReviews} alt="No_Review_Img"/>
                                            {isSameUser ? (
                                                <h4 className="W-no-reviews-text">{t('Profile.NoReviews.Owner')}</h4>
                                            ) : (
                                                <h4 className="W-no-reviews-text">{t('Profile.NoReviews.NotOwner', {user: reviewOwnerUser.username})}</h4>
                                            )}
                                        </div>
                                    )}
                                </div>
                            </div>
                        </div>
                        {amountPages > 1 && (
                            <div>
                                <ul className="pagination justify-content-center W-pagination">
                                    {page > 1 ? (
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
                                            index + 1 === page ? (
                                                <li key={index+1} className="page-item active">
                                                    <p className="page-link W-pagination-color">{index + 1}</p>
                                                </li>
                                            ): index + 1 === page + 4 ? (
                                                <li key={index+1} className="page-item">
                                                    <p className="page-link W-pagination-color" onClick={() => changePage(index + 1)}>
                                                        ...
                                                    </p>
                                                </li>
                                            ): index + 1 === page - 4 ? (
                                                <li key={index+1} className="page-item">
                                                    <p className="page-link W-pagination-color" onClick={() => changePage(index + 1)}>
                                                        ...
                                                    </p>
                                                </li>
                                            ) : ( index + 1 > page - 4 && index + 1 < page + 4 ) && (
                                                <li key={index+1} className="page-item">
                                                    <p className="page-link W-pagination-color" onClick={() => changePage(index + 1)}>
                                                        {index + 1}
                                                    </p>
                                                </li>
                                            )
                                        ))
                                    ) : (
                                        Array.from({ length: amountPages }, (_, index) => (
                                            index + 1 === page ? (
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
                                    {page < amountPages ? (
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
                                </ul>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </>
    );
}