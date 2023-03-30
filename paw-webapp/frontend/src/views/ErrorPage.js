import React from 'react';
import { Link } from 'react-router-dom';
import {useTranslation} from "react-i18next";

function ErrorPage(props) {
    const errorCode = props.errorCode;
    const {t} = useTranslation()

    return (
        <div className="d-flex align-items-center justify-content-center vh-100">
            <div className="text-center">
                <h1 className="display-1 fw-bold">{errorCode}</h1>
                <p className="fs-3">
                    <span className="text-danger">{[401, 404, 400, 405, 403, 500].includes(errorCode)?t("Error.Title"+errorCode.toString()):t("Error.Title",{errorCode:errorCode})}</span>
                </p>
                <p className="lead">
                    {t("Error.Body"+([401, 404, 400, 405, 403, 500].includes(errorCode)?errorCode.toString():""))}
                </p>
                <a href="/" className="btn btn-success">{t("Error.HomeButton")}</a>
            </div>
        </div>
    );
}

export default ErrorPage;