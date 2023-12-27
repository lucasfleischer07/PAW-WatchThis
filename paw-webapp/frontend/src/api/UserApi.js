import {APPLICATION_JSON_TYPE, paths, TEXT_PLAIN} from "../paths";
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
        const options = {method: 'POST', headers: authCheck({'Content-Type': TEXT_PLAIN}), body: userEmail.email}
        return await genericRequest(this.basePath, `${this.basePath}`, options)
    }

    async login(email, password) {
        try {
            const loggedUserInfo = email + ":" + password;
            const hash = btoa(loggedUserInfo);
            const res = await fetch(`${paths.BASE_URL_API}/content?type=all`, {
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

    async promoteUserToAdmin(promotedUserId) {
        const apiUrl = `${this.basePath}/${promotedUserId}/role`
        const options = {method: 'PUT', headers: authCheck({})}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }
    
}