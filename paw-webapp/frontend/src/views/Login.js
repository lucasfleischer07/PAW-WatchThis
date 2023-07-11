import {useContext, useEffect, useState} from 'react';
import {Link, useLocation, useNavigate} from 'react-router-dom';
import {useTranslation} from "react-i18next";
import {AuthContext} from "../context/AuthContext";
import {userService} from "../services";
import {toast} from "react-toastify";

export default function Login() {
    const {t} = useTranslation()
    let location = useLocation()
    let navigate = useNavigate()
    let {signIn} = useContext(AuthContext)
    const previousPath = location.pathname || '/';

    const [userForm, setUserForm] = useState({
        email: undefined,
        password: "",
    });

    const [error, setError] = useState(false)

    const validEmail = () => {
        const emailRegex = /^((([+.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3}))+)?$/
        return userForm.email && emailRegex.test(userForm.email)
    }
    const validForm = () => {
        if(!validEmail() || !userForm.password) {
            setError(true)
            return false
        } else {
            return validEmail() && userForm.password
        }
    }

    const handleChange = (e) => {
        const {name, value} = e.target
        setUserForm((prev) => {
            return {...prev, [name]: value}
        })
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!validForm()) {
            return
        }
        userService.login(userForm.email, userForm.password)
            .then((user) => {
                if(!user.error) {
                    signIn(user.data, user.header)
                    navigate("/", {replace: true})
                    toast.success(t('Login.Success'))
                } else {
                    setError(true)
                    // navigate("/error", { replace: true, state: {errorCode: user.errorCode} })
                }
            })
            .catch(() => {
                setError(true)
                toast.error(t('Login.Error'))
                navigate("/error", { replace: true, state: {errorCode: 404} })
            })
    };


    useEffect(() => {
        document.title = t('Login.LoginMessage')
    })
    

    return (
        <div>
            <div className="W-background">
                <form onSubmit={handleSubmit}>
                    <div className="W-general-div-login">
                        <div className="W-login-title">
                            <h4>{t('Login.PageInfo')}</h4>
                        </div>
                        <div className="card W-login-card">
                            <div className="W-email-verification-message">
                                <h5>{t('Login.WelcomeMessage')}</h5>
                            </div>
                            <div className="mb-3 W-input-label-login-info">
                                {error && (
                                    <div className="alert alert-danger d-flex align-items-center" role="alert">
                                        <div className="W-register-errorMsg">
                                            {t('Login.WrongEmail')}
                                        </div>
                                    </div>
                                )}
                                <label htmlFor="email">{t('Login.Email')}</label>
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
                            <div className="mb-3 W-input-label-login-info">
                                <label htmlFor="password">{t('Login.Password')}</label>
                                <input
                                    type="password"
                                    id="password"
                                    name="password"
                                    className="form-control"
                                    placeholder={t('Placeholder.Asterisk')}
                                    value={userForm.password}
                                    onChange={handleChange}
                                    required
                                />
                                <div>
                                    <Link className="W-forgot-password" to="/login/forgotPassword">{t('Login.ForgotPassword')}</Link>
                                </div>
                            </div>
                        </div>
                        <div className="W-div-login-rememberMe">
                            <div className="W-div-login-button">
                                <button
                                    type="submit"
                                    id="submitButton1"
                                    className="btn btn-success W-login-button">
                                    {t('Login.LoginMessage')}
                                </button>
                            </div>
                        </div>

                        <hr className="d-flex W-line-style-login" />
                        <div className="W-alignment-signup-div W-margin-bottom">
                            <h5>{t('Login.NoAccountMessage')}</h5>
                            <Link to="/login/signUp">
                                <button type="button" className="btn btn-secondary W-sign-up-button-link">{t('Login.SignUpMessage')}</button>
                            </Link>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    );
}

