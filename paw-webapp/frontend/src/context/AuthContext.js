import { createContext } from "react";

export const AuthContext = createContext(null);


export function AuthProvider ({children}) {

    const signIn = (user, authToken, rememberMe) => {
        localStorage.setItem("user", JSON.stringify(user))
        localStorage.setItem("userAuthToken", authToken)
        localStorage.setItem("isAdmin", user.role === 'admin' ? "true" : "false")
        localStorage.setItem("rememberMe", rememberMe ? "true" : "false")

    }

    const signOut = (email, password) => {
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