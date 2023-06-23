import { createContext } from "react";
import {useListApi} from "../api/ListsApi";
import {useNavigate} from "react-router-dom";
import {useUserApi} from "../api/UserApi";
import {useReviewApi} from "../api/ReviewApi";

export const AuthContext = createContext(null);

export function AuthProvider ({children}) {
    const navigate = useNavigate()

    const signIn = (user, authToken) => {
        localStorage.setItem("user", JSON.stringify(user))
        localStorage.setItem("userAuthToken", authToken)
        localStorage.setItem("isAdmin", user.role === 'admin' ? "true" : "false")
    }

    const signOut = () => {
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
        listApi: useListApi(signOut, navigate),
        userApi: useUserApi(signOut, navigate),
        reviewApi: useReviewApi(signOut, navigate),
    };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;

}