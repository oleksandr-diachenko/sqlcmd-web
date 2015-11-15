package ua.com.juja.positiv.sqlcmd.service;

import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;

/**
 * Created by POSITIV on 15.11.2015.
 */
public interface DatabaseManagerFactory {

    DatabaseManager createDatabaseManager();
}
