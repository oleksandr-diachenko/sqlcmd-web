package ua.com.juja.positiv.sqlcmd.service;

import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;
import ua.com.juja.positiv.sqlcmd.databasemanager.JDBCDatabaseManager;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
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
        return Arrays.asList("menu", "help", "connect", "list", "find", "clear");
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
}
