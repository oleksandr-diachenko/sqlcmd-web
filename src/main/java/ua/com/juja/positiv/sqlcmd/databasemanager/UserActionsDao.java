package ua.com.juja.positiv.sqlcmd.databasemanager;

import java.util.List;

/**
 * Created by POSITIV on 21.12.2015.
 */
public interface UserActionsDao {

    void log(String userName, String dbName, String userAction);

    List<UserAction> getAllFor(String userName);
}
