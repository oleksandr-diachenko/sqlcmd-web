package ua.com.juja.positiv.sqlcmd.service;

import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by POSITIV on 31.10.2015.
 */
public interface Service {

    List<String> commandList();

    DatabaseManager connect(String database, String user, String password)
            throws Exception;

    List<List<String>> getTableData(DatabaseManager manager, String tableName) throws Exception;

}
