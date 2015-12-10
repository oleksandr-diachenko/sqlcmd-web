package ua.com.juja.positiv.sqlcmd.web.actions;

import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by POSITIV on 10.12.2015.
 */
public interface Action {

    Action NULL = new NullAction();

    boolean canProcess(String url);

    void post(HttpServletRequest request, HttpServletResponse response);

    void get(HttpServletRequest request, HttpServletResponse response) ;
}
