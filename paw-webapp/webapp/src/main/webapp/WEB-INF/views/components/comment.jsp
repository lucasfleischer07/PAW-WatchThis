<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div>
    <div class="card">
        <div class="card-body W-reviewText">
            <p id="<c:out value="reviewDescription${param.id}"/>"><c:out value="${param.commentText}"/></p>
            <script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>

            <script>
                console.log('reviewDescription'+'${param.id}')
                document.getElementById('reviewDescription'+'${param.id}').innerHTML = marked.parse(document.getElementById('reviewDescription'+'${param.id}').innerHTML);
            </script>
        </div>
    </div>
</div>