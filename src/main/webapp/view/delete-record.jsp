<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<body>
<form action="delete-record" method="post">
    <table>
        <tr>
            <td>Key value</td>
            <td><input type="text" name="keyValue"/></td>
        </tr>

        <tr>
            <td></td>
            <td><input type="submit" value="OK"/></td>
        </tr>
    </table>
</form>
</body>
</html>