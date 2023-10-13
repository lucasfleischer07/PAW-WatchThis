import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";
import jwt from 'jwt-decode';
import {genericRequest} from "./GenericRequest";
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
            if(res.status === 201) {
                return {error: false, data: await res.json()}
            } else {
                return {error: true, errorCode: res.status, data: await res.json()}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async getUserInfo(user) {
        const options = {method: 'GET', headers: authCheck({})}
        return await genericRequest(this.basePath, user, options)
    }

    async updateUserProfileImage(userId, image) {
        try {
            const formData = new FormData();
            formData.set("image", image)
            const res = await fetch(`${this.basePath}/${userId}/profileImage`, {
                method: 'PUT',
                headers: authCheck({}),
                body: formData
            })
            if(res.status === 204) {
                return {error: false, data: []}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}

        }
    }

    async updateUserProfileInfo(userId, userDetails) {
        try {
            const res = await fetch(`${this.basePath}/${userId}`, {
                method: 'PUT',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),
                body: JSON.stringify(userDetails)
            })
            if(res.status === 200) {
                return {error: false, data: []}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async loginForgotPassword(userEmail) {
        try {
            const res = await fetch(`${this.basePath}/login/${userEmail}/forgotPassword`, {
                method: 'POST',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE}),
                body: {}
            })
            if(res.status === 204) {
                return {error: false, data: []}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    // TODO: Este lo podriamos dejar asi, y no hacerlo generico
    async login(email, password) {
        try {
            const loggedUserInfo = email + ":" + password;
            const hash = btoa(loggedUserInfo);
            // TODO: Aca estaba el /loggedUser
            const res = await fetch(`${this.basePath}/content?type=all`, {
                method: 'GET',
                headers: {
                    Authorization: "Basic " + hash
                }
            })
            if(res.status !== 204 && res.status !== 401) {
                return {error: false, data: jwt(res.headers.get("Authorization")?.toString().split(" ")[1]), header: res.headers.get("Authorization")?.toString().split(" ")[1]}
            } else if(res.status === 401) {
                return {error: true, data: [], errorCode: res.status}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async promoteUserToAdmin(promotedUserId, loggedUserId) {
        try {
            const apiUrl = `${this.basePath}/${promotedUserId}/promote`
            const options = {method: 'PUT', headers: authCheck({})}
            const params = {userId: loggedUserId}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)

            if(res.status === 204) {
                return {error: false, data: []}
            } else {
                return {error: true, errorCode: res.status}
            }

        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    // TODO: Este lo podriamos dejar asi, y no hacerlo generico
    async getReviewsLike(userId) {
        try {
            const res = await fetch(`${this.basePath}/${userId}/reviewsLiked`, {
                method: 'GET',
                headers: authCheck({}),
            })

            if(res.status === 200) {
                const jsonData = await res.json();
                const jsonString = JSON.stringify(jsonData);
                if (jsonString === '{}') {
                    return { error: false, data: [] };
                } else {
                    return { error: false, data: jsonData };
                }
            } else {
                return {error: true, errorCode: res.status}
            }

        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    // TODO: Este lo podriamos dejar asi, y no hacerlo generico
    async getReviewsDislike(userId) {
        try {
            const res = await fetch(`${this.basePath}/${userId}/reviewsDisliked`, {
                method: 'GET',
                headers: authCheck({}),
            })
            if(res.status === 200) {
                const jsonData = await res.json();
                const jsonString = JSON.stringify(jsonData);
                if (jsonString === '{}') {
                    return { error: false, data: [] };
                } else {
                    return { error: false, data: jsonData };
                }
            } else {
                return {error: true, errorCode: res.status}
            }

        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }
    
}