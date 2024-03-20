package org.example.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentDAO {
    ArrayList<String> showGrades(String studentName) throws SQLException;
    ArrayList<String> showCourseStat(String courseName) throws SQLException;
    ArrayList<String> showEnrolledCoursesWithInstructor(String name) throws SQLException;
    String enrollInCourse(String studentName, String courseId) throws SQLException;
}
