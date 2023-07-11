import {APPLICATION_JSON_TYPE, paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";
export class UserApi {
    constructor() {
        this.basePath = `${paths.BASE_URL_API}${paths.USERS}`
    }

    async userCreate(userDetails) {
        try {
            const res = await fetch(`${this.basePath}`, {
                method: 'POST',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),
                body: JSON.stringify(userDetails)
            })
            if(res.status === 201) {
                return {error: false, data: await res.json()}
            } else {
                return {error: true, errorCode: res.status, data: await res.json()}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async getUserInfo(userId) {
        try {
            const res = await fetch(`${this.basePath}/${userId}`, {
                method: 'GET',
                headers: authCheck({})
            })
            if(res.status === 200) {
                return {error: false, data: await res.json()}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500 }
        }
    }

    async getUserReviews(userId, pageNumber) {
        try {
            const apiUrl = `${this.basePath}/${userId}/reviews`
            const params = {page: pageNumber}
            const options = {headers: authCheck({})}
            const res = await fetchWithQueryParamsApi(apiUrl, params, options)
            if(res.status === 200) {
                return {error: false, data: res.data, totalPages: res.totalPages}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async updateUserProfileImage(userId, image) {
        try {
            const formData = new FormData();
            formData.set("image", image)
            const res = await fetch(`${this.basePath}/${userId}/profileImage`, {
                method: 'PUT',
                headers: authCheck({}),
                body: formData
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

    async updateUserProfileInfo(userId, userDetails) {
        try {
            const res = await fetch(`${this.basePath}/${userId}/editProfile`, {
                method: 'PUT',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE,}),
                body: JSON.stringify(userDetails)
            })
            console.log(res.status)
            if(res.status === 200) {
                return {error: false, data: []}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async loginForgotPassword(userEmail) {
        try {
            const res = await fetch(`${this.basePath}/login/${userEmail}/forgotPassword`, {
                method: 'POST',
                headers: authCheck({'Content-Type': APPLICATION_JSON_TYPE}),
                body: {}
            })
            if(res.status === 204) {
                return {error: false, data: []}
            } else {
                return {error: false, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async login(email, password) {
        try {
            const loggedUserInfo = email + ":" + password;
            const hash = btoa(loggedUserInfo);
            const res = await fetch(`${this.basePath}/loggedUser`, {
                method: 'GET',
                headers: {
                    Authorization: "Basic " + hash
                }
            })
            if(res.status !== 204 && res.status !== 401) {
                return {error: false, data: await res.json(), header: res.headers.get("Authorization")?.toString().split(" ")[1]}
            } else if(res.status === 401) {
                return {error: true, data: [], errorCode: res.status}
            } else {
                return {error: false, data: []}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async promoteUserToAdmin(userId) {
        try {
            const res = await fetch(`${this.basePath}/promoteUser/${userId}`, {
                method: 'PUT',
                headers: authCheck({}),
                body: {}
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

    async getReviewsLike(userId) {
        try {
            const res = await fetch(`${this.basePath}/${userId}/reviewsLikedByUser`, {
                method: 'GET',
                headers: authCheck({}),
            })

            if(res.status === 200) {
                const jsonData = await res.json();
                const jsonString = JSON.stringify(jsonData);
                if (jsonString === '{}') {
                    return { error: false, data: [] };
                } else {
                    return { error: false, data: jsonData };
                }
            } else {
                return {error: true, errorCode: res.status}
            }

        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async getReviewsDislike(userId) {
        try {
            const res = await fetch(`${this.basePath}/${userId}/reviewsDislikedByUser`, {
                method: 'GET',
                headers: authCheck({}),
            })
            if(res.status === 200) {
                const jsonData = await res.json();
                const jsonString = JSON.stringify(jsonData);
                if (jsonString === '{}') {
                    return { error: false, data: [] };
                } else {
                    return { error: false, data: jsonData };
                }
            } else {
                return {error: true, errorCode: res.status}
            }

        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }
    
}