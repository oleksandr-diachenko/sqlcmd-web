<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<body>
    <form action="create-record" method="post">
        <table>
            <tr>
                <c:forEach items="${columnNames}" var="column" varStatus="loop">
            <tr>
                <td>${column}</td><td><input type="text" name="${column}"/></td>
            </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td><input type="submit" value="create"/></td>
            </tr>
        </table>
    </form>
</body>
</html>