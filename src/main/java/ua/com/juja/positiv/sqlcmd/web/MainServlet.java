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
import java.util.Set;

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

            } else if (action.equals("/createTable")) {
                setColumnCountAndTableName(request);
                goTo("table", request, response);

            } else if (action.equals("/table")) {
                goTo("createTable", request, response);

            } else if (action.equals("/list")) {
                list(manager, request, response);

            } else if (action.equals("/find")) {
                goTo("tableName", request, response);

            } else if (action.equals("/clear")) {
                goTo("clear", request, response);

            } else if (action.equals("/delete")) {
                goTo("delete", request, response);

            } else if (action.equals("/create")) {
                setAttribute("actionURL", "createRecord", request);
                goTo("tableName", request, response);

            } else if (action.equals("/createDatabase")) {
                goTo("createDatabase", request, response);

            } else if (action.equals("/deleteDatabase")) {
                goTo("deleteDatabase", request, response);

            } else if (action.equals("/update")) {
                setAttribute("actionURL", "updateRecord", request);
                goTo("tableName", request, response);

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

            } else if (action.equals("/find")) {
                find(manager, request, response);

            } else if (action.equals("/clear")) {
                clear(manager, request, response);

            } else if (action.equals("/delete")) {
                delete(manager, request, response);

            } else if (action.equals("/createRecord")) {
                setColumnCountAndTableName(request, manager);
                goTo("create", request, response);

            } else if (action.equals("/create")) {
                create(manager, request, response);

            } else if (action.equals("/createDatabase")) {
                createDatabase(manager, request, response);

            } else if (action.equals("/deleteDatabase")) {
                deleteDatabase(manager, request, response);

            } else if (action.equals("/table")) {
                table(manager, request, response);

            } else if (action.equals("/updateRecord")) {
                setColumnCountAndTableName(request, manager);
                goTo("update", request, response);

            } else if (action.equals("/update")) {
                update(manager, request, response);
            }
        } catch (Exception e) {
            error(request, response, e);
        }
    }

    private void setColumnCountAndTableName(HttpServletRequest request, DatabaseManager manager) throws Exception {
        String tableName = getParameter("tableName", request);
        int columnCount = getColumnCount(manager, tableName);
        setAttribute("columnCount", columnCount, request);
        setAttribute("tableName", tableName, request);
    }


    private void setColumnCountAndTableName(HttpServletRequest request) {
        String tableName = getParameter("tableName", request);
        int columnCount = Integer.parseInt(getParameter("columnCount", request));
        setAttribute("tableName", tableName, request);
        setAttribute("columnCount", columnCount, request);
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

    private void goTo(String jsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    private void table(DatabaseManager manager, HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        String tableName = getParameter("tableName", request);
        int columnCount = Integer.parseInt(getParameter("columnCount", request));
        String keyName = getParameter("keyName", request);

        Map<String, Object> data = new HashMap<>();
        for (int index = 1; index < columnCount; index++) {
            data.put(getParameter("columnName" + index, request),
                    getParameter("columnType" + index, request));
        }
        manager.createTable(tableName, keyName, data);
        goTo("success", request, response);
    }

    private void update(DatabaseManager manager, HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        String tableName = getParameter("tableName", request);
        Map<String, Object> data = new HashMap<>();
        for (int index = 1; index < getColumnCount(manager, tableName); index++) {
            data.put(getParameter("columnName" + index, request),
                    getParameter("columnValue" + index, request));
        }

        String keyName = getParameter("keyName", request);
        String keyValue = getParameter("keyValue", request);
        manager.updateRecord(tableName, keyName, keyValue, data);
        goTo("success", request, response);
    }

    private void deleteDatabase(DatabaseManager manager, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        String databaseName = getParameter("databaseName", request);
        manager.dropBase(databaseName);
        goTo("success", request, response);
    }

    private void createDatabase(DatabaseManager manager, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        String databaseName = getParameter("databaseName", request);
        manager.createBase(databaseName);
        goTo("success", request, response);
    }

    private void create(DatabaseManager manager, HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        String tableName = getParameter("tableName", request);
        Map<String, Object> data = new HashMap<>();
        for (int index = 1; index <= getColumnCount(manager, tableName); index++) {
            data.put(getParameter("columnName" + index, request),
                    getParameter("columnValue" + index, request));
        }
        manager.createRecord(tableName, data);
        goTo("success", request, response);
    }

    private void delete(DatabaseManager manager, HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        String tableName = getParameter("tableName", request);
        String keyName = getParameter("keyName", request);
        String keyValue = getParameter("keyValue", request);
        manager.deleteRecord(tableName, keyName, keyValue);
        goTo("success", request, response);
    }

    private void clear(DatabaseManager manager, HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        String tableName = getParameter("tableName", request);
        manager.clearTable(tableName);
        goTo("success", request, response);
    }

    private void list(DatabaseManager manager, HttpServletRequest request,
                      HttpServletResponse response) throws Exception {
        Set<String> tableList = manager.getTableNames();
        setAttribute("tables", tableList, request);
        goTo("list", request, response);
    }

    private void find(DatabaseManager manager, HttpServletRequest request,
                      HttpServletResponse response) throws Exception {
        String tableName = getParameter("tableName", request);
        setAttribute("table", service.getTableData(manager, tableName), request);
        goTo("find", request, response);
    }

    private void connect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String database = getParameter("database", request);
        String user = getParameter("user", request);
        String password = getParameter("password", request);
        DatabaseManager manager = service.connect(database, user, password);
        request.getSession().setAttribute("manager", manager);
        redirect("menu", request, response);
    }
}
