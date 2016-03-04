<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="tableData">
    <td><a href="tables/car/create-record">create-record</a></td>
    <td><a href="tables/car/update-record">update-record</a></td>
    <td><a href="tables/car/delete-record">delete-record</a></td>
    <td><a href="tables/car/clear-table">clear-table</a></td>
    <td><a href="tables/car/delete-table">delete-table</a></td>
    <table border="1" class="container">
        <script template type="text/x-jquery-tmpl">
            <tr>
                {{each $data}}
                    <td>
                        {{= this}}
                    </td>
                {{/each}}
            </tr>
        </script>
    </table>
</div>
