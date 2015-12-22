package ua.com.juja.positiv.sqlcmd.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import ua.com.juja.positiv.sqlcmd.DatabaseLogin;
import ua.com.juja.positiv.sqlcmd.DatabasePreparation;
import ua.com.juja.positiv.sqlcmd.dao.databasemanager.DatabaseException;
import ua.com.juja.positiv.sqlcmd.dao.databasemanager.DatabaseManager;
import ua.com.juja.positiv.sqlcmd.dao.databasemanager.PostgreDatabaseManager;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by POSITIV on 16.09.2015.
 */
public class DatabaseManagerTest {

    DatabaseLogin login = new DatabaseLogin();
    DatabasePreparation preparation = new DatabasePreparation();
    DatabaseManager manager = new PostgreDatabaseManager();

    @Before
    public void run() throws DatabaseException {
        manager.connect(login.getDatabase(), login.getUser(), login.getPassword());
    }

    @Test
    public void testDelete_WithCorrectData() {
        preparation.run();
        manager.deleteRecord("car", "id", "3");

        assertEquals("[[1, ferrari, red, 2002], " +
                      "[2, porsche, black, 1964]]", manager.getTableData("car").toString());
    }

    @Test(expected = BadSqlGrammarException.class)
    public void testDelete_WithIncorrectData_TableName() {
        manager.deleteRecord("qwe", "id", "3");
    }

    @Test
    public void testGetTableNames() {
        preparation.run();
        Set<String> tableNames = manager.getTableNames();

        assertEquals("[car, client]", tableNames.toString());
    }

    @Test
    public void testGetColumnNames(){
        preparation.run();

        assertEquals("[id, name, color, year]", manager.getColumnNames("car").toString());
    }


    @Test
    public void testFind_WithCorrectData() {
        preparation.run();

        assertEquals("[[1, ferrari, red, 2002], " +
                      "[2, porsche, black, 1964], " +
                      "[3, bmw, blue, 2001]]", manager.getTableData("car").toString());
    }

    @Test(expected = BadSqlGrammarException.class)
    public void testFind_WithIncorrectData_TableName() {
        manager.getTableData("qwe");
    }

    @Test
    public void testFindLimitOffset_WithCorrectData() {
        preparation.run();

        assertEquals("[[2, porsche, black, 1964], " +
                      "[3, bmw, blue, 2001]]", manager.getTableData("car LIMIT 2 OFFSET 1").toString());
    }

    @Test
    public void testUpdateAll_WithCorrectData() {
        preparation.run();
        Map<String, Object> columnData = new LinkedHashMap<>();
        columnData.put("name", "mercedes");
        columnData.put("color", "white");
        columnData.put("year", "2008");

        manager.updateRecord("car", "id", "3", columnData);

        assertEquals("[[1, ferrari, red, 2002], " +
                      "[2, porsche, black, 1964], " +
                      "[3, mercedes, white, 2008]]", manager.getTableData("car").toString());
    }

    @Test
    public void testUpdateSingle_WithCorrectData() {
        preparation.run();
        Map<String, Object> columnData = new LinkedHashMap<>();
        columnData.put("name", "mercedes");

        manager.updateRecord("car", "id", "3", columnData);

        assertEquals("[[1, ferrari, red, 2002], " +
                      "[2, porsche, black, 1964], " +
                      "[3, mercedes, blue, 2001]]", manager.getTableData("car").toString());
    }

    @Test(expected = BadSqlGrammarException.class)
    public void testUpdate_WithIncorrectData_TableName() {
        Map<String, Object> columnData = new LinkedHashMap<>();
        columnData.put("name", "mercedes");

        manager.updateRecord("qwe", "id", "3", columnData);
    }

    @Test
    public void testClear_WithCorrectData() {
        preparation.run();
        manager.clearTable("car");

        assertEquals("[]", manager.getTableData("car").toString());
    }

    @Test(expected = BadSqlGrammarException.class)
    public void testClear_WithIncorrectData_TableName() {
        manager.clearTable("qwe");
    }

    @Test
    public void testCreateAll_WithCorrectData() {
        preparation.run();
        Map<String, Object> data = new HashMap<>();
        data.put("id", "4");
        data.put("name", "ferrari");
        data.put("color", "red");
        data.put("year", "6");

        manager.createRecord("car", data);

        assertEquals("[[1, ferrari, red, 2002], " +
                      "[2, porsche, black, 1964], " +
                      "[3, bmw, blue, 2001], " +
                      "[4, ferrari, red, 6]]", manager.getTableData("car").toString());
    }

    @Test
    public void testCreateSingle_WithCorrectData() {
        preparation.run();
        Map<String, Object> data = new HashMap<>();
        data.put("id", "4");

        manager.createRecord("car", data);

        assertEquals("[[1, ferrari, red, 2002], " +
                      "[2, porsche, black, 1964], " +
                      "[3, bmw, blue, 2001], " +
                      "[4, null, null, null]]", manager.getTableData("car").toString());
        }

    @Test(expected = BadSqlGrammarException.class)
    public void testCreate_WithIncorrectData_Length() {
        Map<String, Object> map = new HashMap<>();
        manager.createRecord("qwe", map);
    }

    @Test(expected = BadSqlGrammarException.class)
    public void testCreate_WithIncorrectData_TableName() {
        Map<String, Object> data = new HashMap<>();
        data.put("id", "2");
        manager.createRecord("qwe", data);
    }

    @Test
    public void testCreateTable_WithCorrectData() {
        preparation.run();
        Map<String, Object> data = new HashMap<>();
        data.put("name", "text");
        data.put("population", "int");
        data.put("county", "text");
        manager.createTable("city", "id", data);

        Set<String> tableNames = manager.getTableNames();

        assertEquals("[car, client, city]", tableNames.toString());
    }

    @Test(expected = BadSqlGrammarException.class)
    public void testCreateTable_WithIncorrectData_Type() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "");
        manager.createTable("city", "id", data);
    }

    @Test(expected = BadSqlGrammarException.class)
    public void testDeleteTable_WithIncorrectData_TableName() {
        manager.dropTable("qwe");
    }

    @Test
    public void testCreateAndDeleteDatabase_WithCorrectData() {
        String database = "test" + Math.abs(new Random(100000).nextInt());
        manager.createBase(database);
        manager.dropBase(database);
    }

    @Test(expected = UncategorizedSQLException.class)
    public void testDeleteDatabase_WithIncorrectData_DatabaseName() {
        manager.dropBase("qwe");
    }

    @Test
    public void testPrimaryKeyWithCorrectData() throws DatabaseException {
        assertEquals("id", manager.getPrimaryKey("car"));
    }

    @Test(expected = DatabaseException.class)
    public void testPrimaryKeyWithIncorrectData() throws DatabaseException {
        manager.getPrimaryKey("qwe");
    }

    @Test
    public void testColumnNames() {
        assertEquals("[id, name, color, year]",
                                manager.getColumnNames("car").toString());
    }
}
