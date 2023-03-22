import React, {useContext, useEffect, useState} from "react";
import ContentCard from "./components/ContentCard";
import {useTranslation} from "react-i18next";
import {AuthContext} from "../context/AuthContext";
import {listsService} from "../services";
import {Link} from "react-router-dom";

export default function WatchListPage(props) {

    const {t} = useTranslation()
    let {isLogged} = useContext(AuthContext)
    const [user, setUser] = useState(localStorage.hasOwnProperty("user")? JSON.parse(localStorage.getItem("user")) : null)
    const [currentPage, setCurrentPage] = useState(1)
    const [totalPages, setTotalPages] = useState(undefined)
    const [watchList, setWatchList] = useState([])

    const getUserWatchList = () => {
        if(isLogged) {
            listsService.getUserWatchList(user.id, currentPage)
                .then(watchList => {
                    if(!watchList.error) {
                        setWatchList(watchList.data)
                        setTotalPages(watchList.totalPages)
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
        getUserWatchList()
    }, [])

    useEffect(() => {
        getUserWatchList()
    }, [watchList, currentPage])

    return (
        // TODO: METER EL HEADER y LA PAGINATION

        <div className="row px-4">
            <div className="W-profile-general-div-display">
                <div className="bg-white shadow rounded overflow-hidden W-viewed-watch-list-general-div">
                    <div className="W-profile-background-color">
                        <div>
                            <h2 className="W-watch-viewed-list-title">{t('WatchList.Your')}</h2>
                        </div>
                    </div>
                    <div className="bg-light p-4 d-flex text-center">
                        <h4>{t('WatchList.Titles', {titlesAmount: watchList.length})}</h4>
                    </div>

                    {watchList.length === 0 ? (
                        <div className="W-watchlist-div-info-empty">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-x-fill W-watchlist-empty-icon" viewBox="0 0 16 16">
                                <path fillRule="evenodd" d="M2 15.5V2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.74.439L8 13.069l-5.26 2.87A.5.5 0 0 1 2 15.5zM6.854 5.146a.5.5 0 1 0-.708.708L7.293 7 6.146 8.146a.5.5 0 1 0 .708.708L8 7.707l1.146 1.147a.5.5 0 1 0 .708-.708L8.707 7l1.147-1.146a.5.5 0 0 0-.708-.708L8 6.293 6.854 5.146z"/>
                            </svg>
                            <div>
                                <p>{t('WatchList.Empty')}</p>
                            </div>
                            <div>
                                <p>{t('WatchList.Empty2')}</p>
                            </div>
                            <div>
                                <Link to="/">{t('WatchList.Recomendation')}</Link>
                            </div>
                        </div>
                    ) : (
                        <div className="W-films-div">
                            <div className="row row-cols-1 row-cols-md-2 g-2 W-content-alignment">
                                {watchList.map((content) => (
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
                                        isInWatchList={watchList.length > 0 ? watchList.some(item => item.id === content.id) : false}
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