import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";

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
            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true}
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

            if(res.status !== 204 && res.status !== 200) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            console.log(e)
            return {error: true}
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
            await fetch(`${this.basePath}/${contentId}/deleteContent`, {
                method: 'DELETE',
                headers: authCheck({})
            })
            return {error: false, data: []}
        } catch (e) {
            return {error: true}
        }
    }

    async createContent(contentDetails) {
        try {
            // TODO: Verificar si esta bien el contentDetails.id o como seria esa parte
            const res = await fetch(`${this.basePath}/create`, {
                method: 'POST',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),
                // TODO: contentDetails ya tiene que ser un objeto en formato JSON para mandarle con al info
                body: JSON.stringify(contentDetails)
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

    async getContentByType(contentType, pageNumber) {
        try {
            const apiUrl = `${this.basePath}/${contentType}`
            const params = {pageNumber: pageNumber, pageSize: 10}
            const options = {headers: authCheck({})}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)
            if(res.status !== 204) {
                return {error: false, totalPages: res.totalPages, data: res.data}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            console.log(e)
            return {error: true}
        }
    }

    // TODO: Ver bien ste que seria con paginacion y filtros
    // TODO: Filters seria un objeto que adentro tiene el genre, duration y sortBy
    async filterContentByType(contentType, pageNumber, filters) {
        try {
            const apiUrl = `${this.basePath}/${contentType}/filters`
            const params = {pageNumber: pageNumber, pageSize: 10, ...filters}
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
