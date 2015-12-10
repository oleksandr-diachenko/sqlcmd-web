package ua.com.juja.positiv.sqlcmd.web.actions;

import ua.com.juja.positiv.sqlcmd.service.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by POSITIV on 10.12.2015.
 */
public class UpdateRecordAction extends AbstractAction {

    public UpdateRecordAction(Service service) {
        super(service);
    }

    @Override
    public boolean canProcess(String url) {
        return url.equals("/update-record");
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) {
        setAttribute("actionURL", "update-record", request);
        goTo("table-name", request, response);
    }
}
