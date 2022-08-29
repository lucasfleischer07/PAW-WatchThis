<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
    <head>
        <title>Series</title>
    </head>
    <body>
    <p> Name <c:out value="${serie.name}" escapeXml="true"/> </p>
    <p> Genre <c:out value="${serie.genre}" escapeXml="true"/> </p>
    <p> Description <c:out value="${serie.description}" escapeXml="true"/> </p>
    <p> Released <c:out value="${serie.released}" escapeXml="true"/> </p>
    <p> Creator <c:out value="${serie.creator}" escapeXml="true"/> </p>
    <p> Duration <c:out value="${serie.avgDuration}" escapeXml="true"/> </p>
    <img alt="Movie Foto" src="<c:out value="${serie.image}" escapeXml="true"/>">
    </body>
</html>
