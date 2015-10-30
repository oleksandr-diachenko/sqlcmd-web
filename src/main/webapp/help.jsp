<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>sqlcmd</title>
    </head>
    <body>
        connect|database|user|password\r\n<br>
            подключение к базе\r\n<br>
        createbase|databaseName\r\n<br>
            создание базы\r\n<br>
        table|tableName|primaryKeyName|column1Name|column1Type
        |...|columnNName|columnNType\r\n<br>
            создание таблицы\r\n<br>
        list\r\n<br>
            вывод списка всех таблиц\r\n<br>
        find|tableName или find|tableName|limit|offset\r\n<br>
            вывод всей таблицы или вывод части таблицы\r\n<br>
        create|tableName|column1Name|column1Value|...|columnNName|columnNValue\r\n<br>
            создание поля\r\n<br>
        update|tableName|primaryKeyColumnName|primaryKeyValue
        |column1Name|column1NewValue|...|columnNName|columnNNewValue\r\n<br>
            обновление поля\r\n<br>
        delete|tableName|primaryKeyColumnName|primaryKeyValue\r\n<br>
            удаление поля\r\n<br>
        clear|tableName\r\n<br>
            очистка таблицы\r\n<br>
        drop|tableName\r\n<br>
            удаление таблицы\r\n<br>
        dropbase|databaseName\r\n<br>
            удаление базы\r\n<br>
    </body>
</html>