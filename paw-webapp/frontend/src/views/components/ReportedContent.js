import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import React, {useState} from "react";
import { Modal, Button } from 'react-bootstrap';
import Markdown from 'marked-react';
import {commentService, reportsService, reviewService} from "../../services";
import {toast} from "react-toastify";

export default function ReportedContent(props) {
    const {t} = useTranslation()

    const [showModal, setShowModal] = useState(false);
    const [showModalDismiss, setShowModalDismiss] = useState(false);

    const userName = props.userName
    const userId = props.userId
    const contentId = props.contentId
    const contentName = props.contentName
    const contentType = props.contentType
    const reportTitle = props.reportTitle
    const reportDescription = props.reportDescription
    const reportReasons = props.reportReasons
    const reportsAmount = props.reportsAmount
    const typeId = props.typeId
    const reportType = props.reportType
    const reviewCreatorUserName = props.reviewCreatorUserName
    const reviewCreatorUserId = props.reviewCreatorUserId
    const reviewNameOfReportedComment = props.reviewNameOfReportedComment


    const handleShowModal = () => {
        setShowModal(true);
    };

    const handleCloseModalDismiss = () => {
        setShowModalDismiss(false);
    };

    const handleShowModalDismiss = () => {
        setShowModalDismiss(true);
    };

    const handleCloseModal = () => {
        setShowModal(false);
    };

    const handleDeleteCommentOrReview = (e) => {
        e.preventDefault()
        if(reportType === 'comment') {
            commentService.commentDelete(typeId)
                .then(data => {
                    if(!data.error) {
                        // TODO: HAcer este toast
                        handleCloseModal()
                        // toast.success()
                    }
                })
                .catch(e => {
                // TODO: Llevar a pagina de errror
                })
        } else {
            reviewService.deleteReview(typeId)
                .then(data => {
                    if(!data.error) {
                        handleCloseModal()
                        // TODO: HAcer este toast
                        // toast.success()
                    }
                })
                .catch(e => {
                    // TODO: Llevar a pagina de errror
                })
        }
    }

    const handleDismissReport = (e) => {
        e.preventDefault()

        reportsService.deleteReport(typeId, reportType)
            .then(data => {
                if(!data.error) {
                    handleCloseModalDismiss()
                    // TODO: HAcer este toast
                    // toast.success()
                }
            })
            .catch(e => {
                // TODO: Llevar a pagina de errror
            })
    }


    return(
        <div className="card W-card-reported-width">
            <div className="card-body W-general-div-reports">
                <div className="W-username-report-description-div">
                    <div className="W-comment-username-and-report">
                        <div>
                            {reportType === 'comment' ? (
                                <Link to={`/profile/${userId}`} className="W-creator-review W-margin-right-reports">{t('Comment.Owner', {username: userName})}</Link>
                            ) : (
                                <Link to={`/profile/${userId}`} className="W-creator-review W-margin-right-reports">{t('Review.Owner', {username: userName})}</Link>
                            )}
                            {reportType === 'comment' && (
                                <Link to={`/profile/${reviewCreatorUserId}`} className="W-creator-review W-margin-right-reports">{t('Review.Owner', {username: reviewCreatorUserName})}</Link>
                            )}
                        </div>
                        <div>
                            <Link to={`/content/${contentType}/${contentId}`} className="W-creator-review">{contentName}</Link>
                        </div>


                        <div className="W-amount-reports-and-delete-button">
                            <div className="W-delete-button-report">
                                <span title="Delete">
                                  <form className="W-delete-form-reported" id={`form${typeId}${reportType}`} onSubmit={handleDeleteCommentOrReview}>
                                    <button className="btn btn-danger text-nowrap" type="button" data-bs-toggle="modal" data-bs-target={`#modal${typeId}`} onClick={handleShowModal}>
                                      <svg data-bs-placement="bottom" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-trash3" viewBox="0 0 16 16">
                                        <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
                                        </svg>
                                    </button>
                                  </form>
                                </span>

                                <Modal show={showModal} onHide={handleCloseModal} aria-labelledby={`modal${typeId}`} aria-hidden="true">
                                    <Modal.Header closeButton>
                                        <Modal.Title id={`modalLabel${typeId}`}>
                                            {t('Delete.Confirmation')}
                                        </Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body>
                                        {reportType === "review" ? (
                                            <p>{t('DeleteReview')}</p>
                                        ) : (
                                            <p>{t('DeleteComment')}</p>
                                            )}
                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button variant="secondary" onClick={handleCloseModal}>
                                            {t('No')}
                                        </Button>
                                        <Button variant="success" onClick={handleDeleteCommentOrReview} form={`form${typeId}${reportType}`}>
                                            {t('Yes')}
                                        </Button>
                                    </Modal.Footer>
                                </Modal>

                                <span title="Report.Dismiss">
                                    <form className="W-form-remove-from-reported-bad-added" id={`report${typeId}${reportType}`} onSubmit={handleDismissReport}>
                                        <button type="button" className="btn btn-light W-background-color-report" data-bs-toggle="modal" data-bs-target={`#unreportedCommentModal${typeId}${reportType}`} onClick={handleShowModalDismiss}>
                                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="green" className="bi bi-check-circle" viewBox="0 0 16 16">
                                                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                                <path d="M10.97 4.97a.235.235 0 0 0-.02.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-1.071-1.05z"/>
                                            </svg>
                                        </button>
                                    </form>
                                </span>

                                <Modal show={showModalDismiss} onHide={handleCloseModalDismiss}>
                                    <Modal.Header closeButton>
                                        <Modal.Title>{t('Dismiss.Confirmation')}</Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body>
                                        {reportType === 'review' ? (
                                            <p>{t('Report.DeleteReview')}</p>
                                        ) : (
                                            <p>{t('Report.DeleteComment')}</p>
                                        )}
                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button variant="secondary" onClick={handleCloseModalDismiss}>
                                            {t('No')}
                                        </Button>
                                        <Button variant="success" type="submit" form={`report${typeId}${reportType}`} onClick={handleDismissReport}>
                                            {t('Yes')}
                                        </Button>
                                    </Modal.Footer>
                                </Modal>

                            </div>
                        </div>
                    </div>


                    <div className="W-comment-text">
                        {reportType === "comment" ? (
                            <>
                                <div>
                                    <Markdown id={`commentTextArea1${typeId}${reportType}`} className="W-report-review-paragraph">{reviewNameOfReportedComment}</Markdown>
                                </div>
                                <div>
                                    <Markdown id={`commentTextArea${typeId}${reportType}`} className="W-report-description-paragraph">{reportTitle}</Markdown>
                                </div>
                            </>
                        ) : (
                            <>
                                <div>
                                    <Markdown id={`commentTextArea${typeId}${reportType}`} className="W-report-description-paragraph-review">{reportTitle}</Markdown>
                                </div>
                                <div>
                                    <Markdown id={`commentTextArea1${typeId}${reportType}`} className="W-report-review-paragraph-review">{reportDescription}</Markdown>
                                </div>
                            </>
                        )}
                    </div>
                    <div className="W-type-of-report">
                        <div>
                            {reportsAmount === 1 ? (
                                <span title={reportReasons} className="W-report-margin-zero"> {t('Report.Report', {reportsAmount: reportsAmount})}</span>
                            ) : (
                                <span title={reportReasons} className="W-report-margin-zero">{t('Report.Reports', {reportsAmount: reportsAmount})}</span>
                            )}
                        </div>
                        <div>
                            {reportType === "reviews" ? (
                                <p className="W-report-margin-zero W-color-report-type">{t('Profile.Review')}</p>
                            ) : (
                                <p className="W-report-margin-zero W-color-report-type">{t('Comment.Title')}</p>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
