import { createContext, useContext, useEffect, useState } from "react";
import {use} from "i18next";

export const AuthContext = createContext(null);


export function AuthProvider ({children}) {
    const [loggedUser, setLoggedUser] = useState(undefined);

    const signIn = (user, authToken, rememberMe) => {
        setLoggedUser(user)
        localStorage.setItem("user", JSON.stringify(user))
        localStorage.setItem("userAuthToken", authToken)
        localStorage.setItem("isAdmin", user.role === 'admin' ? "true" : "false")
        localStorage.setItem("rememberMe", rememberMe ? "true" : "false")

    }

    const signOut = (email, password) => {
        setLoggedUser(null)
        localStorage.removeItem("user");
        localStorage.removeItem("userAuthToken");
        localStorage.removeItem("isAdmin");
        localStorage.removeItem("rememberMe");
    }

    const isLogged = () => {
        return localStorage.getItem("user") !== null
    }

    const value = {
        signIn,
        signOut,
        isLogged,
    };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;

}