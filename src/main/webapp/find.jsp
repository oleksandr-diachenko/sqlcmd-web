<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>sqlcmd</title>
</head>
    <table border="1">
      <body>
          <c:forEach items="${table}" var="row">
              <tr>
                <c:forEach items="${row}" var="element">
                    <td>
                        ${element}
                    </td>
                </c:forEach>
              </tr>
          </c:forEach>
      </body>
    </table>
    <tr><td><b>To menu <a href="menu">menu</a></b></td></tr>
</html>