package ua.com.juja.positiv.sqlcmd;

import ua.com.juja.positiv.sqlcmd.dao.databasemanager.DatabaseException;
import ua.com.juja.positiv.sqlcmd.dao.databasemanager.DatabaseManager;
import ua.com.juja.positiv.sqlcmd.dao.databasemanager.PostgreDatabaseManager;

import java.util.*;

/**
 * Created by POSITIV on 14.10.2015.
 */
public class DatabasePreparation {

    DatabaseManager manager = new PostgreDatabaseManager();

    public void run() {
        try {
            connectDatabase();
            clearDatabase();
            createFirstTable();
            createSecondTable();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    private void connectDatabase() throws DatabaseException {
        manager.connect("sqlcmd", "postgres", "postgres");
    }

    private void createFirstTable() throws DatabaseException {
        Map<String, Object> tableCar = new LinkedHashMap<>();
        tableCar.put("name", "text");
        tableCar.put("color", "text");
        tableCar.put("year", "int");
        manager.createTable("car", "id", tableCar);

        Map<String, Object> field1 = new HashMap<>();
        field1.put("id", 1);
        field1.put("name", "ferrari");
        field1.put("color", "red");
        field1.put("year", 2002);
        manager.createRecord("car", field1);

        Map<String, Object> field2 = new HashMap<>();
        field2.put("id", 2);
        field2.put("name", "porsche");
        field2.put("color", "black");
        field2.put("year", 1964);
        manager.createRecord("car", field2);

        Map<String, Object> field3 = new HashMap<>();
        field3.put("id", 3);
        field3.put("name", "bmw");
        field3.put("color", "blue");
        field3.put("year", 2001);
        manager.createRecord("car", field3);
    }

    private void createSecondTable() throws DatabaseException {
        Map<String, Object> tableClient = new LinkedHashMap<>();
        manager.createTable("client", "id", tableClient);
    }

    private void clearDatabase() throws DatabaseException {
        Set<String> tables = manager.getTableNames();
        for (String table : tables) {
            manager.dropTable(table);
        }
    }
}
