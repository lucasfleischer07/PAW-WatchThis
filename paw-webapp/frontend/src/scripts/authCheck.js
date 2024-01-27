import jwt from "jwt-decode";

export function authCheck(headerOptions) {
    const userAuthToken = localStorage.getItem("userAuthToken");
    if (!userAuthToken) {
        return headerOptions;
    } else {
        if(checkUserAuthToken(userAuthToken)){
            headerOptions.Authorization = `Bearer ${userAuthToken}`;
            return headerOptions;
        }
        const userRefreshToken = localStorage.getItem("userRefreshToken");
        headerOptions.Authorization = `Bearer ${userRefreshToken}`;
        return headerOptions;
    }
}

function checkUserAuthToken(authToken) {
    return jwt(authToken).exp * 1000 > new Date().getTime()
}