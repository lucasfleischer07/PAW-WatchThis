import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";
import {ListsApi} from "./ListsApi";

export class ReviewApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.REVIEWS}`
    }

    async reviews(contentId, pageNumber) {
        try {
            const apiUrl = `${this.basePath}`
            const params = {contentId: contentId, page: pageNumber}
            const options = {headers: authCheck({})}
            const res =  await fetchWithQueryParamsApi(apiUrl, params, options)
            if(res.status === 200) {
                const jsonData = await res.data;
                const jsonString = JSON.stringify(jsonData);
                if (jsonString === '{}') {
                    return {error: false, data: [], totalPages: res.totalPages}
                } else {
                    return {error: false, data: await res.data, totalPages: res.totalPages}
                }
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }

    }

    async getSpecificReview(reviewId) {
        try {
            const res = await fetch(`${this.basePath}/${reviewId}`, {
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

    async reviewsCreation(reviewId, type, reviewDetails) {
        try {
            const res = await fetch(`${this.basePath}/${type}/${reviewId}`, {
                method: 'POST',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),
                body: JSON.stringify(reviewDetails)
            })

            if(res.status === 201) {
                return {error: false}
            } else {
                return {error: true, errorCode: res.status }
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async deleteReview(reviewId) {
        try {
            const res = await fetch(`${this.basePath}/${reviewId}`, {
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

    async reviewEdition(reviewId, reviewDetails) {
        try {
            const res = await fetch(`${this.basePath}/${reviewId}`, {
                method: 'PUT',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),
                body: JSON.stringify(reviewDetails)
            })
            if(res.status === 204) {
                return {error: false, data: []}
            } else {
                return {error: true, data: [], errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async reviewThumbUp(reviewId) {
        try {
            const res = await fetch(`${this.basePath}/${reviewId}/thumbUp`, {
                method: 'PUT',
                headers: authCheck({}),
                body: {}
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

    async reviewThumbDown(reviewId) {
        try {
            const res = await fetch(`${this.basePath}/${reviewId}/thumbDown`, {
                method: 'PUT',
                headers: authCheck({}),
                body: {}
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
}