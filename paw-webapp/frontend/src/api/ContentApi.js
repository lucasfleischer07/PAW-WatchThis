import {APPLICATION_JSON_TYPE, paths} from "../paths";

export class ContentApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.CONTENT}`
    }

    async getLandingPage() {
        const res = await fetch(this.basePath)
        return await res.json()
    }

    async getSpecificContent(contentId) {
        const res = await fetch(`${this.basePath}/specificContent/${contentId}`)
        return await res.json()
    }

    async updateContent(contentDetails) {
        // TODO: Verificar si esta bien el contentDetails.id o como seria esa parte
        return  await fetch(`${this.basePath}/editInfo/${contentDetails.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': APPLICATION_JSON_TYPE,
            },
            // TODO: contentDetails ya tiene que ser un objeto en formato JSON para mandarle con al info
            body: JSON.stringify(contentDetails)
        })
    }

    // TODO: Chequear bien este
    async getContentImage(contentId) {
        const res = await fetch(`${this.basePath}/${contentId}/contentImage`)
        return await res.json()
    }

    async updateContentImage(contentId, image) {
        const formData = new FormData();
        formData.append("image", image, image.name)
        // TODO: Verificar si esta bien el contentDetails.id o como seria esa parte
        return  await fetch(`${this.basePath}/${contentId}/contentImage`, {
            method: 'PUT',
            headers: {},
            // TODO: contentDetails ya tiene que ser un objeto en formato JSON para mandarle con al info
            body: formData
        })
    }

    async deleteContent(contentId) {
        await fetch(`${this.basePath}/${contentId}/deleteContent`, {
            method: 'DELETE'
        })
    }

    async createContent(contentDetails) {
        // TODO: Verificar si esta bien el contentDetails.id o como seria esa parte
        return  await fetch(`${this.basePath}/create`, {
            method: 'POST',
            headers: {
                'Content-Type': APPLICATION_JSON_TYPE,
            },
            // TODO: contentDetails ya tiene que ser un objeto en formato JSON para mandarle con al info
            body: JSON.stringify(contentDetails)
        })
    }

    // TODO: Ver bien ste que seria con paginacion
    // async getContentByType(contentType) {
    //     const res = await fetch(`${this.basePath}/${contentType}`)
    //     return await res.json()
    // }

    // TODO: Ver bien ste que seria con paginacion y filtros
    // async filterContentByType(contentType) {
    //     const res = await fetch(`${this.basePath}/${contentType}/filters`)
    //     return await res.json()
    // }

    // TODO: Ver bien ste que seria con paginacion
    async getContentReviewers(contentId) {
        const res = await fetch(`${this.basePath}/${contentId}/reviewers`)
        return await res.json()
    }
}
