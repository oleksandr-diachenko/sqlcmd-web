package ua.com.juja.positiv.sqlcmd.dao.entity;

import javax.persistence.*;

/**
 * Created by POSITIV on 21.12.2015.
 */
@Entity
@Table(name = "database_connection", schema = "public")
public class DatabaseConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "db_name")
    private String dbName;

    public DatabaseConnection() {
        //do nothing
    }

    public DatabaseConnection(String userName, String dbName) {
        this.userName = userName;
        this.dbName = dbName;
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
}
