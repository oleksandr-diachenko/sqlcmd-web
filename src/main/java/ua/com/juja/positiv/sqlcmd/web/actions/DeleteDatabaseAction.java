package ua.com.juja.positiv.sqlcmd.web.actions;

import ua.com.juja.positiv.sqlcmd.service.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by POSITIV on 10.12.2015.
 */
public class DeleteDatabaseAction extends AbstractAction {

    public DeleteDatabaseAction(Service service) {
        super(service);
    }

    @Override
    public boolean canProcess(String url) {
        return url.equals("/delete-database");
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) {
        setAttribute("actionURL", "delete-database", request);
        goTo("database-name", request, response);
    }
}
