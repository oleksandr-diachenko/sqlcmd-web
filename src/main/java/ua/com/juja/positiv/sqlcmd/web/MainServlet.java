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

        if (action.equals("/menu") || action.equals("/")) {
            request.setAttribute("items", service.commandList());
            request.getRequestDispatcher("menu.jsp").forward(request, response);
        } else if (action.equals("/help")) {
            request.getRequestDispatcher("help.jsp").forward(request, response);
        } else if (action.equals("/connect")) {
            request.getRequestDispatcher("connect.jsp").forward(request, response);
        } else if (action.equals("/list")) {
            list(request, response);
        } else if (action.equals("/find")) {
            request.getRequestDispatcher("findInput.jsp").forward(request, response);
        } else if (action.equals("/clear")) {
            request.getRequestDispatcher("clear.jsp").forward(request, response);
        } else if (action.equals("/delete")) {
            request.getRequestDispatcher("delete.jsp").forward(request, response);
        } else if (action.equals("/create")) {
            request.getRequestDispatcher("create.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = getAction(request);
        if (action.startsWith("/connect")) {
            connect(request, response);
        } else if (action.startsWith("/find")) {
            find(request, response);
        } else if (action.startsWith("/clear")) {
            clear(request, response);
        } else if (action.startsWith("/delete")) {
            delete(request, response);
        } else if (action.startsWith("/create")) {
            create(request, response);
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tableName = request.getParameter("tableName");
        Map <String, Object> data = new HashMap<>();
        for (int index = 1; index < 5; index++) {
            data.put(request.getParameter("columnName" + index), request.getParameter("columnValue" + index));
        }
        try {
            service.create(tableName, data);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tableName = request.getParameter("tableName");
        String keyName = request.getParameter("keyName");
        String keyValue = request.getParameter("keyValue");
        try {
            service.delete(tableName, keyName, keyValue);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tableName = request.getParameter("tableName");
        try {
            service.clear(tableName);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private Set<String> list(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Set<String> tableNames = null;
        try {
            tableNames = service.list();
            request.setAttribute("tables", tableNames);
            request.getRequestDispatcher("list.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect(response.encodeRedirectURL("connect")); //TODO придумать информативный вывод при ошибке
        }
        return tableNames;
    }

    private void find(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tableName = request.getParameter("tableName");
        try {
            List<String> tableData = service.find(tableName);
            request.setAttribute("tableData", tableData);
            request.getRequestDispatcher("findOut.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect(response.encodeRedirectURL("connect")); //TODO придумать информативный вывод при ошибке
        }
    }

    private void connect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String database = request.getParameter("database");
        String user = request.getParameter("user");
        String password = request.getParameter("password");

        try {
            service.connect(database, user, password);
            response.sendRedirect(response.encodeRedirectURL("menu"));
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private String getAction(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.substring(request.getContextPath().length(), requestURI.length());
    }
}
