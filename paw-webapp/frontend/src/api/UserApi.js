import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";

export class UserApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.USERS}`
    }

    async userCreate(userDetails) {
        try {
            const res = await fetch(`${this.basePath}`, {
                method: 'POST',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),
                body: JSON.stringify(userDetails)
            })
            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true}
        }
    }

    async getUserInfo(userId) {
        try {
            const res = await fetch(`${this.basePath}/${userId}`, {
                method: 'GET',
                headers: authCheck({})
            })
            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true}
        }
    }

    async getUserReviews(userId, pageNumber) {
        try {
            const apiUrl = `${this.basePath}/${userId}/reviews`
            const params = {pageNumber: pageNumber}
            const options = {headers: authCheck({})}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)
            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true}
        }
    }

    // TODO: Chequear bien este
    async getUserProfileImage(userId) {
        try {
            const res = await fetch(`${this.basePath}/${userId}/profileImage`, {
                method: 'GET',
                headers: authCheck({})
            })
            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true}

        }

    }

    async updateUserProfileImage(userId, image) {
        try {
            const formData = new FormData();
            formData.append("image", image, image.name)
            const res = await fetch(`${this.basePath}/${userId}/profileImage`, {
                method: 'PUT',
                headers: authCheck({}),
                body: formData
            })
            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true}

        }
    }

    async updateUserProfileInfo(userId, userDetails) {
        try {
            const res = await fetch(`${this.basePath}/editInfo/${userId}`, {
                method: 'PUT',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),
                body: JSON.stringify(userDetails)
            })

            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true}
        }
    }

    async loginForgotPassword(userEmail) {
        try {
            const res = await fetch(`${this.basePath}/login/${userEmail}/forgotPassword`, {
                method: 'POST',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE}),
                body: {}
            })
            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true}
        }
    }

    async login(email, password) {
        try {
            const loggedUserInfo = email + ":" + password;
            const hash = btoa(loggedUserInfo);
            const res = await fetch(`${this.basePath}/loggedUser`, {
                method: 'GET',
                headers: {
                    Authorization: "Basic " + hash
                }
            })
            if(res.status !== 204 && res.status !== 401) {
                return {error: false, data: await res.json(), header: res.headers.get("Authorization")?.toString().split(" ")[1]}
            } else if(res.status === 401) {
                return {error: true, data: []}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true}
        }
    }
    
}