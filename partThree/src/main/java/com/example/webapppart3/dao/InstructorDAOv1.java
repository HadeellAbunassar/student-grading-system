package com.example.webapppart3.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InstructorDAOv1 implements InstructorDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ArrayList<String> showInstructorCourses(String name) {
        String instructorIdSql = "SELECT userId FROM Users WHERE name = ? AND type = 'Instructor'";
        String instructorCoursesSql = "SELECT courseId, name FROM Courses WHERE instructorId = ?";

        List<String> instructorCourses = new ArrayList<>();

        String instructorId = jdbcTemplate.queryForObject(instructorIdSql, String.class, name);

        if (instructorId != null) {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(instructorCoursesSql, instructorId);
            for (Map<String, Object> row : rows) {
                String courseId = row.get("courseId").toString();
                String courseName = (String) row.get("name");
                String courseInfo = "Course id " + courseId + ": Course Name " + courseName;
                instructorCourses.add(courseInfo);
            }
        } else {
            System.out.println("An error occurred. Try again later.");
        }

        return (ArrayList<String>) instructorCourses;
    }

    @Override
    public List<String> showCourseStudents(String courseName, String instructorName) {
        String instructorIdSql = "SELECT userId FROM Users WHERE name = ? AND type = 'Instructor'";
        String courseIdSql = "SELECT courseId FROM Courses WHERE name = ? AND instructorId = ?";
        String studentQuery = "SELECT u.name AS student_name " +
                "FROM CourseEnrollment ce " +
                "JOIN Users u ON ce.studentId = u.userId " +
                "WHERE ce.courseId = ?";

        List<String> results = new ArrayList<>();

        String instructorId = jdbcTemplate.queryForObject(instructorIdSql, String.class, instructorName);

        if (instructorId != null) {
            Integer courseId = jdbcTemplate.queryForObject(courseIdSql, Integer.class, courseName, instructorId);
            if (courseId != null) {
                List<Map<String, Object>> rows = jdbcTemplate.queryForList(studentQuery, courseId);
                results.add("Students enrolled in " + courseName + " (Instructor: " + instructorName + "):");
                boolean found = false;
                for (Map<String, Object> row : rows) {
                    String studentName = (String) row.get("student_name");
                    results.add("- " + studentName);
                    found = true;
                }
                if (!found) {
                    results.add("No students enrolled in this course.");
                }
            } else {
                results.add("Course " + courseName + " not taught by " + instructorName + ".");
            }
        } else {
            System.out.println("An error occurred. Try again later.");
        }

        return results;
    }

    @Override
    public String insertingStudentsGrades(String courseName, HashMap<String, Integer> gradesMap) {
        String selectCourseIdSql = "SELECT courseId FROM Courses WHERE name = ?";

        for (Map.Entry<String, Integer> entry : gradesMap.entrySet()) {
            String studentName = entry.getKey();
            int grade = entry.getValue();

            Integer courseId = jdbcTemplate.queryForObject(selectCourseIdSql, Integer.class, courseName);
            if (courseId != null) {
                String selectStudentIdSql = "SELECT userId FROM Users WHERE name = ?";
                Integer studentId = jdbcTemplate.queryForObject(selectStudentIdSql, Integer.class, studentName);
                if (studentId != null) {
                    String insertGradeSql = "INSERT INTO Grades (studentId, CourseId, grade) VALUES (?, ?, ?)";
                    jdbcTemplate.update(insertGradeSql, studentId, courseId, grade);
                } else {
                    return "Student '" + studentName + "' not found.";
                }
            } else {
                return "Course not found.";
            }
        }
        return "Inserting done successfully";
    }
}
