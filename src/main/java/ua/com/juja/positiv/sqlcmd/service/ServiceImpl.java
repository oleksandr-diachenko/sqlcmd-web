package ua.com.juja.positiv.sqlcmd.service;

import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;
import ua.com.juja.positiv.sqlcmd.databasemanager.JDBCDatabaseManager;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by POSITIV on 31.10.2015.
 */
public class ServiceImpl implements Service{

    @Override
    public List<String> commandList() {
        return Arrays.asList("help", "connect", "list", "find", "update", "clear",
                "create", "delete", "createDatabase", "deleteDatabase");
    }

    @Override
    public DatabaseManager connect(String database, String user, String password)
            throws SQLException, ClassNotFoundException {
        DatabaseManager manager = new JDBCDatabaseManager();
        manager.connect(database, user, password);
        return manager;
    }

    @Override
    public Set<String> list(DatabaseManager manager) throws SQLException {
        return manager.getTableNames();
    }

    @Override
    public List<String> find(DatabaseManager manager, String tableName) throws SQLException {
        return manager.getTableData(tableName);
    }

    @Override
    public void clear(DatabaseManager manager, String tableName) throws SQLException {
        manager.clear(tableName);
    }

    @Override
    public void delete(DatabaseManager manager, String tableName, String keyName, String keyValue)
            throws SQLException {
        manager.delete(tableName, keyName, keyValue);
    }

    @Override
    public void create(DatabaseManager manager, String tableName, Map<String, Object> columnData)
            throws SQLException {
        manager.create(tableName, columnData);
    }

    @Override
    public void createBase(DatabaseManager manager, String databaseName) throws SQLException {
        manager.createBase(databaseName);
    }

    @Override
    public void deleteBase(DatabaseManager manager, String databaseName) throws SQLException {
        manager.dropBase(databaseName);
    }

    @Override
    public void update(DatabaseManager manager, String tableName, String keyName,
                       String keyValue, Map<String, Object> columnData) throws SQLException {
        manager.update(tableName, keyName, keyValue, columnData);
    }
}
