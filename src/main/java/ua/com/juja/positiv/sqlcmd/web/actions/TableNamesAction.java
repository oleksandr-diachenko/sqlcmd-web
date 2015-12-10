package ua.com.juja.positiv.sqlcmd.web.actions;

import ua.com.juja.positiv.sqlcmd.service.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by POSITIV on 10.12.2015.
 */
public class TableNamesAction extends AbstractAction {

    public TableNamesAction(Service service) {
        super(service);
    }

    @Override
    public boolean canProcess(String url) {
        return url.equals("/table-names");
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) {
        getTableNames(getManager(request, response), request, response);
    }
}
