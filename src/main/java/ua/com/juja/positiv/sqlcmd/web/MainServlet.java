package ua.com.juja.positiv.sqlcmd.web;

import ua.com.juja.positiv.sqlcmd.service.Service;
import ua.com.juja.positiv.sqlcmd.service.ServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by POSITIV on 30.10.2015.
 */
public class MainServlet extends HttpServlet{

    private Service service;

    @Override
    public void init() throws ServletException {
        super.init();
        service = new ServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getAction(request);

        if(action.equals("/menu") || action.equals("/")){
            request.setAttribute("items", service.commandList());
            request.getRequestDispatcher("menu.jsp").forward(request, response);
        } else if(action.equals("/help")){
            request.getRequestDispatcher("help.jsp").forward(request, response);
        } else if(action.equals("/connect")){
            request.getRequestDispatcher("connect.jsp").forward(request, response);
        } else if(action.equals("/list")){
            try {
                request.setAttribute("items", service.list());
                request.getRequestDispatcher("list.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = getAction(request);
        if(action.startsWith("/connect")){
            String database = request.getParameter("database");
            String user = request.getParameter("user");
            String password = request.getParameter("password");

            try {
                service.connect(database, user, password);
                response.sendRedirect(response.encodeRedirectURL("menu"));
            } catch (Exception e){
                request.setAttribute("message", e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
    }

    private String getAction(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String action = requestURI.substring(request.getContextPath().length(), requestURI.length());
        return action;
    }
}
