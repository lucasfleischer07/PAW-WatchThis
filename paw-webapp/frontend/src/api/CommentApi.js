import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";

export class CommentApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.COMMENT}`
    }

    async getReviewComments(reviewId, pageNumber, pageSize) {
        try {
            const apiUrl = `${this.basePath}/${reviewId}`
            const params = {pageNumber: pageNumber, pageSize: pageSize}
            const options = {}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)
            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (error) {
            return {error: true}
        }

    }

    async createComment(reviewId, commentDetails) {
        try {
            const res = await fetch(`${this.basePath}/${reviewId}/add`, {
                method: 'POST',
                headers: {
                    'Content-Type': APPLICATION_JSON_TYPE,
                },
                // TODO: contentDetails ya tiene que ser un objeto en formato JSON para mandarle con al info
                body: JSON.stringify(commentDetails)
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

    async commentReviewDelete(commentId) {
        try {
            await fetch(`${this.basePath}/delete/${commentId}`, {
                method: 'DELETE'
            })
            return {error: false, data: []}
        } catch (e) {
            return {error: true}
        }

    }
}