package ua.com.juja.positiv.sqlcmd.service;

import org.springframework.stereotype.Component;
import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by POSITIV on 31.10.2015.
 */
@Component
public abstract class ServiceImpl implements Service {

    private List<String> commands;

    @Override
    public List<String> commandList() {
        return commands;
    }

    public abstract DatabaseManager getManager();

    @Override
    public DatabaseManager connect(String database, String user, String password) throws Exception {
        DatabaseManager manager = getManager();
        try {
            manager.connect(database, user, password);
        } catch (Exception e) {
            throw new Exception("Connection error", e);
        }
        return manager;
    }

    @Override
    public List<List<String>> getTableData(DatabaseManager manager, String tableName) throws Exception {
        List<String> tableData ;
        try {
            tableData = manager.getTableData(tableName);
        } catch (Exception e) {
            throw new Exception("Get table data error", e);
        }
        List<List<String>> table = new ArrayList<>(tableData.size() - 1);
        int columnCount = Integer.parseInt(tableData.get(0));
        for (int current = 1; current < tableData.size(); ) {
            List<String> row = new ArrayList<>(columnCount);
            for (int rowIndex = 0; rowIndex < columnCount; rowIndex++) {
                row.add(tableData.get(current++));
            }
            table.add(row);
        }
        return table;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
