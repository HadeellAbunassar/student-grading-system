package org.example.Servlet;

import org.example.dao.InstructorDAOv1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/instructor.do")
public class InstructorServlet extends HttpServlet {
    private InstructorDAOv1 idao;
    @Override
    public void init()   {
        idao = new InstructorDAOv1();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String option = request.getParameter("options");
        String username = (String) session.getAttribute("userName");
        String courseName = request.getParameter("courseName");
        boolean isOptionB = false;

        if ("showCourses".equals(option)) {
            List<String> InstructorCourses = null;
            try {
                InstructorCourses = idao.showInstructorCourses(username);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("username",username);
            request.setAttribute("isOptionB", isOptionB);
            request.setAttribute("msg", InstructorCourses);
            request.getRequestDispatcher("instructorProfile.jsp").forward(request, response);
        } else if ("showstucrs".equals(option)) {
            if(courseName != null){
                List<String> courseStudents = null;
                try {
                    courseStudents = idao.showCourseStudents(courseName,username);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                request.setAttribute("msg", courseStudents);
                request.getRequestDispatcher("instructorProfile.jsp").forward(request, response);
            }
            else {
                isOptionB = true;
                request.setAttribute("isOptionB",isOptionB);
                request.getRequestDispatcher("instructorProfile.jsp").forward(request, response);
            }
        }
        else if ("assigngrades".equals(option)) {
            request.getRequestDispatcher("assignGrades.jsp").forward(request, response);
        } else
            request.getRequestDispatcher("instructor.jsp").forward(request, response);

    }

}

