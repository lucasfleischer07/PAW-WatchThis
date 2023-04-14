import {useTranslation} from "react-i18next";
import ReportedContent from "./components/ReportedContent";
import {useLocation, useNavigate } from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import {AuthContext} from "../context/AuthContext";
import {reportsService} from "../services";

import { Tab, Tabs } from 'react-bootstrap';
import Header from "./components/Header";

export default function ReportedPage() {
    const {t} = useTranslation()
    let navigate = useNavigate()
    let {isLogged} = useContext(AuthContext)

    const [user, setUser] = useState(localStorage.hasOwnProperty("user")? JSON.parse(localStorage.getItem("user")) : null)
    const [reportedReviewsList, setReportedReviewsList] = useState([])
    const [reportedCommentsList, setReportedCommentsList] = useState([])
    const [currentReviewsReportsPage, setCurrentReviewsReportsPage] = useState(1)
    const [currentCommentsReportsPage, setCurrentCommentsReportsPage] = useState(1)

    const [filterReason, setFilterReason] = useState('')
    const [reportType, setReportType] = useState('reviews')
    const [page, setPage] = useState(1)

    const [tabKey, initTabKey] = useState('one')

    const { search } = useLocation();

    useEffect(() => {
        const queryParams = new URLSearchParams(search);
        const newFilterReason = queryParams.get('filterReason')
        const newReportType = queryParams.get('reportType')
        const newPage = queryParams.get('page')
        if (newFilterReason !== filterReason && newFilterReason !== null ) {
            setFilterReason(newFilterReason);
        }
        if (newReportType !== reportType && newReportType !== null){
            setReportType(newReportType)
        }
        if ( parseInt(newPage) !== page && newPage !== null){
            setPage(parseInt(newPage))
        }
    }, [search]);

    const handleOnClickFilter = (value) => {
        setFilterReason(value)
    }


    useEffect(() => {
        if(isLogged() && user.role === 'admin') {
            setCurrentCommentsReportsPage(1)
            setCurrentReviewsReportsPage(1)
            reportsService.getReportsByType('reviews', currentReviewsReportsPage, filterReason)
                .then(data => {
                    if(!data.error) {
                        setReportedReviewsList(data.data)
                    } else {
                        navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                    }
                })
                .catch(() => {
                    navigate("/error", { replace: true, state: {errorCode: 404} })
                })

            reportsService.getReportsByType('comments', currentCommentsReportsPage, filterReason)
                .then(data => {
                    if(!data.error) {
                        setReportedCommentsList(data.data)
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

    }, [filterReason])



    useEffect(() => {
        if(isLogged() && user.role === 'admin') {
            reportsService.getReportsByType('reviews', currentReviewsReportsPage)
                .then(data => {
                    if(!data.error) {
                        setReportedReviewsList(data.data)
                    } else {
                        navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                    }
                })
                .catch(() => {
                    navigate("/error", { replace: true, state: {errorCode: 404} })
                })

            reportsService.getReportsByType('comments', currentCommentsReportsPage)
                .then(data => {
                    if(!data.error) {
                        setReportedCommentsList(data.data)
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

    }, [])



    return(
        <>
            <Header type="all" admin={user?.role === 'admin'} userName={user?.username} userId={user?.id}/>

            <div className="row px-4">
                <div className="W-report-general-div-display">
                    <div className="bg-white shadow rounded overflow-hidden W-reported-list-general-div">
                        <div className="W-reported-background-color">
                            <div>
                                <h2 className="W-watch-viewed-list-title">{t('Report.ReportedContent')}</h2>
                            </div>
                        </div>
                    </div>

                    <div className="bg-light p-4 text-center W-report-header-div">
                        <div>
                            <ul className="list-inline mb-0">
                                <li className="list-inline-item">
                                    <h4 className="font-weight-bold mb-0 d-block">{reportedReviewsList.length}</h4>
                                    {reportedReviewsList.length === 1 ? (
                                        <span className="text-muted">
                                            <i className="fas fa-image mr-1"></i><span>{t('Profile.Review')}</span>
                                        </span>
                                    ) : (
                                        <span className="text-muted">
                                            <i className="fas fa-image mr-1"></i>
                                            <span>{t('Profile.Reviews')}</span>
                                        </span>
                                    )}
                                </li>

                                <li className="list-inline-item">
                                    <h4 className="font-weight-bold mb-0 d-block">{reportedCommentsList.length}</h4>
                                    {reportedCommentsList.length === 1 ? (
                                        <span className="text-muted">
                                            <i className="fas fa-image mr-1"></i>
                                            <span>{t('Comment.Title2')}</span>
                                        </span>
                                    ) : (
                                        <span className="text-muted">
                                            <i className="fas fa-image mr-1"></i>
                                            <span>{t('Comments.Title')}</span>
                                        </span>
                                    )}
                                </li>
                            </ul>
                        </div>

                        <div className="W-review-comment-tabs">
                            <div className="list-group">
                                <div className="dropdown W-dropdown-button">
                                    {filterReason !== 'ANY' && filterReason !== '' ? (
                                        <button id="reportSortByGroup" type="button" className="W-filter-title W-margin-top-report btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                            {filterReason}
                                        </button>
                                    ) : (
                                        <button id="reportSortByGroup" type="button" className="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                            {t('Report.Filter')}
                                        </button>
                                    )}
                                    <ul className="dropdown-menu">
                                        <li>
                                            <a className="dropdown-item" onClick={() => handleOnClickFilter("")}>
                                                {t('Duration.Clear')}
                                            </a>
                                        </li>
                                        <li>
                                            <a className="dropdown-item" onClick={() => handleOnClickFilter("Spam")}>
                                                {t('Report.Spam')}
                                            </a>
                                        </li>
                                        <li>
                                            <a className="dropdown-item" onClick={() => handleOnClickFilter("Insult")}>
                                                {t('Report.Insult')}
                                            </a>
                                        </li>
                                        <li>
                                            <a className="dropdown-item" onClick={() => handleOnClickFilter("Inappropriate")}>
                                                {t('Report.Inappropriate')}
                                            </a>
                                        </li>
                                        <li>
                                            <a className="dropdown-item" onClick={() => handleOnClickFilter("Unrelated")}>
                                                {t('Report.Unrelated')}
                                            </a>
                                        </li>
                                        <li>
                                            <a className="dropdown-item" onClick={() => handleOnClickFilter("Other")}>
                                                {t('Report.Other')}
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className='W-reported-general-div-list'>
                    <Tabs activeKey={tabKey} onSelect={(e) => initTabKey(e)}>
                        <Tab className='W-reported-div-list' eventKey="one" title={t('Content.Review')} >
                            {reportedReviewsList.length === 0 ? (
                                <div className="W-watchlist-div-info-empty">
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-x-fill W-watchlist-empty-icon" viewBox="0 0 16 16">
                                        <path d="m10.79 12.912-1.614-1.615a3.5 3.5 0 0 1-4.474-4.474l-2.06-2.06C.938 6.278 0 8 0 8s3 5.5 8 5.5a7.029 7.029 0 0 0 2.79-.588zM5.21 3.088A7.028 7.028 0 0 1 8 2.5c5 0 8 5.5 8 5.5s-.939 1.721-2.641 3.238l-2.062-2.062a3.5 3.5 0 0 0-4.474-4.474L5.21 3.089z"/>
                                        <path d="M5.525 7.646a2.5 2.5 0 0 0 2.829 2.829l-2.83-2.829zm4.95.708-2.829-2.83a2.5 2.5 0 0 1 2.829 2.829zm3.171 6-12-12 .708-.708 12 12-.708.708z"/>
                                    </svg>
                                    <div>
                                        <p>{t('Report.Review.Empty')}</p>
                                    </div>
                                </div>
                            ) : (
                                <>
                                    {reportedReviewsList.map((content) => (
                                            <ReportedContent
                                                key={`reportedContent${content.id}`}
                                                userName={content.review.user.username}
                                                userId={content.review.user.id}
                                                contentId={content.review.content.id}
                                                contentName={content.review.content.name}
                                                contentType={content.review.content.type}
                                                reportTitle={content.review.name}
                                                reportDescription={content.review.description}
                                                reportReasons={content.review.reportReasons}
                                                reportsAmount={content.review.reportAmount}
                                                typeId={content.review.id}
                                                reviewCreatorId={content.review.user.id}
                                                reviewNameOfReportedComment={content.review.name}
                                                reportType="reviews"
                                            />
                                        )
                                    )}
                                </>
                            )}
                        </Tab>
                        <Tab eventKey="two" title={t('Comments.Title')}>
                            {reportedCommentsList.length === 0 ? (
                                <div className="W-watchlist-div-info-empty">
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-x-fill W-watchlist-empty-icon" viewBox="0 0 16 16">
                                        <path d="m10.79 12.912-1.614-1.615a3.5 3.5 0 0 1-4.474-4.474l-2.06-2.06C.938 6.278 0 8 0 8s3 5.5 8 5.5a7.029 7.029 0 0 0 2.79-.588zM5.21 3.088A7.028 7.028 0 0 1 8 2.5c5 0 8 5.5 8 5.5s-.939 1.721-2.641 3.238l-2.062-2.062a3.5 3.5 0 0 0-4.474-4.474L5.21 3.089z"/>
                                        <path d="M5.525 7.646a2.5 2.5 0 0 0 2.829 2.829l-2.83-2.829zm4.95.708-2.829-2.83a2.5 2.5 0 0 1 2.829 2.829zm3.171 6-12-12 .708-.708 12 12-.708.708z"/>
                                    </svg>
                                    <div>
                                        <p>{t('Report.Comment.Empty')}</p>
                                    </div>
                                </div>
                            ) : (
                                <>
                                    {reportedCommentsList.map((content) => (
                                            <ReportedContent
                                                userName={content.comment.user.username}
                                                userId={content.comment.user.id}
                                                contentId={content.comment.review.content.id}
                                                contentName={content.comment.review.content.name}
                                                contentType={content.comment.review.content.type}
                                                reportTitle={content.comment.review.name}
                                                reportDescription={content.comment.text}
                                                reportReasons={content.comment.reportReasons}
                                                reportsAmount={content.comment.reportAmount}
                                                typeId={content.comment.commentId}
                                                reviewCreatorUserName={content.comment.review.user.userName}
                                                reviewCreatorId={content.comment.review.user.id}
                                                reviewNameOfReportedComment={content.comment.review.name}
                                                reportType="comments"
                                            />
                                        )
                                    )}
                                </>
                            )}
                        </Tab>
                    </Tabs>
                </div>
            </div>
        </>

        // {/TODO PAGINACION/}
    )
}