package ua.com.juja.positiv.sqlcmd.databasemanager;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

/**
 * Created by POSITIV on 16.09.2015.
 */
@Component
@Scope(value = "prototype")
public class PostgreDatabaseManager implements DatabaseManager {

    public static final String JDBC_POSTGRESQL_URL = "jdbc:postgresql://localhost:5432/";
    private Connection connection;

    @Override
    public void connect(String database, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(
                JDBC_POSTGRESQL_URL + database + "", "" + user + "",
                "" + password + "");
    }

    @Override
    public void createTable(String tableName, String keyName, Map<String, Object> columnParameters) throws SQLException {
        Statement stmt = connection.createStatement();
        StringBuilder url = new StringBuilder();
        url.append("CREATE TABLE ")
                .append(tableName)
                .append("(")
                .append(keyName)
                .append(" INT  PRIMARY KEY NOT NULL").
                append(getParameters(columnParameters))
                .append(")");
        stmt.executeUpdate(url.toString());
        stmt.close();
    }

    private String getParameters(Map<String, Object> columnParameters) {
        String result = "";
        for (Map.Entry<String, Object> pair : columnParameters.entrySet()) {
            result += ", " + pair.getKey() + " " + pair.getValue();
        }
        return result;
    }

    @Override
    public Set<String> getTableNames() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, "public", "%", new String[]{"TABLE"});

        Set<String> tableNames = new LinkedHashSet<>();
        while (resultSet.next()) {
            tableNames.add(resultSet.getString(3));
        }
        resultSet.close();
        return tableNames;
    }

    @Override
    public List<String> getTableData(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM " + tableName);
        ResultSetMetaData rsmd = resultSet.getMetaData();

        List<String> tableData = new ArrayList<>();
        tableData.add(String.valueOf(rsmd.getColumnCount()));
        for (int indexColumn = 1; indexColumn <= rsmd.getColumnCount(); indexColumn++) {
            tableData.add(resultSet.getMetaData().getColumnName(indexColumn));
        }

        while (resultSet.next()) {
            for (int indexData = 1; indexData <= rsmd.getColumnCount(); indexData++) {
                if (resultSet.getString(indexData) == null) {
                    tableData.add("");
                } else {
                    tableData.add(resultSet.getString(indexData));
                }
            }
        }
        stmt.close();
        resultSet.close();
        return tableData;
    }

    @Override
    public void createRecord(String tableName, Map<String, Object> columnData) throws SQLException {
        Statement stmt = connection.createStatement();
        StringBuilder url = new StringBuilder();
        url.append("INSERT INTO ")
                .append(tableName)
                .append(" (")
                .append(getColumnNames(columnData))
                .append(")")
                .append(" VALUES (")
                .append(getColumnValues(columnData))
                .append(")");
        stmt.executeUpdate(url.toString());
        stmt.close();
    }

    private String getColumnNames(Map<String, Object> columnData) {
        StringBuilder columnNames = new StringBuilder();
        for (Map.Entry<String, Object> pair : columnData.entrySet()) {
            columnNames.append(pair.getKey())
                    .append(", ");
        }
        return columnNames.substring(0, columnNames.length() - 2);
    }

    private String getColumnValues(Map<String, Object> columnData) {
        StringBuilder columnValues = new StringBuilder();
        for (Map.Entry<String, Object> pair : columnData.entrySet()) {
            columnValues.append("'")
                    .append(pair.getValue())
                    .append("', ");
        }
        return columnValues.substring(0, columnValues.length() - 2);
    }

    @Override
    public void updateRecord(String tableName, String keyName, String keyValue, Map<String, Object> columnData) throws SQLException {
        Statement stmt = connection.createStatement();
        for (Map.Entry<String, Object> pair : columnData.entrySet()) {
            StringBuilder url = new StringBuilder();
            url.append("UPDATE ")
                    .append(tableName)
                    .append(" SET ")
                    .append(pair.getKey())
                    .append(" = '")
                    .append(pair.getValue())
                    .append("' WHERE ")
                    .append(keyName)
                    .append(" = '")
                    .append(keyValue)
                    .append("'");
            stmt.executeUpdate(url.toString());
        }
        stmt.close();
    }

    @Override
    public void deleteRecord(String tableName, String keyName, String keyValue) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM " + tableName + " WHERE " + keyName + " = '" + keyValue + "'");
        stmt.close();
    }

    @Override
    public void clearTable(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM " + tableName);
        stmt.close();
    }

    @Override
    public void dropTable(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP TABLE " + tableName);
        stmt.close();
    }

    @Override
    public void createBase(String database) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE DATABASE " + database);
        stmt.close();
    }

    @Override
    public void dropBase(String database) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP DATABASE " + database);
        stmt.close();
    }
}
