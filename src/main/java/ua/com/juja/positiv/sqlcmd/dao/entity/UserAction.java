package ua.com.juja.positiv.sqlcmd.dao.entity;

import javax.persistence.*;

/**
 * Created by POSITIV on 21.12.2015.
 */
@Entity
@Table(name = "user_actions", schema = "public")
public class UserAction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "action")
    private String userAction;

    @JoinColumn(name = "database_connection_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DatabaseConnection connection;

    public UserAction() {
        //do nothing
    }

    public UserAction(String userAction, DatabaseConnection connection) {
        this.userAction = userAction;
        this.connection = connection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    public DatabaseConnection getConnection() {
        return connection;
    }

    public void setConnection(DatabaseConnection connection) {
        this.connection = connection;
    }
}
