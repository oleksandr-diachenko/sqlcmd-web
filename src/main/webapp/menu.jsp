<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>sqlcmd</title>
    </head>
    <body>
        <c:forEach items="${items}" var="item">
           <b> <a href="${item}">${item}</a></b><br>
        </c:forEach>
    </body>
</html>