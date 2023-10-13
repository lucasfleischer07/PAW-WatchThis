import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";
import jwt from 'jwt-decode';
import {genericFetchWithQueryParams, genericRequest} from "./GenericRequest";
export class UserApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.USERS}`
    }

    async userCreate(userDetails) {
        const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(userDetails)}
        return await genericRequest(this.basePath, `${this.basePath}`, options)
    }

    async getUserInfo(user) {
        const options = {method: 'GET', headers: authCheck({})}
        return await genericRequest(this.basePath, user, options)
    }

    async updateUserProfileImage(userId, image) {
        const formData = new FormData();
        formData.set("image", image)

        const apiUrl = `${this.basePath}/${userId}/profileImage`
        const options = {method: 'PUT', headers: authCheck({}), body: formData}
        return genericRequest(this.basePath, apiUrl, options)

    }

    async updateUserProfileInfo(userId, userDetails) {
        const options = {method: 'PUT', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(userDetails)}
        return genericRequest(this.basePath, userId, options)
    }

    async loginForgotPassword(userEmail) {
        const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE}), body: {}}
        return await genericRequest(this.basePath, `${this.basePath}/login/${userEmail}/forgotPassword`, options)
    }

    // TODO: Este lo podriamos dejar asi, y no hacerlo generico
    async login(email, password) {
        try {
            const loggedUserInfo = email + ":" + password;
            const hash = btoa(loggedUserInfo);
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
        const apiUrl = `${this.basePath}/${promotedUserId}/promote`
        const options = {method: 'PUT', headers: authCheck({})}
        const params = {userId: loggedUserId}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async getReviewsLike(userId) {
        const options = {method: 'GET', headers: authCheck({})}
        const url = `${this.basePath}/${userId}/reviewsLiked`
        return await genericRequest(this.basePath, url, options)
    }

    async getReviewsDislike(userId) {
        const options = {method: 'GET', headers: authCheck({})}
        const url = `${this.basePath}/${userId}/reviewsDisliked`
        return await genericRequest(this.basePath, url, options)
    }
    
}