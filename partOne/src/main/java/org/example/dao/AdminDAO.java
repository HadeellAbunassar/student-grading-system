package org.example.dao;

public interface AdminDAO {
    String addStudent(String name);
    String addInstructor(String name);
    String  assignInstructorToCourse(String courseName,String InstructorName);

}
