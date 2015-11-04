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

    private DatabaseManager manager;

    public ServiceImpl(){
        manager = new JDBCDatabaseManager();
    }

    @Override
    public List<String> commandList() {
        return Arrays.asList("help", "connect", "table", "list", "find", "update", "clear",
                "create", "delete", "createDatabase", "deleteDatabase");
    }

    @Override
    public void connect(String database, String user, String password) throws SQLException, ClassNotFoundException {
        manager.connect(database, user, password);
    }

    @Override
    public Set<String> list() throws SQLException {
        return manager.getTableNames();
    }

    @Override
    public List<String> find(String tableName) throws SQLException {
        return manager.getTableData(tableName);
    }

    @Override
    public void clear(String tableName) throws SQLException {
        manager.clear(tableName);
    }

    @Override
    public void delete(String tableName, String keyName, String keyValue) throws SQLException {
        manager.delete(tableName, keyName, keyValue);
    }

    @Override
    public void create(String tableName, Map<String, Object> columnData) throws SQLException {
        manager.create(tableName, columnData);
    }

    @Override
    public void createBase(String databaseName) throws SQLException {
        manager.createBase(databaseName);
    }

    @Override
    public void deleteBase(String databaseName) throws SQLException {
        manager.dropBase(databaseName);
    }

    @Override
    public void update(String tableName, String keyName, String keyValue, Map<String, Object> columnData) throws SQLException {
        manager.update(tableName, keyName, keyValue, columnData);
    }

    @Override
    public void table(String tableName, String keyName, Map<String, Object> columnData) throws SQLException {
        manager.table(tableName, keyName, columnData);
    }
}
