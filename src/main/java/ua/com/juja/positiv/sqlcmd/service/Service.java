package ua.com.juja.positiv.sqlcmd.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by POSITIV on 31.10.2015.
 */
public interface Service {

    List<String> commandList();

    void connect(String database, String user, String password) throws SQLException, ClassNotFoundException;

    Set<String> list() throws SQLException;

    List<String> find(String tableName) throws SQLException;

    void clear(String tableName) throws SQLException;

    void delete(String tableName, String keyName, String keyValue) throws SQLException;

    void create(String tableName, Map<String, Object> columnData) throws SQLException;

    void createBase(String databaseName) throws SQLException;

    void deleteBase(String databaseName) throws SQLException;

    void update(String tableName, String keyName, String keyValue, Map<String, Object> columnData) throws SQLException;

    void table(String tableName, String keyName, Map<String, Object> columnData) throws SQLException;
}
