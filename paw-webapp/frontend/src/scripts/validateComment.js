function validate(reviewId){
    let f=document.getElementById("commentInput"+reviewId);
    if(validateComment(f)){
        let button = document.getElementById("subButton");
        button.className += ' spinner-border';
        button.innerHTML =  '|';
        f.submit();
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