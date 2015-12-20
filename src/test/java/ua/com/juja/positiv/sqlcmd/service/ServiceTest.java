package ua.com.juja.positiv.sqlcmd.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
                      "table-names, table-data, " +
                      "update-record, clear-table, " +
                      "create-record, delete-record, " +
                      "delete-table, create-database, " +
                      "delete-database]", service.commandList().toString());
    }
}
