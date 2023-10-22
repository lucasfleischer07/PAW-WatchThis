import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi, fetchWithQueryParamsPostApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";
import {genericFetchWithQueryParams, genericRequest} from "./GenericRequest";

export class ContentApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.CONTENT}`
    }

    async getLandingPage() {
        const url = `${this.basePath}/landing`
        const options = {method: 'GET', headers: authCheck({})}
        return await genericRequest(this.basePath, url, options)
    }

    async getSpecificContent(content) {
        const options = {method: 'GET', headers: authCheck({})}
        return await genericRequest(this.basePath, content, options)
    }

    async updateContent(contentId, contentDetails) {
        contentDetails.genre = contentDetails.genre.split(" ")
        if(typeof contentDetails.contentPicture == 'string' || contentDetails.contentPicture == null) {
            delete contentDetails.contentPicture
        }
        else {
            await this.updateContentImage(contentId, contentDetails.contentPicture)
            delete contentDetails.contentPicture
        }

        const apiUrl = `${this.basePath}/${contentId}`
        const options = {method: 'PUT', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(contentDetails)}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async updateContentImage(contentId, image) {
        const formData = new FormData();
        formData.append("image", image, image.name)

        const apiUrl = `${this.basePath}/${contentId}/contentImage`
        const options = {method: 'PUT', headers: authCheck({}), body: formData}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async deleteContent(contentId, userId) {
        const apiUrl = `${this.basePath}/${contentId}`
        const options = {method: 'DELETE', headers: authCheck({})}
        const params = {userId: userId}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    // TODO: Ver de cambiar este pero medio dificil poruqe llama a otras funciones y si devuelve algo
    async createContent(contentDetails, image) {
        try {
            contentDetails.genre = contentDetails.genre.split(" ")
            const contentDetailsAux = contentDetails
            delete contentDetailsAux.contentPicture

            const apiUrl = `${this.basePath}`
            const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(contentDetails)}
            const params = {}
            const res = await fetchWithQueryParamsPostApi(apiUrl, params, options)
            if(res.status === 201) {
                const contentData = await res.data
                await this.updateContentImage(parseInt(contentData.id), image)
                return {error: false, data: contentData}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async getContentByType(contentType, page, genre, durationFrom, durationTo, sorting, query, userId = null, isWatchList = false, isHomePage = false, paginated = true) {
        const params = {}
        if(isWatchList) {
            if(!paginated) {
                params.paginated = paginated
            }
            params.watchListSavedBy = userId
        } else if(!isWatchList && userId != null && !isHomePage) {
            if(!paginated) {
                params.paginated = paginated
            }
            params.viewedListSavedBy = userId
        } else {
            if(contentType !== null) {
                params.type = contentType
                if(contentType === 'recommendedUser') {
                    params.userId = userId
                }
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
        params.page = page

        const apiUrl = `${this.basePath}`
        const options = {method: 'GET', headers: authCheck({})}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }


    async getLists(listUrl, paginated = true) {
        let params = {}
        if(!paginated) {
            params.paginated = paginated
        }
        const apiUrl = listUrl
        const options = {method: 'GET', headers: authCheck({})}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }


    async filterContentByType(contentType, page, filters) {
        const apiUrl = `${this.basePath}/${contentType}/filters`
        const params = {page: page, ...filters}
        const options = {method: 'GET', headers: authCheck({})}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async getContentReviewers(contentId) {
        const options = {method: 'GET', headers: authCheck({})}
        return await genericRequest(this.basePath, contentId, options)
    }

    async addUserWatchList(contentId, userId) {
        const apiUrl = `${this.basePath}/${contentId}/watchList`
        const options = {method: 'POST', headers: authCheck({}), body: {}}
        const params = {userId: userId}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async deleteUserWatchList(contentId, userId) {
        const apiUrl = `${this.basePath}/${contentId}/watchList`
        const options = {method: 'DELETE', headers: authCheck({}), body: {}}
        const params = {userId: userId}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async addUserViewedList(contentId, userId) {
        const apiUrl = `${this.basePath}/${contentId}/viewedList`
        const options = {method: 'POST', headers: authCheck({}), body: {}}
        const params = {userId: userId}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async deleteUserViewedList(contentId, userId) {
        const apiUrl = `${this.basePath}/${contentId}/viewedList`
        const options = {method: 'DELETE', headers: authCheck({}), body: {}}
        const params = {userId: userId}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }
}
