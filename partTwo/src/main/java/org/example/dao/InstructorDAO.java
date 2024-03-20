package org.example.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface InstructorDAO {
    ArrayList<String> showInstructorCourses(String name) throws SQLException;
    String insertingStudentsGrades(String CourseName, HashMap<String, Integer> gradesMap);
    ArrayList<String> showCourseStudents(String CourseNamem , String Iname) throws SQLException;
}
