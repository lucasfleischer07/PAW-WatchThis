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
                return {error: false, data: await res.data, totalPages: res.totalPages}
            } else {
                return {error: false, data: [], totalPages: res.totalPages}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async getUserWatchListContentIds(userId) {
        try {
            const res = await fetch(`${this.basePath}/watchListContentIds/${userId}`, {
                method: 'GET',
                headers: authCheck({}),
            })

            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
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
            return {error: true, errorCode: e.response.status || 500}
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
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async getUserViewedList(userId, pageNumber) {
        try {
            const apiUrl = `${this.basePath}/viewedList/${userId}`
            const params = {pageNumber: pageNumber, pageSize: 10}
            const options = {headers: authCheck({})}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)
            if(res.status !== 204) {
                return {error: false, data: await res.data, totalPages: res.totalPages}
            } else {
                return {error: false, data: [], totalPages: res.totalPages}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async getUserViewedListContentIds(userId) {
        try {
            const res = await fetch(`${this.basePath}/viewedListContentIds/${userId}`, {
                method: 'GET',
                headers: authCheck({}),
            })

            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
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
            return {error: true, errorCode: e.response.status || 500}
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
            return {error: true, errorCode: e.response.status || 500}
        }
    }
}