package ua.com.juja.positiv.sqlcmd.web;

import ua.com.juja.positiv.sqlcmd.databasemanager.DatabaseManager;
import ua.com.juja.positiv.sqlcmd.service.Service;
import ua.com.juja.positiv.sqlcmd.service.ServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by POSITIV on 30.10.2015.
 */
public class MainServlet extends HttpServlet {

    private Service service;

    @Override
    public void init() throws ServletException {
        super.init();
        service = new ServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = getAction(request);

        if (action.equals("/connect")) {
            request.getRequestDispatcher("connect.jsp").forward(request, response);
            return;
        }

        DatabaseManager manager = (DatabaseManager) request.getSession().getAttribute("manager");

        if (manager == null) {
            response.sendRedirect(response.encodeRedirectURL("connect"));
            return;
        }

        if (action.equals("/menu") || action.equals("/")) {
            request.setAttribute("items", service.commandList());
            request.getRequestDispatcher("menu.jsp").forward(request, response);

        } else if (action.equals("/help")) {
            request.getRequestDispatcher("help.jsp").forward(request, response);

        } else if (action.equals("/list")) {
            list(manager, request, response);

        } else if (action.equals("/find")) {
            request.getRequestDispatcher("tableName.jsp").forward(request, response);

        } else if (action.equals("/clear")) {
            request.getRequestDispatcher("clear.jsp").forward(request, response);

        } else if (action.equals("/delete")) {
            request.getRequestDispatcher("delete.jsp").forward(request, response);

        } else if (action.equals("/create")) {
            setActionURL(request, "createRecord");
            request.getRequestDispatcher("tableName.jsp").forward(request, response);

        } else if (action.equals("/createDatabase")) {
            request.getRequestDispatcher("createDatabase.jsp").forward(request, response);

        } else if (action.equals("/deleteDatabase")) {
            request.getRequestDispatcher("deleteDatabase.jsp").forward(request, response);

        } else if (action.equals("/update")) {
            setActionURL(request, "updateRecord");
            request.getRequestDispatcher("tableName.jsp").forward(request, response);

        } else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = getAction(request);

        DatabaseManager manager = (DatabaseManager) request.getSession().getAttribute("manager");

        if (action.equals("/connect")) {
            connect(request, response);

        } else if (action.equals("/find")) {
            find(manager, request, response);

        } else if (action.equals("/clear")) {
            clear(manager, request, response);

        } else if (action.equals("/delete")) {
            delete(manager, request, response);

        } else if (action.equals("/createRecord")) {
            String tableName = getTableName(request);
            setColumnCount(request, manager, tableName);
            setTableName(request, tableName);
            try {
                request.getRequestDispatcher("create.jsp").forward(request, response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }

        } else if (action.equals("/create")) {
            create(manager, request, response);

        } else if (action.equals("/createDatabase")) {
            createDatabase(manager, request, response);

        } else if (action.equals("/deleteDatabase")) {
            deleteDatabase(manager, request, response);

        } else if (action.equals("/updateRecord")) {
            String tableName = getTableName(request);
            setColumnCount(request, manager, tableName);
            setTableName(request, tableName);
            try {
                request.getRequestDispatcher("update.jsp").forward(request, response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }

        } else if (action.equals("/update")) {
            update(manager, request, response);
        }
    }

    private void setActionURL(HttpServletRequest request, String actionURL) {
        request.setAttribute("actionURL", actionURL);
    }

    private void setColumnCount(HttpServletRequest request, DatabaseManager manager, String tableName) {
        try {
            List<String> tableData = service.find(manager, tableName);
            request.setAttribute("columnCount", tableData.get(0));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setTableName(HttpServletRequest request, String tableName) {
        request.setAttribute("tableName", tableName);
    }

    private String getTableName(HttpServletRequest request) {
        return request.getParameter("tableName");
    }

    private int getColumnCount(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("columnCount"));
    }

    private String getAction(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.substring(request.getContextPath().length(), requestURI.length());
    }

    private void update(DatabaseManager manager, HttpServletRequest request,
                        HttpServletResponse response) {
        Map<String, Object> data = new HashMap<>();
        for (int index = 1; index < getColumnCount(request); index++) {
            data.put(request.getParameter("columnName" + index),
                    request.getParameter("columnValue" + index));
        }

        String tableName = getTableName(request);
        String keyName = request.getParameter("keyName");
        String keyValue = request.getParameter("keyValue");
        try {
            service.update(manager, tableName, keyName, keyValue, data);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
            error(request, response, e);
        }
    }

    private void deleteDatabase(DatabaseManager manager, HttpServletRequest request,
                                HttpServletResponse response) {
        String databaseName = request.getParameter("databaseName");
        try {
            service.deleteBase(manager, databaseName);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
            error(request, response, e);
        }
    }

    private void createDatabase(DatabaseManager manager, HttpServletRequest request,
                                HttpServletResponse response) {
        String databaseName = request.getParameter("databaseName");
        try {
            service.createBase(manager, databaseName);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
            error(request, response, e);
        }
    }

    private void create(DatabaseManager manager, HttpServletRequest request,
                        HttpServletResponse response) {
        Map<String, Object> data = new HashMap<>();
        for (int index = 1; index <= getColumnCount(request); index++) {
            data.put(request.getParameter("columnName" + index),
                    request.getParameter("columnValue" + index));
        }
        String tableName = getTableName(request);
        try {
            service.create(manager, tableName, data);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
            error(request, response, e);
        }
    }

    private void delete(DatabaseManager manager, HttpServletRequest request,
                        HttpServletResponse response) {
        String tableName = getTableName(request);
        String keyName = request.getParameter("keyName");
        String keyValue = request.getParameter("keyValue");
        try {
            service.delete(manager, tableName, keyName, keyValue);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
            error(request, response, e);
        }
    }

    private void clear(DatabaseManager manager, HttpServletRequest request,
                       HttpServletResponse response) {
        String tableName = getTableName(request);
        try {
            service.clear(manager, tableName);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
            error(request, response, e);
        }
    }

    private void list(DatabaseManager manager, HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            Set<String> tableNames = service.list(manager);
            request.setAttribute("tables", tableNames);
            request.getRequestDispatcher("list.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
            error(request, response, e);
        }
    }

    private void find(DatabaseManager manager, HttpServletRequest request,
                      HttpServletResponse response) {
        String tableName = getTableName(request);
        try {
            List<String> tableData = service.find(manager, tableName);
            request.setAttribute("tableData", tableData);
            request.setAttribute("columnCount", tableData.get(0));
            request.getRequestDispatcher("find.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
            error(request, response, e);
        }
    }

    private void connect(HttpServletRequest request, HttpServletResponse response) {
        String database = request.getParameter("database");
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        try {
            DatabaseManager manager = service.connect(database, user, password);
            request.getSession().setAttribute("manager", manager);
            response.sendRedirect(response.encodeRedirectURL("menu"));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            error(request, response, e);
        }
    }

    private void error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        request.setAttribute("message", e.getMessage());
        try {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (ServletException | IOException e1) {
            e.printStackTrace();
        }
    }
}
