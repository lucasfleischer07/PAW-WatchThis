import {paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";

export class ReportsApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.LISTS}`
    }

    async getUserWatchList(userId, pageNumber, pageSize) {
        try {
            const apiUrl = `${this.basePath}/watchList/${userId}`
            const params = {pageNumber: pageNumber, pageSize: pageSize}
            const options = {}
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

    async addUserWatchList(contentId) {
        try {
            const res = await fetch(`${this.basePath}/watchList/add/${contentId}`, {
                method: 'PUT',
                headers: {},
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
            const res = await fetch(`${this.basePath}/watchList/delete/${contentId}`, {
                method: 'DELETE',
                headers: {},
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

    async getUserViewedList(userId, pageNumber, pageSize) {
        try {
            const apiUrl = `${this.basePath}/viewedList/${userId}`
            const params = {pageNumber: pageNumber, pageSize: pageSize}
            const options = {}
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

    async addUserViewedList(contentId) {
        try {
            const res = await fetch(`${this.basePath}/viewedList/add/${contentId}`, {
                method: 'PUT',
                headers: {},
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
                headers: {},
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