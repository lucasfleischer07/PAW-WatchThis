import {useTranslation} from "react-i18next";
import React, {useContext, useEffect, useState} from "react";
import {Link, useLocation, useNavigate, useParams} from "react-router-dom";
import {toast} from "react-toastify";
import TooltipComponent from "./components/Tooltip";
import Header from "./components/Header";
import ReviewCard from "./components/ReviewCard";
import {AuthContext} from "../context/AuthContext";
import ExpiredCookieModal from "./components/ExpiredCookieModal";
import {reviewService, userService} from "../services";
import {updateUrlVariable} from "../scripts/validateParam";
import {checkIsNumber} from "../scripts/filtersValidations";

export default function UserInfoPage() {
    const {t} = useTranslation()
    let navigate = useNavigate()
    const { search } = useLocation();
    let {isLogged} = useContext(AuthContext)
    const { userProfileId } = useParams();
    const [showExpiredCookiesModal, setShowExpiredCookiesModal] = useState(false)

    const [user, setUser] = useState(localStorage.hasOwnProperty("user")? JSON.parse(localStorage.getItem("user")) : null)
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
        updateUrlVariable(page, (typeof queryParams.get('page') === 'string' ?  checkIsNumber(queryParams.get('page')) : queryParams.get('page')), (x) =>setPage(x))
    }, [search]);


    useEffect(() => {
        userService.getUserReviews(parseInt(userProfileId), page)
            .then(reviews => {
                if(!reviews.error) {
                    setReviews(reviews.data)
                    setTotalReviews(reviews.totalReviews)
                    if(reviews.data.length === 0) {
                        userService.getUserInfo(parseInt(userProfileId))
                             .then((data) => {
                                 if(!data.error) {
                                    setReviewOwnerUser(data.data)
                                     if(user?.role === 'admin' && data.data.role !== 'admin') {
                                         setCanPromote(true)
                                     } else {
                                         setCanPromote(false)
                                     }
                                 } else {
                                     navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                                 }
                             })
                            .catch(() => {
                                navigate("/error", { replace: true, state: {errorCode: 404} })
                            })
                        setReputation(0)
                    } else {
                        setReviewOwnerUser(reviews.data[0].user)
                        let reputation = 0
                        for(let i = 0; i < reviews.data.length; i++) {
                            reputation += reviews.data[i].reputation
                        }
                        setReputation(reputation)

                        if(user?.role === 'admin' && reviews.data[0].user.role !== 'admin') {
                            setCanPromote(true)
                        } else {
                            setCanPromote(false)
                        }
                    }
                    setAmountReviews(reviews.totalReviews)
                    setAmountPages(reviews.totalPages)

                    if(user?.id === parseInt(userProfileId)) {
                        setIsSameUser(true)
                    } else {
                        setIsSameUser(false)
                    }

                } else {
                    navigate("/error", { replace: true, state: {errorCode: reviews.errorCode} })
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

        }

    }, [page])


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
                                                <img src={"/paw-2022b-3/images/defaultUserImg.png"} alt="User_img" className="W-edit-profile-picture" />
                                            ) : (
                                                <img src={reviewOwnerUser.image} alt="User_img" className="W-edit-profile-picture" />
                                            )}
                                            <h4 className="W-username-profilepage">{reviewOwnerUser.username}</h4>
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
                                    {reviews.length === 0 ? (
                                        <div className="W-no-reviews-icon">
                                            <img className="W-no-reviews-image" src={"/paw-2022b-3/images/noReviews.png"} alt="No_Review_Img"/>
                                            {isSameUser ? (
                                                <h4 className="W-no-reviews-text">{t('Profile.NoReviews.Owner')}</h4>
                                            ) : (
                                                <h4 className="W-no-reviews-text">{t('Profile.NoReviews.NotOwner', {user: reviewOwnerUser.username})}</h4>
                                            )}
                                        </div>
                                    ) : (
                                        reviews.map((review) => (
                                            <div key={review.id}>
                                                <Link className="W-movie-title" to={`/content/${review.content.type}/${review.content.id}`}>
                                                    <h5>{review.content.name}</h5>
                                                </Link>
                                                <ReviewCard
                                                    id={`reviewCardUser${review.id}`}
                                                    key={isLikeReviewsList && isDislikeReviewsList }
                                                    reviewTitle={review.name}
                                                    reviewDescription={review.description}
                                                    reviewRating={review.rating}
                                                    reviewId={review.id}
                                                    reviewReputation={review.reputation}
                                                    reviewUser={review.user.username}
                                                    reviewUserId={review.user.id}
                                                    contentId={review.content.id}
                                                    contentType={review.type}
                                                    loggedUserName={user?.username}
                                                    loggedUserId={user?.id}
                                                    isAdmin={user?.role === 'admin'}
                                                    isLikeReviews={isLikeReviewsList.length > 0 ? isLikeReviewsList.some(item => parseInt(item.id) === parseInt(review.id)) : false}
                                                    isDislikeReviews={isDislikeReviewsList.length > 0 ? isDislikeReviewsList.some(item => parseInt(item.id) === parseInt(review.id)) : false}
                                                    alreadyReport={review.reviewReporters.includes(user?.username)}
                                                    canComment={false}
                                                    seeComments={false}
                                                />
                                            </div>
                                        ))
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
                    </div>
                </div>
            </div>
        </>
    );
}