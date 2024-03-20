package org.example.dao;

import java.util.ArrayList;
import java.util.HashMap;

public interface InstructorDAO {
    ArrayList<String> showInstructorCourses(String name);
    void insertingStudentsGrades(String CourseName,HashMap<String, Integer> gradesMap);
    String showCourseStudents(String CourseNamem , String Iname);
}
