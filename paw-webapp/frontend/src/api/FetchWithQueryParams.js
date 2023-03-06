import parse from "parse-link-header";

export async function fetchWithQueryParamsApi(url, queryParams = {}, options = {}) {
    try {
        const urlObj = new URL(url);
        Object.keys(queryParams).forEach(key => urlObj.searchParams.append(key, queryParams[key]));
        const res = await fetch(urlObj, options)
        const totalPages = parse(res.headers.get('Link')).last.page;
        return res.json().then(data => ({ error:false, data, totalPages}));
    } catch (e) {
        return {error: true}
    }

}