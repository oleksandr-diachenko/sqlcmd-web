<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>sqlcmd</title>
</head>
    <body>
        <form action="delete" method="post">
            <table>
                <tr>
                    <td>Table name</td>
                    <td><input type="text" name="tableName"/></td>
                </tr>

                <tr>
                  <td>Primary key name</td>
                  <td><input type="text" name="keyName"/></td>
                </tr>

                <tr>
                  <td>Primary key value</td>
                  <td><input type="text" name="keyValue"/></td>
                </tr>

                <tr>
                  <td></td>
                  <td><input type="submit" value="delete"/></td>
                </tr>
            </table>
        </form>
    </body>
</html>