import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {userService} from "../services";
import {toast} from "react-toastify";

export default function ForgotPassword() {
    const {t} = useTranslation()
    let navigate = useNavigate()

    const [error, setError] = useState(false)
    const [userForm, setUserForm] = useState({
        email: undefined,
    });

    const validEmail = () => {
        const emailRegex = /^((([+.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3}))+)?$/
        return userForm.email && emailRegex.test(userForm.email)
    }

    const handleChange = (e) => {
        const {name, value} = e.target
        setUserForm((prev) => {
            return {...prev, [name]: value}
        })
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        if (!validEmail()) {
            setError(true)
            return
        }

        userService.loginForgotPassword(userForm)
            .then(data => {
                if(!data.error) {
                    toast.success(t('Login.ForgotPass.Snackbar'))
                    navigate("/login", {replace: true})
                } else {
                    if(data.errorCode === 404) {
                        console.log("Hola")
                        setError(true)
                    } else {
                        console.log("Hola2")
                        navigate("/error", { replace: true, state: {errorCode: data.errorCode} })
                    }
                }
            })
            .catch(() => {
                console.log("Hola3")
                navigate("/error", { replace: true, state: {errorCode: 404} })
            })
    }

    useEffect(() => {
        document.title = t('Login.LoginMessage')
    })

    return (
        <div className="W-general-div-login">
            <div className="W-login-title">
                <h4>{t('Login.ForgotPass')}</h4>
            </div>
            <div className="card W-login-card">
                <div className="mb-3 W-input-label-login-info">
                    <h5 className="W-password-title">
                        {t('Login.ForgotPass.msg')}
                    </h5>
                    <div className="mb-3 W-input-label-login-info">
                        {error && (
                            <div className="alert alert-danger d-flex align-items-center" role="alert">
                                <div className="W-register-errorMsg">
                                    {t('Login.WrongEmail2')}
                                </div>
                            </div>
                        )}
                        <label htmlFor="email" className="form-label">
                            {t('Signup.Email')}<span className="W-red-asterisco">{t('Asterisk')}</span>
                        </label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            className="form-control"
                            placeholder={t('Placeholder.EmailExample')}
                            value={userForm.email}
                            onChange={handleChange}
                            required
                        />
                    </div>
                </div>
            </div>
            <div className="W-margin-bottom">
                <button id="submitButton3" type="submit" className="btn btn-success W-send-password" onClick={handleSubmit}>{t('Send')}</button>
            </div>
        </div>
    );
}