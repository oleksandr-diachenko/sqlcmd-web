package ua.com.juja.positiv.sqlcmd.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by POSITIV on 31.10.2015.
 */
public interface Service {

    List<String> commandList();

    void connect(String database, String user, String password) throws SQLException, ClassNotFoundException;

    Set<String> list() throws SQLException;

    List<String> find(String tableName) throws SQLException;
}
