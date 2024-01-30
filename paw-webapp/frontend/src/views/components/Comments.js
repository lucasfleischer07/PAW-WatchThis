import {Link, useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";
import React, {useContext, useEffect, useState} from 'react';
import {Button, Modal} from "react-bootstrap";
import {AuthContext} from "../../context/AuthContext";
import {commentService, reportsService, userService} from "../../services";
import {toast} from "react-toastify";
import TooltipComponent from './Tooltip';
import defaultUserImg from "../../images/defaultUserImg.png"


export default function Comments(props) {
    const {t} = useTranslation()
    let navigate = useNavigate()
    let {isLogged} = useContext(AuthContext)
    const authFunctions = useContext(AuthContext)

    const [reportFrom, setReportForm] = useState({
        reportType: ""
    })

    const commentId = props.commentId
    const commentText = props.commentText

    const userCreatorUrl = props.userCreatorUrl
    const [userCreatorId, setUserCreatorId] = useState(-1)
    const [userCreatorImage, setUserCreatorImage] = useState("")
    const [userCreatorUsername, setUserCreatorUsername] = useState("")


    const [alreadyReport, setAlreadyReport] = useState(props.alreadyReport)
    const [loggedUserIsAdmin,setIsAdmin] = useState(props.loggedUserIsAdmin);

    const loggedUserName = props.loggedUserName
    const loggedUserId = props.loggedUserId

    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const handleShowDeleteModal = () => {
        setShowDeleteModal(true);
    };
    const handleCloseDeleteModal = () => {
        setShowDeleteModal(false);
    };

    const handleSubmitDeleteComment = () => {
        commentService.commentDelete(commentId)
            .then(data => {
                if(!data.error) {
                    toast.success(t('Mail.CommentDeleted'))
                    handleCloseDeleteModal()
                    props.setAdded(!props.added)
                } else {
                    navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                }
            })
            .catch(() => {
                navigate("/error", { replace: true, state: {errorCode: 404} })
            })
    }

    const [showReportModal, setShowReportModal] = useState(false);
    const handleShowReportModal = () => {
        setShowReportModal(true);
    };
    const handleCloseReportModal = () => {
        setShowReportModal(false);
    };

    const handleSubmitReport = () => {
        commentService.addCommentReport(authFunctions, parseInt(loggedUserId), commentId, reportFrom)
            .then(data => {
                if(!data.error) {
                    setAlreadyReport(!alreadyReport)
                    toast.success(t('Report.Success'))
                    handleCloseReportModal()
                } else {
                    navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                }
            })
            .catch(() => {
                navigate("/error", { replace: true, state: {errorCode: 404} })
            })
    };

    const [showLoginModal, setShowLoginModal] = useState(false);
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


    useEffect(() => {
        userService.getUserInfo(authFunctions, userCreatorUrl)
            .then(userCreatorData => {
                if(!userCreatorData.error) {
                    setUserCreatorId(userCreatorData.data.id)
                    setUserCreatorUsername(userCreatorData.data.username)
                    setUserCreatorImage(userCreatorData.data.image)
                } else {
                    navigate("/error", { replace: true, state: {errorCode: userCreatorData.errorCode} })
                }
            })
            .catch(() => {
                navigate("/error", { replace: true, state: {errorCode: 404} })
            })
    }, [userCreatorUrl])


    return(
            <div className="card W-comment-general-card">
                <div className="card-body W-general-div-comment">
                    <div className="W-img-comment-div-margin-right">
                        {userCreatorImage == null ? (
                            <img src={defaultUserImg} alt="User_img" className="W-comment-profile-picture" />
                        ) : (
                            <img src={userCreatorImage} alt="User_img" className="W-comment-profile-picture" />
                        )}
                    </div>
                    <div className="W-comment-username-report-description-div ">
                        <div className="W-comment-username-and-report">
                            <div>
                                <Link to={`/user/profile/${userCreatorId}`} className="W-creator-review">
                                    {userCreatorUsername}
                                </Link>
                            </div>
                            <div className="W-report-and-delete-comment-buttons">
                                {isLogged() ? (
                                    <>
                                        {userCreatorUsername === loggedUserName || loggedUserIsAdmin ? (
                                            <>
                                                <form className="W-delete-form" id={`formDeleteComment${commentId}`} method="post">
                                                    <button className="btn btn-danger text-nowrap W-prueba" type="button" data-bs-toggle="modal" data-bs-target={`#deleteCommentModal${commentId}`} onClick={handleShowDeleteModal} data-testid={`delete${commentId}`}>
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-trash3"  viewBox="0 0 16 16">
                                                            <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
                                                        </svg>
                                                    </button>
                                                </form>

                                                <Modal show={showDeleteModal} onHide={handleCloseDeleteModal} aria-labelledby={`deleteCommentModal${commentId}`} aria-hidden="true">
                                                    <Modal.Header closeButton>
                                                        <Modal.Title id={`modalLabel${commentId}`}>
                                                            {t('Delete.Confirmation')}
                                                        </Modal.Title>
                                                    </Modal.Header>
                                                    <Modal.Body>
                                                        {t('DeleteComment')}
                                                    </Modal.Body>
                                                    <Modal.Footer>
                                                        <Button variant="secondary" onClick={handleCloseDeleteModal}>
                                                            {t('No')}
                                                        </Button>
                                                        <Button variant="success" onClick={handleSubmitDeleteComment}>
                                                            {t('Yes')}
                                                        </Button>
                                                    </Modal.Footer>
                                                </Modal>
                                            </>
                                        ):(
                                            <></>
                                        )}
                                        {<>
                                            {userCreatorUsername !== loggedUserName && !alreadyReport && !loggedUserIsAdmin ? (
                                                <>
                                                    <TooltipComponent text={t('Report.Add')}>
                                                        <button id={`reportCommentButton${commentId}`} type="button" className="btn btn-light W-background-color-report" data-bs-toggle="modal" data-bs-target={`#reportCommentModal${commentId}`} onClick={handleShowReportModal} data-testid={`report${commentId}`}>
                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#b21e26" className="bi bi-exclamation-circle" viewBox="0 0 16 16">
                                                                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                                                <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
                                                            </svg>
                                                        </button>
                                                    </TooltipComponent>

                                                    <Modal show={showReportModal} onHide={handleCloseReportModal} aria-labelledby={`reportCommentModal${commentId}`} aria-hidden="true">
                                                        <Modal.Header closeButton>
                                                            <Modal.Title id={`modalLabel${commentId}`}>
                                                                {t('Report.CommentTitle')}
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
                                                </>
                                            ):(<></>)}
                                        </>

                                        }
                                    </>
                                ) : (
                                    <>
                                        <div>
                                            <TooltipComponent text={t('Report.Add')}>
                                                <button id="reportCommentButtonNoLogin" type="button" className="btn btn-light" data-bs-toggle="modal" data-bs-target="#reportCommentModalNoLogin" onClick={handleShowLoginModal}>
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#b21e26" className="bi bi-exclamation-circle" viewBox="0 0 16 16">
                                                        <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                                        <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
                                                    </svg>
                                                </button>
                                            </TooltipComponent>
                                        </div>

                                        <Modal show={showLoginModal} onHide={handleCloseLoginModal} aria-labelledby={`reportCommentModalNoLogin${commentId}`} aria-hidden="true">
                                            <Modal.Header closeButton>
                                                <Modal.Title id={`modalLabel${commentId}`}>
                                                    {t('Report.CommentTitle')}
                                                </Modal.Title>
                                            </Modal.Header>
                                            <Modal.Body>
                                                {t('Comment.WarningAdd')}
                                                {t('Review.WarningAddMessage')}
                                            </Modal.Body>
                                            <Modal.Footer>
                                                <Button variant="secondary" onClick={handleCloseReportModal}>
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
                        <div className="W-comment-text">
                            <p id="floatingTextarea2">{commentText}</p>
                        </div>
                    </div>
                </div>
            </div>
        );
}







