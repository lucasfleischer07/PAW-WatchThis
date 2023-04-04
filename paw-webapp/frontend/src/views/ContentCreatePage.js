import {useTranslation} from "react-i18next";
import {useContext, useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import {AuthContext} from "../context/AuthContext";
import {contentService} from "../services";
import SimpleMDE from "react-simplemde-editor";
import {dropDownStayGenreCreate} from "../scripts/dropDownBehaviour";
// import {dropDownStayGenreCreate} from "../scripts/dropDownBehaviour";


export default function ContentCreatePage() {
    const {t} = useTranslation()
    const { formType, contentId } = useParams();
    let navigate = useNavigate()
    let {isLogged} = useContext(AuthContext)
    const [user, setUser] = useState(localStorage.hasOwnProperty("user")? JSON.parse(localStorage.getItem("user")) : null)

    const [nameError, setNameError] = useState(false)
    const [descriptionError, setDescriptionError] = useState(false)
    const [releaseDateError, setReleaseDateError] = useState(false)
    const [genreError, setGenreError] = useState(false)
    const [creatorError, setCreatorError] = useState(false)
    const [durationError, setDurationError] = useState(false)
    const [typeError, setTypeError] = useState(false)
    const [imageError, setImageError] = useState(false)

    const [genreButtonLabel, setGenreButtonLabel] = useState("")

    const [contentForm, setContentForm] = useState({
        name: "",
        description: "",
        releaseDate: "",
        genre: "",
        creator: "",
        duration: 0,
        type: "movie",
        contentPicture: undefined
    })

    const validateName = () => {
        const regex = /([a-zA-Z0-9ñáéíóú!,.:;=+\-_()?<>$%&#@{}[\]|*"'~/`^\s]+)?/
        if(contentForm.name.length < 1 || contentForm.name.length > 55 || !regex.test(contentForm.name)) {
            setNameError(true)
            return false
        }
        setNameError(false)
        return true
    }

    const validateDescription = () => {
        const regex = /([a-zA-Z0-9ñáéíóú!,.:;=+\- _()?<>$%&#@{}[\]|*"'~/`^\s]+)?/
        if(contentForm.description.length < 20 || contentForm.description.length > 2000) {
            setDescriptionError(true)
            return false
        }
        setDescriptionError(false)
        return regex.test(contentForm.description)
    }

    const validateReleaseDate = () => {
        const regex = /(19[0-9][0-9]|20[01][0-9]|202[0-2])?/
        if(contentForm.releaseDate.length !== 4 || !regex.test(contentForm.releaseDate)) {
            setReleaseDateError(true)
            return false
        }
        setReleaseDateError(false)
        return true
    }

    const validateGenre = () => {
        if(contentForm.genre.length <= 0) {
            setGenreError(true)
            return false
        }
        setGenreError(false)
        return true
    }

    const validateCreator = () => {
        const regex = /(19[0-9][0-9]|20[01][0-9]|202[0-2])?/
        if(contentForm.creator.length < 4 || contentForm.creator.length > 100 || !regex.test(contentForm.creator)) {
            setCreatorError(true)
            return false
        }
        setCreatorError(false)
        return true
    }

    const validateDuration = () => {
        if(contentForm.duration < 20 || contentForm.duration > 300) {
            setDurationError(true)
            return false
        }
        setDurationError(false)
        return true
    }

    const validateType = () => {
        if(contentForm.type === undefined) {
            setTypeError(true)
            return false
        }
        setTypeError(false)
        return true
    }

    const validateImage = () => {
        if(contentForm.contentPicture === undefined) {
            setImageError(true)
            return false
        }
        setImageError(false)
        return true
    }


    const validateForm = () => {
        return (validateName() && validateDescription() && validateReleaseDate() && validateGenre() && validateCreator() && validateDuration() && validateType() && validateImage())
    }

    const validateFormEdition = () => {
        return (validateName() && validateDescription() && validateReleaseDate() && validateGenre() && validateCreator() && validateDuration() && validateType())
    }

    const handleSubmitForm = (e) => {
        e.preventDefault()

        if(formType === 'create') {
            if(!validateForm()) {
                return
            }
            contentService.createContent(contentForm, contentForm.contentPicture)
                .then(data => {
                    if(!data.error) {
                        navigate(`/content/${data.data.type}/${data.data.id}`, {replace:true})
                    } else {
                        //     TODO: Pagina de error
                    }
                })
                .catch(e => {
                    //     TODO: pagina de error
                })
        } else if(formType === 'edition'){
            if(!validateFormEdition()) {
                return
            }
            contentService.updateContent(contentId, contentForm)
                .then(data => {
                    if(!data.error) {
                        navigate(`/content/${contentForm.type}/${contentId}`, {replace:true})
                    } else {
                    }
                })
                .catch(e => {
                    //     TODO: HAcer algo
                })
        } else {
            //     TODO: Llevar a pagina de error
        }
    }


    const handleChange = (e) => {
        // TODO: Ver problema de como solucionar el tema de que no me accede a los parametros type ni checked cuando esta dentro del boton
        const {name, value, type, checked} = e.target
        // console.log("Type: " + type)
        if(type === "checkbox") {
            // console.log("Es un checkbox: " + checked)
            if(checked) {
                const aux = contentForm.genre
                contentForm.genre = aux + " " + value
                setContentForm({...contentForm, genre: contentForm.genre})
                setGenreButtonLabel(contentForm.genre)
            } else {
                const aux = contentForm.genre
                contentForm.genre = aux.replace(value, " ")
                setContentForm({...contentForm, genre: contentForm.genre})
                setGenreButtonLabel(contentForm.genre)
            }
        } else if (name === 'contentPicture') {
            const file = e.target.files[0];
            setContentForm({ ...contentForm, [name]: file });
        } else {
            setContentForm((prev) => {
                return {...prev, [name]: value}
            })
        }
    }

    useEffect(() => {
        if(isLogged() && user.role === 'admin') {
            if(formType === 'edition') {
                contentService.getSpecificContent(contentId)
                    .then((data) => {
                        if(!data.error) {
                            setContentForm({
                                name: data.data.name,
                                description: data.data.description,
                                releaseDate: data.data.releaseDate,
                                genre: data.data.genre,
                                creator: data.data.creator,
                                duration: data.data.durationNum,
                                type: data.data.type,
                                contentPicture: data.data.contentPictureUrl
                            })
                            setGenreButtonLabel(data.data.genre)
                        }
                    })
                    .catch(e => {
                        navigate("/login", {replace: true})
                    })
            } else if((formType !== 'edition') && (formType !== 'create')) {
                navigate("/login", {replace: true})
            }
        } else {
            navigate("/login", {replace: true})
        }
    }, [])

    useEffect(() => {
        document.title = t('CreateContent.Message')
    })

    return(
        <div>
            {formType === 'create' ? (
                <h3 className="W-creating-content-title">
                    {t('CreateContent.Title')}
                </h3>
            ) : (
                <h3 className="W-creating-content-title">
                    {t('EditContent.Title')}
                </h3>
            )}

            <form method="post" encType="multipart/form-data">
                <div className="W-general-div-review-info">
                    <div className="card W-review-card">
                        <div className="mb-3 W-input-label-review-info">
                            {nameError &&
                                <p style={{color: '#b21e26'}}>{t('Pattern.contentCreate.name')}</p>
                            }
                            <label htmlFor="name" className="form-label">
                                {t('CreateContent.ContentName')}
                                <span className="W-red-asterisco">{t('Asterisk')}</span>
                            </label>
                            <p className="W-review-registration-text">{t('CreateContent.CharacterLimits', {min: 1, max: 55})}</p>
                            <input type="text" className="form-control" name="name" placeholder={t('Placeholder.FilmEx')} value={contentForm.name} onChange={handleChange} />
                        </div>

                        <div className="mb-3 W-input-label-review-info">
                            {descriptionError &&
                                <p style={{color: '#b21e26'}}>{t('Pattern.contentCreate.description')}</p>
                            }
                            <label className="form-label">
                                {t('CreateContent.ContentDescription')}
                                <span className="W-red-asterisco">{t('Asterisk')}</span>
                            </label>
                            <p className="W-review-registration-text">{t('CreateContent.CharacterLimits', {min: 20, max: 2000})}</p>

                            <textarea className="form-control" name="description" id="description" cols="30" rows="10" onChange={handleChange} value={contentForm.description}/>

                            {/*<SimpleMDE*/}
                            {/*    id="description"*/}
                            {/*    className="form-control"*/}
                            {/*    name="description"*/}
                            {/*    options={{*/}
                            {/*        showIcons: ["strikethrough"],*/}
                            {/*        hideIcons: ["link", "image","table","preview","fullscreen","guide","side-by-side","quote"]*/}
                            {/*    }}*/}
                            {/*    value={contentForm.description}*/}
                            {/*    onChange={handleChange}*/}
                            {/*    rows="3"*/}
                            {/*/>*/}
                        </div>

                        <div className="mb-3 W-input-label-review-info">
                            {releaseDateError &&
                                <p style={{color: '#b21e26'}}>{t('Pattern.contentCreate.releaseDate')}</p>
                            }
                            <label htmlFor="releaseDate" className="form-label">
                                {t('CreateContent.ContentReleaseDate')}
                                <span className="W-red-asterisco">{t('Asterisk')}</span>
                            </label>
                            <p className="W-review-registration-text">{t('CreateContent.YearCharacterLimits')}</p>
                            <input type="text" className="form-control" name="releaseDate" placeholder={t('Placeholder.YearEx')} value={contentForm.releaseDate} onChange={handleChange} />
                        </div>

                        <div className="mb-3 W-input-label-review-info">
                            {creatorError &&
                                <p style={{color: '#b21e26'}}>{t('Pattern.contentCreate.name')}</p>
                            }
                            <label htmlFor="creator" className="form-label">
                                {t('CreateContent.ContentCreator')}
                                <span className="W-red-asterisco">{t('Asterisk')}</span>
                            </label>
                            <p className="W-review-registration-text">{t('CreateContent.CharacterLimits', {min: 4, max: 100})}</p>
                            <input type="text" className="form-control" name="creator" placeholder={t('Placeholder.CreatorEx')} value={contentForm.creator} onChange={handleChange} />
                        </div>

                        <div className="mb-3 W-input-label-review-info">
                            {durationError &&
                                <p style={{color: '#b21e26'}}>{t('Min.contentCreate.duration')} - {t('Max.contentCreate.duration')} </p>
                            }
                            <label htmlFor="duration" className="form-label">
                                {t('CreateContent.ContentDuration')}
                                <span className="W-red-asterisco">{t('Asterisk')}</span>
                            </label>
                            <p className="W-review-registration-text">{t('CreateContent.CharacterLimits', {min: 20, max: 300})}</p>
                            <input type="number" className="form-control" name="duration" placeholder={t('Placeholder.DurationEx')} value={contentForm.duration} onChange={handleChange} />
                        </div>

                        <div className="mb-3 W-input-label-review-info">
                            {genreError &&
                                <p style={{color: '#b21e26'}}>{t('Error.Genre')}</p>
                            }
                            <label htmlFor="genre" className="form-label">
                                {t('CreateContent.ContentGenre')}
                                <span className="W-red-asterisco">{t('Asterisk')}</span>
                            </label>

                            <button id="createGenre" name="genre" type="button" className="W-genre-create-button btn dropdown-toggle" data-bs-toggle="dropdown"  datatype="button" aria-expanded="false">
                                {t('Genre.Message2')} {genreButtonLabel}
                            </button>
                            <ul className="dropdown-menu" id="drop3">
                                <li className="mb-1 px-2">
                                    <label>
                                        <input type="checkbox" checked={contentForm.genre.includes("Action")} className="px-2" name="genre" value="Action" onChange={handleChange}/>{" "}
                                        {t('Genre.Action')}
                                    </label>
                                </li>
                                <li className="mb-1 px-2">
                                    <label>
                                        <input type="checkbox" checked={contentForm.genre.includes("Sci-Fi")} className="px-2" name="genre" value="Sci-Fi" onChange={handleChange}/>{" "}
                                        {t('Genre.Science')}
                                    </label>
                                </li>
                                <li className="mb-1 px-2">
                                    <label>
                                        <input type="checkbox" checked={contentForm.genre.includes("Comedy")} className="px-2" name="genre" value="Comedy" onChange={handleChange}/>{" "}
                                        {t('Genre.Comedy')}
                                    </label>
                                </li>
                                <li className="mb-1 px-2">
                                    <label>
                                        <input type="checkbox" checked={contentForm.genre.includes("Adventure")} className="px-2" name="genre" value="Adventure" onChange={handleChange}/>{" "}
                                        {t('Genre.Adventure')}
                                    </label>
                                </li>
                                <li className="mb-1 px-2">
                                    <label>
                                        <input type="checkbox" checked={contentForm.genre.includes("Drama")} className="px-2" name="genre" value="Drama" onChange={handleChange}/>{" "}
                                        {t('Genre.Drama')}
                                    </label>
                                </li>
                                <li className="mb-1 px-2">
                                    <label>
                                        <input type="checkbox" checked={contentForm.genre.includes("Horror")} className="px-2" name="genre" value="Horror" onChange={handleChange}/>{" "}
                                        {t('Genre.Horror')}
                                    </label>
                                </li>
                                <li className="mb-1 px-2">
                                    <label>
                                        <input type="checkbox" checked={contentForm.genre.includes("Animation")} className="px-2" name="genre" value="Animation" onChange={handleChange}/>{" "}
                                        {t('Genre.Animation')}
                                    </label>
                                </li>
                                <li className="mb-1 px-2">
                                    <label>
                                        <input type="checkbox" checked={contentForm.genre.includes("Thriller")} className="px-2" name="genre" value="Thriller" onChange={handleChange}/>{" "}
                                        {t('Genre.Thriller')}
                                    </label>
                                </li>
                                <li className="mb-1 px-2">
                                    <label>
                                        <input type="checkbox" checked={contentForm.genre.includes("Mystery")} className="px-2" name="genre" value="Mystery" onChange={handleChange}/>{" "}
                                        {t('Genre.Mystery')}
                                    </label>
                                </li>
                                <li className="mb-1 px-2">
                                    <label>
                                        <input type="checkbox" checked={contentForm.genre.includes("Crime")} className="px-2" name="genre" value="Crime" onChange={handleChange}/>{" "}
                                        {t('Genre.Crime')}
                                    </label>
                                </li>
                                <li className="mb-1 px-2">
                                    <label>
                                        <input type="checkbox" checked={contentForm.genre.includes("Fantasy")} className="px-2" name="genre" value="Fantasy" onChange={handleChange}/>{" "}
                                        {t('Genre.Fantasy')}
                                    </label>
                                </li>
                                <li className="mb-1 px-2">
                                    <label>
                                        <input type="checkbox" checked={contentForm.genre.includes("Romance")} className="px-2" name="genre" value="Romance" onChange={handleChange}/>{" "}
                                        {t('Genre.Romance')}
                                    </label>
                                </li>
                            </ul>

                            <div className="mb-3 W-input-label-review-info-particular">
                                {typeError &&
                                    <p style={{color: '#b21e26'}}>{t('CreateContent.ContentType')}</p>
                                }
                                <label htmlFor="type" className="form-label">
                                    {t('CreateContent.ContentType')}
                                    <span className="W-red-asterisco">{t('Asterisk')}</span>
                                </label>
                                <select id="type" name="type" className="form-select" aria-label="Default select example" value={contentForm.type} onChange={handleChange}>
                                    <option value="movie">
                                        {t('CreateContent.ContentType.Movie')}
                                    </option>
                                    <option value="serie">
                                        {t('CreateContent.ContentType.Serie')}
                                    </option>
                                </select>
                            </div>

                            <div className="mb-3 W-input-label-review-info-particular">
                                {imageError &&
                                    <p style={{color: '#b21e26'}}>{t('ImageNotNull')}</p>
                                }
                                {formType === 'create' ?(
                                    <label className="form-label">
                                        <span className="W-red-asterisco">
                                            {t('Asterisk')}
                                        </span>
                                    </label>
                                ): (
                                    <></>
                                )}
                                <input type="file" accept="image/jpeg, image/jpg,  image/png" className="form-control W-input-width" name="contentPicture" onChange={handleChange}/>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="W-submit-cancel-buttons">
                    <Link to="/"><button type="button" className="btn btn-danger">{t('Form.Cancel')}</button></Link>
                    <button type="submit" className="btn btn-success" onClick={handleSubmitForm}>{t('Form.Submit')}</button>
                </div>
            </form>
        </div>
    )
}