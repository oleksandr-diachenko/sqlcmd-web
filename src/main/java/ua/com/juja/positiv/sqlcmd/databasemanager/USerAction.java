package ua.com.juja.positiv.sqlcmd.databasemanager;

/**
 * Created by POSITIV on 21.12.2015.
 */
public class UserAction {

    private int id;
    private String userName;
    private String dbName;
    private String userAction;

    public UserAction() {
        //do nothing
    }

    public UserAction(String userName, String dbName, String userAction) {
        this.userName = userName;
        this.dbName = dbName;
        this.userAction = userAction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }
}
