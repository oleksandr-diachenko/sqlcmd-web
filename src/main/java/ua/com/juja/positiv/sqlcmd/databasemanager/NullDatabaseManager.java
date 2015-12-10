package ua.com.juja.positiv.sqlcmd.databasemanager;

import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseException;
import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;

import java.util.*;

/**
 * Created by POSITIV on 10.12.2015.
 */
public class NullDatabaseManager implements DatabaseManager {

    @Override
    public void connect(String database, String user, String command) throws DatabaseException {
        //do nothing
    }

    @Override
    public Set<String> getTableNames() throws DatabaseException {
        return new HashSet<>();
    }

    @Override
    public List<String> getTableData(String tableName) throws DatabaseException {
        return new LinkedList<>();
    }

    @Override
    public void createTable(String tableName, String keyName, Map<String, Object> columnParameters) throws DatabaseException {
        //do nothing
    }

    @Override
    public void createRecord(String tableName, Map<String, Object> columnData) throws DatabaseException {
        //do nothing
    }

    @Override
    public void updateRecord(String tableName, String keyName, String keyValue, Map<String, Object> columnData) throws DatabaseException {
        //do nothing
    }

    @Override
    public void deleteRecord(String tableName, String keyName, String keyValue) throws DatabaseException {
        //do nothing
    }

    @Override
    public void clearTable(String tableName) throws DatabaseException {
        //do nothing
    }

    @Override
    public void dropTable(String tableName) throws DatabaseException {
        //do nothing
    }

    @Override
    public void createBase(String database) throws DatabaseException {
        //do nothing
    }

    @Override
    public void dropBase(String database) throws DatabaseException {
        //do nothing
    }
}
