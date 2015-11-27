package ua.com.juja.positiv.sqlcmd.databasemanager;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by POSITIV on 16.09.2015.
 */
public interface DatabaseManager {

    void connect(String database, String user, String command) throws DatabaseException;

    Set<String> getTableNames() throws DatabaseException;

    /**
     * @param tableName .
     * @return tableData .
     * List[0] = columnCount.
     * List[1, columnCount + 1] = columnName.
     * List[columnCount + 1, size] = columnData.
     * @throws DatabaseException .
     */
    List<String> getTableData(String tableName) throws DatabaseException;

    void createTable(String tableName, String keyName, Map<String, Object> columnParameters) throws DatabaseException;

    void createRecord(String tableName, Map<String, Object> columnData) throws DatabaseException;

    void updateRecord(String tableName, String keyName, String keyValue, Map<String, Object> columnData) throws DatabaseException;

    void deleteRecord(String tableName, String keyName, String keyValue) throws DatabaseException;

    void clearTable(String tableName) throws DatabaseException;

    void dropTable(String tableName) throws DatabaseException;

    void createBase(String database) throws DatabaseException;

    void dropBase(String database) throws DatabaseException;
}
