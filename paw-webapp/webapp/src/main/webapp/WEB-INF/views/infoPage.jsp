<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%--        Para el Fav ico--%>
    <link rel="icon" type="image/x-icon" href="<c:url value="/resources/img/favicon.ico"/>">
    <%--        <!-- * Link de la libreria de Bootstrap -->--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <%--       * Referencia a nuestra hoja de estilos propia --%>
    <link href="<c:url value="/resources/css/reviewsStyles.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/navBarStyles.css"/>" rel="stylesheet" type="text/css"/>
    <title>${details.name}</title>
  </head>

  <body>
    <jsp:include page="components/header.jsp" />
    <div class="W-column-display">

        <div>
          <div class="card W-inv-film-card-margin">
            <div class="row g-0">
              <div class="col-md-4 W-img-aligment">
                <img src="<c:url value="${details.image}"/>" class="W-img-size" alt="foto ">
              </div>
              <div class="col-md-8">
                <div class="card-body W-card-body-aligment">
                  <h5 class="card-title"><c:out value="${details.name}"/></h5>
                  <p class="card-text"><c:out value="${details.description}"/></p>
                  <p class="card-text"><span class="W-span-text-info-card-movie">Duration:</span> <c:out value="${details.duration}"/></p>
                  <p class="card-text"><span class="W-span-text-info-card-movie">Genre:</span> <c:out value="${details.genre}"/></p>
                  <p class="card-text"><span class="W-span-text-info-card-movie">Released:</span> <c:out value="${details.released}"/></p>
                  <p class="card-text"><span class="W-span-text-info-card-movie">Creator:</span> <c:out value="${details.creator}"/></p>
                </div>
              </div>
            </div>
            <%--
            <a href="#" class="list-group-item border-0 W-star-location W-star-aligment">
              <div class="W-star-style-info-page">
                <svg xmlns="http://www.w3.org/2000/svg"  fill="currentColor" class="bi bi-star-fill W-star-color" viewBox="0 0 16 16">
                  <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                </svg>
                <svg xmlns="http://www.w3.org/2000/svg"  fill="currentColor" class="bi bi-star-fill W-star-color" viewBox="0 0 16 16">
                  <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                </svg>
                <svg xmlns="http://www.w3.org/2000/svg"  fill="currentColor" class="bi bi-star-fill W-star-color" viewBox="0 0 16 16">
                  <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                </svg>
                <svg xmlns="http://www.w3.org/2000/svg"  fill="currentColor" class="bi bi-star-fill W-star-color" viewBox="0 0 16 16">
                  <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                </svg>
                <svg xmlns="http://www.w3.org/2000/svg"  fill="currentColor" class="bi bi-star-fill W-star-color" viewBox="0 0 16 16">
                  <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                </svg>
              </div>
            </a> --%>

          </div>

          <div class="W-add-review-button">
            <a href="<c:url value="/reviewForm/${details.type}/${details.id}"/>"><button type="button" class="btn btn-dark">Add review</button></a>
          </div>

        </div>

      </div>

    <%--        TODO: el value de review score queda harcodeado porq no esta includio en el primer spint guardar, lo hacemos para el segundo--%>
    <c:forEach var="review" items="${reviews}">
      <jsp:include page="components/reviewCard.jsp">
        <jsp:param name="reviewTitle" value="${review.name}" />
        <jsp:param name="reviewDescription" value="${review.description}" />
        <jsp:param name="reviewUsername" value="${review.userName}" />
        <jsp:param name="reviewId" value="${review.reviewId}" />
      </jsp:include>
    </c:forEach>



    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
  </body>
</html>
