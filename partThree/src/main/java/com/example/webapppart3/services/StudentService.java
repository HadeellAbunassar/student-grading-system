package com.example.webapppart3.services;

import com.example.webapppart3.dao.StudentDAOv1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentDAOv1 studentDAO;

    public List<String> showGrades(String username) throws SQLException {
        return studentDAO.showGrades(username);
    }

    public List<String> showCourseStat(String courseName,String username) throws SQLException {
        return studentDAO.showCourseStat(courseName,username);
    }

    public List<String> showEnrolledCoursesWithInstructor(String username) throws SQLException {
        return studentDAO.showEnrolledCoursesWithInstructor(username);
    }

    public List<String> showAvailableCourses() throws SQLException {
        return studentDAO.showAvailableCourses();
    }

    public String enrollInCourse(String studentName, String courseId) throws SQLException {
        return studentDAO.enrollInCourse(studentName, courseId);
    }
}
