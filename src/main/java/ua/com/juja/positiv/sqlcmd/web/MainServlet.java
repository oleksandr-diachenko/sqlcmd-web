package ua.com.juja.positiv.sqlcmd.web;

import ua.com.juja.positiv.sqlcmd.service.Service;
import ua.com.juja.positiv.sqlcmd.service.ServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getAction(request);

        if (action.equals("/")) {
            request.getRequestDispatcher("connect.jsp").forward(request, response);
        } else if (action.equals("/menu")) {
            request.setAttribute("items", service.commandList());
            request.getRequestDispatcher("menu.jsp").forward(request, response);
        } else if (action.equals("/connect")) {
            request.getRequestDispatcher("connect.jsp").forward(request, response);
        } else if (action.equals("/help")) {
            request.getRequestDispatcher("help.jsp").forward(request, response);
        } else if (action.equals("/table")) {
            request.getRequestDispatcher("table.jsp").forward(request, response);
        } else if (action.equals("/list")) {
            list(request, response);
        } else if (action.equals("/find")) {
            request.getRequestDispatcher("tableName.jsp").forward(request, response);
        } else if (action.equals("/clear")) {
            request.getRequestDispatcher("clear.jsp").forward(request, response);
        } else if (action.equals("/delete")) {
            request.getRequestDispatcher("delete.jsp").forward(request, response);
        } else if (action.equals("/create")) {
            request.setAttribute("message", "create");
            request.getRequestDispatcher("tableName.jsp").forward(request, response);
        } else if (action.equals("/createDatabase")) {
            request.getRequestDispatcher("createDatabase.jsp").forward(request, response);
        } else if (action.equals("/deleteDatabase")) {
            request.getRequestDispatcher("deleteDatabase.jsp").forward(request, response);
        } else if (action.equals("/update")) {
            request.getRequestDispatcher("update.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = getAction(request);
        if (action.equals("/connect")) {
            connect(request, response);
        } else if (action.equals("/find")) {
            find(request, response);
        } else if (action.equals("/clear")) {
            clear(request, response);
        } else if (action.equals("/delete")) {
            delete(request, response);
        } else if (action.equals("/create")) {
            create(request, response);
        } else if (action.equals("/createDatabase")) {
            createDatabase(request, response);
        } else if (action.equals("/deleteDatabase")) {
            deleteDatabase(request, response);
        } else if (action.equals("/table")) {
            table(request, response);
        } else if (action.equals("/update")) {
            update(request, response);
        }
    }

    private void table(HttpServletRequest request, HttpServletResponse response) {
        String tableName = request.getParameter("tableName");
        String keyName = request.getParameter("keyName");

        Map<String, Object> data = new HashMap<>();
        for (int index = 1; index < 4; index++) { //TODO убрать меджик намбер(количество колонок)
            data.put(request.getParameter("columnName" + index), request.getParameter("columnType" + index));
        }
        try {
            service.table(tableName, keyName, data);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
            error(request, response, e);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        String tableName = request.getParameter("tableName");
        String keyName = request.getParameter("keyName");
        String keyValue = request.getParameter("keyValue");

        Map<String, Object> data = new HashMap<>();
        for (int index = 1; index < 4; index++) { //TODO убрать меджик намбер(количество колонок)
            data.put(request.getParameter("columnName" + index), request.getParameter("columnValue" + index));
        }
        try {
            service.update(tableName, keyName, keyValue, data);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
            error(request, response, e);
        }
    }

    private void deleteDatabase(HttpServletRequest request, HttpServletResponse response) {
        String databaseName = request.getParameter("databaseName");
        try {
            service.deleteBase(databaseName);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
            error(request, response, e);
        }
    }

    private void createDatabase(HttpServletRequest request, HttpServletResponse response) {
        String databaseName = request.getParameter("databaseName");
        try {
            service.createBase(databaseName);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
            error(request, response, e);
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response) {
        try {
            String tableName = request.getParameter("tableName");
            List<String> tableData = service.find(tableName);
            int columnCount = Integer.parseInt(tableData.get(0));
            request.setAttribute("columnCount", columnCount);
            request.getRequestDispatcher("create.jsp").include(request, response);
            Map<String, Object> data = new HashMap<>();
            for (int index = 1; index <= columnCount; index++) { //TODO убрать меджик намбер(количество колонок)
                data.put(request.getParameter("columnName" + index), request.getParameter("columnValue" + index));
            }
            service.create(tableName, data);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
//            error(request, response, e);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        String tableName = request.getParameter("tableName");
        String keyName = request.getParameter("keyName");
        String keyValue = request.getParameter("keyValue");
        try {
            service.delete(tableName, keyName, keyValue);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
            error(request, response, e);
        }
    }

    private void clear(HttpServletRequest request, HttpServletResponse response) {
        String tableName = request.getParameter("tableName");
        try {
            service.clear(tableName);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
            error(request, response, e);
        }
    }

    private Set<String> list(HttpServletRequest request, HttpServletResponse response) {
        Set<String> tableNames = null;
        try {
            tableNames = service.list();
            request.setAttribute("tables", tableNames);
            request.getRequestDispatcher("list.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException e) {
            error(request, response, e);
        }
        return tableNames;
    }

    private void find(HttpServletRequest request, HttpServletResponse response) {
        String tableName = request.getParameter("tableName");
        try {
            List<String> tableData = service.find(tableName);
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
            service.connect(database, user, password);
            response.sendRedirect(response.encodeRedirectURL("menu"));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            error(request, response, e);
        }
    }

    private String getAction(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.substring(request.getContextPath().length(), requestURI.length());
    }

    private void error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        request.setAttribute("message", e.getMessage());
        try {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (ServletException | IOException e1) {
            // do nothing
        }
    }
}
