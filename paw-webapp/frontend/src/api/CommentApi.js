import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";
import {genericFetchWithQueryParams, genericRequest} from "./GenericRequest";

export class CommentApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.COMMENT}`
    }

    async getReviewComments(reviewId, userId = null, reports = false) {
        const apiUrl = `${this.basePath}`
        const options = {method: 'GET', headers: authCheck({})}
        let params
        if(reports) {
            params = {reportedById: userId}
        } else {
            params = {reviewId: reviewId}
        }
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async getSpecificComment(comment) {
        const options = {method: 'GET', headers: authCheck({})}
        return await genericRequest(this.basePath, comment, options)
    }

    async createComment(reviewId, userId, commentDetails) {
        const apiUrl = `${this.basePath}`
        const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(commentDetails)}
        const params = {userId: userId, reviewId: reviewId}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async commentDelete(commentId, userId) {
        const apiUrl = `${this.basePath}/${commentId}`
        const options = {method: 'DELETE', headers: authCheck({})}
        const params = {userId: userId}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async getCommentsReports(filter= '') {
        const apiUrl = `${this.basePath}/reports`
        const params = {reason: filter}
        const options = {method: 'GET', headers: authCheck({})}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async addCommentReport(userId, commentId, commentReportReasons) {
        const apiUrl = `${this.basePath}/${commentId}/reports`
        const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(commentReportReasons)}
        const params = {userId: userId}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async deleteCommentReports(commentId) {
        const apiUrl = `${this.basePath}/${commentId}/reports`
        const options = {method: 'DELETE', headers: authCheck({})}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }
}