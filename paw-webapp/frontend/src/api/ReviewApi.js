import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";

export class ReviewApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.REVIEWS}`
    }

    async reviews(contentId, pageNumber, pageSize) {
        const apiUrl = `${this.basePath}/${contentId}`
        const params = {pageNumber: pageNumber, pageSize: pageSize}
        const options = {}
        return await fetchWithQueryParamsApi(apiUrl, params, options)
    }

    async reviewsCreation(reviewId, type, reviewDetails) {
        try {
            const res = await fetch(`${this.basePath}/create/${type}/${reviewId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': APPLICATION_JSON_TYPE,
                },
                // TODO: reviewDetails ya tiene que ser un objeto en formato JSON para mandarle con al info
                body: JSON.stringify(reviewDetails)
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

    async deleteReview(reviewId) {
        try {
            const res = await fetch(`${this.basePath}/delete/${reviewId}`, {
                method: 'DELETE'
            })
            // TODO: Ver bien este que devolver si vacio o no, y creo que vacio pero ndea
            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true}
        }
    }

    async reviewEdition(reviewId, reviewDetails) {
        try {
            // TODO: Verificar si esta bien el reviewId o seria el reviewDetails.id o como seria esa parte
            const res = await fetch(`${this.basePath}/editReview/${reviewId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': APPLICATION_JSON_TYPE,
                },
                // TODO: reviewDetails ya tiene que ser un objeto en formato JSON para mandarle con al info
                body: JSON.stringify(reviewDetails)
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

    async reviewThumbUp(reviewId) {
        try {
            // TODO: Verificar si esta bien el contentId o seri el contentDetails.id o como seria esa parte
            const res = await fetch(`${this.basePath}/reviewReputation/thumbUp/${reviewId}`, {
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

    async reviewThumbDown(reviewId) {
        try {
            // TODO: Verificar si esta bien el contentId o seri el contentDetails.id o como seria esa parte
            const res = await fetch(`${this.basePath}/reviewReputation/thumbDown/${reviewId}`, {
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

}