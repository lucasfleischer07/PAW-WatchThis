// import {Link} from "react-router-dom";
// import {useTranslation} from "react-i18next";
// import { useState } from 'react';
//
// export default function Comments(param) {
//     const {t} = useTranslation()
//
//         const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
//         const [isReportModalOpen, setIsReportModalOpen] = useState(false);
//
//         const handleDelete = () => {
//             document.getElementById(`formDeleteComment${comment.commentId}`).submit();
//         };
//
//         const handleReport = () => {
//             const postPath = `/report/comment/${comment.commentId}`;
//             document.getElementById('reportCommentForm').setAttribute('action', postPath);
//             document.getElementById('reportCommentForm').submit();
//         };
//
//     return(
//         <div className="card W-comment-general-card">
//             <div className="card-body W-general-div-comment">
//                 <div className="W-img-comment-div-margin-right">
//                     {comment.user.image == null ? (
//                         <img src="/resources/img/defaultUserImg.png" alt="User_img" className="W-comment-profile-picture" />
//                     ) : (
//                         <img src={`/profile/${comment.user.userName}/profileImage`} alt="User_img" className="W-comment-profile-picture" />
//                     )}
//                 </div>
//                 <div className="W-comment-username-report-description-div ">
//                     <div className="W-comment-username-and-report">
//                         <div>
//                             <Link to="<c:url value="/profile/${comment.user.userName}"/>" className="W-creator-review">
//                                 <c:url value="${comment.user.userName}"/>
//                             </Link>
//                         </div>
//                         <div className="W-report-and-delete-comment-buttons">
//                             {param.loggedUserName !== 'null' ? (
//                                 <>
//                                     {comment.user.userName === param.loggedUserName || param.isAdmin === 'true' ? (
//                                         <>
//                                             <form className="W-delete-form" id={`formDeleteComment${comment.commentId}`} method="post" action={`/comment/${comment.commentId}/delete`}>
//                                                 <button className="btn btn-danger text-nowrap W-prueba" type="button" data-bs-toggle="modal" data-bs-target={`#deleteCommentModal${comment.commentId}`}>
//                                                     <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-trash3"  viewBox="0 0 16 16">
//                                                         <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
//                                                     </svg>
//                                                 </button>
//                                             </form>
//                                             <div className="modal fade" id={`deleteCommentModal${comment.commentId}`} tabIndex="-1" aria-labelledby={`modalLabelDeleteComment${comment.commentId}`} aria-hidden="true">
//                                                 <div className="modal-dialog">
//                                                     <div className="modal-content">
//                                                         <div className="modal-header">
//                                                             <h5 className="modal-title">{t('Delete.Confirmation')}</h5>
//                                                             <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
//                                                         </div>
//                                                         <div className="modal-body">
//                                                             <p>{t('Delete.Confirmation')}</p>
//                                                         </div>
//                                                         <div className="modal-footer">
//                                                             <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">{t('No')}</button>
//                                                             <button type="submit" form={`formDeleteComment${comment.commentId}`} className="btn btn-success" onClick={(event) => { event.target.classList.add('spinner-border'); event.target.innerText = '|'; }}>{t('Yes')}</button>
//                                                         </div>
//                                                     </div>
//                                                 </div>
//                                             </div>
//                                         </>
//                                     ) : (
//                                         <>
//                                             {comment.user.userName !== param.loggedUserName && !param.alreadyReport ? (
//                                                 <>
//                                                     {t('Report.Add')}
//                                                     <button id={`reportCommentButton${comment.commentId}`} type="button" className="btn btn-light W-background-color-report" data-bs-toggle="modal" data-bs-target={`#reportCommentModal${comment.commentId}`}>
//                                                         <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#b21e26" className="bi bi-exclamation-circle" viewBox="0 0 16 16">
//                                                             <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
//                                                             <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
//                                                         </svg>
//                                                     </button>
//                                                     <div className="modal fade" id={`reportCommentModal${comment.commentId}`} tabIndex="-1" aria-labelledby={`reportCommentModalLabel${comment.commentId}`} aria-hidden="true">
//                                                         <div className="modal-dialog">
//                                                             <form id={`reportCommentForm${comment.commentId}`} method="post" encType="multipart/form-data" action={postPath}>
//                                                                 <div className="modal-content">
//                                                                     <div className="modal-header">
//                                                                         <h5 className="modal-title" id={`reportCommentModalLabel${comment.commentId}`}>{t('Report.CommentTitle')}</h5>
//                                                                         <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
//                                                                     </div>
//                                                                     <div className="modal-body">
//                                                                         <div>
//                                                                             <ul className="W-no-bullets-list">
//                                                                                 <li>
//                                                                                     <label>
//                                                                                         <input type="radio" name={`reportType${comment.commentId}`} value="Spam"/> {t('Report.Spam')}
//                                                                                         <p className="W-modal-comment-desc">{t('Report.Spam.Description')}</p>
//                                                                                     </label>
//                                                                                 </li>
//                                                                                 <li>
//                                                                                     <label>
//                                                                                         <input type="radio" name={`reportType${comment.commentId}`} value="Insult"/> {t('Report.Insult')}
//                                                                                         <p className="W-modal-comment-desc">{t('Report.Insult.Description')}</p>
//                                                                                     </label>
//                                                                                 </li>
//                                                                                 <li>
//                                                                                 <label>
//                                                                                     <input type="radio" name="reportType" value="Inappropriate"/> Report.Inappropriate
//                                                                                     <p className="W-modal-comment-desc">Report.Insult.Description</p>
//                                                                                 </label>
//                                                                             </li>
//                                                                             <li>
//                                                                                 <label>
//                                                                                     <input type="radio" name="reportType" value="Unrelated"/> Report.Unrelated
//                                                                                     <p className="W-modal-comment-desc">Report.Unrelated.Description</p>
//                                                                                 </label>
//                                                                             </li>
//                                                                             <li>
//                                                                                 <label>
//                                                                                     <input type="radio" name="reportType" value="Other"/> {t('Report.Other')}
//                                                                                     <p className="W-modal-comment-desc">Report.Other.Description</p>
//                                                                                 </label>
//                                                                             </li>
//                                                                         </ul>
//                                                                     </div>
//                                                                 </div>
//                                                                     <div className="modal-footer">
//                                                                         <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">{t('Close')}</button>
//                                                                         <button type="submit" className="btn btn-success" onClick={(e) => {
//                                                                             e.target.className += ' spinner-border'
//                                                                             e.target.innerText = '|'
//                                                                         }}>{t('Form.Submit')}
//                                                                         </button>
//                                                                     </div>
//                                                                 </div>
//                                                             </form>
//                                                         </div>
//                                                     </div>
//                                                 </>
//                                             )}
//                                         </>
//                                     )}
//                                 </>
//                             ) : (
//                                 <>
//                                     <div>
//                                         <span title={<spring:message code="Report.Add" var="reportCommentAdd" />}>
//                                           <button id="reportCommentButtonNoLogin" type="button" className="btn btn-light" data-bs-toggle="modal" data-bs-target="#reportCommentModalNoLogin">
//                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#b21e26" className="bi bi-exclamation-circle" viewBox="0 0 16 16">
//                                                     <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
//                                                     <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
//                                                 </svg>
//                                           </button>
//                                         </span>
//                                     </div>
//                                     <div className="modal fade"id="reportCommentModalNoLogin"tabIndex="-1"aria-labelledby="reportCommentModalNoLoginLabel" aria-hidden="true">
//                                         <div className="modal-dialog">
//                                             <div className="modal-content">
//                                                 <div className="modal-header">
//                                                     <h5 className="modal-title"> {t('Report.CommentTitle')}</h5>
//                                                     <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close" ></button>
//                                                 </div>
//                                                 <div className="modal-body">
//                                                     {t('Report.CommentWarningAdd')}
//                                                     {t('Report.WarningAddMessage')}
//                                                 </div>
//                                                 <div className="modal-footer">
//                                                     <button type="button" className="btn btn-secondary" data-bs-dismiss="modal" onClick={props.onClose}>{t('Close')}</button>
//                                                     <a href="<c:url value="/login/sign-in"/>">
//                                                         <button type="button" className="btn btn-success" onClick={handleSubmit} disabled={isLoading}>
//                                                             {isLoading ? (
//                                                                 <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
//                                                             ) : null}
//                                                             {t('Login.LoginMessage')}
//                                                         </button>
//                                                     </a>
//                                                 </div>
//                                             </div>
//                                         </div>
//                                     </div>
//                                 </>
//                             )}
//             </div>
//         </div>
//
//
//
//
//
//
