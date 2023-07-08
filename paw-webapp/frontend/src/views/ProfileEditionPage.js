import {useTranslation} from "react-i18next";
import React, {useContext, useEffect, useState} from "react";
import {AuthContext} from "../context/AuthContext";
import {userService} from "../services";
import {useForm} from "react-hook-form";
import {toast} from "react-toastify";
import {useLocation, useNavigate} from "react-router-dom";
import Header from "./components/Header";
import ExpiredCookieModal from "./components/ExpiredCookieModal";


export default function ProfileEditionPage() {
    const {t} = useTranslation()
    let navigate = useNavigate()
    let location = useLocation()
    const {reset} = useForm()
    let {isLogged} = useContext(AuthContext)
    const [showExpiredCookiesModal, setShowExpiredCookiesModal] = useState(false)

    const [user, setUser] = useState(localStorage.hasOwnProperty("user")? JSON.parse(localStorage.getItem("user")) : null)
    const [error, setError] = useState(false)
    const [errorPassword, setErrorPassword] = useState(false)
    const [image, setImage] = useState(undefined)


    const [userForm, setUserForm] = useState({
        currentPassword: "",
        newPassword: "",
        confirmPassword: "",
    });


    const validatePasswords = () => {
        if(userForm.newPassword !== userForm.confirmPassword || (userForm.newPassword.length < 6 && userForm.newPassword.length > 0)) {
            setErrorPassword(true)
            return false
        } else {
            return true
        }
    }

    const onSubmitPassword = (e) => {
        // TODO: FAlta manda al back la actual y checkear de que sean iguales, no anda todavia
        if(isLogged()) {
            e.preventDefault();

            if(!validatePasswords()) {
                return
            }
            if(userForm.currentPassword.length !== 0 && userForm.newPassword.length !== 0 && userForm.confirmPassword.length !== 0) {
                userService.updateUserProfileInfo(user.id, userForm)
                    .then(data => {
                        if (!data.error) {
                            setErrorPassword(false)
                            reset()
                            toast.info(t('EditProfile.Upload.Password.Success'))
                            navigate(-1)
                        } else if (data.errorCode === 400) {
                            setErrorPassword(true)
                        } else {
                            if (data.errorCode === 404) {
                                setShowExpiredCookiesModal(true)
                            } else {
                                toast.error(t('EditProfile.Upload.Password.Error'))
                                navigate("/error", {replace: true, state: {errorCode: data.errorCode}})
                            }
                        }
                    })
                    .catch(() => {
                        navigate("/error", {replace: true, state: {errorCode: 404}})
                    })
            }
        } else {
            navigate("/error", { replace: true, state: {errorCode: 401} })
        }
    }

    const onSubmitImage = (e) => {
        e.preventDefault();
        if(isLogged() && image !== undefined) {
            userService.updateUserProfileImage(user.id, image)
                .then(data => {
                    if (!data.error) {
                        setError(false)
                        toast.success(t('EditProfile.Upload.Image'));
                        navigate(-1)
                    } else {
                        if(data.errorCode === 404) {
                            setShowExpiredCookiesModal(true)
                        } else {
                            setError(true)
                            toast.error(t('EditProfile.Upload.Image.Error'));
                            navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                        }
                    }
                })
                .catch(() => {
                    navigate("/error", { replace: true, state: {errorCode: 404} })
                })
        }

    }

    const handleChange = (e) => {
        const {name, value} = e.target
        setUserForm((prev) => {
            return {...prev, [name]: value}
        })
    }

    const handleImageChange = (e) => {
        const img = e.target
        setImage(img.files[0])
    }

    useEffect(() => {
        if(!isLogged()) {
            navigate("/login", {replace: true})
        }
    }, [])

    useEffect(() => {
        document.title = t('Profile')
    })


    return(
        <>
            {showExpiredCookiesModal && (
                <ExpiredCookieModal/>
            )}
            <Header type="all" admin={user?.role === 'admin'} userName={user?.username} userId={user?.id}/>
            
            <div className="row py-5 px-4 W-set-margins">
                <div className="col-md-5 mx-auto W-edit-profile-display">
                    <div className="bg-white shadow rounded overflow-hidden W-profile-general-div">
                        <div className="W-profile-background-color bg-dark">
                            <div>
                                <div className="profile mr-3">
                                    <div className="W-img-and-quote-div">
                                        <div className="W-div-img-quote">
                                            {user.image == null ? (
                                                <img src={"/images/defaultUserImg.png"} alt="User_img" className="W-edit-profile-picture"/>
                                            ) : (
                                                <img src={user.image} alt="User_img" className="W-edit-profile-picture"/>
                                            )}
                                            <h4 className="W-username-profilepage">{user.username}</h4>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="W-edit-profile-div">
                            <form onSubmit={onSubmitImage} method="post" encType="multipart/form-data">
                                <div className="W-picture-upload-button-text">
                                    <div className="W-input-profile-picture">
                                        <h5 className="W-edit-picture-text">{t('EditProfile.EditPicture')}</h5>
                                        <div className="bg-light W-div-img-username">
                                            <div>
                                                <input onChange={handleImageChange}
                                                       name="image"
                                                       type="file"
                                                       accept=" image/jpeg, image/jpg,  image/png"
                                                       className="form-control W-input-width"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className="W-submit-changes-edit-profile">
                                    <button type="submit" className="btn btn-success">
                                        {/*TODO: Cambair este mensaje por uno que diga: Upload Image*/}
                                        {t('EditProfile.Upload')}
                                    </button>
                                </div>
                            </form>

                            <form onSubmit={onSubmitPassword} method="post" encType="multipart/form-data">
                                <div className="W-div-margin">
                                    <h5 className="W-margin-bottom">{t('EditProfile.ChangePassword')}</h5>
                                    <div className="bg-light d-flex justify-content-end text-center W-edit-divs-display">
                                        {errorPassword && (
                                            <div className="alert alert-danger d-flex align-items-center" role="alert">
                                                <div className="W-register-errorMsg">
                                                    {t('EditProfile.NotSamePassword')}
                                                </div>
                                            </div>
                                        )}
                                        <div>
                                            <div className="mb-3 W-input-label-edit-password">
                                                <input onChange={handleChange}
                                                       name="currentPassword"
                                                       type="password"
                                                       className="form-control"
                                                       value={userForm.currentPassword}
                                                       placeholder={t('EditProfile.CurrentPassword')}/>
                                            </div>
                                        </div>
                                        <div>
                                            <div className="mb-3 W-input-label-edit-password">
                                                <input onChange={handleChange}
                                                       name="newPassword"
                                                       type="password"
                                                       className="form-control"
                                                       value={userForm.newPassword}
                                                       placeholder={t('EditProfile.NewPassword')}/>
                                            </div>
                                        </div>
                                        <div>
                                            <div className="mb-3 W-input-label-edit-password">
                                                <input onChange={handleChange}
                                                       name="confirmPassword"
                                                       type="password"
                                                       className="form-control"
                                                       value={userForm.confirmPassword}
                                                       placeholder={t('EditProfile.ConfirmPassword')}/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className="W-submit-changes-edit-profile">
                                    <button type="submit" className="btn btn-success">
                                        {t('EditProfile.Upload')}
                                    </button>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </>
    )

}