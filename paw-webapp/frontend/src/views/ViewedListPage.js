import React, {useContext, useEffect, useState} from "react";
import ContentCard from "./components/ContentCard";
import {useTranslation} from "react-i18next";
import {AuthContext} from "../context/AuthContext";
import {listsService} from "../services";
import {Link} from "react-router-dom";

export default function ViewedListPage(props) {

    const {t} = useTranslation()
    let {isLogged} = useContext(AuthContext)
    const [user, setUser] = useState(localStorage.hasOwnProperty("user")? JSON.parse(localStorage.getItem("user")) : null)
    const [currentPage, setCurrentPage] = useState(1)
    const [totalPages, setTotalPages] = useState(undefined)
    const [viewedList, setViewedList] = useState([])

    const getUserViewedList = () => {
        if(isLogged) {
            listsService.getUserViewedList(user.id, currentPage)
                .then(watchList => {
                    if(!watchList.error) {
                        setViewedList(watchList.data)
                        setTotalPages(watchList.totalPages)
                    } else {
                        //     TODO: Meter toasts
                    }
                })
                .catch(e => {
                    //     TODO: Meter toasts
                })
        } else {
            //     TODO: Tirar a la pagina de error de que no tiene permisos
        }
    }

    useEffect(() => {
        getUserViewedList()
    }, [])

    useEffect(() => {
        getUserViewedList()
    }, [viewedList, currentPage])

    return (
        // TODO: METER EL HEADER y LA PAGINATION

        <div className="row px-4">
            <div className="W-profile-general-div-display">
                <div className="bg-white shadow rounded overflow-hidden W-viewed-watch-list-general-div">
                    <div className="W-profile-background-color">
                        <div>
                            <h2 className="W-watch-viewed-list-title">{t('ViewedList.Your')}</h2>
                        </div>
                    </div>
                    <div className="bg-light p-4 d-flex text-center">
                        <h4>
                            <h4>{t('WatchList.Titles', {titlesAmount: viewedList.length})}</h4>
                        </h4>
                    </div>
                    {viewedList.length === 0 ? (
                        <div className="W-watchlist-div-info-empty">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-x-fill W-watchlist-empty-icon" viewBox="0 0 16 16">
                                <path d="m10.79 12.912-1.614-1.615a3.5 3.5 0 0 1-4.474-4.474l-2.06-2.06C.938 6.278 0 8 0 8s3 5.5 8 5.5a7.029 7.029 0 0 0 2.79-.588zM5.21 3.088A7.028 7.028 0 0 1 8 2.5c5 0 8 5.5 8 5.5s-.939 1.721-2.641 3.238l-2.062-2.062a3.5 3.5 0 0 0-4.474-4.474L5.21 3.089z"/>
                                <path d="M5.525 7.646a2.5 2.5 0 0 0 2.829 2.829l-2.83-2.829zm4.95.708-2.829-2.83a2.5 2.5 0 0 1 2.829 2.829zm3.171 6-12-12 .708-.708 12 12-.708.708z"/>
                            </svg>
                            <div>
                                <p>{t('ViewedList.Empty')}</p>
                            </div>
                            <div>
                                <p>{t('ViewedList.Empty2')}</p>
                            </div>
                            <div>
                                <Link to="/">{t('WatchList.Recomendation')}</Link>
                            </div>
                        </div>
                    ) : (
                        <div className="W-films-div">
                            <div className="row row-cols-1 row-cols-md-2 g-2 W-content-alignment">
                                {viewedList.map((content) => (
                                    <ContentCard
                                        contentName={content.name}
                                        contentReleased={content.releaseDate}
                                        contentCreator={content.creator}
                                        contentGenre={content.genre}
                                        contentImage={content.contentPictureUrl}
                                        contentId={content.id}
                                        contentType={content.type}
                                        contentRating={content.rating}
                                        reviewsAmount={content.reviewsAmount}
                                        isInWatchList={viewedList.length > 0 ? viewedList.some(item => item.id === content.id) : false}
                                        key={content.id}
                                    />
                                ))}
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </div>
    )
}