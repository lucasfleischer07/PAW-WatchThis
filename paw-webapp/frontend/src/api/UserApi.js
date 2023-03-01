import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";

export class UserApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.USERS}`
    }

    async userCreate(userDetails) {
        try {
            const res = await fetch(`${this.basePath}`, {
                method: 'POST',
                headers: {
                    'Content-Type': APPLICATION_JSON_TYPE,
                },
                // TODO: userDetails ya tiene que ser un objeto en formato JSON para mandarle con al info
                body: JSON.stringify(userDetails)
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

    async getUserInfo(userId) {
        try {
            const res = await fetch(`${this.basePath}/${userId}`)
            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true}
        }
    }

    async getUserReviews(userId, pageNumber) {
        try {
            const apiUrl = `${this.basePath}/${userId}/reviews`
            const params = {pageNumber: pageNumber}
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

    // TODO: Chequear bien este
    async getUserProfileImage(userId) {
        try {
            const res = await fetch(`${this.basePath}/${userId}/profileImage`)
            if(res.status !== 204) {
                return {error: false, data: await res.json()}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true}

        }

    }

    async updateUserProfileImage(userId, image) {
        try {
            const formData = new FormData();
            formData.append("image", image, image.name)
            // TODO: Verificar si esta bien el contentDetails.id o como seria esa parte
            const res = await fetch(`${this.basePath}/${userId}/profileImage`, {
                method: 'PUT',
                headers: {},
                // TODO: contentDetails ya tiene que ser un objeto en formato JSON para mandarle con al info
                body: formData
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

    async updateUserProfileInfo(userId, userDetails) {
        try {
            // TODO: Verificar si esta bien el userDetails.id o como seria esa parte
            const res = await fetch(`${this.basePath}/editInfo/${userId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': APPLICATION_JSON_TYPE,
                },
                // TODO: contentDetails ya tiene que ser un objeto en formato JSON para mandarle con al info
                body: JSON.stringify(userDetails)
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