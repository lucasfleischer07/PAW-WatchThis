import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {authCheck} from "../scripts/authCheck";
import {genericFetchWithQueryParams, genericRequest} from "./GenericRequest";

export class ReviewApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.REVIEWS}`
    }

    async getReviews(authFunctions, userId, contentId, page, reported = false) {
        const apiUrl = `${this.basePath}`
        let params
        if(userId == null) {
            params = {contentId: contentId, page: page}
        } else if(reported) {
            params = {reportedById: userId}
        } else if(contentId == null) {
            params = {userId: userId, page: page}
        } else {
            params = {contentId: contentId, userId: userId, page: page}
        }
        const options = {method: 'GET', headers: authCheck({})}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async getSpecificReview(authFunctions, review) {
        const options = {method: 'GET', headers: authCheck({})}
        return await genericRequest(this.basePath, review, options, authFunctions)
    }

    async reviewsCreation(authFunctions, userId, contentId, type, reviewDetails) {
        const apiUrl = `${this.basePath}`
        reviewDetails.userId = userId
        reviewDetails.contentId = contentId
        reviewDetails.type = type
        const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(reviewDetails)}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async deleteReview(authFunctions, reviewId) {
        const apiUrl = `${this.basePath}/${reviewId}`
        const options = {method: 'DELETE', headers: authCheck({})}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async reviewEdition(authFunctions, reviewId, userId, contentId, reviewDetails) {
        const apiUrl = `${this.basePath}/${reviewId}`
        reviewDetails.userId = userId
        reviewDetails.contentId = contentId
        const options = {method: 'PUT', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(reviewDetails)}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async reviewThumbUpPost(authFunctions, reviewId, userId) {
        const apiUrl = `${this.basePath}/${reviewId}/upVote`
        let bodyRequest = {userId: userId}
        const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(bodyRequest)}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async reviewThumbUpDelete(authFunctions, reviewId, userId) {
        const apiUrl = `${this.basePath}/${reviewId}/upVoteUserId/${userId}`
        const options = {method: 'DELETE', headers: authCheck({})}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async reviewThumbDownPost(authFunctions, reviewId, userId) {
        const apiUrl = `${this.basePath}/${reviewId}/downVote`
        let bodyRequest = {userId: userId}
        const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(bodyRequest)}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async reviewThumbDownDelete(authFunctions, reviewId, userId) {
        const apiUrl = `${this.basePath}/${reviewId}/downVoteUserId/${userId}`
        const options = {method: 'DELETE', headers: authCheck({})}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async getReviewReports(authFunctions, reason= '', page) {
        const apiUrl = `${this.basePath}/reports`
        let params = {page: page}
        if(reason !== '') {
            params.reason = reason
        }
        const options = {method: 'GET', headers: authCheck({})}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async addReviewReport(authFunctions, reviewId, userId, reviewReportReasons) {
        const apiUrl = `${this.basePath}/${reviewId}/reports`
        reviewReportReasons.userId = userId
        const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(reviewReportReasons)}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async deleteReviewReport(authFunctions, reviewId) {
        const apiUrl = `${this.basePath}/${reviewId}/reports`
        const options = {method: 'DELETE', headers: authCheck({})}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async getReviewsLike(authFunctions, userId) {
        const options = {method: 'GET', headers: authCheck({})}
        const apiUrl = `${this.basePath}`
        const params = {likedById: userId}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }

    async getReviewsDislike(authFunctions, userId) {
        const options = {method: 'GET', headers: authCheck({})}
        const apiUrl = `${this.basePath}`
        const params = {dislikedById: userId}
        return genericFetchWithQueryParams(apiUrl, options, params, authFunctions)
    }
}