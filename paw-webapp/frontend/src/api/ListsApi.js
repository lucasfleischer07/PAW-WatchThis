import {paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";

export class ListsApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.LISTS}`
    }

    async getUserWatchList(userId, pageNumber) {
        try {
            const apiUrl = `${this.basePath}/watchList/${userId}`
            const params = {pageNumber: pageNumber, pageSize: 10}
            const options = {headers: authCheck({})}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)
            if(res.status !== 204) {
                return {error: false, data: await res.json(), totalPages: res.totalPages}
            } else {
                return {error: false, data: [], totalPages: res.totalPages}
            }
        } catch (e) {
            return {error: true}
        }
    }

    async addUserWatchList(contentId) {
        try {
            const res = await fetch(`${this.basePath}/watchList/add/${contentId}`, {
                method: 'PUT',
                headers: authCheck({}),
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

    async deleteUserWatchList(contentId) {
        try {
            await fetch(`${this.basePath}/watchList/delete/${contentId}`, {
                method: 'DELETE',
                headers: authCheck({}),
                body: {}
            })
            return {error: false, data: []}
        } catch (e) {
            return {error: true}
        }
    }

    async getUserViewedList(userId, pageNumber) {
        try {
            const apiUrl = `${this.basePath}/viewedList/${userId}`
            const params = {pageNumber: pageNumber, pageSize: 10}
            const options = {headers: authCheck({})}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)
            if(res.status !== 204) {
                return {error: false, data: await res.json(), totalPages: res.totalPages}
            } else {
                return {error: false, data: [], totalPages: res.totalPages}
            }
        } catch (e) {
            return {error: true}
        }
    }

    async addUserViewedList(contentId) {
        try {
            const res = await fetch(`${this.basePath}/viewedList/add/${contentId}`, {
                method: 'PUT',
                headers: authCheck({}),
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

    async deleteUserViewedList(contentId) {
        try {
            const res = await fetch(`${this.basePath}/viewedList/delete/${contentId}`, {
                method: 'DELETE',
                headers: authCheck({}),
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
}