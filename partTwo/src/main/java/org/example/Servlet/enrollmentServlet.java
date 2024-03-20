package org.example.Servlet;

import org.example.dao.studentDAOv1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/enrollment.do")
public class enrollmentServlet extends HttpServlet {

    private studentDAOv1 sdao;

    @Override
    public void init() {
        sdao = new studentDAOv1();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("userName");
        String courseId = request.getParameter("courseId");

        String enrollInCourse;
        try {
            enrollInCourse = sdao.enrollInCourse(username, courseId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("enrollInCourse", enrollInCourse);
        request.getRequestDispatcher("enrollment.jsp").forward(request, response);

    }
}
