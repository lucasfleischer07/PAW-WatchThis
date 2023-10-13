export async function genericRequest(basePath, parameter, options){
    try {
        let url
        if(typeof parameter === 'number') {
            url = `${basePath}/${parameter}`
        } else {
            url = parameter
        }

        const res = await fetch(url, options)

        // TODO: Verificar si anda bien con un || or && (lo del 204 es de ContentApi->getContentReviewers
        if(res.status === 200 || res.status !== 204) {
            return {error: false, data: await res.json()}
        } else if(res.status === 204) {
            return {error: false, data: []}
        } else {
            return {error: true, errorCode: res.status}
        }

    } catch (e) {
        return {error: true, errorCode: e.response.status || 500}
    }
}


export 
