package org.example.Servlet;

import org.example.models.userAuthentication;
import java.io.*;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns = "/index.do")
public class IndexServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("index.jsp").forward(
                request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setAttribute("userName", request.getParameter("name"));
        String username = request.getParameter("name");
        String password = request.getParameter("password");
        String type = request.getParameter("role");

        boolean isUser;
        userAuthentication authManager = new userAuthentication();
        try {
            isUser = authManager.login(username, password, type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (isUser) {
            String redirectUrl;
            if ("Student".equals(type)) {
                redirectUrl = "/student.jsp";
            } else if ("Admin".equals(type)) {
                redirectUrl = "/Admin.jsp";
            } else if ("Instructor".equals(type)) {
                redirectUrl = "/instructor.jsp";
            } else {
                redirectUrl = "/index.jsp";
            }

            response.sendRedirect(request.getContextPath() + redirectUrl);
        } else {
            request.setAttribute("errorMessage", "Invalid User Name or Password, Try Again.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }



}
