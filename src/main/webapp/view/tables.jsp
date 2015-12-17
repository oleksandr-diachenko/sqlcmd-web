<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<body>
    <table border="1">
        <tr>
            <td></td>
            <td>Tables</td>
        </tr>
        <c:forEach items="${list}" var="table" varStatus="loop">
            <tr>
                <td>${loop.count}</td>
                <td><a href="tables/${table}">${table}</a></td>
            </tr>
        </c:forEach>
    </table>
    <tr>
        <td><b>To menu <a href="/sqlcmd/menu">menu</a></b></td>
    </tr>
</body>
</html>
