package ua.com.juja.positiv.sqlcmd.service;

import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by POSITIV on 31.10.2015.
 */
public class ServiceImpl implements Service{

    private DatabaseManager manager;
    private List<String> commands;

    public ServiceImpl(DatabaseManager manager, List<String> commands){
        this.manager = manager;
        this.commands = commands;
    }

    @Override
    public List<String> commandList() {
        return commands;
    }
    
    @Override
    public DatabaseManager connect(String database, String user, String password)
            throws SQLException, ClassNotFoundException {
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
