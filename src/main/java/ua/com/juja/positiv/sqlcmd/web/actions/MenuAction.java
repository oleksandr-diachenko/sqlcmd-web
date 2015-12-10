package ua.com.juja.positiv.sqlcmd.web.actions;

import ua.com.juja.positiv.sqlcmd.service.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by POSITIV on 10.12.2015.
 */
public class MenuAction extends AbstractAction {

    public MenuAction(Service service) {
        super(service);
    }

    @Override
    public boolean canProcess(String url) {
        return url.equals("/menu") || url.equals("/");
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) {
        List<String> attributeValue = new LinkedList<>();
        attributeValue.addAll(Arrays.asList("menu", "connect", "create-table",
                "table-names", "table-data", "update-record", "clear-table",
                "create-record", "delete-record", "delete-table", "create-database", "delete-database"));
        setAttribute("items", attributeValue, request);
        goTo("menu", request, response);
    }
}

