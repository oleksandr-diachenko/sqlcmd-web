<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <title>SQLCmd</title>
    </head>
    <body>
        <form:form method="POST" action="connect" modelAttribute="connection">
            <form:input type="hidden" path="fromPage" id="from-page"/>
            <table>
                <tr>
                    <td>Database name</td>
                    <td><form:input path="dbName" id="database"/></td>
                </tr>
                <tr>
                    <td>User name</td>
                    <td><form:input path="userName" id="username"/></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><form:password path="password" id="password"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="connect" id="connect"/></td>
                </tr>
            </table>
        </form:form>
    </body>
</html>