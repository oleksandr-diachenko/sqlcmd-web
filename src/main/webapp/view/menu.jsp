<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<table border="1">
    <body>
        <c:forEach items="${items}" var="item">
            <tr>
                <td>
                    <b> <a href="${item}">${item}</a></b><br>
                </td>
            </tr>
        </c:forEach>
    </body>
</table>
</html>