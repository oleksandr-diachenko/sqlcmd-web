<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>sqlcmd</title>
    </head>
    <body>
        <c:forEach items="${item}" var="item">
            <a href="item">${item}</a>
        </c:forEach>
    </body>
</html>