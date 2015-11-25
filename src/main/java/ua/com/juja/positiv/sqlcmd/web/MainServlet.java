package ua.com.juja.positiv.sqlcmd.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;
import ua.com.juja.positiv.sqlcmd.service.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by POSITIV on 30.10.2015.
 */
public class MainServlet extends HttpServlet {

    @Autowired
    private Service service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = getAction(request);

        if (action.equals("/connect")) {
            goTo("connect", request, response);
            return;
        }

        DatabaseManager manager = (DatabaseManager) request.getSession().getAttribute("manager");

        if (manager == null) {
            redirect("connect", request, response);
            return;
        }

        try {
            if (action.equals("/menu") || action.equals("/")) {
                setAttribute("items", service.commandList(), request);
                goTo("menu", request, response);

            } else if (action.equals("/create-table")) {
                goTo("table", request, response);

            } else if (action.equals("/table-names")) {
                getTableNames(manager, request, response);

            } else if (action.equals("/table-data")) {
                goTo("table-name", request, response);

            } else if (action.equals("/clear-table")) {
                goTo("clear-table", request, response);

            } else if (action.equals("/delete-record")) {
                goTo("delete-record", request, response);

            } else if (action.equals("/create-record")) {
                setAttribute("actionURL", "create", request);
                goTo("table-name", request, response);

            } else if (action.equals("/create-database")) {
                goTo("create-database", request, response);

            } else if (action.equals("/delete-database")) {
                goTo("delete-database", request, response);

            } else if (action.equals("/update-record")) {
                setAttribute("actionURL", "update", request);
                goTo("table-name", request, response);

            } else {
                goTo("error", request, response);
            }
        } catch (Exception e) {
            error(request, response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = getAction(request);

        DatabaseManager manager = (DatabaseManager) request.getSession().getAttribute("manager");

        try {
            if (action.equals("/connect")) {
                connect(request, response);

            } else if (action.equals("/table-data")) {
                getTableData(manager, request, response);

            } else if (action.equals("/clear-table")) {
                clearTable(manager, request, response);

            } else if (action.equals("/delete-record")) {
                deleteRecord(manager, request, response);

            } else if (action.equals("/create")) {
                setColumnCountAndTableName(request, manager);
                goTo("create-record", request, response);

            } else if (action.equals("/create-record")) {
                createRecord(manager, request, response);

            } else if (action.equals("/create-database")) {
                createDatabase(manager, request, response);

            } else if (action.equals("/delete-database")) {
                deleteDatabase(manager, request, response);

            } else if (action.equals("/column-parameters")) {
                setColumnCountAndTableName(request);
                goTo("create-table", request, response);

            } else if (action.equals("/table")) {
                createTable(manager, request, response);

            } else if (action.equals("/update")) {
                setColumnCountAndTableName(request, manager);
                goTo("update-record", request, response);

            } else if (action.equals("/update-record")) {
                updateRecord(manager, request, response);
            }
        } catch (Exception e) {
            error(request, response, e);
        }
    }

    private void setColumnCountAndTableName(HttpServletRequest request, DatabaseManager manager)
            throws Exception {
        setAttribute("columnCount", getColumnCount(manager, getParameter("tableName", request)), request);
        setAttribute("tableName", getParameter("tableName", request), request);
    }


    private void setColumnCountAndTableName(HttpServletRequest request) {
        setAttribute("tableName",  getParameter("tableName", request), request);
        setAttribute("columnCount", getParameter("columnCount", request), request);
    }

    private void setAttribute(String attributeName, Object attributeValue, HttpServletRequest request) {
        request.setAttribute(attributeName, attributeValue);
    }

    private String getParameter(String parameterName, HttpServletRequest request) {
        return request.getParameter(parameterName);
    }

    private int getColumnCount(DatabaseManager manager, String tableName) throws Exception {
        return Integer.parseInt(manager.getTableData(tableName).get(0));
    }

    private String getAction(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.substring(request.getContextPath().length(), requestURI.length());
    }

    private void error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        setAttribute("message", e.getMessage(), request);
        try {
            goTo("error", request, response);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void goTo(String jsp, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.getRequestDispatcher(jsp + ".jsp").forward(request, response);
        } catch (Exception e) {
            error(request, response, e);
        }
    }

    private void redirect(String url, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(response.encodeRedirectURL(url));
        } catch (Exception e) {
            error(request, response, e);
        }
    }

    private void createTable(DatabaseManager manager, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        int columnCount = Integer.parseInt(getParameter("columnCount", request));
        Map<String, Object> data = getData(
                "columnName",
                "columnType",
                columnCount - 1,
                request);

        manager.createTable(getParameter("tableName", request), getParameter("keyName", request), data);
        goTo("success", request, response);
    }

    private void updateRecord(DatabaseManager manager, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        String tableName = getParameter("tableName", request);
        Map<String, Object> data = getData(
                "columnName",
                "columnValue",
                getColumnCount(manager, tableName) - 1,
                request);

        manager.updateRecord(tableName, getParameter("keyName", request), getParameter("keyValue", request), data);
        goTo("success", request, response);
    }

    private void createRecord(DatabaseManager manager, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        String tableName = getParameter("tableName", request);
        Map<String, Object> data = getData(
                "columnName",
                "columnValue",
                getColumnCount(manager, tableName),
                request);

        manager.createRecord(tableName, data);
        goTo("success", request, response);
    }

    private void deleteDatabase(DatabaseManager manager, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        manager.dropBase(getParameter("databaseName", request));
        goTo("success", request, response);
    }

    private void createDatabase(DatabaseManager manager, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        manager.createBase(getParameter("databaseName", request));
        goTo("success", request, response);
    }

    private void deleteRecord(DatabaseManager manager, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        manager.deleteRecord(
                getParameter("tableName", request),
                getParameter("keyName", request),
                getParameter("keyValue", request));
        goTo("success", request, response);
    }

    private void clearTable(DatabaseManager manager, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        manager.clearTable(getParameter("tableName", request));
        goTo("success", request, response);
    }

    private void getTableNames(DatabaseManager manager, HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        setAttribute("tables", manager.getTableNames(), request);
        goTo("table-names", request, response);
    }

    private void getTableData(DatabaseManager manager, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        String tableName = getParameter("tableName", request);
        setAttribute("table", service.getTableData(manager, tableName), request);
        goTo("table-data", request, response);
    }

    private void connect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DatabaseManager manager = service.connect(
                getParameter("database", request),
                getParameter("user", request),
                getParameter("password", request));
        request.getSession().setAttribute("manager", manager);
        redirect("menu", request, response);
    }

    private Map<String, Object> getData(String key, String value,
                                        int columnCount, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        for (int index = 0; index < columnCount; index++) {
            int columnIndex = index + 1;
            data.put(getParameter(key + columnIndex, request),
                    getParameter(value + columnIndex, request));
        }
        return data;
    }
}
