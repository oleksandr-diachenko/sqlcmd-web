package ua.com.juja.positiv.sqlcmd.databasemanager;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.positiv.sqlcmd.DatabaseLogin;
import ua.com.juja.positiv.sqlcmd.DatabasePreparation;

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
    public void testDelete_WithCorrectData() throws DatabaseException {
        preparation.run();
        manager.deleteRecord("car", "id", "3");

        List<String> tableData = manager.getTableData("car");
        assertEquals("[4, id, name, color, year, " +
                      "1, ferrari, red, 2002, " +
                      "2, porsche, black, 1964]", tableData.toString());
    }

    @Test(expected = DatabaseException.class)
    public void testDelete_WithIncorrectData_TableName() throws DatabaseException {
        manager.deleteRecord("qwe", "id", "3");
    }

    @Test
    public void testGetTableNames() throws Exception {
        preparation.run();
        Set<String> tableNames = manager.getTableNames();
        assertEquals("[car, client]", tableNames.toString());
    }

    @Test
    public void testFind_WithCorrectData() throws DatabaseException {
        preparation.run();
        List<String> tableData = manager.getTableData("car");
        assertEquals("[4, id, name, color, year, " +
                      "1, ferrari, red, 2002, " +
                      "2, porsche, black, 1964, " +
                      "3, bmw, blue, 2001]", tableData.toString());
    }

    @Test(expected = DatabaseException.class)
    public void testFind_WithIncorrectData_TableName() throws DatabaseException {
        manager.getTableData("qwe");
    }

    @Test
    public void testFindLimitOffset_WithCorrectData() throws DatabaseException {
        preparation.run();
        List<String> tableData = manager.getTableData("car LIMIT 2 OFFSET 1");
        assertEquals("[4, id, name, color, year, " +
                      "2, porsche, black, 1964, " +
                      "3, bmw, blue, 2001]", tableData.toString());
    }

    @Test
    public void testUpdateAll_WithCorrectData() throws DatabaseException {
        preparation.run();
        Map<String, Object> columnData = new LinkedHashMap<>();
        columnData.put("name", "mercedes");
        columnData.put("color", "white");
        columnData.put("year", "2008");
        manager.updateRecord("car", "id", "3", columnData);

        List<String> tableData = manager.getTableData("car");
        assertEquals("[4, id, name, color, year, " +
                      "1, ferrari, red, 2002, " +
                      "2, porsche, black, 1964, " +
                      "3, mercedes, white, 2008]", tableData.toString());
    }

    @Test
    public void testUpdateSingle_WithCorrectData() throws DatabaseException {
        preparation.run();
        Map<String, Object> columnData = new LinkedHashMap<>();
        columnData.put("name", "mercedes");
        manager.updateRecord("car", "id", "3", columnData);

        List<String> tableData = manager.getTableData("car");
        assertEquals("[4, id, name, color, year, " +
                      "1, ferrari, red, 2002, " +
                      "2, porsche, black, 1964, " +
                      "3, mercedes, blue, 2001]", tableData.toString());
    }

    @Test(expected = DatabaseException.class)
    public void testUpdate_WithIncorrectData_TableName() throws DatabaseException {
        Map<String, Object> columnData = new LinkedHashMap<>();
        columnData.put("name", "mercedes");
        manager.updateRecord("qwe", "id", "3", columnData);
    }

    @Test
    public void testClear_WithCorrectData() throws DatabaseException {
        preparation.run();
        manager.clearTable("car");

        List<String> tableData = manager.getTableData("car");
        assertEquals("[4, id, name, color, year]", tableData.toString());
    }

    @Test(expected = DatabaseException.class)
    public void testClear_WithIncorrectData_TableName() throws DatabaseException {
        manager.clearTable("qwe");
    }

    @Test
    public void testCreateAll_WithCorrectData() throws DatabaseException {
        preparation.run();
        Map<String, Object> data = new HashMap<>();
        data.put("id", "4");
        data.put("name", "ferrari");
        data.put("color", "red");
        data.put("year", "6");
        manager.createRecord("car", data);

        List<String> tableData = manager.getTableData("car");
        assertEquals("[4, id, name, color, year, " +
                      "1, ferrari, red, 2002, " +
                      "2, porsche, black, 1964, " +
                      "3, bmw, blue, 2001, " +
                      "4, ferrari, red, 6]", tableData.toString());
    }

    @Test
    public void testCreateSingle_WithCorrectData() throws DatabaseException {
        preparation.run();
        Map<String, Object> data = new HashMap<>();
        data.put("id", "4");
        manager.createRecord("car", data);

        List<String> tableData = manager.getTableData("car");
        assertEquals("[4, id, name, color, year, " +
                      "1, ferrari, red, 2002, " +
                      "2, porsche, black, 1964, " +
                      "3, bmw, blue, 2001, " +
                      "4, , , ]", tableData.toString());
        }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void testCreate_WithIncorrectData_Length() throws DatabaseException {
        Map<String, Object> map = new HashMap<>();
        manager.createRecord("qwe", map);
    }

    @Test(expected = DatabaseException.class)
    public void testCreate_WithIncorrectData_TableName() throws DatabaseException {
        Map<String, Object> data = new HashMap<>();
        data.put("id", "2");
        manager.createRecord("qwe", data);
    }

    @Test
    public void testTable_WithCorrectData() throws DatabaseException {
        preparation.run();
        Map<String, Object> data = new HashMap<>();
        data.put("name", "text");
        data.put("population", "int");
        data.put("county", "text");
        manager.createTable("city", "id", data);

        Set<String> tableNames = manager.getTableNames();
        assertEquals("[car, city, client]", tableNames.toString());
        manager.dropTable("city");
    }

    @Test(expected = DatabaseException.class)
    public void testCreateTable_WithIncorrectData_Type() throws DatabaseException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "");
        manager.createTable("city", "id", data);
    }

    @Test(expected = DatabaseException.class)
    public void testDeleteTable_WithIncorrectData_TableName() throws DatabaseException {
        manager.dropTable("qwe");
    }

    @Test
    public void testCreateAndDeleteDatabase_WithCorrectData() throws DatabaseException {
        String database = "test" + Math.abs(new Random(100000).nextInt());
        manager.createBase(database);
        manager.dropBase(database);
    }

    @Test(expected = DatabaseException.class)
    public void testDeleteDatabase_WithIncorrectData_DatabaseName() throws DatabaseException {
        manager.dropBase("qwe");
    }

    @Test
    public void testPrimaryKeyWithCorrectData() throws DatabaseException {
        assertEquals("id", manager.getPrimaryKey("car"));
    }
}
