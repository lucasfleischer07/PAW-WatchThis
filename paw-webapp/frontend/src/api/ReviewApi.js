import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";
import {ListsApi} from "./ListsApi";

export class ReviewApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.REVIEWS}`
    }

    async getReviews(userId, contentId, pageNumber) {
        try {
            const apiUrl = `${this.basePath}`
            let params
            if(userId == null) {
                params = {contentId: contentId, page: pageNumber}
            } else {
                params = {userId: userId, page: pageNumber}
            }
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

    async getSpecificReview(review) {
        try {
            let url
            if(typeof review === 'number') {
                url = `${this.basePath}/${review}`
            } else {
                url = review
            }

            const res = await fetch(`${url}`, {
                method: 'GET',
                headers: authCheck({})
            })
            if(res.status === 200) {
                return {error: false, data: await res.json()}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            console.log("Entre aca 2")
            return {error: true, errorCode: e.response.status || 500}
        }

    }

    async reviewsCreation(userId, contentId, type, reviewDetails) {
        try {
            const apiUrl = `${this.basePath}`
            const params = {userId: userId, contentId: contentId, type: type}
            const options = {method: 'POST', body: JSON.stringify(reviewDetails), headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)
            if(res.status === 201) {
                return {error: false}
            } else {
                return {error: true, errorCode: res.status }
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async deleteReview(reviewId, userId) {
        try {
            const apiUrl = `${this.basePath}/${reviewId}`
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

    async reviewEdition(reviewId, userId, reviewDetails) {
        try {
            const apiUrl = `${this.basePath}/${reviewId}`
            const options = {method: 'PUT', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(reviewDetails)}
            const params = {userId: userId}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)

            if(res.status === 204) {
                return {error: false, data: []}
            } else {
                return {error: true, data: [], errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async reviewThumbUp(reviewId, userId) {
        try {
            const apiUrl = `${this.basePath}/${reviewId}/thumbUp`
            const options = {method: 'PUT', headers: authCheck({}), body: {}}
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

    async reviewThumbDown(reviewId, userId) {
        try {
            const apiUrl = `${this.basePath}/${reviewId}/thumbDown`
            const options = {method: 'PUT', headers: authCheck({}), body: {}}
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

    async getReviewReports(userId, filter= '') {
        try {
            const apiUrl = `${this.basePath}/reports`
            const params = {userId: userId, reason: filter}
            const options = {headers: authCheck({})}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)
            if(res.status === 200) {
                return {error: false, data: await res.data, totalPages: res.totalPages, totalReviewsReports: res.totalReviewsReports, totalCommentsReports: res.totalCommentsReports}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async addReviewReport(reviewId, userId, reviewReportReasons) {
        try {
            const apiUrl = `${this.basePath}/${reviewId}/reports`
            const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(reviewReportReasons)}
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

    async deleteReviewReport(reviewId, userId) {
        try {
            const apiUrl = `${this.basePath}/${reviewId}/reports`
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
}