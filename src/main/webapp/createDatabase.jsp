<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>sqlcmd</title>
</head>
<body>
<form action="createDatabase" method="post">
  <table>
    <tr>
      <td>Database name</td>
      <td><input type="text" name="databaseName"/></td>
    </tr>

    <tr>
      <td></td>
      <td><input type="submit" value="createDatabase"/></td>
    </tr>
  </table>
</form>
</body>
</html>