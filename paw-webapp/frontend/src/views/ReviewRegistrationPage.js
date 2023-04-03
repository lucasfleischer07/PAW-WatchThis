import {useTranslation} from "react-i18next";
import {Link, useLocation, useNavigate, useParams} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import {AuthContext} from "../context/AuthContext";
import {contentService, reviewService, userService} from "../services";
import {toast} from "react-toastify";
import SimpleMDE from 'react-simplemde-editor';
import 'easymde/dist/easymde.min.css';


export default function ReviewRegistrationPage() {
    const {t} = useTranslation()
    let location = useLocation()
    let navigate = useNavigate()
    let {isLogged} = useContext(AuthContext)
    let origin = location.state?.from?.pathname || "/";

    const { contentType, contentId } = useParams();
    const [user, setUser] = useState(localStorage.hasOwnProperty("user")? JSON.parse(localStorage.getItem("user")) : null)
    const [content, setContent] = useState({})
    const [nameError, setNameError] = useState(false)
    const [descriptionError, setDescriptionError] = useState(false)
    const [ratingError, setRatingError] = useState(false)

    const [reviewForm, setReviewForm] = useState({
        name: "",
        description: "",
        rating: 1,
        type: contentType
    });


    const validName = () => {
        const regex = /([a-zA-Z0-9ñáéíóú!,.:;=+\-_()?<>$%&#@{}[\]|*"'~/`^\s]+)?/
        if(reviewForm.name.length < 6 || reviewForm.name.length > 200 || !regex.test(reviewForm.name)) {
            setNameError(true)
            return false
        }
        setNameError(false)
        return true
    }

    const validDescription = () => {
        const regex = /([a-zA-Z0-9ñáéíóú!,.:;=+\- _()?<>$%&#@{}[\]|*"'~/`^\s]+)?/
        if(reviewForm.description.length < 20 || reviewForm.description.length > 2000 || !regex.test(reviewForm.description)) {
            setDescriptionError(true)
            return false
        }
        setDescriptionError(false)
        return true
    }

    const validRating = () => {
        if(reviewForm.rating <= 0 || reviewForm.rating > 5) {
            setRatingError(true)
            return false
        }
        setRatingError(false)
        return true
    }

    const validForm = () => {
        return (validName() && validDescription() && validRating());

    }

    const handleChange = (e) => {
        const {name, value} = e.target
        setReviewForm((prev) => {
            return {...prev, [name]: value}
        })
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        if(!validForm()) {
            return
        }

        reviewService.reviewsCreation(parseInt(contentId), contentType, reviewForm)
            .then(data => {
                if(!data.error) {
                    navigate(origin, {replace: true})
                } else {
                    toast.error(t('Content.Review.Create.Error'))
                }
            })
            .catch(e => {
            //     TODO: Llevar a la pagian de error
            })
    }



    useEffect(() => {
        if(isLogged() && user.role === 'admin') {
            contentService.getSpecificContent(parseInt(contentId))
                .then(data => {
                    if(!data.error) {
                        setContent(data.data)
                    } else {
                    //     TODO: Ver que hacer
                    }
                })
                .catch(e => {
                //     TODO: Ver que hacer
                })
        } else {
            //     TODO: Llevar a la pagian de error
        }
    }, [])


    return (

        <form method="post" onSubmit={handleSubmit}>
            <div className="W-general-div-review-info">
                <div className="W-review-registration-img-and-name">
                    <img className="W-review-registration-img" src={content.contentPictureUrl} alt={`Image ${content.name}`} />
                    <h4 className="W-review-registration-name">{content.name}</h4>
                </div>
                <div className="card W-review-card">
                    <div className="mb-3 W-input-label-review-info">
                        {nameError &&
                            <p style={{color: '#b21e26'}}>{t('Pattern.contentCreate.name')}</p>
                        }
                        {/*<p style={{ color: '#b21e26' }}>{nameErrors}</p>*/}
                        <label className="form-label">
                            {t('CreateReview.ReviewName')}<span className="W-red-asterisco">{t('Asterisk')}</span>
                        </label>
                        <p className="W-review-registration-text">
                            {t('CreateReview.CharacterLimits', {min: 6, max: 200})}
                        </p>
                        <input
                            type="text"
                            className="form-control"
                            name="name"
                            value={reviewForm.name}
                            onChange={handleChange}
                            placeholder={t('Review.Mine')}
                        />
                    </div>
                    <div className="mb-3 W-input-label-review-info">
                        {descriptionError &&
                            <p style={{color: '#b21e26'}}>{t('Pattern.contentCreate.description')}</p>
                        }
                        <label className="form-label">
                            {t('CreateReview.ReviewDescription')}<span className="W-red-asterisco">{t('Asterisk')}</span>
                        </label>
                        <p className="W-review-registration-text">
                            {t('CreateReview.CharacterLimits', {min: 20, max: 2000})}
                        </p>

                        <textarea className="form-control" name="description" id="description" cols="30" rows="10" onChange={handleChange} value={reviewForm.description}/>


                        {/*TODO: VER PORQUE ESTO NO ANDA*/}
                        {/*<SimpleMDE*/}
                        {/*    id="MyTextArea"*/}
                        {/*    className="form-control"*/}
                        {/*    name="description"*/}
                        {/*    options={{*/}
                        {/*        showIcons: ["strikethrough"],*/}
                        {/*        hideIcons: ["link", "image","table","preview","fullscreen","guide","side-by-side","quote"]*/}
                        {/*    }}*/}
                        {/*    value={reviewForm.description}*/}
                        {/*    onChange={handleChange}*/}
                        {/*    rows="3"*/}
                        {/*/>*/}

                    </div>
                    <div className="mb-3 W-input-label-review-info">
                        {ratingError &&
                            <p style={{color: '#b21e26'}}>Error</p>
                        }
                        <label className="form-label">
                            {t('CreateReview.ReviewRating')}<span className="W-red-asterisco">{t('Asterisk')}</span>
                        </label>
                        <select
                            className="form-select"
                            name="rating"
                            value={reviewForm.rating}
                            onChange={handleChange}
                            aria-label="Default select example">
                            <option value="1">
                                {t('Stars.quantity.1')}
                            </option>
                            <option value="2">
                                {t('Stars.quantity.2')}

                            </option>
                            <option value="3">
                                {t('Stars.quantity.3')}
                            </option>
                            <option value="4">
                                {t('Stars.quantity.4')}
                            </option>
                            <option value="5">
                                {t('Stars.quantity.5')}
                            </option>
                        </select>
                    </div>
                </div>
            </div>
            <div className="W-submit-cancel-buttons">
                <Link to={`/content/${content.type}/${content.id}`}>
                    <button type="button" className="btn btn-danger" id="cancelButton">{t('Form.Cancel')}</button>
                </Link>
                <button id="submitButton" type="submit" className="btn btn-success" onClick={handleSubmit}>{t('Form.Submit')}</button>
            </div>
        </form>

    )
}