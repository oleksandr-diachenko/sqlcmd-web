<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>sqlcmd</title>
</head>
<table border="1">
  <body>
        <tr>
        <c:forEach begin="1" end="4" items="${tableData}" var="data">
          <td>${data}</td>
        </c:forEach>
        </tr>

        <tr>
        <c:forEach begin="5" end="8" items="${tableData}" var="data">
          <td>${data}</td>
        </c:forEach>
        </tr>

        <tr>
        <c:forEach begin="9" end="12" items="${tableData}" var="data">
          <td>${data}</td>
        </c:forEach>
        </tr>

        <tr>
        <c:forEach begin="13" end="16" items="${tableData}" var="data">
          <td>${data}</td>
        </c:forEach>
        </tr>
  </body>
</table>
</html>