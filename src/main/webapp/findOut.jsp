<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>sqlcmd</title>
</head>
    <table border="1">
      <body>
            <c:forEach begin="1" items="${tableData}" step="${tableData[0]}" var="data" varStatus="loop">
                <tr>
                    <c:forEach begin="${loop.index}" end="${loop.index - 1 + tableData[0]}" items="${tableData}" var="data">
                        <td>${data}</td>
                    </c:forEach>
                </tr>
            </c:forEach>

      </body>
    </table>
    <tr><td><b>В меню <a href="menu">menu</a></b></td></tr>
</html>