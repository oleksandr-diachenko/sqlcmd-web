package ua.com.juja.positiv.sqlcmd.web;

import org.junit.*;
import ua.com.juja.positiv.sqlcmd.DatabasePreparation;

import java.util.Random;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

/**
 * Created by POSITIV on 25.11.2015.
 */

public class WebTest {

    DatabasePreparation preparation = new DatabasePreparation();

    @Before
    public void prepare() {
        setBaseUrl("http://localhost:8080/sqlcmd");
        beginAt("/connect");
        setTextField("database", "sqlcmd");
        setTextField("user", "postgres");
        setTextField("password", "postgres");
        submit();
    }

    @Test
    public void testMenu() {
        assertLinkPresentWithText("connect");
        assertLinkPresentWithText("create-table");
        assertLinkPresentWithText("table-names");
        assertLinkPresentWithText("table-data");
        assertLinkPresentWithText("update-record");
        assertLinkPresentWithText("clear-table");
        assertLinkPresentWithText("create-record");
        assertLinkPresentWithText("delete-record");
        assertLinkPresentWithText("create-database");
        assertLinkPresentWithText("delete-database");
    }

    @Test
    public void testTableNames(){
        preparation.run();
        clickLinkWithText("table-names");
        assertTableEquals("",
                new String[][] {
                        {"", "Tables"},
                        {"1", "car"},
                        {"2", "client"}});
        assertTextPresent("To menu menu");
        assertLinkPresentWithText("menu");
    }

    @Test
    public void testTableData(){
        preparation.run();
        clickLinkWithText("table-data");
        assertTextPresent("Table name");
        setTextField("tableName", "car");
        submit();
        assertTableEquals("",
                new String[][] {
                        {"id", "name", "color", "year"},
                        {"1", "ferrari", "red", "2002"},
                        {"2", "porsche", "black", "1964"},
                        {"3", "bmw", "blue", "2001"}});
        assertTextPresent("To menu menu");
        assertLinkPresentWithText("menu");
    }

    @Test
    public void testCreateTable(){
        preparation.run();
        String tableName = "test"+ Math.abs(new Random(100000).nextInt());
        clickLinkWithText("create-table");

        assertTextPresent("Table name");
        assertTextPresent("Column count");
        setTextField("tableName", tableName);
        setTextField("columnCount", "2");
        submit();

        assertTextPresent("Primary key name");
        assertTextPresent("Column name1");
        assertTextPresent("Column type1");
        setTextField("keyName", "id");
        setTextField("columnName1", "test");
        setTextField("columnType1", "text");
        submit();

        assertTextPresent("Success!");
        assertTextPresent("To menu menu");
        assertLinkPresentWithText("menu");
    }
}
