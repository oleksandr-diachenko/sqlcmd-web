package ua.com.juja.positiv.sqlcmd.service;

import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;

/**
 * Created by POSITIV on 15.11.2015.
 */
public class DatabaseManagerFactoryImpl implements DatabaseManagerFactory {

    String className;

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public DatabaseManager createDatabaseManager() {
        try {
            return (DatabaseManager) Class.forName(className).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
