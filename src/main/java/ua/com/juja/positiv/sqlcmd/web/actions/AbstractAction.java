package ua.com.juja.positiv.sqlcmd.web.actions;

import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseException;
import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;
import ua.com.juja.positiv.sqlcmd.service.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by POSITIV on 10.12.2015.
 */
public class AbstractAction implements Action{

    protected Service service;

    public AbstractAction(Service service) {
        this.service = service;
    }


    @Override
    public boolean canProcess(String url) {
        return false;
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void redirect(String url, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(response.encodeRedirectURL(url));
        } catch (Exception e) {
            error(request, response, e);
        }
    }


    protected void error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        setAttribute("message", e.getMessage(), request);
        goTo("error", request, response);
    }

    protected void goTo(String jsp, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(jsp + ".jsp").forward(request, response);
        } catch (Exception e) {
            error(request, response, e);
        }
    }

    protected void setAttribute(String attributeName, Object attributeValue, HttpServletRequest request) {
        request.setAttribute(attributeName, attributeValue);
    }

    protected DatabaseManager getManager(HttpServletRequest request, HttpServletResponse response) {
        DatabaseManager manager = (DatabaseManager) request.getSession().getAttribute("manager");

        if (request.getSession().getAttribute("manager") != null) {
            return manager;
        }
        redirect("connect", request, response);
        return DatabaseManager.NULL;
    }

    protected void getTableNames(DatabaseManager manager, HttpServletRequest request,
                               HttpServletResponse response) {
        try {
            setAttribute("tables", manager.getTableNames(), request);
            goTo("table-names", request, response);
        } catch (DatabaseException e) {
            error(request, response, e);
        }
    }
}
