package ua.com.juja.positiv.sqlcmd.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by POSITIV on 30.10.2015.
 */
public class MainServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getAction(request);

        if(action.startsWith("/menu") || action.equals("/")){
            request.getRequestDispatcher("menu.jsp").forward(request, response);
        } else if(action.startsWith("/help")){
            request.getRequestDispatcher("help.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("menu.jsp").forward(request, response);
        }
    }

    private String getAction(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String action = requestURI.substring(request.getContextPath().length(), requestURI.length());
        return action;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        System.out.println(request.getParameterMap().toString());
    }
}
