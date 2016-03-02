<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="tables">
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
