package ua.com.juja.positiv.sqlcmd.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

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
                "table-names, table-data, " +
                "update-record, clear-table, " +
                "create-record, delete-record, " +
                "delete-table, create-database, " +
                "delete-database]", service.commandList().toString());
    }

    @Test
    public void testTableData() throws Exception {
        DatabaseManager manager = Mockito.mock(DatabaseManager.class);
        when(manager.getTableData("test")).thenReturn(Arrays.asList(
                "3", "id", "name", "password",
                "1", "testName1", "testPassword1",
                "2", "testName2", "testPassword2"));

        assertEquals("[[id, name, password], " +
                "[1, testName1, testPassword1], " +
                "[2, testName2, testPassword2]]", service.getTableData(manager, "test").toString());
    }
}
