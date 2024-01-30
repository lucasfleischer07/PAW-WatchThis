import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";
import {genericFetchWithQueryParams, genericRequest} from "./GenericRequest";

export class CommentApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.COMMENT}`
    }



    async getReviewComments(authFunctions, reviewId, userId = null, reports = false) {
        const apiUrl = `${this.basePath}`
        const options = {method: 'GET', headers: authCheck({})}
        let params
        if(reports) {
            params = {reportedById: userId}
        } else {
            params = {reviewId: reviewId}
        }
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async getSpecificComment(authFunctions, comment) {
        const options = {method: 'GET', headers: authCheck({})}
        return await genericRequest(this.basePath, comment, options, authFunctions)
    }

    async createComment(authFunctions, reviewId, userId, commentDetails) {
        const apiUrl = `${this.basePath}`
        commentDetails.userId = userId
        commentDetails.reviewId = reviewId
        const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(commentDetails)}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async commentDelete(authFunctions, commentId) {
        const apiUrl = `${this.basePath}/${commentId}`
        const options = {method: 'DELETE', headers: authCheck({})}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async getCommentsReports(authFunctions, reason= '', page) {
        const apiUrl = `${this.basePath}/reports`
        let params = {page: page}
        if(reason !== '') {
            params.reason = reason
        }
        const options = {method: 'GET', headers: authCheck({})}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async addCommentReport(authFunctions, userId, commentId, commentReportReasons) {
        const apiUrl = `${this.basePath}/${commentId}/reports`
        commentReportReasons.userId = userId
        const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(commentReportReasons)}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async deleteCommentReports(authFunctions, commentId) {
        const apiUrl = `${this.basePath}/${commentId}/reports`
        const options = {method: 'DELETE', headers: authCheck({})}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }
}