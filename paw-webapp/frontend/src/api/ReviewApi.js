import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";

export class ReviewApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.REVIEWS}`
    }

    async reviews(contentId, pageNumber) {
        try {
            const apiUrl = `${this.basePath}/${contentId}`
            const params = {pageNumber: pageNumber, pageSize: 10}
            const options = {headers: authCheck({})}
            const res =  await fetchWithQueryParamsApi(apiUrl, params, options)
            if(res.status !== 204) {
                return {error: false, data: await res.data, totalPages: res.totalPages}
            } else {
                return {error: false, data: [], totalPages: res.totalPages}
            }
        } catch (e) {
            return {error: true}
        }

    }

    async getSpecificReview(reviewId) {
        try {
            const res = await fetch(`${this.basePath}/specificReview/${reviewId}`, {
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

    async reviewsCreation(reviewId, type, reviewDetails) {
        try {
            const res = await fetch(`${this.basePath}/create/${type}/${reviewId}`, {
                method: 'POST',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),
                body: JSON.stringify(reviewDetails)
            })

            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: true, data: []}
            }
        } catch (e) {
            return {error: true}
        }
    }

    async deleteReview(reviewId) {
        try {
            await fetch(`${this.basePath}/delete/${reviewId}`, {
                method: 'DELETE',
                headers: authCheck({})
            })
            return {error: false, data: []}
        } catch (e) {
            return {error: true}
        }
    }

    async reviewEdition(reviewId, reviewDetails) {
        try {
            const res = await fetch(`${this.basePath}/editReview/${reviewId}`, {
                method: 'PUT',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),
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

    async reviewThumbDown(reviewId) {
        try {
            // TODO: Verificar si esta bien el contentId o seri el contentDetails.id o como seria esa parte
            const res = await fetch(`${this.basePath}/reviewReputation/thumbDown/${reviewId}`, {
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

}