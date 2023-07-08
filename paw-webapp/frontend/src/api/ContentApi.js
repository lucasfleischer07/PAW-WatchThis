import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";
import {ListsApi} from "./ListsApi";

export class ContentApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.CONTENT}`
    }

    async getLandingPage() {
        const res = await fetch(this.basePath, {
            method: 'GET',
            headers: authCheck({})
        })
        return {error: false, data: await res.json()}
    }

    async getSpecificContent(contentId) {
        try {
            const res = await fetch(`${this.basePath}/specificContent/${contentId}`, {
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

    async updateContent(contentId, contentDetails) {
        try {
            contentDetails.genre = contentDetails.genre.split(" ")
            if(typeof contentDetails.contentPicture == 'string' || contentDetails.contentPicture == null) {
                delete contentDetails.contentPicture
            }
            else {
                await this.updateContentImage(contentId, contentDetails.contentPicture)
                delete contentDetails.contentPicture
            }

            const res = await fetch(`${this.basePath}/editInfo/${contentId}`, {
                method: 'PUT',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),
                body: JSON.stringify(contentDetails)
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

    async updateContentImage(contentId, image) {
        try {
            const formData = new FormData();
            formData.append("image", image, image.name)
            const res = await fetch(`${this.basePath}/${contentId}/contentImage`, {
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

    async deleteContent(contentId) {
        try {
            const res = await fetch(`${this.basePath}/${contentId}/deleteContent`, {
                method: 'DELETE',
                headers: authCheck({})
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

    async createContent(contentDetails, image) {
        try {
            contentDetails.genre = contentDetails.genre.split(" ")
            const contentDetailsAux = contentDetails
            delete contentDetailsAux.contentPicture
            const res = await fetch(`${this.basePath}/create`, {
                method: 'POST',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),
                body: JSON.stringify(contentDetails)
            })
            if(res.status === 201) {
                const contentData = await res.json()
                await this.updateContentImage(contentData.id, image)
                return {error: false, data: contentData}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async getContentByType(contentType, pageNumber, genre, durationFrom, durationTo, sorting, query) {
        const params = {pageNumber: pageNumber}
        if(genre !== ''){
            params.genre = genre.split(",")
        }
        if(durationFrom !== ''){
            params.durationFrom = durationFrom
        }
        if(durationTo !== ''){
            params.durationTo = durationTo
        }
        if(sorting !== ''){
            params.sorting = sorting
        }
        if(query !== ''){
            params.query = query
        }

        console.log(params)

        try {
            const apiUrl = `${this.basePath}/${contentType}/filters`
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

    // TODO: Ver bien ste que seria con paginacion y filtros
    // TODO: Filters seria un objeto que adentro tiene el genre, duration y sortBy
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
}
