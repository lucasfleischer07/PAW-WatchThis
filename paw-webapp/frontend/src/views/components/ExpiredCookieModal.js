import {Button, Modal} from "react-bootstrap";
import React, {useContext, useState} from "react";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {AuthContext} from "../../context/AuthContext";

export default function ExpiredCookieModal() {
    const {t} = useTranslation()
    let navigate = useNavigate()
    let {signOut} = useContext(AuthContext)
    const authFunctions = useContext(AuthContext)


    const handleSubmitModal = () => {
        signOut()
        navigate("/login", {replace: true})
    }

    return(
        <Modal show={true} onHide={handleSubmitModal} aria-labelledby={`modalCookies`} aria-hidden="true">
            <Modal.Header closeButton>
                <Modal.Title id={`modalLabelCookies`}>
                    {t('Cookies.Title')}
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {t('Cookies.Body')}
            </Modal.Body>
            <Modal.Footer>
                <Button variant="success" onClick={handleSubmitModal}>
                    {t('Login.LoginMessage')}
                </Button>
            </Modal.Footer>
        </Modal>
    )

}