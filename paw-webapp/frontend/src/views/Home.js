import CarrouselContent from "./components/CarrouselContent";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import {AuthContext} from "../context/AuthContext";
import {contentService, listsService} from "../services";

export default function Home(props) {
    const {t} = useTranslation()
    let navigate = useNavigate()
    let {isLogged} = useContext(AuthContext)
    // const [user, setUser] = useState(undefined)
    const [bestRatedList, setBestRatedList] = useState(undefined)
    const [lastAddedList, setLastAddedList] = useState(undefined)
    const [mostSavedContentByUsersList, setMostSavedContentByUsersList] = useState(undefined)
    const [recommendedUserList, setRecommendedUserList] = useState(undefined)

    // const [userRecommendedList, setUserRecommendedList] = useState(undefined)

    useEffect(() => {
        contentService.getLandingPage()
            .then(data => {
                if(!data.error) {
                    setBestRatedList(data.data.arrays[0])
                    setLastAddedList(data.data.arrays[1])
                    if(data.data.hasOwnProperty("recommendedUserList")) {
                        setRecommendedUserList(data.data.arrays[2])
                    } else {
                        setMostSavedContentByUsersList(data.data.arrays[2])
                    }
                } else {
                //     TODO: Meter un toast de error
                }
            })
            .catch(() => {
                //     TODO: Meter un toast o algo asi
            })
    }, [bestRatedList, lastAddedList, mostSavedContentByUsersList, recommendedUserList])

    let user = useState(undefined)

    useEffect(() => {
        // setUser(localStorage.getItem("user") ? localStorage.getItem("user") : undefined)
        user = localStorage.getItem("user") ? localStorage.getItem("user") : undefined
    }, [])



    return(
        <div className="W-carousels-div">
            {isLogged && userWatchListContentId != null && recommendedUserList != null ? (
                <>
                    <h3 className="W-carousel-title"><spring:message code="Content.Carousel.RecommendedForYou" /></h3>
                    <div id="carouselRecommended" className="carousel slide" data-ride="carousel">
                        <div className="carousel-inner">
                            {[...Array(Math.ceil(recommendedUserListSize / 5)).keys()]
                                .map((externalIndex) => (
                                    <div className={`carousel-item ${externalIndex === 0 ? "active" : ""}`} key={externalIndex}>
                                        <div className="cards-wrapper">
                                            {[...Array(Math.min(4, recommendedUserListSize - externalIndex * 5)).keys()]
                                                .map((internalIndex) => (
                                                    <CarrouselContent
                                                        contentName={recommendedUserList[internalIndex + externalIndex * 5].name}
                                                        contentReleased={recommendedUserList[internalIndex + externalIndex * 5].released}
                                                        contentCreator={recommendedUserList[internalIndex + externalIndex * 5].creator}
                                                        contentGenre={recommendedUserList[internalIndex + externalIndex * 5].genre}
                                                        contentImage={recommendedUserList[internalIndex + externalIndex * 5].image}
                                                        contentId={recommendedUserList[internalIndex + externalIndex * 5].id}
                                                        contentType={recommendedUserList[internalIndex + externalIndex * 5].type}
                                                        contentRating={recommendedUserList[internalIndex + externalIndex * 5].rating}
                                                        reviewsAmount={recommendedUserList[internalIndex + externalIndex * 5].contentReviews.size()}
                                                        userName={userName}
                                                        userWatchListContentId1={userWatchListContentId.includes(recommendedUserList[internalIndex + externalIndex * 5].id)}
                                                        key={internalIndex}
                                                    />
                                                )
                                            )}
                                        </div>
                                    </div>
                                )
                            )}
                        </div>
                        <button className="carousel-control-prev" type="button" data-bs-target="#carouselRecommended" data-bs-slide="prev">
                            <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span className="visually-hidden"><spring:message code="Content.Carousel.PreviousButton" /></span>
                        </button>
                        <button className="carousel-control-next" type="button" data-bs-target="#carouselRecommended" data-bs-slide="next">
                            <span className="carousel-control-next-icon" aria-hidden="true"></span>
                            <span className="visually-hidden"><spring:message code="Content.Carousel.NextButton" /></span>
                        </button>
                    </div>
                </>
            ) : (
                <div>
                    <h3 className="W-carousel-title">Content.Carousel.MostSaved</h3>
                    <div id="mostSavedContentByUsersList" className="carousel slide" data-ride="carousel">
                        <div className="carousel-inner">
                            {[...Array(Math.ceil(mostSavedContentByUsersListSize / 5)).keys()].map((index) => (
                                <div key={index} className={`carousel-item${index === 0 ? " active" : ""}`}>
                                    <div className="cards-wrapper">
                                        {mostSavedContentByUsersList.slice(index * 5, index * 5 + 5)
                                            .map((content, internalIndex) => (
                                                <CarrouselContent
                                                    key={internalIndex}
                                                    contentName={content.name}
                                                    contentReleased={content.released}
                                                    contentCreator={content.creator}
                                                    contentGenre={content.genre}
                                                    contentImage={content.image}
                                                    contentId={content.id}
                                                    contentType={content.type}
                                                    contentRating={content.rating}
                                                    reviewsAmount={content.reviewsAmount}
                                                    userName={props.userName}
                                                    userWatchListContentId1={props.userWatchListContentId.includes(content.id)}
                                                />
                                        ))}
                                    </div>
                                </div>
                            ))}
                        </div>
                        <button className="carousel-control-prev" type="button" data-bs-target="#mostSavedContentByUsersList" data-bs-slide="prev">
                            <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span className="visually-hidden">Content.Carousel.PreviousButton</span>
                        </button>
                        <button className="carousel-control-next" type="button" data-bs-target="#mostSavedContentByUsersList" data-bs-slide="next">
                            <span className="carousel-control-next-icon" aria-hidden="true"></span>
                            <span className="visually-hidden">Content.Carousel.NextButton</span>
                        </button>
                    </div>
                </div>
            )}





        </div>
    )
}