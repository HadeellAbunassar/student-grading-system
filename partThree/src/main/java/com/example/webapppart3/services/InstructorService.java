package com.example.webapppart3.services;

import com.example.webapppart3.dao.InstructorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Service
public class InstructorService {

    @Autowired
    private InstructorDAO instructorDAO;

    public List<String> showInstructorCourses(String name) throws SQLException {
        return instructorDAO.showInstructorCourses(name);
    }

    public List<String> showCourseStudents(String courseName, String instructorName) throws SQLException {
        return instructorDAO.showCourseStudents(courseName, instructorName);
    }

    public String insertingStudentsGrades(String CourseName, HashMap<String, Integer> gradesMap) throws SQLException {
       return instructorDAO.insertingStudentsGrades(CourseName, gradesMap);
    }
}
