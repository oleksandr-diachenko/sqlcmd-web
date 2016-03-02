<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <head>
        <title>SQLCmd</title>
        <script type="text/javascript" src="${ctx}/resources/js/jquery-2.2.0.js"></script>
        <script type="text/javascript" src="${ctx}/resources/js/jquery.tmpl.js"></script>
        <script type="text/javascript" src="${ctx}/resources/js/main.js"></script>
        <script type="text/javascript">
            $(window).load(function () {
                init('${ctx}');
            });
        </script>
    </head>
    <body>
        <div id="loading" style="display:none;">Loading...</div>
        <%@include file="table-data.jsp" %>
        <%@include file="menu.jsp" %>
        <%@include file="tables.jsp" %>
    </body>
</html>