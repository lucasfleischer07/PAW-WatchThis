import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import {userService} from "../services";
import {AuthContext} from "../context/AuthContext";
import {toast} from "react-toastify";

export default function SignUp() {
    const {t} = useTranslation()
    let navigate = useNavigate()
    let {signIn, signOut} = useContext(AuthContext)

    const [user, setUser] = useState(localStorage.hasOwnProperty("user")? JSON.parse(localStorage.getItem("user")) : null)
    const [usernameError, setUsernameError] = useState(false)
    const [emailError, setEmailError] = useState(false)
    const [passwordError, setPasswordError] = useState(false)
    const [confirmPasswordError, setConfirmPasswordError] = useState(false)

    const [userForm, setUserForm] = useState({
        email: "",
        username: "",
        password: "",
        confirmPassword: ""
    });

    const validateUsername = () => {
        const usernameRegex = /([a-zA-Z0-9ñ\s]+)?/
        if(userForm.username.length < 4 || userForm.username.length > 30 || !usernameRegex.test(userForm.username)) {
            setUsernameError(true)
            return false
        }
        setUsernameError(false)
        return true
    }

    const validEmail = () => {
        const emailRegex = /^((([+.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3}))+)?$/
        if(userForm.email.length < 10 || userForm.email.length > 50 || !emailRegex.test(userForm.email)) {
            setEmailError(true)
            return false
        }
        setEmailError(false)
        return true
    }

    const validatePassword = () => {
        const passwordRegex = /([a-zA-Z0-9ñáéíóú!,.:;=+\-_()?<>$%&#@{}\[\]|*"'~/`^\s]+)?/
        if(userForm.password.length < 6 || userForm.password.length > 50 || !passwordRegex.test(userForm.password)) {
            setPasswordError(true)
            return false
        } else if(userForm.password !== userForm.confirmPassword) {
            setConfirmPasswordError(true)
            return false
        }
        setPasswordError(false)
        setConfirmPasswordError(false)
        return true
    }

    const validateForm = () => {
        return (validateUsername() && validEmail() && validatePassword())
    }

    const handleSubmitNewUser = (e) => {
        e.preventDefault()
        if(!validateForm()) {
            return
        }
        userService.userCreate(userForm)
            .then(data => {
                // TODO: Ver como manejar el tema de ususario que ay existe y eso
                if(!data.error) {
                    userService.login(data.data.email, userForm.password)
                        .then(user2 => {
                            if(!user2.error) {
                                if(user != null) {
                                    signOut()
                                }
                                signIn(user2.data, user2.header)
                                navigate("/", {replace: true})
                                toast.success(t('Login.Success'))
                            } else {
                                navigate("/error", { replace: true, state: {errorCode: user2.errorCode} })
                            }
                        })
                        .catch(() => {
                            navigate("/error", { replace: true, state: {errorCode: 401} })
                        })
                } else {
                    navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                }
            })
            .catch(() => {
                navigate("/error", { replace: true, state: {errorCode: 404} })
            })
    }

    const handleChange = (e) => {
        const {name, value} = e.target
        setUserForm((prev) => {
            return {...prev, [name]: value}
        })
    }

    useEffect(() => {
        document.title = t('Login.SignUpMessage')
    })

    return(
        <div className="W-background">
            <form method="post">
                <div className="W-general-div-login">
                    <div className="W-login-title">
                        <h4>{t('Signup.PageInfo')}</h4>
                    </div>

                    <div className="card W-login-card">
                        <div className="mb-3 W-input-label-login-info">
                            <div className="mb-3 W-input-label-login-info">
                                {usernameError &&
                                    <p style={{color: "#b21e26"}}>Meter el error</p>
                                }
                                <label className="form-label" htmlFor="username">{t('Signup.Username')} <span className="W-red-asterisco">{t('Asterisk')}</span></label>
                                <p className="W-review-registration-text">{t('Signup.CharacterLimits', {min: "4", max: "30"})}</p>
                                <input type="text" className="form-control" id="username" name="username" placeholder={t('Placeholder.UserExample')} value={userForm.username} onChange={handleChange} required />
                            </div>
                        </div>

                        <div className="mb-3 W-input-label-login-info">
                            <div className="mb-3 W-input-label-login-info">
                                {emailError &&
                                    <p style={{color: "#b21e26"}}>Meter el error</p>
                                }
                                <label className="form-label" htmlFor="username">{t('Signup.Email')} <span className="W-red-asterisco">{t('Asterisk')}</span></label>
                                <p className="W-review-registration-text">{t('Signup.CharacterLimits', {min: "4", max: "30"})}</p>
                                <input type="email" className="form-control" id="email" name="email" placeholder={t('Placeholder.EmailExample')} value={userForm.email} onChange={handleChange} required />
                            </div>
                        </div>

                        <div className="mb-3 W-input-label-login-info">
                            <div className="mb-3 W-input-label-login-info">
                                {passwordError &&
                                    <p style={{color: "#b21e26"}}>Meter el error</p>
                                }
                                <label className="form-label" htmlFor="username">{t('Signup.Password')} <span className="W-red-asterisco">{t('Asterisk')}</span></label>
                                <p className="W-review-registration-text">{t('Signup.CharacterLimits', {min: "6", max: "50"})}</p>
                                <input type="password" className="form-control" id="password" name="password" placeholder={t('Placeholder.Asterisk')} value={userForm.password} onChange={handleChange} required />
                            </div>
                        </div>

                        <div className="mb-3 W-input-label-login-info">
                            <div className="mb-3 W-input-label-login-info">
                                {confirmPasswordError &&
                                    <p style={{color: "#b21e26"}}>Meter el error</p>
                                }
                                <label className="form-label" htmlFor="username">{t('Signup.ConfirmPassword')} <span className="W-red-asterisco">{t('Asterisk')}</span></label>
                                <p className="W-review-registration-text">{t('Signup.CharacterLimits', {min: "6", max: "50"})}</p>
                                <input type="password" className="form-control" id="confirmPassword" name="confirmPassword" placeholder={t('Placeholder.Asterisk')} value={userForm.confirmPassword} onChange={handleChange} required />
                            </div>
                        </div>
                    </div>

                    <div className="W-margin-bottom">
                        <button id="submitButton2" type="submit" className="btn btn-success W-login-button" onClick={handleSubmitNewUser}>
                            {t('Signup.SignupMessage')}
                        </button>
                    </div>
                </div>
            </form>
        </div>
    )

}