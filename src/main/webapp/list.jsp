<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
  <body>
    Tables:<br>
    <c:forEach items="${items}" var="item">
      <b> ${item}</b><br>
    </c:forEach>
  </body>
</html>
