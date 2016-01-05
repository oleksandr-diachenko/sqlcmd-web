package ua.com.juja.positiv.sqlcmd.dao.databasemanager;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
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
    private JdbcTemplate template;
    private String user;
    private String database;

    @Override
    public void connect(String database, String user, String password)
                                            throws DatabaseException {
        try {
            Class.forName("org.postgresql.Driver");
            if(connection != null) {
                connection.close();
                template = null;
            }
            connection = DriverManager.getConnection(
                    JDBC_POSTGRESQL_URL + database + "", "" + user + "",
                    "" + password + "");
            DataSource dataSource = new SingleConnectionDataSource(connection, false);
            template = new JdbcTemplate(dataSource);
            this.user = user;
            this.database = database;
        } catch (SQLException e) {
            throw new DatabaseException("Can't connect to database. " +
                                                    e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("Can't find driver jar. Add it to project. "
                                                                + e.getMessage(), e);
        }
    }

    @Override
    public void createTable(String tableName, String keyName,
                            Map<String, Object> columnParameters) {
        template.execute(String.format("CREATE TABLE IF NOT EXISTS public.%s " +
                "(%s INT  PRIMARY KEY NOT NULL %s)",
                tableName, keyName, getParameters(columnParameters)));
    }

    private String getParameters(Map<String, Object> columnParameters) {
        StringBuilder url = new StringBuilder(4);
        for (Map.Entry<String, Object> pair : columnParameters.entrySet()) {
            url.append(", ")
                    .append(pair.getKey())
                    .append(" ")
                    .append(pair.getValue());
        }
        return url.toString();
    }

    @Override
    public Set<String> getTableNames() {
        return new LinkedHashSet<>(template.query(
            "SELECT table_name FROM information_schema.tables " +
                    "WHERE table_schema = 'public'",
                (rs, rowNum) -> {
                    return rs.getString("table_name");
                }));
    }

    @Override
    public List<String> getColumnNames(String tableName) {
        return template.query(String.format("SELECT * FROM information_schema.columns " +
                "WHERE table_schema = 'public' AND table_name = '%s'", tableName),
                (resultSet, rowNum) -> {
                    return resultSet.getString("column_name");
                });
    }

    @Override
    public List<List<String>> getTableData(String tableName) {
        return  template.query(String.format("SELECT * FROM %s", tableName),
                (resultSet, rowNum) -> {
                    List<String> row = new ArrayList<>();
                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    for (int index = 0; index < rsmd.getColumnCount(); index++) {
                        row.add(resultSet.getString(index + 1));
                    }
                    return row;
                });
    }

    @Override
    public void createRecord(String tableName, Map<String, Object> columnData) {
        StringJoiner keyJoiner = new StringJoiner(", ");
        StringJoiner valueJoiner = new StringJoiner("', '", "'", "'");
        for (Map.Entry<String, Object> pair : columnData.entrySet()) {
            keyJoiner.add(pair.getKey());
            valueJoiner.add(pair.getValue().toString());
        }
        template.update(String.format("INSERT INTO public.%s(%s) values (%s)",
                tableName, keyJoiner.toString(), valueJoiner.toString()));
    }

    @Override
    public void updateRecord(String tableName, String keyName, String keyValue,
                                                Map<String, Object> columnData) {
        for (Map.Entry<String, Object> pair : columnData.entrySet()) {
            template.update(String.format("UPDATE public.%s SET %s = '%s' " +
                                            "WHERE %s = '%s'",
                                            tableName, pair.getKey(),
                                            pair.getValue(), keyName, keyValue));
        }
    }

    @Override
    public void deleteRecord(String tableName, String keyName, String keyValue) {
        template.update(String.format("DELETE FROM public.%s WHERE %s = '%s'",
                                            tableName, keyName, keyValue));
    }

    @Override
    public void clearTable(String tableName) {
        template.update(String.format("DELETE FROM public.%s", tableName));
    }

    @Override
    public void dropTable(String tableName) {
        template.update(String.format("DROP TABLE public.%s", tableName));
    }

    @Override
    public void createBase(String database)  {
        template.execute(String.format("CREATE DATABASE %s", database));
    }

    @Override
    public void dropBase(String database) {
        template.execute(String.format("DROP DATABASE %s", database));
    }

    @Override
    public String getPrimaryKey(String tableName) throws DatabaseException {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet rs = meta.getPrimaryKeys(null, null, tableName);
            while (rs.next()) {
                return rs.getString("COLUMN_NAME");
            }
            throw new RuntimeException();
        } catch (Exception e) {
            throw new DatabaseException("Can't get primary key. " + e.getMessage(), e);
        }
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getDatabase() {
        return database;
    }
}
