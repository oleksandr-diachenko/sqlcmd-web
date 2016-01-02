package ua.com.juja.positiv.sqlcmd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.juja.positiv.sqlcmd.dao.databasemanager.DatabaseException;
import ua.com.juja.positiv.sqlcmd.dao.databasemanager.DatabaseManager;
import ua.com.juja.positiv.sqlcmd.dao.entity.DatabaseConnection;
import ua.com.juja.positiv.sqlcmd.dao.entity.UserAction;
import ua.com.juja.positiv.sqlcmd.dao.repository.DatabaseConnectionRepository;
import ua.com.juja.positiv.sqlcmd.dao.repository.UserActionRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by POSITIV on 31.10.2015.
 */
@Component
public abstract class ServiceImpl implements Service {

    private List<String> commands;

    public abstract DatabaseManager getManager();

    @Autowired
    private UserActionRepository actionRepository;

    @Autowired
    private DatabaseConnectionRepository databaseConnectionRepository;

    @Override
    public List<String> commandList() {
        return commands;
    }

    @Override
    public DatabaseManager connect(String database, String user, String password)
                                                        throws ServiceException {
        DatabaseManager manager = getManager();
        try {
            manager.connect(database, user, password);
            saveAction("CONNECT", user, database);
            return manager;
        } catch (DatabaseException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Set<String> getTableNames(DatabaseManager manager) {
        saveAction("GET TABLES LIST", manager.getUser(), manager.getDatabase());
        return manager.getTableNames();
    }

    @Override
    public List<List<String>> getTableData(DatabaseManager manager, String tableName) {
        List<List<String>> tableData = manager.getTableData(tableName);
        List<String> columnNames = manager.getColumnNames(tableName);
        tableData.add(0, columnNames);
        saveAction("GET TABLE ( " + tableName + " )", manager.getUser(), manager.getDatabase());
        return tableData;
    }

    @Override
    public void createTable(DatabaseManager manager, String tableName, String keyName,
                            Map<String, Object> columnParameters) {
        manager.createTable(tableName, keyName, columnParameters);
        saveAction("CREATE TABLE ( " + tableName + " )", manager.getUser(), manager.getDatabase());
    }

    @Override
    public void createRecord(DatabaseManager manager, String tableName,
                             Map<String, Object> columnData) {
        manager.createRecord(tableName, columnData);
        saveAction("CREATE RECORD IN TABLE ( " + tableName + " )", manager.getUser(), manager.getDatabase());
    }

    @Override
    public void updateRecord(DatabaseManager manager, String tableName,
                             String keyName, String keyValue,
                             Map<String, Object> columnData) {
        manager.updateRecord(tableName, keyName, keyValue, columnData);
        saveAction("UPDATE RECORD IN TABLE ( " + tableName + " ) KEY = " + keyValue, manager.getUser(), manager.getDatabase());
    }

    @Override
    public void deleteRecord(DatabaseManager manager, String tableName,
                             String keyName, String keyValue) {
        manager.deleteRecord(tableName, keyName, keyValue);
        saveAction("DELETE RECORD IN TABLE ( " + tableName + " ) KEY = " + keyValue, manager.getUser(), manager.getDatabase());
    }

    @Override
    public void clearTable(DatabaseManager manager, String tableName) {
        manager.clearTable(tableName);
        saveAction("CLEAR TABLE ( " + tableName + " )", manager.getUser(), manager.getDatabase());
    }

    @Override
    public void dropTable(DatabaseManager manager, String tableName) {
        manager.dropTable(tableName);
        saveAction("DELETE TABLE ( " + tableName + " )", manager.getUser(), manager.getDatabase());
    }

    @Override
    public void createBase(DatabaseManager manager, String database) {
        manager.createBase(database);
        saveAction("CREATE DATABASE ( " + database + " )", manager.getUser(), manager.getDatabase());
    }

    @Override
    public void dropBase(DatabaseManager manager, String database) {
        manager.dropBase(database);
        saveAction("DELETE DATABASE ( " + database + " )", manager.getUser(), manager.getDatabase());
    }

    @Override
    public List<UserAction> getAllFor(String userName){
        if(userName == null) {
            throw new IllegalArgumentException("User name cant be null");
        }
        return  actionRepository.findByUserName(userName);
    }

    private void saveAction(String action, String user, String database) {
        DatabaseConnection databaseConnection = databaseConnectionRepository
                .findByUserNameAndDbName(user, database);
        if(databaseConnection == null) {
            databaseConnection = databaseConnectionRepository.save(
                    new DatabaseConnection(user, database));
        }
        actionRepository.save(new UserAction(action, databaseConnection));
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
