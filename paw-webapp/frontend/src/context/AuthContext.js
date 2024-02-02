import { createContext } from "react";

export const AuthContext = createContext(null);

export function AuthProvider ({children}) {

    const signIn = (user, authToken, refreshToken) => {
        let json={
            username:user.name,
            id:user.id,
            role:user.authorization
        }
        localStorage.setItem("user",JSON.stringify(json))
        localStorage.setItem("userAuthToken", authToken)
        localStorage.setItem("userRefreshToken", refreshToken)
        localStorage.setItem("isAdmin", user.authorization === 'admin' ? "true" : "false")
    }

    const signOut = () => {
        localStorage.removeItem("user");
        localStorage.removeItem("userAuthToken");
        localStorage.removeItem("userRefreshToken");
        localStorage.removeItem("isAdmin");
        localStorage.removeItem("rememberMe");
    }

    const isLogged = () => {
        return localStorage.getItem("user") !== null
    }

    const resetTokens = (authToken, refreshToken) => {
        localStorage.removeItem("userAuthToken");
        localStorage.removeItem("userRefreshToken");
        localStorage.setItem("userAuthToken", authToken);
        localStorage.setItem("userRefreshToken", refreshToken);
    }

    const value = {
        signIn,
        signOut,
        isLogged,
        resetTokens
    };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;

}