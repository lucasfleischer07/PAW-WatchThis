import {fetchWithQueryParamsApi} from "./FetchWithQueryParams";


export async function genericRequest(basePath, parameter, options, authFunctions){
    try {
        let url
        if(typeof parameter === 'number') {
            url = `${basePath}/${parameter}`
        } else {
            url = parameter
        }
        const res = await fetch(url, options)

        if(res.headers != null && res.headers.get('X-Refresh-Token')){
            authFunctions.resetTokens(res.headers.get('Access-Token'), res.headers.get('X-Refresh-Token'));
        }

        if(options.method === 'GET') {
            if(res.status === 200 || res.status !== 204) {
                return {error: false, data: await res.json()}
            } else if(res.status === 204) {
                return {error: false, data: []}
            } else {
                return {error: true, errorCode: res.status}
            }
        } else if(options.method === 'POST') {
            const status = res.status;
            return {error: status !== 201 && status !== 204, errorCode: status, data: (status === 204 || status === 404) ? [] : await res.json()};
        } else if(options.method === 'PUT') {
            if(res.status === 200 || res.status === 204) {
                return {error: false, data: []}
            }
        }

    } catch (e) {
        return {error: true, errorCode: e.response? e.response.status : 500}
    }
}


export async function genericFetchWithQueryParams(apiUrl, options, params, authFunctions) {

    try {
        const res = await fetchWithQueryParamsApi(apiUrl, params, options)
        if(res.headers != null && res.headers.get('X-Refresh-Token')){
            authFunctions.resetTokens(res.headers.get('Access-Token'), res.headers.get('X-Refresh-Token'));
        }
        if(options.method === 'GET') {
            if(res.status === 200) {
                return {error: false, data: await res.data, totalPages: res.totalPages, totalReviewsReports: res.totalReviewsReports, totalCommentsReports: res.totalCommentsReports, totalUserReviews: res.totalUserReviews, totalContent: res.totalContent}
            }
        } else if(options.method === 'POST') {
            if(res.status === 201 || res.status === 204) {
                return {error: false, data: []}
            } else if(res.status === 200) {
                return {error: false, data: await res.data, totalPages: res.totalPages}
            }
        } else if(options.method === 'PUT') {
            if(res.status === 200 || res.status === 204) {
                return {error: false, data: []}
            }
        } else if(options.method === 'DELETE') {
            if(res.status === 204) {
                return {error: false, data: []}
            }

        }

        if(res.status !== 200 && res.status !== 201 && res.status !== 204) {
            return {error: true, errorCode: res.status}
        }

    } catch (e) {
        console.log("Exc: " + e)
        return {error: true, errorCode: e.response? e.response.status : 500}
    }
}
