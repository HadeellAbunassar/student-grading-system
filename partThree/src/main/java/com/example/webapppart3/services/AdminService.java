package com.example.webapppart3.services;

import com.example.webapppart3.dao.AdminDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class AdminService {
    @Autowired
    private AdminDAO adminDAO;

    public String addStudent(String name) throws SQLException {
        return adminDAO.addStudent(name);
    }

    public String addInstructor(String name) throws SQLException {
        return adminDAO.addInstructor(name);
    }

    public String assignInstructorToCourse(String courseName, String instructorName) throws SQLException {
        return adminDAO.assignInstructorToCourse(courseName, instructorName);
    }
}
