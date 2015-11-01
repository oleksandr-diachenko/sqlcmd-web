package ua.com.juja.positiv.sqlcmd.web;

import ua.com.juja.positiv.sqlcmd.service.Service;
import ua.com.juja.positiv.sqlcmd.service.ServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by POSITIV on 30.10.2015.
 */
public class MainServlet extends HttpServlet{

    private Service service = new ServiceImpl();



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
        } else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = getAction(request);
        if(action.startsWith("/connect")){
            response.sendRedirect(response.encodeRedirectURL("/menu"));
        }
    }

    private String getAction(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String action = requestURI.substring(request.getContextPath().length(), requestURI.length());
        return action;
    }
}
