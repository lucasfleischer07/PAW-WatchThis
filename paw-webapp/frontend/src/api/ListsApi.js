import {paths} from "../paths";
import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";
import {authCheck} from "../scripts/authCheck";
import {genericRequest} from "./GenericRequest";

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
        const url = `${this.basePath}/watchListContentIds`
        const options = {method: 'GET', headers: authCheck({})}
        return await genericRequest(url, userId, options)
    }

    async getUserViewedListContentIds(userId) {
        const url = `${this.basePath}/viewedListContentIds`
        const options = {method: 'GET', headers: authCheck({})}
        return await genericRequest(url, userId, options)
    }
}