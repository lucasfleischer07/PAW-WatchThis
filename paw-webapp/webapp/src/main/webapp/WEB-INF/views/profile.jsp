<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
    <head>
        <title>Profile</title>
    </head>
    <body>
        <h2> Profile of: <c:out value="${user.email}" escapeXml="true"/> </h2>
    </body>
</html>
