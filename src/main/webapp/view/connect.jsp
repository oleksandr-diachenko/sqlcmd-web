<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<body>
    <form action="connect" method="post">
        <b>Connect to database.</b>
        <table>
            <tr>
                <td>database</td>
                <td><input type="text" name="database"/></td>
            </tr>
            <tr>
                <td>user</td>
                <td><input type="text" name="user"/></td>
            </tr>
            <tr>
                <td>password</td>
                <td><input type="password" name="password"/></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="connect"/></td>
            </tr>
        </table>
    </form>
</body>
</html>