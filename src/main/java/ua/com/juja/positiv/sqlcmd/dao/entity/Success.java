package ua.com.juja.positiv.sqlcmd.dao.entity;

/**
 * Created by POSITIV on 17.01.2016.
 */
public class Success {
    private String action;
    private String menu = "To menu";

    public Success(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public String getMenu() {
        return menu;
    }
}
