package com.example.webapppart3.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface StudentDAO {
    List<String> showGrades(String studentName) throws SQLException;
    List<String> showCourseStat(String courseName,String userName) throws SQLException;
    List<String> showEnrolledCoursesWithInstructor(String name) throws SQLException;
    String enrollInCourse(String studentName, String courseId) throws SQLException;
}
