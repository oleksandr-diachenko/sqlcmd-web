<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="menu">
    <div class="container">
        <script template type="text/x-jquery-tmpl">
            <a href="#/{{= $data}}">{{= $data}}</a></br>
        </script>
    </div>
</div>