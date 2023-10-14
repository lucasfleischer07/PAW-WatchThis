import React, {useContext, useEffect, useState} from "react";
import Reputation from "./Reputation";
import {useTranslation} from "react-i18next";
import {Button, Modal} from "react-bootstrap";
import {AuthContext} from "../../context/AuthContext";
import {Link, useNavigate} from "react-router-dom";
import {contentService, reportsService, reviewService, userService} from "../../services";
import {toast} from "react-toastify";
export default function ReviewCard(props) {
    const {t} = useTranslation()
    let navigate = useNavigate()
    let {isLogged} = useContext(AuthContext)

    const reviewId = props.reviewId
    const reviewRating = props.reviewRating
    const reviewTitle = props.reviewTitle
    const ratingStars = [];
    const reviewDescription = props.reviewDescription
    const reviewReputation = props.reviewReputation
    const [reviewUser, setReviewUser] = useState("")
    const [reviewUserId, setReviewUserId] = useState(-1)
    const reviewUserUrl = props.reviewUserUrl
    const setReviewsChange = props.setReviewsChange
    const loggedUserName = props.loggedUserName
    const loggedUserId = props.loggedUserId
    const [isAdmin,setIsAdmin] = useState(props.isAdmin);

    const [isLikeReviews, setIsLikeReviews] = useState(props.isLikeReviews);
    const [isDislikeReviews, setIsDislikeReviews] = useState(props.isDislikeReviews);

    const alreadyReviewed = props.alreadyReviewed
    const setAlreadyReviewed = props.setAlreadyReviewed

    const [alreadyReport, setAlreadyReport] = useState(props.alreadyReport)
    const canComment = props.canComment
    const seeComments = props.seeComments

    const contentType = props.contentType
    const contentUrl = props.contentUrl
    const [contentId, setContentId] = useState(props.contentId)

    const [showReportModal, setShowReportModal] = useState(false);
    const [showDeleteReviewModal, setShowDeleteReviewModal] = useState(false);
    const [showLoginModal, setShowLoginModal] = useState(false);

    const [reportFrom, setReportForm] = useState({
        reportType: ""
    })

    const handleShowReportModal = () => {
        setShowReportModal(true);
    };
    const handleCloseReportModal = () => {
        setShowReportModal(false);
    };
    const handleShowDeleteReviewModal = () => {
        setShowDeleteReviewModal(true);
    };
    const handleCloseDeleteReviewModal = () => {
        setShowDeleteReviewModal(false);
    };
    const handleShowLoginModal = () => {
        setShowLoginModal(true);
    };
    const handleCloseLoginModal = () => {
        setShowLoginModal(false);
    };
    const handleGoToLogin = () => {
        navigate("/login", {replace: true})
    }

    const handleChange = (e) => {
        const {name, value} = e.target
        setReportForm((prev) => {
            return {...prev, [name]: value}
        })
    }

    for (let i = 1; i <= 5; i++) {
        ratingStars.push(
            i <= reviewRating ? (
                <svg key={i} xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-star-fill W-reviewCard-stars" viewBox="0 0 16 16">
                    <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                </svg>
            ) : (
                <svg key={i} xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-star W-reviewCard-stars" viewBox="0 0 16 16">
                    <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                </svg>
            )
        );
    }
    const handleDelete = () => {
        reviewService.deleteReview(reviewId, loggedUserId)
            .then(data => {
                if(!data.error) {
                    handleCloseDeleteReviewModal()
                    setReviewsChange(!props.reviewsChange)
                    toast.success(t('Review.Deleted'))
                    setAlreadyReviewed(!alreadyReviewed)
                } else {
                    navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                }
            })
            .catch((e) => {
                navigate("/error", { replace: true, state: {errorCode: 404} })
            })
    }

    const handleSubmitReport = () => {
        reviewService.addReviewReport(reviewId, loggedUserId, reportFrom)
            .then(data => {
                if(!data.error) {
                    toast.success(t('Report.Success'))
                    handleCloseReportModal()
                    setAlreadyReport(true)
                } else {
                    navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                }
            })
            .catch(() => {
                navigate("/error", { replace: true, state: {errorCode: 404} })
            })

    };

    useEffect(() => {
        if(canComment) {
            userService.getUserInfo(reviewUserUrl)
                .then(reviewUserData => {
                    if(!reviewUserData.error) {
                        setReviewUserId(reviewUserData.data.id)
                        setReviewUser(reviewUserData.data.username)
                    } else {
                        navigate("/error", { replace: true, state: {errorCode: reviewUserData.errorCode} })
                    }
                })
                .catch(() => {
                    navigate("/error", { replace: true, state: {errorCode: 404} })
                })
        } else {
            setReviewUserId(loggedUserId)
            setReviewUser(loggedUserName)
        }


        if(contentId === undefined) {
            contentService.getSpecificContent(contentUrl)
                .then(data => {
                    if(!data.error) {
                        setContentId(data.data.id)
                    } else {
                        navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                    }
                })
                .catch(() => {
                    navigate("/error", { replace: true, state: {errorCode: 404} })
                })
        }

    }, [reviewUserUrl])

    return(
        <div className="accordion W-accordion-margin" id={`accordion${reviewId}`}>
            <div className="accordion-item W-accordion-border-transparent">
                <div className="accordion-header" id={`heading${reviewId}`}>
                    <div className="card">
                        <div className="card-header W-accordion-card-header">
                            <Link to={`/user/profile/${reviewUserId}`} className="W-creator-review">
                                {reviewUser}
                            </Link>
                            <div className="W-delete-edit-report-review-buttons">
                                {isLogged() && (reviewUser === loggedUserName) && (
                                    <div>
                                        <div className="W-delete-edit-buttons">
                                            <button className="btn btn-danger text-nowrap" id={`deleteReviewButton${reviewId}`} onClick={handleShowDeleteReviewModal} data-testid={'delete'}>
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-trash3" viewBox="0 0 16 16">
                                                    <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
                                                </svg>
                                            </button>
                                            <Link className="W-edit-button-review" to={`/content/${contentType}/${contentId}/${reviewId}/reviewEdition`}>
                                                <button id={`editReviewButton${reviewId}`} className="btn btn-dark text-nowrap" data-testid={'edit'}>
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-pencil-square" viewBox="0 0 16 16">
                                                        <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                                        <path fillRule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                                    </svg>
                                                </button>
                                            </Link>
                                        </div>
                                    </div>
                                )}
                                {isLogged() && isAdmin && (reviewUser !== loggedUserName) &&(
                                    <button className="btn btn-danger text-nowrap" id={`deleteReviewButton${reviewId}`} onClick={handleShowDeleteReviewModal}>
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-trash3" viewBox="0 0 16 16">
                                            <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
                                        </svg>
                                    </button>
                                )}
                                {isLogged() ? (
                                    (!alreadyReport) && (reviewUser !== loggedUserName) && !isAdmin &&(
                                        <span title={t('Report.Review.Add')}>
                                            <button id={`reportReviewButton${props.reviewId}`} type="button" className="btn btn-light W-background-color-report" data-bs-toggle="modal" data-testid={'report'}  onClick={handleShowReportModal} data-bs-target={`reportReviewModal${props.reviewId}`}>
                                                <svg data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title={t("Report.Review.Add")} xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#b21e26" className="bi bi-exclamation-circle" viewBox="0 0 16 16">
                                                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                                    <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
                                                </svg>
                                            </button>
                                        </span>
                                    )
                                ):(
                                    <div>
                                        <span title={t('Report.Review.Add')}>
                                            <button id={`reportReviewButton${props.reviewId}`} type="button" className="btn btn-light W-background-color-report" data-bs-toggle="modal"  onClick={handleShowLoginModal} data-bs-target={`loginModal${props.reviewId}`}>
                                                <svg data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-title={t("Report.Review.Add")} xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#b21e26" className="bi bi-exclamation-circle" viewBox="0 0 16 16">
                                                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                                    <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
                                                </svg>
                                            </button>
                                        </span>
                                        <Modal show={showLoginModal} id={`reportReviewModalNoLogin${props.reviewId}`} tabIndex="-1" aria-labelledby={`reportReviewModalNoLoginLabel${props.reviewId}`} aria-hidden="true">
                                            <Modal.Header >
                                                <Modal.Title id={`reportReviewModalNoLoginLabel${props.reviewId}`}>{t("Report.ReviewTitle")}</Modal.Title>
                                                <button type="button" onClick={handleCloseLoginModal} className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </Modal.Header>
                                            <Modal.Body >
                                                <span>{t("Report.ReviewWarningAdd")}</span>
                                                <span>{t("Review.WarningAddMessage")}</span>
                                            </Modal.Body>
                                                <Modal.Footer >
                                                <button type="button" onClick={handleCloseLoginModal} className="btn btn-secondary" data-bs-dismiss="modal">{t("Close")}</button>
                                                <button type="button" onClick={handleGoToLogin} className="btn btn-success">{t("Login.LoginMessage")}</button>
                                            </Modal.Footer>
                                        </Modal>
                                    </div>
                                )}
                            </div>
                        </div>
                        <div className="card-body W-accordion-card-body">
                            <button id={`button${reviewId}`} className="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target={`#collapse${reviewId}`} aria-expanded="false" aria-controls={`collapse${reviewId}`}>
                                <div className="W-stars">
                                    {ratingStars}
                                </div>
                                <div className="W-review-title-creator">
                                    <h2 className="W-title-review">
                                        {reviewTitle}
                                    </h2>
                                </div>
                            </button>
                        </div>
                    </div>
                </div>
                <div id={`collapse${reviewId}`} className="accordion-collapse collapse" aria-labelledby={`heading${reviewId}`} data-bs-parent={`#accordion${reviewId}`}>
                    <div className="accordion-body">
                        <Reputation
                            key ={`reputation ${reviewId}`}
                            reviewId={reviewId}
                            isLikeReviews={isLikeReviews}
                            isDislikeReviews={isDislikeReviews}
                            reviewReputation={reviewReputation}
                            reviewDescription={reviewDescription}
                            loggedUserIsAdmin={isAdmin}
                            loggedUserId={props.loggedUserId}
                            loggedUserName={loggedUserName}
                            canComment={canComment}
                            seeComments={seeComments}
                        />
                    </div>
                </div>
            </div>

            <Modal show={showDeleteReviewModal} onHide={handleCloseDeleteReviewModal} id={`modalDeleteReview${reviewId}`} tabIndex="-1" aria-labelledby={`modalLabel${reviewId}`} aria-hidden="true">
                <div className="modal-content">
                    <Modal.Header className="modal-header">
                        <Modal.Title className="modal-title" id={`modalLabel${reviewId}`}>
                            {t("Delete.Confirmation")}
                        </Modal.Title>
                        <button type="button" onClick={handleCloseDeleteReviewModal} className="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </Modal.Header>
                    <Modal.Body className="modal-body">
                        <p>{t("DeleteReview")}</p>
                    </Modal.Body>
                    <Modal.Footer className="modal-footer">
                        <button type="button" onClick={handleCloseDeleteReviewModal} className="btn btn-secondary" data-bs-dismiss="modal">
                            {t("No")}
                        </button>
                        <button type="submit" form={`formDeleteReview${reviewId}`} className="btn btn-success" onClick={handleDelete}>
                            {t("Yes")}
                        </button>
                    </Modal.Footer>
                </div>
            </Modal>

            <Modal show={showReportModal} onHide={handleCloseReportModal} aria-labelledby={`reportReviewModal${reviewId}`} aria-hidden="true">
                <Modal.Header closeButton>
                    <Modal.Title id={`modalLabel${reviewId}`}>
                        {t('Report.ReviewTitle')}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <ul className="W-no-bullets-list">
                        <li>
                            <label>
                                <input type="radio" name="reportType" value="Spam" onChange={handleChange}/> {t('Report.Spam')}
                                <p className="W-modal-comment-desc">{t('Report.Spam.Description')}</p>
                            </label>
                        </li>
                        <li>
                            <label>
                                <input type="radio" name="reportType" value="Insult" onChange={handleChange}/> {t('Report.Insult')}
                                <p className="W-modal-comment-desc">{t('Report.Insult.Description')}</p>
                            </label>
                        </li>
                        <li>
                            <label>
                                <input type="radio" name="reportType" value="Inappropriate" onChange={handleChange}/> {t('Report.Inappropriate')}
                                <p className="W-modal-comment-desc">{t('Report.Inappropriate.Description')}</p>
                            </label>
                        </li>
                        <li>
                            <label>
                                <input type="radio" name="reportType" value="Unrelated" onChange={handleChange}/> {t('Report.Unrelated')}
                                <p className="W-modal-comment-desc">{t('Report.Unrelated.Description')}</p>
                            </label>
                        </li>
                        <li>
                            <label>
                                <input type="radio" name="reportType" value="Other" onChange={handleChange}/> {t('Report.Other')}
                                <p className="W-modal-comment-desc">{t('Report.Other.Description')}</p>
                            </label>
                        </li>
                    </ul>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseReportModal}>
                        {t('No')}
                    </Button>
                    <Button variant="success" onClick={handleSubmitReport}>
                        {t('Yes')}
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}