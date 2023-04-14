import {useContext, useEffect, useState} from 'react';
import {useTranslation} from "react-i18next";
import {Link, useNavigate} from "react-router-dom";
import {AuthContext} from "../../context/AuthContext";
import {listsService} from "../../services";
import {toast} from "react-toastify";

export default function CarrouselContent(props) {
    const {t} = useTranslation()
    let navigate = useNavigate()
    let {isLogged} = useContext(AuthContext)

    const [isInWatchList, setIsInWatchList] = useState(props.isInWatchList);

    const handleAddToWatchlist = (event) => {
        event.preventDefault();
        listsService.addUserWatchList(props.id)
            .then(data => {
                if(!data.error) {
                    setIsInWatchList(true);
                    toast.success(t('WatchList.Added'))
                }
            })
            .catch(() => {
            //     TODO: Meter un toast o algo asi
            //     TODO Llegar a pagina de error
            })
    }

    const handleRemoveFromWatchlist = (event) => {
        event.preventDefault();
        listsService.deleteUserWatchList(props.id)
            .then(data => {
                if(!data.error) {
                    setIsInWatchList(false);
                    toast.success(t('WatchList.Removed'))
                }
            })
            .catch(() => {
                //     TODO: Meter un toast o algo asi
                //     TODO Llegar a pagina de error
             })
    }

    const handleLogin = (event) => {
        event.preventDefault();
        navigate('/login', {replace: true})
    }

    const watchListHandle = () => {
        listsService.getUserWatchListContentIds(props.user.id)
            .then(watchList => {
                if(!watchList.error) {
                    setIsInWatchList(watchList.data.some(item => item.id === props.id))
                }
            })
            .catch(e => {
                //     TODO: Meter algo
            })
    }

    useEffect(() => {
        if(isLogged()) {
            watchListHandle()
        }
    }, [isInWatchList])


    return (
        <div className="card-group W-card-text-carousel W-films-margin-carrousel">
            <div className="col">
                <div className="card W-films-card-body-carousel W-more-style">
                    <div className="W-img-watchList-button-div">
                        <div className="d-grid gap-2 W-watchList-button-div">
                            {isLogged() && !isInWatchList ? (
                                <form id={`form${props.id}`} onSubmit={handleAddToWatchlist}>
                                    <button id="buttonAddToWatchList" className="btn btn-secondary W-watchList-button" type="submit">
                                        <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                                            <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"/>
                                            <path d="M8 4a.5.5 0 0 1 .5.5V6H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V7H6a.5.5 0 0 1 0-1h1.5V4.5A.5.5 0 0 1 8 4z"/>
                                        </svg>
                                    </button>
                                </form>
                            ) : isLogged() && isInWatchList ? (
                                <form id={`form${props.id}`} onSubmit={handleRemoveFromWatchlist}>
                                    <button className="btn btn-secondary W-watchList-button" type="submit">
                                        <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                                            <path fillRule="evenodd" d="M2 15.5V2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.74.439L8 13.069l-5.26 2.87A.5.5 0 0 1 2 15.5zM6.854 5.146a.5.5 0 1 0-.708.708L7.293 7 6.146 8.146a.5.5 0 1 0 .708.708L8 7.707l1.146 1.147a.5.5 0 1 0 .708-.708L8.707 7l1.147-1.146a.5.5 0 0 0-.708-.708L8 6.293 6.854 5.146z"/>
                                        </svg>
                                    </button>
                                </form>
                            ) : (
                                <form id={`login${props.id}`} onSubmit={handleLogin}>
                                    <button className="btn btn-secondary W-watchList-button" type="submit">
                                        <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-bookmark-plus W-watchList-icon" viewBox="0 0 16 16">
                                            <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"/>
                                            <path d="M8 4a.5.5 0 0 1 .5.5V6H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V7H6a.5.5 0 0 1 0-1h1.5V4.5A.5.5 0 0 1 8 4z"/>
                                        </svg>
                                    </button>
                                </form>
                            )}
                        </div>
                        <Link className="card-img-top W-card-link-style" to={`/content/${props.type}/${props.id}`}>
                            {/* eslint-disable-next-line jsx-a11y/img-redundant-alt */}
                            <img className="card-img-top" src={props.image} alt={`Image ${props.name}`} />
                        </Link>
                    </div>

                    <div className="card-body W-films-card-body-div">
                        <Link className="W-card-link-style" to={`/content/${props.type}/${props.id}`}>
                            <div className="W-margin-one">
                                <h4 className="card-title W-movie-title-carousel W-long-words-contemplation-title">{props.name}</h4>
                                <div>
                                    <p className="card-text W-movie-description W-card-details-margin"><span className="W-span-text-info-card-movie W-card-details-color">{t('Content.Released')} </span>{props.releaseDate}</p>
                                    <div>
                                        <p className="card-text W-movie-description W-card-details-margin W-long-words-contemplation-genre"><span className="W-span-text-info-card-movie W-card-details-color">{t('Content.Genre')} </span>{props.genre}</p>
                                    </div>
                                    <div>
                                        <p className="card-text W-movie-description W-card-details-margin W-long-words-contemplation-creator"><span className="W-span-text-info-card-movie W-card-details-color">{t('Content.Creator')} </span> {props.creator}</p>
                                    </div>
                                </div>
                                {[...Array(5)].map((_, index) => {
                                    if (props.rating >= index + 1) {
                                        return <svg key={index} xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-star-fill W-stars-width" viewBox="0 0 16 16"><path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/></svg>;
                                    } else {
                                        return <svg key={index} xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-star W-stars-width" viewBox="0 0 16 16"><path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/></svg>;
                                    }
                                })}
                                <p className="card-text W-movie-description W-card-details-margin">{t('Content.ReviewAmount', {reviewsAmount: props.reviewsAmount})}</p>
                            </div>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    );
}