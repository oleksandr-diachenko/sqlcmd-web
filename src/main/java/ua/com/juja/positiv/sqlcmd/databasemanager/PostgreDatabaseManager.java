package ua.com.juja.positiv.sqlcmd.databasemanager;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

    @Override
    public void connect(String database, String user, String password) throws DatabaseException {
        try {
            Class.forName("org.postgresql.Driver");
            if(connection != null) {
                connection.close();
            }
            if(template != null) {
                template = null;
            }
            connection = DriverManager.getConnection(
                    JDBC_POSTGRESQL_URL + database + "", "" + user + "",
                    "" + password + "");
            template = new JdbcTemplate(new SingleConnectionDataSource(connection, false));
        } catch (SQLException e) {
            throw new DatabaseException("Can't connect to database. " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("Can't find driver jar. Add it to project. " + e.getMessage(), e);
        }
    }

    @Override
    public void createTable(String tableName, String keyName, Map<String, Object> columnParameters) {
        template.execute("CREATE TABLE public." + tableName +
                " (" + keyName + " INT  PRIMARY KEY NOT NULL" +
                getParameters(columnParameters) + ")");
    }

    private String getParameters(Map<String, Object> columnParameters) {
        StringBuilder url = new StringBuilder(4);
        for (Map.Entry<String, Object> pair : columnParameters.entrySet()) {
            url.append(", ").append(pair.getKey()).append(" ").append(pair.getValue());
        }
        return url.toString();
    }

    @Override
    public Set<String> getTableNames() {
        return new LinkedHashSet<>(template.query(
                "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'",
                new RowMapper<String>() {
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString("table_name");
                    }
                }));
    }

    @Override
    public List<String> getTableData(final String tableName) throws DatabaseException {
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

//        return this.template.query( //TODO закончить
//                "SELECT * FROM public." + tableName,
//                    new RowMapper<String>() {
//                        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//                            ResultSetMetaData rsmd = rs.getMetaData();
//                            List<String> tableData = new ArrayList<>();
//                            tableData.add(String.valueOf(rsmd.getColumnCount()));
//                            for (int index = 0; index < rsmd.getColumnCount(); index++) {
//                                tableData.add(rsmd.getColumnName(index + 1));
//                            }
//                            for (int index = 0; index < rsmd.getColumnCount(); index++) {
//                                tableData.add(rs.getString(index + 1));
//                            }
//                            return tableData;
//                        }
//                });
    }

    @Override
    public void createRecord(String tableName, Map<String, Object> columnData) {
        StringJoiner keyJoiner = new StringJoiner(", ");
        StringJoiner valueJoiner = new StringJoiner("', '", "'", "'");
        for (Map.Entry<String, Object> pair : columnData.entrySet()) {
            keyJoiner.add(pair.getKey());
            valueJoiner.add(pair.getValue().toString());
        }
        template.update("INSERT INTO public." + tableName +
                "(" + keyJoiner.toString() + ") values (" + valueJoiner.toString()+ ")");
    }

    @Override
    public void updateRecord(String tableName, String keyName, String keyValue, Map<String, Object> columnData) {
        for (Map.Entry<String, Object> pair : columnData.entrySet()) {
            template.update("UPDATE public." + tableName + " SET " + pair.getKey() + " = '" + pair.getValue() +
                    "' WHERE " + keyName + " = '" + keyValue + "'");
        }
    }

    @Override
    public void deleteRecord(String tableName, String keyName, String keyValue) {
        template.update("DELETE FROM public." + tableName + " WHERE " + keyName + " = '" + keyValue + "'");
    }

    @Override
    public void clearTable(String tableName) {
        template.update("DELETE FROM public." + tableName);
    }

    @Override
    public void dropTable(String tableName) {
        template.update("DROP TABLE public." + tableName);
    }

    @Override
    public void createBase(String database)  {
        template.execute("CREATE DATABASE " + database);
    }

    @Override
    public void dropBase(String database) {
        template.execute("DROP DATABASE " + database);
    }

    @Override
    public String getPrimaryKey(String tableName) throws DatabaseException {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet rs = meta.getPrimaryKeys(null, null, tableName);
            String columnName = "";
            while (rs.next()) {
                columnName = rs.getString("COLUMN_NAME");
            }
            return columnName;
        } catch (SQLException e) {
            throw new DatabaseException("Can't get primary key. " + e.getMessage(), e);
        }
    }
}
