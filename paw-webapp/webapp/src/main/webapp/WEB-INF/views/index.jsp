<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
    <body>
        <h2>Hello World!</h2>
        <h1>HOLA LUCAS</h1>
        <p> Lucas <c:out value="${username}" escapeXml="true"/> </p>
<%--        <p> Email: <c:out value="${user.email}" escapeXml="true"/> </p>--%>
    </body>
</html>
