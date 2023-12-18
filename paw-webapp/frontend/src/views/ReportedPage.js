import {useTranslation} from "react-i18next";
import ReportedContent from "./components/ReportedContent";
import {useLocation, useNavigate } from "react-router-dom";
import React, {useContext, useEffect, useState} from "react";
import {AuthContext} from "../context/AuthContext";
import {commentService, reportsService, reviewService} from "../services";

import { Tab, Tabs } from 'react-bootstrap';
import Header from "./components/Header";
import ExpiredCookieModal from "./components/ExpiredCookieModal";
import {updateUrlVariable, validateParam} from "../scripts/validateParam";
import {checkIsNumber, checkIsReason} from "../scripts/filtersValidations";

export default function ReportedPage() {
    const {t} = useTranslation()
    let navigate = useNavigate()
    let {isLogged} = useContext(AuthContext)
    const [showExpiredCookiesModal, setShowExpiredCookiesModal] = useState(false)

    const [user, setUser] = useState(localStorage.hasOwnProperty("user")? JSON.parse(localStorage.getItem("user")) : null)
    const [reportedReviewsList, setReportedReviewsList] = useState([])
    const [reportedCommentsList, setReportedCommentsList] = useState([])
    const [currentReviewsReportsPage, setCurrentReviewsReportsPage] = useState(1)
    const [currentCommentsReportsPage, setCurrentCommentsReportsPage] = useState(1)
    const [logOut, setLogOut] = useState(false)

    const [commentOrReviewDismissedOrDeleted, setCommentOrReviewDismissedOrDeleted] = useState(false)

    const [filterReason, setFilterReason] = useState('')
    const [page, setPage] = useState(1)
    const [amountPages, setAmountPages] = useState(1)
    const [amountReportedReviews, setAmountReportedReviews] = useState(0)
    const [amountReportedComments, setAmountReportedComments] = useState(0)

    const [tabKey, initTabKey] = useState('one')
    const [reportType, setReportType] = useState('reviews')

    const { search } = useLocation();
    const [paramsSetted, setParamsSetted] = useState(false)


    useEffect(() => {
        const queryParams = new URLSearchParams(search);
        updateUrlVariable(filterReason, checkIsReason(queryParams.get('reason')),(x) => setFilterReason(x))
        updateUrlVariable(page,checkIsNumber(queryParams.get('page')), (x) =>setPage(x))
        setParamsSetted(true)
    }, [search]);

    const handleOnClickFilter = (value) => {
        setFilterReason(value)
        changeUrlReason(value )
    }

    const handleOnClickFilterClear = () => {
        setFilterReason("")
        clearUrlReason()
    }

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

    const changeUrlReason = (reason) => {
        const searchParams = new URLSearchParams(window.location.search);
        searchParams.set('reason', reason);
        const currentPath = window.location.pathname.substring('/paw-2022b-3'.length);
        navigate(currentPath + '?' + searchParams.toString());
    }

    const clearUrlReason = () => {
        const searchParams = new URLSearchParams(window.location.search);
        searchParams.delete('reason');
        const currentPath = window.location.pathname.substring('/paw-2022b-3'.length);
        navigate(currentPath + '?' + searchParams.toString());
    }

    const tabChange = (e) => {
        initTabKey(e)
        setReportType(e === 'one' ? "reviews" : "comments")
        setPage(1)
    }

    useEffect(() => {
        if(page < 1 || page > amountPages) {
            navigate("/error", {replace: true, state: {errorCode: 404}})
        } else {
            const queryParams = new URLSearchParams(search);
            const currentPage = checkIsNumber(queryParams.get('page'));

            if(currentPage !== page) {
                setPage(currentPage)
            } else {
                if(paramsSetted) {
                    if (isLogged() && user.role === 'admin') {
                        setCurrentCommentsReportsPage(1)
                        setCurrentReviewsReportsPage(1)
                        if (reportType === "reviews") {
                            reviewService.getReviewReports(filterReason, page)
                                .then(data => {
                                    if (!data.error) {
                                        setReportedReviewsList(data.data)
                                        setAmountPages(data.totalPages)
                                        setAmountReportedComments(data.totalCommentsReports)
                                        setAmountReportedReviews(data.totalReviewsReports)
                                    } else {
                                        if (data.errorCode === 404) {
                                            setShowExpiredCookiesModal(true)
                                        } else {
                                            navigate("/error", {replace: true, state: {errorCode: data.errorCode}})
                                        }
                                    }
                                })
                                .catch(() => {
                                    navigate("/error", {replace: true, state: {errorCode: 404}})
                                })

                        } else {
                            commentService.getCommentsReports(filterReason, page)
                                .then(data => {
                                    if (!data.error) {
                                        setReportedCommentsList(data.data)
                                        setAmountPages(data.totalPages)
                                        setAmountReportedComments(data.totalCommentsReports)
                                        setAmountReportedReviews(data.totalReviewsReports)
                                    } else {
                                        if (data.errorCode === 404) {
                                            setShowExpiredCookiesModal(true)
                                        } else {
                                            navigate("/error", {replace: true, state: {errorCode: data.errorCode}})
                                        }
                                    }
                                })
                                .catch(() => {
                                    navigate("/error", {replace: true, state: {errorCode: 404}})
                                })
                        }
                    } else {
                        navigate("/error", {replace: true, state: {errorCode: 401}})
                    }
                }
            }
        }

    }, [filterReason, commentOrReviewDismissedOrDeleted, reportType, paramsSetted, page])

    useEffect(() => {
        if(reportType === "reviews" || reportType === "comments") {
            changeUrlPage(1)
        }
    }, [reportType])


    useEffect(() => {
        document.title = t('Report.ReportedContent')
    })


    return(
        <>
            {showExpiredCookiesModal && (
                <ExpiredCookieModal/>
            )}

            <Header type="all"
                    admin={user?.role === 'admin'}
                    userName={user?.username}
                    userId={user?.id}
                    setUser={setUser}
                    setLogOut={setLogOut}
            />

            <div className="row px-4">
                <div className="W-report-general-div-display">
                    <div className="bg-white shadow rounded overflow-hidden W-reported-list-general-div">
                        <div className="W-reported-background-color">
                            <div>
                                <h2 className="W-watch-viewed-list-title">{t('Report.ReportedContent')}</h2>
                            </div>
                        </div>
                    </div>

                    <div className="bg-light p-1 text-center W-report-header-div">
                        <div className="W-report-header-qtty-margin">
                            <ul className="list-inline mb-0">
                                <li className="list-inline-item">
                                    <h4 className="font-weight-bold mb-0 d-block">{amountReportedReviews}</h4>
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
                                    <h4 className="font-weight-bold mb-0 d-block">{amountReportedComments}</h4>
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
                                            <a className="dropdown-item" onClick={() => handleOnClickFilterClear()}>
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
                    <Tabs activeKey={tabKey} onSelect={(e) =>  tabChange(e)}>
                        <Tab eventKey="one" title={t('Content.Review')} >
                            {reportedReviewsList.length > 0 ? (
                                <>
                                    {reportedReviewsList.map((reportReview) => (
                                            <ReportedContent
                                                key={`reportedContent${reportReview.id}`}
                                                reportReasons={reportReview.reportReason}
                                                reportsAmount={reportReview.reportAmount}
                                                contentUrl={reportReview.content}
                                                reviewUrl={reportReview.review}
                                                loggedUserId = {user.id}
                                                loggedUserName = {user.username}
                                                setCommentOrReviewDismissedOrDeleted={setCommentOrReviewDismissedOrDeleted}
                                                reportType="review"
                                            />
                                        )
                                    )}
                                </>
                            ) : (
                                <div className="W-watchlist-div-info-empty">
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-x-fill W-watchlist-empty-icon" viewBox="0 0 16 16">
                                        <path d="m10.79 12.912-1.614-1.615a3.5 3.5 0 0 1-4.474-4.474l-2.06-2.06C.938 6.278 0 8 0 8s3 5.5 8 5.5a7.029 7.029 0 0 0 2.79-.588zM5.21 3.088A7.028 7.028 0 0 1 8 2.5c5 0 8 5.5 8 5.5s-.939 1.721-2.641 3.238l-2.062-2.062a3.5 3.5 0 0 0-4.474-4.474L5.21 3.089z"/>
                                        <path d="M5.525 7.646a2.5 2.5 0 0 0 2.829 2.829l-2.83-2.829zm4.95.708-2.829-2.83a2.5 2.5 0 0 1 2.829 2.829zm3.171 6-12-12 .708-.708 12 12-.708.708z"/>
                                    </svg>
                                    <div>
                                        <p>{t('Report.Review.Empty')}</p>
                                    </div>
                                </div>
                            )}

                        </Tab>

                        <Tab eventKey="two" title={t('Comments.Title')}>
                            {reportedCommentsList.length > 0 ? (
                                <>
                                    {reportedCommentsList.map((reportComment) => (
                                        <ReportedContent
                                            reportReasons={reportComment.reportReason}
                                            reportsAmount={reportComment.reportAmount}
                                            commentUrl={reportComment.comment}
                                            contentUrl={reportComment.content}
                                            reviewUrl={reportComment.review}
                                            loggedUserId = {user.id}
                                            loggedUserName = {user.username}
                                            setCommentOrReviewDismissedOrDeleted={setCommentOrReviewDismissedOrDeleted}
                                            reportType="comment"
                                        />
                                    ))}

                                </>
                            ) : (
                                <div className="W-watchlist-div-info-empty">
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-x-fill W-watchlist-empty-icon" viewBox="0 0 16 16">
                                        <path d="m10.79 12.912-1.614-1.615a3.5 3.5 0 0 1-4.474-4.474l-2.06-2.06C.938 6.278 0 8 0 8s3 5.5 8 5.5a7.029 7.029 0 0 0 2.79-.588zM5.21 3.088A7.028 7.028 0 0 1 8 2.5c5 0 8 5.5 8 5.5s-.939 1.721-2.641 3.238l-2.062-2.062a3.5 3.5 0 0 0-4.474-4.474L5.21 3.089z"/>
                                        <path d="M5.525 7.646a2.5 2.5 0 0 0 2.829 2.829l-2.83-2.829zm4.95.708-2.829-2.83a2.5 2.5 0 0 1 2.829 2.829zm3.171 6-12-12 .708-.708 12 12-.708.708z"/>
                                    </svg>
                                    <div>
                                        <p>{t('Report.Comment.Empty')}</p>
                                    </div>
                                </div>
                            )}
                        </Tab>
                    </Tabs>
                </div>
            </div>

            {amountPages > 1 && (
                <div>
                    <ul className="pagination justify-content-center W-pagination">
                        {page > 1 ? (
                            <li key={"prev"}className="page-item">
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
        </>
    )
}