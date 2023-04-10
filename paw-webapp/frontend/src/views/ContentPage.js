import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {contentService, listsService} from "../services";
import ContentCard from "./components/ContentCard";
import {Link, useParams} from "react-router-dom";
import Header from "./components/Header";

// TODO: Desde el HEADER, le tengo que pasar el tipo ('movie' o 'serie') que se lo paso por URL,
//  El tema es como le paso la query (lo que busco en la search bar), el genero y el durationFrom por parametro ya que uso Link
export default function ContentPage(props) {
    const {t} = useTranslation()
    const { contentType } = useParams();

    const [allContent, setAllContent] = useState([])
    const [actualPage, setActualPage] = useState(1)
    const [totalPages, setTotalPages] = useState(undefined)
    const [userWatchListIds, setUserWatchListIds] = useState([])
    const [user, setUser] = useState(localStorage.hasOwnProperty("user")? JSON.parse(localStorage.getItem("user")) : null)

    // TODO: Esto esta asi porque necesito pasarselo por parametro, pero mientras para probar
    // const query = "props.query"
    // const genre = "props.genre"
    // const durationFrom = "props.durationFrom"
    const query = null
    const genre = null
    const durationFrom = null

    useEffect(() => {
        if(user === undefined) {
            setUser(JSON.parse(localStorage.getItem("user")))
        }
    }, [user])


    useEffect(() => {
        contentService.getContentByType(contentType, actualPage)
            .then(data => {
                if(!data.error) {
                    setAllContent(data.data)
                    setTotalPages(data.totalPages)
                }
            })
            .catch(e => {
            })
    }, [actualPage, contentType])


    // TODO: Ver tema de useEffect de cuando tengo que ponerle nada y cunado tengo que ponerle dependencia.
    //  Si a este no le pongo nada, va bien pero si lo pongo deps, no me carga al entrar a la pagina
    useEffect(() => {
        user !== null?
            listsService.getUserWatchListContentIds(user.id)
                .then(watchList => {
                    if(!watchList.error) {
                        setUserWatchListIds(watchList.data)
                    }
                })
            : setUserWatchListIds(null)
    }, [user])


    return (
        <div>
            <Header type={contentType} admin={user?.role === 'admin'} userName={user?.username} userId={user?.id}/>

            {/*<Filter*/}
            {/*    query={query}*/}
            {/*    genre={genre}*/}
            {/*    durationFrom={durationFrom}*/}
            {/*    durationTo={durationTo}*/}
            {/*    type={contentType}*/}
            {/*    sorting={sorting}/>*/}


            {(props.query != null && props.query !== '' && props.query !== 'ANY' && allContent.length > 0) && (
                <h3 className="W-search-context-title">
                    <span>{t('Search.Title', {query: query})}</span>
                </h3>
            )}
            <div className="W-films-div">
                <div className="row row-cols-1 row-cols-md-2 g-2 W-content-alignment">
                    {allContent.map((content) => (
                        <ContentCard
                            key={content.id}
                            contentName={content.name}
                            contentReleased={content.releaseDate}
                            contentCreator={content.creator}
                            contentGenre={content.genre}
                            contentImage={content.contentPictureUrl}
                            contentId={content.id}
                            contentType={content.type}
                            contentRating={content.rating}
                            reviewsAmount={content.reviewsAmount}
                            user={user}
                            isInWatchList={userWatchListIds !== null ? userWatchListIds.some(item => item.id === content.id) : false}
                        />
                    ))}
                </div>
            </div>
            {(allContent.length === 0) && (
                <div className="card W-not-found-card">
                    <div className="card-body W-row-display">
                        <div className="W-search-notFound-image">
                            <img className="W-not-found" src={'./images/noResults.png'} alt="Not_Found_Ing"/>
                        </div>
                        <div className="W-search-notFound-text">
                            {((query == null || query === '' || query === 'ANY') && (genre != null || durationFrom !== 'ANY')) ? (
                                <p className="W-not-found-message">{t('Content.NoContent.Filters')}</p>
                            ) : (
                                <p className="W-not-found-message">
                                    <span>{t('Content.NoContent', {query: query})}</span>
                                </p>
                            )}
                            <div>
                                <h5 className="W-search-notFound-text2">
                                    <Link to="/">{t('WatchList.Recomendation')}</Link>
                                </h5>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}