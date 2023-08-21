import {paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";

export const useListApi = (signOut, navigate) => {
    return new ListsApi(navigate, signOut)
}
export class ListsApi {
    constructor(navigate, signOut) {
        this.basePath = `${paths.BASE_URL_API}${paths.LISTS}`
        this.navigate = navigate
        this.signOut = signOut
    }


    async getUserWatchListContentIds(userId) {
        try {
            const res = await fetch(`${this.basePath}/watchListContentIds/${userId}`, {
                method: 'GET',
                headers: authCheck({}),
            })

            if(res.status === 200) {
                return {error: false, data: await res.json()}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }

    async getUserViewedListContentIds(userId) {
        try {
            const res = await fetch(`${this.basePath}/viewedListContentIds/${userId}`, {
                method: 'GET',
                headers: authCheck({}),
            })

            if(res.status === 200) {
                return {error: false, data: await res.json()}
            } else {
                return {error: true, errorCode: res.status}
            }
        } catch (e) {
            return {error: true, errorCode: e.response.status || 500}
        }
    }
}