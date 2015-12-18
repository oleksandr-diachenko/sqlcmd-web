package ua.com.juja.positiv.sqlcmd.integration;

import net.sourceforge.jwebunit.api.IElement;
import org.junit.*;
import ua.com.juja.positiv.sqlcmd.DatabaseLogin;
import ua.com.juja.positiv.sqlcmd.DatabasePreparation;

import java.util.*;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by POSITIV on 25.11.2015.
 */

public class IntegrationTest {

    DatabaseLogin login = new DatabaseLogin();
    DatabasePreparation preparation = new DatabasePreparation();

    @Before
    public void prepare() {
        setBaseUrl("http://localhost:8080/sqlcmd");
        beginAt("/connect");
        setTextField("database", login.getDatabase());
        setTextField("user", login.getUser());
        setTextField("password", login.getPassword());
        submit();
    }

    private void success() {
        assertTextPresent("Success!");
        assertTextPresent("To menu menu");
        assertLinkPresentWithText("menu");
    }

    @Test
    public void testMenu() {
        List<String> commands = new LinkedList<>(Arrays.asList("connect", "create-table",
                "tables", "create-database", "delete-database"));
        List<IElement> links = getElementsByXPath("//a");

        assertTrue(commands.size() == links.size());
        for (int index = 0; index < links.size(); index++) {
            assertEquals(commands.get(index), links.get(index).getTextContent());
        }
    }

    @Test
    public void testTableNames() {
        preparation.run();
        clickLinkWithText("tables");

        assertTableEquals("",
                new String[][]{
                        {"", "Tables"},
                        {"1", "car"},
                        {"2", "client"}});
        assertTextPresent("To menu");
        assertLinkPresentWithText("menu");
    }

    @Test
    public void testTableData() {
        preparation.run();
        clickLinkWithText("tables");
        clickLinkWithText("car");

        assertLinkPresentWithText("create");
        assertLinkPresentWithText("update");
        assertLinkPresentWithText("delete");
        assertLinkPresentWithText("clear");
        assertLinkPresentWithText("drop");
        assertTableEquals("",
                new String[][]{
                        {"id", "name", "color", "year"},
                        {"1", "ferrari", "red", "2002"},
                        {"2", "porsche", "black", "1964"},
                        {"3", "bmw", "blue", "2001"}});
        assertTextPresent("To menu");
        assertLinkPresentWithText("menu");
    }

    @Test
    public void testCreateRecord() {
        preparation.run();
        gotoPage("/tables/car/create-record");

        assertTextPresent("id");
        assertTextPresent("name");
        assertTextPresent("color");
        assertTextPresent("year");
        int random = Math.abs(new Random().nextInt(100500));
        setTextField("id", String.valueOf(random));
        setTextField("name", "testName" + random);
        setTextField("color", "testColor" + random);
        setTextField("year", String.valueOf(random));
        submit();

        success();
    }

    @Test
    public void testClearTable() {
        preparation.run();
        gotoPage("/tables/car/clear-table");

        success();
    }

    @Test
    public void testDeleteRecord() {
        preparation.run();
        gotoPage("/tables/car/delete-record");

        assertTextPresent("Key value");
        setTextField("keyValue", "1");
        submit();

        success();
    }

    @Test
    public void testUpdateRecord() {
        preparation.run();
        gotoPage("/tables/car/update-record");

        assertTextPresent("id");
        assertTextPresent("name");
        assertTextPresent("color");
        assertTextPresent("year");
        setTextField("id", "1");
        int random = Math.abs(new Random().nextInt(100500));
        setTextField("name", "testName" + random);
        setTextField("color", "testColor" + random);
        setTextField("year", String.valueOf(random));
        submit();

        success();
    }

    @Test
    public void testCreateAndDeleteDatabase() {
        clickLinkWithText("create-database");

        assertTextPresent("Database name");
        setTextField("databaseName", "test" + Math.abs(new Random(10000).nextInt()));
        submit();

        success();

        clickLinkWithText("menu");
        clickLinkWithText("delete-database");

        assertTextPresent("Database name");
        setTextField("databaseName", "test" + Math.abs(new Random(10000).nextInt()));
        submit();

        success();
    }

    @Test
    public void testCreateTable() {
        clickLinkWithText("create-table");

        assertTextPresent("Table name");
        assertTextPresent("Column count");
        setTextField("tableName", "test");
        setTextField("columnCount", "2");
        submit();

        assertTextPresent("Primary key name");
        assertTextPresent("Column name 1");
        assertTextPresent("Column type 1");
        setTextField("keyName", "id");
        setTextField("columnName1", "test");
        setTextField("columnType1", "text");
        submit();

        assertTextPresent("Success!");
        assertTextPresent("To menu menu");
        assertLinkPresentWithText("menu");
    }

    @Test
    public void testDeleteTable() {
        preparation.run();
        gotoPage("/tables/car/delete-table");

        success();
    }
}
