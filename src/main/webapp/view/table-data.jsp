<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
    <body>
        <table id="crud_container" border="1">
            <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-2.2.0.js"></script>
            <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/crud.js"></script>
        </table>
        <br>
        <table id="table_data_container" border="1">
            <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-2.2.0.js"></script>
            <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/table-data.js"></script>
        </table>
    </body>
</html>