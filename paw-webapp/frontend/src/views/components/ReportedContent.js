import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import React, { useEffect } from "react";
import marked from "marked";

export default function ReportedContent(props) {

    // function CommentText({ param }) {
    //     useEffect(() => {
    //         const commentTextArea = document.getElementById(`commentTextArea${param.typeId}${param.reportType}`);
    //         const commentTextArea1 = document.getElementById(`commentTextArea1${param.typeId}${param.reportType}`);
    //         commentTextArea.innerHTML = marked.parse(commentTextArea.innerHTML);
    //         commentTextArea1.innerHTML = marked.parse(commentTextArea1.innerHTML);
    //     }, [param.typeId, param.reportType]);

    const {t} = useTranslation()

    return(
        <div className="card W-card-reported-width">
            <div className="card-body W-general-div-reports">
                <div className="W-username-report-description-div">
                    <div className="W-comment-username-and-report">
                        <div>
                            {param.reportType === 'comment' ? (
                                <Link to={`/profile/${param.userName}`} className="W-creator-review W-margin-right-reports">
                                    <spring:message code="Comment.Owner" arguments={[param.userName]} />
                                    {t('Comment.Owner')}
                                </Link>
                            ) : (
                                <Link to={`/profile/${param.userName}`} className="W-creator-review W-margin-right-reports">
                                    <spring:message code="Review.Owner" arguments={[param.userName]} />
                                    {t('Review.Owner')}
                                </Link>
                            )}
                            {param.reportType === 'comment' && (
                                <Link to={`/profile/${param.reviewCreatorUserName}`} className="W-creator-review W-margin-right-reports">
                                    <spring:message code="Review.Owner" arguments={[param.reviewCreatorUserName]} />
                                    {t('Review.Owner')}
                                </Link>
                            )}
                        </div>
                        <div>
                            <Link to="<c:url value="/${param.contentType}/${param.contentId}"/>" className="W-creator-review"> <c:out value="${param.contentName}"/></Link>
                        </div>
                        <div className="W-amount-reports-and-delete-button">
                            <div className="W-delete-button-report">
                                {t('Delete')}
                                <span>
                                    <form className="W-delete-form-reported" id={`form${typeId}${reportType}`} method="post" action={`/${reportType}/${typeId}/delete`}>
                                        <button className="btn btn-danger text-nowrap" type="button" data-bs-toggle="modal" data-bs-target={`#modal${typeId}`}>
                                            <svg data-bs-placement="bottom" xmlns="http://www.w3.org/2000/svg" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"  className="bi bi-trash3" viewBox="0 0 16 16">
                                            <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
                                            </svg>
                                        </button>
                                    </form>
                                </span>

                                <div className="modal fade" id={`modal${typeId}`} tabIndex="-1" aria-labelledby={`modalLabel${typeId}`} aria-hidden="true">
                                    <div className="modal-dialog">
                                        <div className="modal-content">
                                            <div className="modal-header">
                                                <h5 className="modal-title" id={`modalLabel${typeId}`}><FormattedMessage id="Delete.Confirmation" /></h5>
                                                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div className="modal-body">
                                                {param.reportType === 'review' ? <p><FormattedMessage id="DeleteReview" /></p> : <p><FormattedMessage id="DeleteComment" /></p>}
                                            </div>
                                            <div className="modal-footer">
                                                <button type="button" className="btn btn-secondary" data-bs-dismiss="modal"><FormattedMessage id="No" /></button>
                                                <button type="submit" form={`form${typeId}${reportType}`} className="btn btn-success" onClick={(event) => { event.target.className += ' spinner-border'; event.target.innerText = '|' }}><FormattedMessage id="Yes" /></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                {t('Report.Dismiss')}

                                <span title={unreported}>
                                    <form className="W-form-remove-from-reported-bad-added" id={`report${typeId}${reportType}`} method="post" action={`/report/reportedContent/${reportType}/${typeId}/report/delete`}>
                                        <button type="button" className="btn btn-light W-background-color-report" data-bs-toggle="modal" data-bs-target={`#unreportedCommentModal${typeId}${reportType}`}>
                                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="green" className="bi bi-check-circle" viewBox="0 0 16 16">
                                                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                                <path d="M10.97 4.97a.235.235 0 0 0-.02.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-1.071-1.05z"/>
                                            </svg>
                                        </button>
                                    </form>
                                </span>
                                <div className="modal fade" id={`unreportedCommentModal${param.typeId}${param.reportType}`} tabIndex="-1" aria-labelledby={`unreportedCommentModalLabel${param.typeId}${param.reportType}`} aria-hidden="true">
                                    <div className="modal-dialog">
                                        <div className="modal-content">
                                            <div className="modal-header">
                                                <h5 className="modal-title" id={`unreportedCommentModalLabel${param.typeId}${param.reportType}`}><spring:message code="Dismiss.Confirmation"/></h5>
                                                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div className="modal-body">
                                                {param.reportType === 'review' ? (
                                                    <p><spring:message code="Report.DeleteReview"/></p>
                                                ) : (
                                                    <p><spring:message code="Report.DeleteComment"/></p>
                                                )}
                                            </div>
                                            <div className="modal-footer">
                                                <button type="button" className="btn btn-secondary" data-bs-dismiss="modal"><spring:message code="No"/></button>
                                                <button type="submit" form={`report${param.typeId}${param.reportType}`} className="btn btn-success" onClick={(e) => {e.target.form.submit(); e.target.classList.add('spinner-border'); e.target.innerText = '|' }}><spring:message code="Yes"/></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="W-comment-text">
                        {param.reportType === "comment" ? (
                            <>
                                <p id={`commentTextArea1${param.typeId}${param.reportType}`} className="W-report-review-paragraph">{param.reviewNameOfReportedComment}</p>
                                <p id={`commentTextArea${param.typeId}${param.reportType}`} className="W-report-description-paragraph">{param.reportDescription}</p>
                            </>
                        ) : (
                            <>
                                <p id={`commentTextArea${param.typeId}${param.reportType}`} className="W-report-description-paragraph-review">{param.reportDescription}</p>
                                <p id={`commentTextArea1${param.typeId}${param.reportType}`} className="W-report-review-paragraph-review">{param.reportDescription2}</p>
                            </>
                        )}
                    </div>
                    <div className="W-type-of-report">
                        <div>
                            {param.reportsAmount === 1 ? (
                                <span title={param.reportReasons} className="W-report-margin-zero">
                                    <FormattedMessage id="Report.Report" values={{ count: param.reportsAmount }} />
                                </span>
                            ) : (
                                <span title={param.reportReasons} className="W-report-margin-zero">
                                    <FormattedMessage id="Report.Reports" values={{ count: param.reportsAmount }} />
                                </span>
                            )}
                        </div>
                        <div>
                            {param.reportType === 'review' ? (
                                <p className="W-report-margin-zero W-color-report-type">
                                    {t('Profile.Review')}
                                </p>
                            ) : (
                                <p className="W-report-margin-zero W-color-report-type">
                                    {t('Comment.Title')}
                                </p>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
