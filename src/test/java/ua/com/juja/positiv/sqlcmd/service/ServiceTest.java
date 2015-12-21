package ua.com.juja.positiv.sqlcmd.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseException;
import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseException;
import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;
import ua.com.juja.positiv.sqlcmd.databasemanager.PostgreDatabaseManager;

import java.sql.SQLException;
import java.util.HashMap;
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
        try {
            service.dropTable(manager, "user_actions");
        } catch (Exception e) {
            //do nothing
        }
        Map<String, Object> columnParameters = new HashMap<>();
        columnParameters.put("user_name", "text");
        columnParameters.put("db_name", "text");
        columnParameters.put("action", "text");
        service.createTable(manager, "user_actions", "id", columnParameters);

        manager.connect("sqlcmd", "postgres", "postgres");
        service.getTableNames(manager);
        service.getTableData(manager, "car");
        service.clearTable(manager, "car");
        Map<String, Object> columnData = new HashMap<>();
        columnData.put("id", "100500");
        service.createRecord(manager, "car", columnData);

        manager.connect("sqlcmd_log", "postgres", "postgres");
        assertEquals("[[1, sqlcmd_log, postgres, CREATE TABLE ( user_actions )], " +
                      "[2, sqlcmd, postgres, GET TABLES LIST], " +
                      "[3, sqlcmd, postgres, GET TABLE ( car )], " +
                      "[4, sqlcmd, postgres, CLEAR TABLE( car )], " +
                      "[5, sqlcmd, postgres, CREATE RECORD IN TABLE( car )]]",
                              manager.getTableData("user_actions").toString());
    }

}
