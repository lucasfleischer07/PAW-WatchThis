import parse from "parse-link-header";

export async function fetchWithQueryParamsApi(url, queryParams = {}, options = {}) {
    try {
        const urlObj = new URL(url);
        Object.keys(queryParams).forEach(key => urlObj.searchParams.append(key, queryParams[key]));
        const res = await fetch(urlObj, options)
        let totalPages = 1
        let totalReviews = 0
        if(res.headers.get('Link')) {
            totalPages = parse(res.headers.get('Link')).last.page;
        }

        if(res.headers.get('Total-Reviews')) {
            totalReviews = res.headers.get('Total-Reviews')
        }
        if(res.ok) {
            return res.json().then(data => ({error:false, data, totalPages, status: res.status, totalReviews: totalReviews}));
        } else {
            return {error: true, status: res.status}
        }
    } catch (e) {
        return {error: true, errorCode: e.response.status}
    }

}