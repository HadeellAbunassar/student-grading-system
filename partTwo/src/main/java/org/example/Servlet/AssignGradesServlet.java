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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/assignGrades.do")
public class AssignGradesServlet extends HttpServlet {
    private InstructorDAOv1 idao;
    @Override
    public void init()   {
        idao = new InstructorDAOv1();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String courseName = request.getParameter("courseName");
        String studentName = request.getParameter("studentName");
        int grade = Integer.parseInt(request.getParameter("grades"));
        Map<String, Integer> gradesMap = new HashMap<>();
        gradesMap.put(studentName,grade);
        String msg = idao.insertingStudentsGrades(courseName, (HashMap<String, Integer>) gradesMap);
        try {
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("assignGrades.jsp").forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
