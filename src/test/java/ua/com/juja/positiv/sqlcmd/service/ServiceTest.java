package ua.com.juja.positiv.sqlcmd.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.juja.positiv.sqlcmd.dao.databasemanager.DatabaseException;
import ua.com.juja.positiv.sqlcmd.dao.databasemanager.DatabaseManager;
import ua.com.juja.positiv.sqlcmd.dao.databasemanager.PostgreDatabaseManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by POSITIV on 27.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ("classpath:test-application-context.xml"))
public class ServiceTest {

    @Autowired
    private Service service;

    @Test
    public void testCommandList() {
        assertEquals("[connect, create-table, " +
                      "tables, " +
                      "create-database, " +
                      "delete-database]", service.commandList().toString());
    }

    @Test
    public void testLogger() throws DatabaseException, SQLException {
        DatabaseManager manager = new PostgreDatabaseManager();
        manager.connect("sqlcmd_log", "postgres", "postgres");
        service.clearTable(manager, "user_actions");

        manager.connect("sqlcmd", "postgres", "postgres");
        service.getTableNames(manager);
        service.getTableData(manager, "car");
        service.clearTable(manager, "car");
        Map<String, Object> columnData = new HashMap<>();
        columnData.put("id", "100500");
        service.createRecord(manager, "car", columnData);

        manager.connect("sqlcmd_log", "postgres", "postgres");
        List<List<String>> userActions = manager.getTableData("user_actions");
        for(List<String> row : userActions) {
            row.remove(0);
        }
        assertEquals("[[sqlcmd_log, postgres, CLEAR TABLE ( user_actions )], " +
                      "[sqlcmd, postgres, GET TABLES LIST], " +
                      "[sqlcmd, postgres, GET TABLE ( car )], " +
                      "[sqlcmd, postgres, CLEAR TABLE ( car )], " +
                      "[sqlcmd, postgres, CREATE RECORD IN TABLE ( car )]]",
                              userActions.toString());
    }
}
