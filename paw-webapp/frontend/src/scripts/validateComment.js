function validate(reviewId){
    let f=document.getElementById("commentInput"+reviewId);
    if(validateComment(f)){
       // handleAddComment(f["comment"].value)
        f["comment"].value=null
    }
}


function validateComment(form){
    let comment=form["comment"].value;

    if( comment==null || comment === "" ){
        let error=document.getElementById("emptyComment")
        error.style.display = "block";
        return false;
    }else if(comment.length < 10 || comment.length > 500 ){
        let error=document.getElementById("shortComment")
        error.style.display = "block";
        return false;
    }
    return true;
}
export {validate,validateComment}