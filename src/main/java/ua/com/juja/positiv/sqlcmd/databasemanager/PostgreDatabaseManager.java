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
    public void connect(String database, String user, String password) throws DatabaseException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    JDBC_POSTGRESQL_URL + database + "", "" + user + "",
                    "" + password + "");
        } catch (SQLException e) {
            throw new DatabaseException("Can't connect to database. " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("Can't find driver jar. Add it to project. " + e.getMessage(), e);
        }
    }

    @Override
    public void createTable(String tableName, String keyName, Map<String, Object> columnParameters)
            throws DatabaseException {
        StringBuilder url = new StringBuilder(7);
        url.append("CREATE TABLE public.")
                .append(tableName)
                .append("(")
                .append(keyName)
                .append(" INT  PRIMARY KEY NOT NULL").
                append(getParameters(columnParameters))
                .append(")");
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(url.toString());
        } catch (SQLException e) {
            throw new DatabaseException("Can't create table. " + e.getMessage(), e);
        }
    }

    private String getParameters(Map<String, Object> columnParameters) {
        StringBuilder url = new StringBuilder(4);
        for (Map.Entry<String, Object> pair : columnParameters.entrySet()) {
            url.append(", ").append(pair.getKey()).append(" ").append(pair.getValue());
        }
        return url.toString();
    }

    @Override
    public Set<String> getTableNames() throws DatabaseException {
        DatabaseMetaData metaData;
        try {
            metaData = connection.getMetaData();
        } catch (SQLException e) {
            throw new DatabaseException("Can't get table names. " + e.getMessage(), e);
        }

        try (ResultSet resultSet = metaData.getTables(null, "public", "%", new String[]{"TABLE"})) {
            Set<String> tableNames = new LinkedHashSet<>();
            while (resultSet.next()) {
                tableNames.add(resultSet.getString(3));
            }
            return tableNames;
        } catch (SQLException e) {
            throw new DatabaseException("Can't get table names. " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> getTableData(String tableName) throws DatabaseException {
        StringBuilder url = new StringBuilder(2);
        url.append("SELECT * FROM public.").append(tableName);
        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(url.toString())) {
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
            return tableData;
        } catch (SQLException e) {
            throw new DatabaseException("Can't get table data. " + e.getMessage(), e);
        }
    }

    @Override
    public void createRecord(String tableName, Map<String, Object> columnData) throws DatabaseException {
        try (Statement stmt = connection.createStatement()) {
            StringBuilder url = new StringBuilder(8);
            url.append("INSERT INTO public.")
                    .append(tableName)
                    .append(" (")
                    .append(getColumnNames(columnData))
                    .append(")")
                    .append(" VALUES (")
                    .append(getColumnValues(columnData))
                    .append(")");
            stmt.executeUpdate(url.toString());
        } catch (SQLException e) {
            throw new DatabaseException("Can't create record. " + e.getMessage(), e);
        }
    }

    private String getColumnNames(Map<String, Object> columnData) {
        StringBuilder columnNames = new StringBuilder(2);
        for (Map.Entry<String, Object> pair : columnData.entrySet()) {
            columnNames.append(pair.getKey()).append(", ");
        }
        return columnNames.substring(0, columnNames.length() - 2);
    }

    private String getColumnValues(Map<String, Object> columnData) {
        StringBuilder columnValues = new StringBuilder(3);
        for (Map.Entry<String, Object> pair : columnData.entrySet()) {
            columnValues.append("'").append(pair.getValue()).append("', ");
        }
        return columnValues.substring(0, columnValues.length() - 2);
    }

    @Override
    public void updateRecord(String tableName, String keyName, String keyValue, Map<String, Object> columnData)
            throws DatabaseException {
        try (Statement stmt = connection.createStatement()) {
            for (Map.Entry<String, Object> pair : columnData.entrySet()) {
                StringBuilder url = new StringBuilder(11);
                url.append("UPDATE public.")
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
        } catch (SQLException e) {
            throw new DatabaseException("Can't update record. " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteRecord(String tableName, String keyName, String keyValue) throws DatabaseException {
        StringBuilder url = new StringBuilder(7);
        url.append("DELETE FROM public.")
                .append(tableName)
                .append(" WHERE ")
                .append(keyName)
                .append(" = '")
                .append(keyValue)
                .append("'");
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(url.toString());
        } catch (SQLException e) {
            throw new DatabaseException("Can't delete record. " + e.getMessage(), e);
        }
    }

    @Override
    public void clearTable(String tableName) throws DatabaseException {
        StringBuilder url = new StringBuilder(2);
        url.append("DELETE FROM public.").append(tableName);
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(url.toString());
        } catch (SQLException e) {
            throw new DatabaseException("Can't clear table. " + e.getMessage(), e);
        }
    }

    @Override
    public void dropTable(String tableName) throws DatabaseException {
        StringBuilder url = new StringBuilder(2);
        url.append("DROP TABLE public.").append(tableName);
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(url.toString());
        } catch (SQLException e) {
            throw new DatabaseException("Can't delete table. " + e.getMessage(), e);
        }
    }

    @Override
    public void createBase(String database) throws DatabaseException {
        StringBuilder url = new StringBuilder(2);
        url.append("CREATE DATABASE ").append(database);
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(url.toString());
        } catch (SQLException e) {
            throw new DatabaseException("Can't create database. " + e.getMessage(), e);
        }
    }

    @Override
    public void dropBase(String database) throws DatabaseException {
        StringBuilder url = new StringBuilder(2);
        url.append("DROP DATABASE ").append(database);
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(url.toString());
        } catch (SQLException e) {
            throw new DatabaseException("Can't delete database. " + e.getMessage(), e);
        }
    }
}
