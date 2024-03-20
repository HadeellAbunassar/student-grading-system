package com.example.webapppart3.dao;

import java.sql.SQLException;

public interface AdminDAO {
    String addStudent(String name) throws SQLException;
    String addInstructor(String name) throws SQLException;
    String  assignInstructorToCourse(String courseName,String InstructorName) throws SQLException;

}
