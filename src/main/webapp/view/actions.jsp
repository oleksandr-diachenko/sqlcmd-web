<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<table border="1">
    <body>
    <c:forEach items="${actions}" var="action">
        <tr>
            <td>${action.userName}</td>
            <td>${action.dbName}</td>
            <td>${action.userAction}</td>
        </tr>
    </c:forEach>
    </body>
</table>
</html>