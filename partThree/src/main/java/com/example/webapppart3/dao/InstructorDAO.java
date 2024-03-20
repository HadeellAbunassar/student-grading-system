package com.example.webapppart3.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface InstructorDAO {
    ArrayList<String> showInstructorCourses(String name) throws SQLException;
    String insertingStudentsGrades(String CourseName, HashMap<String, Integer> gradesMap) throws SQLException;
    List<String> showCourseStudents(String CourseNamem , String Iname) throws SQLException;
}
