package ua.com.juja.positiv.sqlcmd.web.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by POSITIV on 10.12.2015.
 */
public class NullAction implements Action {

    @Override
    public boolean canProcess(String url) {
        return false;
    }

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) {
        //do nothing
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) {
        //do nothing
    }
}
