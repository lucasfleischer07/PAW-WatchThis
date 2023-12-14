import {Outlet} from "react-router-dom";
import Header from "./components/Header";
import React from "react";

export default function LoginPage() {
    return (
        <>
            <Header type="all"
                    admin={false}
                    userName={null}
                    userId={null}
            />

            <Outlet/>
        </>
    );
}