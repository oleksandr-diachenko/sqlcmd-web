package ua.com.juja.positiv.sqlcmd.dao.databasemanager;

import java.util.*;

/**
 * Created by POSITIV on 10.12.2015.
 */
public class NullDatabaseManager implements DatabaseManager {

    @Override
    public void connect(String database, String user, String command)
                                            throws DatabaseException {
        //do nothing
    }

    @Override
    public Set<String> getTableNames(){
        return new HashSet<>();
    }

    @Override
    public List<List<String>> getTableData(String tableName) {
        return new ArrayList<>();
    }

    @Override
    public void createTable(String tableName, String keyName,
                            Map<String, Object> columnParameters) {
        //do nothing
    }

    @Override
    public List<String> getColumnNames(String tableName) {
        return new ArrayList<>();
    }

    @Override
    public void createRecord(String tableName, Map<String, Object> columnData) {
        //do nothing
    }

    @Override
    public void updateRecord(String tableName, String keyName, String keyValue,
                             Map<String, Object> columnData) {
        //do nothing
    }

    @Override
    public void deleteRecord(String tableName, String keyName, String keyValue) {
        //do nothing
    }

    @Override
    public void clearTable(String tableName) {
        //do nothing
    }

    @Override
    public void dropTable(String tableName) {
        //do nothing
    }

    @Override
    public void createBase(String database) {
        //do nothing
    }

    @Override
    public void dropBase(String database) {
        //do nothing
    }

    @Override
    public String getPrimaryKey(String tableName) throws DatabaseException {
        return "";
    }

    @Override
    public String getUser() {
        return "";
    }

    @Override
    public String getDatabase() {
        return "";
    }
}
