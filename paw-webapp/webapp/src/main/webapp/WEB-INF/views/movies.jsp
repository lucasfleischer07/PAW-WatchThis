<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
    <head>
        <title>Movies</title>
    </head>
    <body>
        <p> Name <c:out value="${movies.name}" escapeXml="true"/> </p>
        <p> Genre <c:out value="${movies.genre}" escapeXml="true"/> </p>
        <p> Description <c:out value="${movies.description}" escapeXml="true"/> </p>
        <p> Released <c:out value="${movies.released}" escapeXml="true"/> </p>
        <p> Creator <c:out value="${movies.creator}" escapeXml="true"/> </p>
        <p> Duration <c:out value="${movies.duration}" escapeXml="true"/> </p>
        <img alt="Movie Foto" src="<c:out value="${movies.image}" escapeXml="true"/>">
    </body>
</html>
