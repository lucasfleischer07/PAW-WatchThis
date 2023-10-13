import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";

export class CommentApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.COMMENT}`
    }

    async getReviewComments(reviewId) {
        try {
            const apiUrl = `${this.basePath}`
            const options = {headers: authCheck({})}
            const params = {reviewId: reviewId}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)

            if(res.status === 200) {
                return {error: false, data: await res.data, totalPages: res.totalPages}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }

    }

    async getSpecificComment(comment) {
        try {
            let url
            console.log(comment)
            if(typeof comment === 'number') {
                url = `${this.basePath}/${comment}`
            } else {
                url = comment
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
            return {error: true, errorCode: e.response.status || 500}
        }

    }

    async createComment(reviewId, userId, commentDetails) {
        try {

            const apiUrl = `${this.basePath}`
            const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(commentDetails)}
            const params = {userId: userId, reviewId: reviewId}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)

            if(res.status === 201) {
                return {error: false, data: []}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }

    }

    async commentDelete(commentId, userId) {
        try {
            const apiUrl = `${this.basePath}/${commentId}`
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

    async getCommentsReports(userId, filter= '') {
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

    async addCommentReport(userId, commentId, commentReportReasons) {
        try {
            const apiUrl = `${this.basePath}/${commentId}/reports`
            const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(commentReportReasons)}
            const params = {userId: userId}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)
            if(res.status === 200) {
                return {error: false, data: await res.data, totalPages: res.totalPages}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async deleteCommentReports(commentId, userId) {
        try {
            const apiUrl = `${this.basePath}/${commentId}/reports`
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