package ua.com.juja.positiv.sqlcmd.dao.databasemanager;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by POSITIV on 16.09.2015.
 */
public interface DatabaseManager {

    DatabaseManager NULL = new NullDatabaseManager();

    void connect(String database, String user, String command)
            throws DatabaseException;

    Set<String> getTableNames();

    List<String> getColumnNames(String tableName);

    List<List<String>> getTableData(String tableName);

    void createTable(String tableName, String keyName,
                     Map<String, Object> columnParameters);

    void createRecord(String tableName, Map<String, Object> columnData);

    void updateRecord(String tableName, String keyName, String keyValue,
                      Map<String, Object> columnData);

    void deleteRecord(String tableName, String keyName, String keyValue);

    void clearTable(String tableName);

    void dropTable(String tableName);

    void createBase(String database);

    void dropBase(String database);

    String getPrimaryKey(String tableName) throws DatabaseException;

    String getUser();

    String getDatabase();
}
