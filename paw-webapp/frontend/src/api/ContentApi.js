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

    async getSpecificContent(authFunctions, content) {
        const options = {method: 'GET', headers: authCheck({})}
        return await genericRequest(this.basePath, content, options, authFunctions)
    }

    async updateContent(authFunctions, contentId, contentDetails) {
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
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async updateContentImage(contentId, image) {
        const formData = new FormData();
        formData.append("image", image, image.name)

        const apiUrl = `${this.basePath}/${contentId}/contentImage`
        const options = {method: 'PUT', headers: authCheck({}), body: formData}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async deleteContent(authFunctions, contentId) {
        const apiUrl = `${this.basePath}/${contentId}`
        const options = {method: 'DELETE', headers: authCheck({})}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async createContent(authFunctions, contentDetails, image) {
        try {
            contentDetails.genre = contentDetails.genre.split(" ")
            const contentDetailsAux = contentDetails
            delete contentDetailsAux.contentPicture

            const apiUrl = `${this.basePath}`
            const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(contentDetails)}
            const params = {}
            const res = await fetchWithQueryParamsPostApi(apiUrl, params, options, authFunctions)
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

    async getContentByType(authFunctions,contentType, page, genre, durationFrom, durationTo, sorting, query, userId = null, isWatchList = false, isHomePage = false, paginated = true) {
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
        if(paginated) {
            params.page = page
        }
        if(isHomePage && !paginated) {
            params.paginated = paginated
        }

        const apiUrl = `${this.basePath}`
        const options = {method: 'GET', headers: authCheck({})}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }


    async getLists(authFunctions, listUrl, paginate, page = 1) {
        let params = {}
        if(!paginate) {
            params.paginated = paginate
        } else {
            params.page = page
        }
        const apiUrl = listUrl
        const options = {method: 'GET', headers: authCheck({})}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
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

    async addUserWatchList(authFunctions, contentId, userId) {
        const apiUrl = `${this.basePath}/${contentId}/watchList`
        const bodyRequest = {userId: userId}
        const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(bodyRequest)}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async deleteUserWatchList(authFunctions, contentId, userId) {
        const apiUrl = `${this.basePath}/${contentId}/watchListId/${userId}`
        const options = {method: 'DELETE', headers: authCheck({}), body: {}}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async addUserViewedList(authFunctions, contentId, userId) {
        const apiUrl = `${this.basePath}/${contentId}/viewedList`
        const bodyRequest = {userId: userId}
        const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(bodyRequest)}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async deleteUserViewedList(authFunctions, contentId, userId) {
        const apiUrl = `${this.basePath}/${contentId}/viewedListId/${userId}`
        const options = {method: 'DELETE', headers: authCheck({}), body: {}}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }
}
