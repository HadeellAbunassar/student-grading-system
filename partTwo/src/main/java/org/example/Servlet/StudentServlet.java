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
import java.util.List;

@WebServlet(urlPatterns = "/student.do")
public class StudentServlet extends HttpServlet {
    private studentDAOv1 sdao;
    @Override
    public void init()   {
        sdao = new studentDAOv1();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String option = request.getParameter("options");
        String username = (String) session.getAttribute("userName");
        String course = request.getParameter("course");


        if ("showGrades".equals(option)) {
            List<String> studentGrades = null;
            try {
                studentGrades = sdao.showGrades(username);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("studentChoice", studentGrades);
            request.getRequestDispatcher("studentProfile.jsp").forward(request, response);
        } else if ("showCourseInfo".equals(option)) {
            List<String> courseInfo = null;
            try {
                courseInfo  = sdao.showCourseStat(course);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("studentChoice", courseInfo);
            request.getRequestDispatcher("studentProfile.jsp").forward(request, response);
        }
        else if ("showYourCourses".equals(option)) {
            List<String> enrollmentCourses = null;
            try {
                enrollmentCourses  = sdao.showEnrolledCoursesWithInstructor(username);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("studentChoice", enrollmentCourses);
            request.getRequestDispatcher("studentProfile.jsp").forward(request, response);
        } else if("enrollInCourse".equals(option)) {
            List<String> availableCourses = null;
            try {
                availableCourses  = sdao.showAvailableCourses();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("username",username);
            request.setAttribute("availableCourses", availableCourses);
            request.getRequestDispatcher("enrollment.jsp").forward(request, response);
        }



    }



}

