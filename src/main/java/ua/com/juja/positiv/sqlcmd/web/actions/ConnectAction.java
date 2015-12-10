package ua.com.juja.positiv.sqlcmd.web.actions;

import ua.com.juja.positiv.sqlcmd.service.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by POSITIV on 10.12.2015.
 */
public class ConnectAction extends AbstractAction {

    public ConnectAction(Service service) {
        super(service);
    }

    @Override
    public boolean canProcess(String url) {
        return url.equals("/connect");
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) {
        goTo("connect", request, response);
    }
}
