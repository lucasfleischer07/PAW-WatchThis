import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";

export class ReportsApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.REPORTS}`
    }

    async getReportsByType(type, pageNumber, pageSize) {
        try {
            const apiUrl = `${this.basePath}/${type}`
            const params = {pageNumber: pageNumber, pageSize: pageSize}
            const options = {}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)
            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true}
        }
    }

    // TODO: VERIFICAR VIEN ESTE PORUQE NI IDEA
    async addReviewReport(reviewId, reviewReportReasons) {
        try {
            const res = await fetch(`${this.basePath}/review/${reviewId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': APPLICATION_JSON_TYPE,
                },
                // TODO: reviewReportReasons ya tiene que ser un objeto en formato JSON para mandarle con al info
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

    // TODO: VERIFICAR VIEN ESTE PORUQE NI IDEA
    async addCommentReport(reviewId, commentReportReasons) {
        try {
            const res = await fetch(`${this.basePath}/comment/${reviewId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': APPLICATION_JSON_TYPE,
                },
                // TODO: commentReportReasons ya tiene que ser un objeto en formato JSON para mandarle con al info
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

    async deleteReport(contentId, type) {
        try {
            const res = await fetch(`${this.basePath}/deleteReport/${type}/${contentId}`, {
                method: 'DELETE'
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