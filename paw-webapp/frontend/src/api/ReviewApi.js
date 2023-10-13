import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";
import {ListsApi} from "./ListsApi";
import {genericFetchWithQueryParams, genericRequest} from "./GenericRequest";

export class ReviewApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.REVIEWS}`
    }

    async getReviews(userId, contentId, pageNumber) {
        const apiUrl = `${this.basePath}`
        let params
        if(userId == null) {
            params = {contentId: contentId, page: pageNumber}
        } else {
            params = {userId: userId, page: pageNumber}
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
        const params = {userId: userId, contentId: contentId, type: type}
        const options = {method: 'POST', body: JSON.stringify(reviewDetails), headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async deleteReview(reviewId, userId) {
        const apiUrl = `${this.basePath}/${reviewId}`
        const options = {method: 'DELETE', headers: authCheck({})}
        const params = {userId: userId}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async reviewEdition(reviewId, userId, reviewDetails) {
        const apiUrl = `${this.basePath}/${reviewId}`
        const options = {method: 'PUT', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(reviewDetails)}
        const params = {userId: userId}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async reviewThumbUp(reviewId, userId) {
        const apiUrl = `${this.basePath}/${reviewId}/thumbUp`
        const options = {method: 'PUT', headers: authCheck({}), body: {}}
        const params = {userId: userId}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async reviewThumbDown(reviewId, userId) {
        const apiUrl = `${this.basePath}/${reviewId}/thumbDown`
        const options = {method: 'PUT', headers: authCheck({}), body: {}}
        const params = {userId: userId}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async getReviewReports(userId, filter= '') {
        const apiUrl = `${this.basePath}/reports`
        const params = {userId: userId, reason: filter}
        const options = {method: 'GET', headers: authCheck({})}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async addReviewReport(reviewId, userId, reviewReportReasons) {
        const apiUrl = `${this.basePath}/${reviewId}/reports`
        const options = {method: 'POST', headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}), body: JSON.stringify(reviewReportReasons)}
        const params = {userId: userId}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }

    async deleteReviewReport(reviewId, userId) {
        const apiUrl = `${this.basePath}/${reviewId}/reports`
        const options = {method: 'DELETE', headers: authCheck({})}
        const params = {userId: userId}
        return genericFetchWithQueryParams(apiUrl, options, params)
    }
}