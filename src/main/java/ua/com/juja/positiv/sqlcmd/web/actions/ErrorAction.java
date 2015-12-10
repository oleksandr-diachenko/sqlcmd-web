package ua.com.juja.positiv.sqlcmd.web.actions;

import ua.com.juja.positiv.sqlcmd.service.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by POSITIV on 10.12.2015.
 */
public class ErrorAction extends AbstractAction {

    public ErrorAction(Service service) {
        super(service);
    }

    @Override
    public boolean canProcess(String url) {
        return true;
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) {
        goTo("error", request, response);
    }
}
