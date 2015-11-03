<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>sqlcmd</title>
</head>
<body>
<form action="create" method="post">
  <table>
    <tr>
      <td>Table name</td>
      <td><input type="text" name="tableName"/></td>
    </tr>

    <tr>
      <td>Column name</td>
      <td><input type="text" name="columnName"/></td>

      <td>Column value</td>
      <td><input type="text" name="columnValue"/></td>
    </tr>

    <tr>
      <td></td>
      <td><input type="submit" name="create"/></td>
    </tr>
  </table>
</form>
</body>
</html>