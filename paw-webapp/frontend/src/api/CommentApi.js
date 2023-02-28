import {APPLICATION_JSON_TYPE, paths} from "../paths";

export class CommentApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.COMMENT}`
    }

    async getReviewComments(reviewId) {
        const res = await fetch(`${this.basePath}/${reviewId}`)
        return await res.json()
    }

    async createComment(reviewId, commentDetails) {
        // TODO: Verificar si esta bien el contentDetails.id o como seria esa parte
        return  await fetch(`${this.basePath}/${reviewId}/add`, {
            method: 'POST',
            headers: {
                'Content-Type': APPLICATION_JSON_TYPE,
            },
            // TODO: contentDetails ya tiene que ser un objeto en formato JSON para mandarle con al info
            body: JSON.stringify(commentDetails)
        })
    }

    async commentReviewDelete(commentId) {
        await fetch(`${this.basePath}/delete/${commentId}`, {
            method: 'DELETE'
        })
    }
}