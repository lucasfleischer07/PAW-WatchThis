import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi, fetchWithQueryParamsPostApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";
import {ListsApi} from "./ListsApi";

export class ContentApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.CONTENT}`
    }

    async getLandingPage() {
        const res = await fetch(`${this.basePath}/landing`, {
            method: 'GET',
            headers: authCheck({})
        })
        return {error: false, data: await res.json()}
    }

    async getSpecificContent(contentId) {
        try {
            const res = await fetch(`${this.basePath}/${contentId}`, {
                method: 'GET',
                headers: authCheck({})
            })
            if(res.status === 200) {
                return {error: false, data: await res.json()}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }

    }

    async updateContent(contentId, userId, contentDetails) {
        try {
            contentDetails.genre = contentDetails.genre.split(" ")
            if(typeof contentDetails.contentPicture == 'string' || contentDetails.contentPicture == null) {
                delete contentDetails.contentPicture
            }
            else {
                await this.updateContentImage(contentId, userId, contentDetails.contentPicture)
                delete contentDetails.contentPicture
            }

            const apiUrl = `${this.basePath}/${contentId}`
            const options = {method: 'PUT', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(contentDetails)}
            const params = {userId: userId}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)

            if(res.status === 200) {
                return {error: false, data: []}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async updateContentImage(contentId, userId, image) {
        try {
            const formData = new FormData();
            formData.append("image", image, image.name)

            const apiUrl = `${this.basePath}/${contentId}/contentImage`
            const options = {method: 'PUT', headers: authCheck({}), body: formData}
            const params = {userId: userId}
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

    async deleteContent(contentId, userId) {
        try {
            const apiUrl = `${this.basePath}/${contentId}`
            const options = {method: 'DELETE', headers: authCheck({})}
            const params = {userId: userId}
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

    async createContent(contentDetails, userId, image) {
        try {
            contentDetails.genre = contentDetails.genre.split(" ")
            const contentDetailsAux = contentDetails
            delete contentDetailsAux.contentPicture

            const apiUrl = `${this.basePath}`
            const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(contentDetails)}
            const params = {userId: userId}
            const res = await fetchWithQueryParamsPostApi(apiUrl, params, options)
            if(res.status === 201) {
                const contentData = await res.data
                await this.updateContentImage(contentData.id, userId, image)
                return {error: false, data: contentData}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async getContentByType(contentType, pageNumber, genre, durationFrom, durationTo, sorting, query, userId = null, isWatchList = false, isHomePage = false) {
        const params = {}
        if(isWatchList) {
            params.watchListSavedBy = userId
        } else if(!isWatchList && userId != null && !isHomePage) {
            params.viewedListSavedBy = userId
        } else {
            if(contentType !== null) {
                params.type = contentType
            }
            if(genre !== ''){
                params.genre = genre
            }
            if(durationFrom !== '' && durationTo !== ''){
                params.durationFrom = durationFrom
                params.durationTo = durationTo
            }
            if(sorting !== ''){
                params.sorting = sorting
            }
            if(query !== ''){
                params.query = query
            }
        }
        params.page = pageNumber

        try {
            const apiUrl = `${this.basePath}`
            const options = {headers: authCheck({})}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)
            if(res.status === 200) {
                return {error: false, totalPages: res.totalPages, data: res.data}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async filterContentByType(contentType, pageNumber, filters) {
        try {
            const apiUrl = `${this.basePath}/${contentType}/filters`
            const params = {pageNumber: pageNumber, ...filters}
            const options = {headers: authCheck({})}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)
            if(res.status !== 204) {
                return {error: false, data: await res.data, totalPages: res.totalPages}
            } else {
                return {error: false, data: [], totalPages: res.totalPages}
            }
        } catch (e) {
            return {error: true}
        }
    }

    async getContentReviewers(contentId) {
        try {
            const res = await fetch(`${this.basePath}/${contentId}/reviewers`, {
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

    async addUserWatchList(contentId, userId) {
        try {
            const apiUrl = `${this.basePath}/${contentId}/watchList`
            const options = {method: 'POST', headers: authCheck({}), body: {}}
            const params = {userId: userId}
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

    async deleteUserWatchList(contentId, userId) {
        try {
            const apiUrl = `${this.basePath}/${contentId}/watchList`
            const options = {method: 'DELETE', headers: authCheck({}), body: {}}
            const params = {userId: userId}
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

    async addUserViewedList(contentId, userId) {
        try {
            const apiUrl = `${this.basePath}/${contentId}/viewedList`
            const options = {method: 'POST', headers: authCheck({}), body: {}}
            const params = {userId: userId}
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

    async deleteUserViewedList(contentId, userId) {
        try {
            const apiUrl = `${this.basePath}/${contentId}/viewedList`
            const options = {method: 'DELETE', headers: authCheck({}), body: {}}
            const params = {userId: userId}
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
}
