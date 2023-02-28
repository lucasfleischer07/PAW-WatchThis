import parse from "parse-link-header";

export async function fetchWithQueryParamsApi(url, queryParams = {}, options = {}) {
    try {
        const urlObj = new URL(url);
        Object.keys(queryParams).forEach(key => urlObj.searchParams.append(key, queryParams[key]));
        const res = await fetch(urlObj, options)
        // TODO: Ver porque no me deja usar el header, tiene que evr con el back en la parte de WebAuthConfig con corsConfigurationSource()
        const totalPages = parse(res.headers.get('Link')).last.page;
        return res.json().then(data => ({ error:false, data, totalPages}));
    } catch (e) {
        return {error: true}
    }

}