export function authCheck(headerOptions) {
    const userAuthToken = localStorage.getItem("userAuthToken");
    if (!userAuthToken) {
        return headerOptions;
    } else {
        headerOptions.Authorization = `Bearer ${userAuthToken}`;
        return headerOptions;
    }
}