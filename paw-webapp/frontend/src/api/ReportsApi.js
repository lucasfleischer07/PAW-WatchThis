import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";

export class ReportsApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.REPORTS}`
    }

    async getReportsByType(type, pageNumber, filter= '') {
        try {
            const apiUrl = `${this.basePath}/${type}`
            const params = {reason: filter, pageNumber: pageNumber, pageSize: 10}
            const options = {headers: authCheck({})}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)
            if(res.status !== 204) {
                return {error: false, data: await res.data, totalPages: res.totalPages}
            } else {
                return {error: false, data: [], totalPages: res.totalPages}
            }
        } catch (e) {
            return {error: true, errorCode: e.statusCode || e.status || 500}
        }
    }

    // TODO: VERIFICAR BIEN ESTE PORUQE NI IDEA
    async addReviewReport(reviewId, reviewReportReasons) {
        try {
            const res = await fetch(`${this.basePath}/review/${reviewId}`, {
                method: 'POST',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),
                body: JSON.stringify(reviewReportReasons)
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

    // TODO: VERIFICAR BIEN ESTE PORUQE NI IDEA
    async addCommentReport(commentId, commentReportReasons) {
        try {
            const res = await fetch(`${this.basePath}/comment/${commentId}`, {
                method: 'POST',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),
                body: JSON.stringify(commentReportReasons)
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

    async deleteReport(commentOrReviewId, type) {
        try {
            await fetch(`${this.basePath}/deleteReport/${type}/${commentOrReviewId}`, {
                method: 'DELETE',
                headers: authCheck({})
            })
            return {error: false, data: []}
        } catch (e) {
            return {error: true}
        }
    }
}