<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>sqlcmd</title>
    </head>
    <body>
        <b>connect|database|user|password</b><br>
        &#8195; подключение к базе<br>
        <b>createbase|databaseName</b><br>
        &#8195;  создание базы<br>
        <b>table|tableName|primaryKeyName|column1Name|column1Type
        |...|columnNName|columnNType</b><br>
        &#8195; создание таблицы<br>
        <b>list</b><br>
        &#8195;  вывод списка всех таблиц<br>
        <b>find|tableName или find|tableName|limit|offset</b><br>
        &#8195; вывод всей таблицы или вывод части таблицы<br>
        <b>create|tableName|column1Name|column1Value|...|columnNName|columnNValue</b><br>
        &#8195; создание поля<br>
        <b>update|tableName|primaryKeyColumnName|primaryKeyValue
        |column1Name|column1NewValue|...|columnNName|columnNNewValue</b><br>
        &#8195;  обновление поля<br>
        <b>delete|tableName|primaryKeyColumnName|primaryKeyValue</b><br>
        &#8195; удаление поля<br>
        <b>clear|tableName</b><br>
        &#8195; очистка таблицы<br>
        <b>drop|tableName</b><br>
        &#8195; удаление таблицы<br>
        <b>dropbase|databaseName</b><br>
        &#8195; удаление базы<br>

        <tr><td><b>В меню <a href="menu">menu</a></b></td></tr>
    </body>
</html>