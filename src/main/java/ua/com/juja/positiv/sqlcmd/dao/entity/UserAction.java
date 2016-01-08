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
    private String action;

    @JoinColumn(name = "database_connection_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DatabaseConnection connection;

    public UserAction() {
        //do nothing
    }

    public UserAction(String action, DatabaseConnection connection) {
        this.action = action;
        this.connection = connection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public DatabaseConnection getConnection() {
        return connection;
    }

    public void setConnection(DatabaseConnection connection) {
        this.connection = connection;
    }
}
