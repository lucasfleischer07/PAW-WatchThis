import {useTranslation} from "react-i18next";
import {useContext, useEffect, useState} from "react";
import {AuthContext} from "../context/AuthContext";
import {userService} from "../services";
import {useForm} from "react-hook-form";
import {toast} from "react-toastify";
import {useLocation, useNavigate} from "react-router-dom";


export default function ProfileEditionPage() {
    const {t} = useTranslation()
    let navigate = useNavigate()
    let location = useLocation()
    let {isLogged} = useContext(AuthContext)
    let origin = location.state?.from?.pathname || "/";

    const [user, setUser] = useState(localStorage.hasOwnProperty("user")? JSON.parse(localStorage.getItem("user")) : null)
    const [error, setError] = useState(undefined)
    const [image, setImage] = useState(undefined)
    const {reset} = useForm()


    const [userForm, setUserForm] = useState({
        currentPassword: "",
        newPassword: "",
        confirmPassword: "",
    });


    const validatePasswords = () => {
        if(userForm.newPassword !== userForm.confirmPassword || !userForm.newPassword.length < 6) {
            setError(true)
            return false
        } else {
            return true
        }
    }

    const onSubmitPassword = (e) => {
        // TODO: FAlta manda al back la actual y checkear de que sean iguales
        if(isLogged()) {
            validatePasswords()
            e.preventDefault();
            userService.updateUserProfileInfo(user.id, userForm.newPassword)
                .then(data => {
                    if(!data.error) {
                        setError(false)
                        reset()
                        toast.info(t('EditProfile.Upload.Password.Success'))
                    }
                })
                .catch(e => {
                    toast.error(t('EditProfile.Upload.Password.Error'))
                })
        } else {
            if(!isLogged()) {
                toast.warn(t('Action.Forbidden.Login'))
                navigate(origin, {replace: true})
            }
        }
    }

    // TODO: FALTA AHCER QUE SE RESETEE LA IMAGEN CUANOD SE PONE EL UPLOAD
    const onSubmitImage = (e) => {
        e.preventDefault();
        if(isLogged() && image !== undefined) {
            userService.updateUserProfileImage(user.id, image)
                .then(data => {
                    if (!data.error) {
                        setError(false)
                        reset()
                        toast.success(t('EditProfile.Upload.Image'));
                    } else {
                        setError(true)
                    }
                })
                .catch(e => {
                    toast.error(t('EditProfile.Upload.Image.Error'));
                })
        } else {
            if(!isLogged()) {
                toast.warn(t('Action.Forbidden.Login'));
                navigate(origin, {replace: true})
            }
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
        document.title = t('Profile')
    })


    return(
        <div className="row py-5 px-4 W-set-margins">
            <div className="col-md-5 mx-auto W-edit-profile-display">
                <div className="bg-white shadow rounded overflow-hidden W-profile-general-div">
                    <div className="W-profile-background-color bg-dark">
                        <div>
                            <div className="profile mr-3">
                                <div className="W-img-and-quote-div">
                                    <div className="W-div-img-quote">
                                        {user.image == null ? (
                                            <img src={'./images/defaultUserImg.png'} alt="User_img" className="W-edit-profile-picture"/>
                                        ) : (
                                            <img src={user.image} alt="User_img" className="W-edit-profile-picture"/>
                                        )}
                                        <h4 className="W-username-profilepage">{user.name}</h4>
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
                                    {/*TODO: Cambair este mensaje por el de Upload Image*/}
                                    {t('EditProfile.Upload')}
                                </button>
                            </div>
                        </form>

                        <form onSubmit={onSubmitPassword} method="post" encType="multipart/form-data">
                            <div className="W-div-margin">
                                <h5 className="W-margin-bottom">{t('EditProfile.ChangePassword')}</h5>
                                <div className="bg-light d-flex justify-content-end text-center W-edit-divs-display">
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
    )

}