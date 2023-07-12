import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";

export class CommentApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.COMMENT}`
    }

    async getReviewComments(reviewId) {
        try {
            const apiUrl = `${this.basePath}/${reviewId}`
            const options = {headers: authCheck({})}
            const params = {}
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

    async createComment(reviewId, commentDetails) {
        try {
            const res = await fetch(`${this.basePath}/${reviewId}/add`, {
                method: 'POST',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),
                body: JSON.stringify(commentDetails)
            })
            if(res.status === 201) {
                return {error: false, data: []}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }

    }

    async commentDelete(commentId) {
        try {
            const res = await fetch(`${this.basePath}/delete/${commentId}`, {
                method: 'DELETE',
                headers: authCheck({})
            })

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