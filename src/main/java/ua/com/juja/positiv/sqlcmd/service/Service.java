package ua.com.juja.positiv.sqlcmd.service;

import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by POSITIV on 31.10.2015.
 */
public interface Service {

    List<String> commandList();

    DatabaseManager connect(String database, String user, String password)
            throws SQLException, ClassNotFoundException;

    Set<String> list(DatabaseManager manager) throws SQLException;

    List<String> find(DatabaseManager manager, String tableName) throws SQLException;

    void clear(DatabaseManager manager, String tableName) throws SQLException;

    void delete(DatabaseManager manager, String tableName, String keyName, String keyValue) throws SQLException;

    void create(DatabaseManager manager, String tableName, Map<String, Object> columnData) throws SQLException;

    void createBase(DatabaseManager manager, String databaseName) throws SQLException;

    void deleteBase(DatabaseManager manager, String databaseName) throws SQLException;

    void update(DatabaseManager manager, String tableName, String keyName,
                String keyValue, Map<String, Object> columnData) throws SQLException;

    void table(DatabaseManager manager, String tableName, String keyName,
               Map<String, Object> columnParameter) throws SQLException;;

}
