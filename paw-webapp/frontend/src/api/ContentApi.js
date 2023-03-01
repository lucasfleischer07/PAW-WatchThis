import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";

export class ContentApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.CONTENT}`
    }

    async getLandingPage() {
        const res = await fetch(this.basePath)
        return await res.json()
    }

    async getSpecificContent(contentId) {
        try {
            const res = await fetch(`${this.basePath}/specificContent/${contentId}`)
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
            // TODO: Verificar si esta bien el contentDetails.id o como seria esa parte
            const res = await fetch(`${this.basePath}/editInfo/${contentId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': APPLICATION_JSON_TYPE,
                },
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

    // TODO: Chequear bien este
    async getContentImage(contentId) {
        try {
            const res = await fetch(`${this.basePath}/${contentId}/contentImage`)
            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true}

        }

    }

    async updateContentImage(contentId, image) {
        try {
            const formData = new FormData();
            formData.append("image", image, image.name)
            // TODO: Verificar si esta bien el contentDetails.id o como seria esa parte
            const res = await fetch(`${this.basePath}/${contentId}/contentImage`, {
                method: 'PUT',
                headers: {},
                // TODO: contentDetails ya tiene que ser un objeto en formato JSON para mandarle con al info
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
                method: 'DELETE'
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
                headers: {
                    'Content-Type': APPLICATION_JSON_TYPE,
                },
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


    async getContentByType(contentType, pageNumber, pageSize) {
        try {
            const apiUrl = `${this.basePath}/${contentType}`
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

    // TODO: Ver bien ste que seria con paginacion y filtros
    // TODO: Filters seria un objeto que adentro tiene el genre, duration y sortBy
    async filterContentByType(contentType, pageNumber, pageSize, filters) {
        try {
            const apiUrl = `${this.basePath}/${contentType}/filters`
            const params = {pageNumber: pageNumber, pageSize: pageSize, ...filters}
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

    async getContentReviewers(contentId) {
        try {
            const res = await fetch(`${this.basePath}/${contentId}/reviewers`)
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
