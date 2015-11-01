<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
    <body>
    <form action="connect" method="post"></form>
        <table>
            <td>
                database
            </td>
            <tr><td>
                <input type="text" name="database"/>
            </tr> </td>
            <td>
                user
            </td>
            <tr><td>
                <input type="text" name="user"/>
            </tr></td>
            <td>
                password
            </td>
            <tr><td>
                <input type="text" name="password"/>
            </tr></td>
            <td></td>
            <tr><td>
                <input type="submit" name="connect"/>
            </tr></td>
        </table>
    </body>
</html>