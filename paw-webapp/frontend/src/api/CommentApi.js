import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";

export class CommentApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.COMMENT}`
    }

    async getReviewComments(reviewId, pageNumber) {
        try {
            const apiUrl = `${this.basePath}/${reviewId}`
            const params = {pageNumber: pageNumber, pageSize: 10}
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

    async createComment(reviewId, commentDetails) {
        try {
            const res = await fetch(`${this.basePath}/${reviewId}/add`, {
                method: 'POST',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),
                body: JSON.stringify(commentDetails)
            })
            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            console.log(e)
            return {error: true, errorCode: e.statusCode || e.status || 500}
        }

    }

    async commentDelete(commentId) {
        try {
            await fetch(`${this.basePath}/delete/${commentId}`, {
                method: 'DELETE',
                headers: authCheck({})
            })
            return {error: false, data: []}
        } catch (e) {
            return {error: true, errorCode: e.statusCode || e.status || 500}
        }

    }
}