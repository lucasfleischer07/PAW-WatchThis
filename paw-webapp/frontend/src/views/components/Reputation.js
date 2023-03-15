import React from "react";
import marked from 'marked';

function ReviewButtons({ loggedUserName, isLikeReviews, reviewId, reviewReputation, isDislikeReviews }) {
    useEffect(() => {
        const reviewDescription = document.getElementById(`reviewDescription${reviewId}`);
        if (reviewDescription) {
            reviewDescription.innerHTML = marked.parse(reviewDescription.innerHTML);
        }
    }, [reviewId]);

    return (
        <div>
            <div className="W-movie-description-and-thumb-buttons">
                <div className="W-column-display-thumbs">
                    <div className="W-thumbs-buttons-and-text">
                        {loggedUserName !== "null" ? (
                            <>
                                {isLikeReviews ? (
                                    <a
                                        className="W-thumb-up W-bottom-buttons-size"
                                        href={`/reviewReputation/thumbUp/${reviewId}`}
                                    >
                                        hola1
                                    </a>
                                ) : (
                                    <a
                                        className="W-thumb-up W-bottom-buttons-size"
                                        href={`/reviewReputation/thumbUp/${reviewId}`}
                                    >
                                        hola2
                                    </a>
                                )}
                                <div>
                                    <p className="W-thumbsButton-orientation">{reviewReputation}</p>
                                </div>
                                {isDislikeReviews ? (
                                    <a
                                        className="W-thumb-down W-bottom-buttons-size"
                                        href={`/reviewReputation/thumbDown/${reviewId}`}
                                    >
                                        hola3
                                    </a>
                                ) : (
                                    <a
                                        className="W-thumb-down W-bottom-buttons-size"
                                        href={`/reviewReputation/thumbDown/${reviewId}`}
                                    >
                                        hola4
                                    </a>
                                )}
                            </>
                        ) : (
                            <>
                                <a
                                    className="W-thumb-up W-bottom-buttons-size"
                                    href="#"
                                    data-bs-toggle="modal"
                                    data-bs-target="#thumbsModal"
                                >
                                    hola6
                                </a>
                                <div>
                                    <p className="W-thumbsButton-orientation">{reviewReputation}</p>
                                </div>
                                <a
                                    href="#"
                                    data-bs-toggle="modal"
                                    data-bs-target="#thumbsModal"
                                    className="W-thumb-down W-bottom-buttons-size"
                                >
                                    hola7
                                </a>
                                <div
                                    className="modal fade"
                                    id="thumbsModal"
                                    tabIndex="-1"
                                    aria-labelledby="thumbsModalLabel"
                                    aria-hidden="true"
                                >
                                    <div className="modal-dialog">
                                        <div className="modal-content">
                                            <div className="modal-header">
                                                <h5 className="modal-title" id="thumbsModalLabel">
                                                    Reputation Title
                                                </h5>
                                                <button
                                                    type="button"
                                                    className="btn-close"
                                                    data-bs-dismiss="modal"
                                                    aria-label="Close"
                                                ></button>
                                            </div>
                                            <div className="modal-body">
                                                <span>Reputation Warning Add</span>
                                                <span>Review Warning Add Message</span>
                                            </div>
                                            <div className="modal-footer">
                                                <button
                                                    type="button"
                                                    className="btn btn-secondary"
                                                    data-bs-dismiss="modal"
                                                >
                                                    Close
                                                </button>
                                                <a href="/login/sign-in">
                                                    <button
                                                        type="button"
                                                        className="btn btn-success"
                                                        onClick={(event) => {
                                                            event.target.form.submit();
                                                            event.target.classList += " spinner-border";
                                                            event.target.innerText = "|";
                                                        }}
                                                    >
                                                        Login Login Message
                                                    </button>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </>
                        )}
                    </div>
                </div>
                <div className="card W-card-width">
                    <div className="card-body W-reviewText">
                        <p id={`reviewDescription${reviewId}`}>{commentText}</p>
                    </div>
                </div>
            </div>
            <div className="W-comment-section-general-div">
                {requestScope.comments.map((comment) => (
                    <Comments
                        comment={comment}
                        loggedUserName={param.loggedUserName}
                        reviewId={param.reviewId}
                        isAdmin={param.isAdmin}
                        alreadyReport={comment.reporterUsernames.contains(param.loggedUserName)}
                    />
                ))}
            </div>
        </div>

    );
}

export default ReviewButtons;