import {APPLICATION_JSON_TYPE, paths} from "../paths";

export class ReportsApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.REPORTS}`
    }

    async getReportsByType(type) {
        const res = await fetch(`${this.basePath}/${type}`)
        return await res.json()
    }

    // TODO: VERIFICAR VIEN ESTE PORUQE NI IDEA
    async addReviewReport(reviewId, reviewReportReasons) {
        return  await fetch(`${this.basePath}/review/${reviewId}`, {
            method: 'POST',
            headers: {
                'Content-Type': APPLICATION_JSON_TYPE,
            },
            // TODO: reviewReportReasons ya tiene que ser un objeto en formato JSON para mandarle con al info
            body: JSON.stringify(reviewReportReasons)
        })
    }

    // TODO: VERIFICAR VIEN ESTE PORUQE NI IDEA
    async addCommentReport(reviewId, commentReportReasons) {
        return  await fetch(`${this.basePath}/comment/${reviewId}`, {
            method: 'POST',
            headers: {
                'Content-Type': APPLICATION_JSON_TYPE,
            },
            // TODO: commentReportReasons ya tiene que ser un objeto en formato JSON para mandarle con al info
            body: JSON.stringify(commentReportReasons)
        })
    }

    async deleteReport(contentId, type) {
        await fetch(`${this.basePath}/deleteReport/${type}/${contentId}`, {
            method: 'DELETE'
        })
    }
}