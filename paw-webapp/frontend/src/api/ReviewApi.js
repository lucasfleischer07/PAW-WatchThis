import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {authCheck} from "../scripts/authCheck";
import {genericFetchWithQueryParams, genericRequest} from "./GenericRequest";

export class ReviewApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.REVIEWS}`
    }

    async getReviews(userId, contentId, page, reported = false) {
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
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async getSpecificReview(review) {
        const options = {method: 'GET', headers: authCheck({})}
        return await genericRequest(this.basePath, review, options)
    }

    async reviewsCreation(userId, contentId, type, reviewDetails) {
        const apiUrl = `${this.basePath}`
        reviewDetails.userId = userId
        reviewDetails.contentId = contentId
        reviewDetails.type = type
        const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(reviewDetails)}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async deleteReview(reviewId) {
        const apiUrl = `${this.basePath}/${reviewId}`
        const options = {method: 'DELETE', headers: authCheck({})}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async reviewEdition(reviewId, userId, contentId, reviewDetails) {
        const apiUrl = `${this.basePath}/${reviewId}`
        reviewDetails.userId = userId
        reviewDetails.contentId = contentId
        const options = {method: 'PUT', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(reviewDetails)}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async reviewThumbUp(reviewId, userId) {
        const apiUrl = `${this.basePath}/${reviewId}/thumbUp`
        let bodyRequest = {userId: userId}
        const options = {method: 'PUT', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(bodyRequest)}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async reviewThumbDown(reviewId, userId) {
        const apiUrl = `${this.basePath}/${reviewId}/thumbDown`
        let bodyRequest = {userId: userId}
        const options = {method: 'PUT', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(bodyRequest)}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async getReviewReports(filter= '') {
        const apiUrl = `${this.basePath}/reports`
        const params = {reason: filter}
        const options = {method: 'GET', headers: authCheck({})}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async addReviewReport(reviewId, userId, reviewReportReasons) {
        const apiUrl = `${this.basePath}/${reviewId}/reports`
        reviewReportReasons.userId = userId
        const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(reviewReportReasons)}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async deleteReviewReport(reviewId) {
        const apiUrl = `${this.basePath}/${reviewId}/reports`
        const options = {method: 'DELETE', headers: authCheck({})}
        const params = {}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }
}