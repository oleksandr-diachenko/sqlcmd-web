<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>sqlcmd</title>
</head>
<body>
<form action="find" method="post">
  <table>
    <tr>
      <td>Database name</td>
      <td><input type="text" name="tableName"/></td>
    </tr>

    <tr>
      <td></td>
      <td><input type="submit" name="find"/></td>
    </tr>
  </table>
</form>
</body>
</html>