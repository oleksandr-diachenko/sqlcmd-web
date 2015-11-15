package ua.com.juja.positiv.sqlcmd.service;

import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;
import ua.com.juja.positiv.sqlcmd.databasemanager.JDBCDatabaseManager;

/**
 * Created by POSITIV on 15.11.2015.
 */
public class DatabaseManagerFactory {

    public DatabaseManager createDatabaseManager(){
        return new JDBCDatabaseManager();
    }
}
