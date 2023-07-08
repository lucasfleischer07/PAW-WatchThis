import PropTypes from "prop-types";

function validateParam(param,paramPulled) {
    //alert(paramPulled)
    //alert(param)
    return  param !== paramPulled && (paramPulled !== null || (param.length > 0))
}

function updateUrlVariable(param,paramPulled,setter){
    if( validateParam(param,paramPulled)){
        setter(paramPulled === null ? '' : paramPulled)
    }
}

updateUrlVariable.propTypes = {
    param: PropTypes.any.isRequired,
    paramPulled: PropTypes.any,
    setter: PropTypes.func.isRequired,
};

export {validateParam,updateUrlVariable}