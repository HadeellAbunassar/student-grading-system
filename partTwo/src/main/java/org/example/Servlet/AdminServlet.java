package org.example.Servlet;

import org.example.dao.AdminDAOv1;
import org.example.dbManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/admin.do")
public class AdminServlet extends HttpServlet {
    private AdminDAOv1 adao;

    @Override
    public void init() {
        adao = new AdminDAOv1();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String option = request.getParameter("options");
        String studentName = request.getParameter("studentname");
        String instructorName = request.getParameter("Instructorname");
        String courseName = request.getParameter("coursename");
        String instructorName1 = request.getParameter("Instructorname1");

        String result = null;

        if ("addstudent".equals(option)) {
                result = adao.addStudent(studentName);
        } else if ("addinstructor".equals(option)) {
                result  = adao.addInstructor(instructorName);
        }
        else {
                result = adao.assignInstructorToCourse(courseName,instructorName1);
        }
        request.setAttribute("msg", result);
        request.getRequestDispatcher("Admin.jsp").forward(request, response);

        }

    }

