package com.example.webapppart3.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class StudentDAOv1 implements StudentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<String> showGrades(String studentName) {
        String studentIdQuery = "SELECT userId FROM Users WHERE name = ?";
        String gradesQuery = "SELECT c.name AS course_name, g.grade " +
                "FROM Grades g " +
                "JOIN Courses c ON g.CourseId = c.courseId " +
                "WHERE g.studentId = ?";

        List<String> gradesList = new ArrayList<>();

        Integer studentId = jdbcTemplate.queryForObject(studentIdQuery, Integer.class, studentName);
        if (studentId != null) {
            List<Object[]> rows = jdbcTemplate.query(gradesQuery, (rs, rowNum) -> {
                String courseName = rs.getString("course_name");
                int grade = rs.getInt("grade");
                return new Object[]{courseName, grade};
            }, studentId);

            if (!rows.isEmpty()) {
                gradesList.add("Grades for student: " + studentName);
                for (Object[] row : rows) {
                    String courseName = (String) row[0];
                    int grade = (int) row[1];
                    gradesList.add(courseName + "\t\t" + grade);
                }
            } else {
                gradesList.add("No grades found for the student: " + studentName);
            }
        } else {
            gradesList.add("Student '" + studentName + "' not found.");
        }

        return gradesList;
    }

    @Override
    public List<String> showCourseStat(String courseName, String username) {
        String courseIdQuery = "SELECT ce.courseId FROM CourseEnrollment ce " +
                "JOIN Users u ON ce.studentId = u.userId " +
                "JOIN Courses c ON ce.courseId = c.courseId " +
                "WHERE c.name = ? AND u.name = ?";
        String minGradeQuery = "SELECT MIN(grade) FROM Grades WHERE courseId = ?";
        String maxGradeQuery = "SELECT MAX(grade) FROM Grades WHERE courseId = ?";
        String avgGradeQuery = "SELECT AVG(grade) FROM Grades WHERE courseId = ?";

        List<String> courseInfo = new ArrayList<>();

        Integer courseId = jdbcTemplate.queryForObject(courseIdQuery, Integer.class, courseName, username);
        if (courseId != null) {
            double minGrade = jdbcTemplate.queryForObject(minGradeQuery, Double.class, courseId);
            double maxGrade = jdbcTemplate.queryForObject(maxGradeQuery, Double.class, courseId);
            double avgGrade = jdbcTemplate.queryForObject(avgGradeQuery, Double.class, courseId);

            courseInfo.add("Course Name: " + courseName);
            courseInfo.add("Minimum Grade: " + minGrade);
            courseInfo.add("Maximum Grade: " + maxGrade);
            courseInfo.add("Average Grade: " + avgGrade);
        } else {
            courseInfo.add("Course '" + courseName + "' not found for student '" + username + "'.");
        }

        return courseInfo;
    }


    @Override
    public List<String> showEnrolledCoursesWithInstructor(String name) {
        String studentIdQuery = "SELECT userId FROM Users WHERE name = ?";
        String enrolledCoursesQuery = "SELECT c.name AS course_name, u.name AS instructor_name " +
                "FROM CourseEnrollment ce " +
                "JOIN Courses c ON ce.courseId = c.courseId " +
                "JOIN Users u ON c.instructorId = u.userId " +
                "WHERE ce.studentId = ?";

        List<String> enrolledCourses = new ArrayList<>();

        Integer studentId = jdbcTemplate.queryForObject(studentIdQuery, Integer.class, name);
        if (studentId != null) {
            List<Object[]> rows = jdbcTemplate.query(enrolledCoursesQuery, (rs, rowNum) -> {
                String courseName = rs.getString("course_name");
                String instructorName = rs.getString("instructor_name");
                return new Object[]{courseName, instructorName};
            }, studentId);

            for (Object[] row : rows) {
                String courseName = (String) row[0];
                String instructorName = (String) row[1];
                enrolledCourses.add(courseName + " (Instructor: " + instructorName + ")");
            }
        } else {
            enrolledCourses.add("Student '" + name + "' not found.");
        }

        return enrolledCourses;
    }

    public List<String> showAvailableCourses() {
        String availableCoursesQuery = "SELECT c.courseId, c.name AS course_name, u.name AS instructor_name " +
                "FROM Courses c " +
                "JOIN Users u ON c.instructorId = u.userId";

        List<String> courses = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(availableCoursesQuery);
        for (Map<String, Object> row : rows) {
            int courseId = (int) row.get("courseId");
            String courseName = (String) row.get("course_name");
            String instructorName = (String) row.get("instructor_name");
            String courseString = "Course ID: " + courseId + ", Course Name: " + courseName + ", Instructor: " + instructorName;
            courses.add(courseString);
        }

        return courses;
    }

    @Override
    public String enrollInCourse(String studentName, String courseId) {
        String StudentId = "SELECT userId FROM Users WHERE name = ? AND type = 'Student'";
        String enrollQuery = "INSERT INTO CourseEnrollment (courseId, studentId) VALUES (?, ?)";

        String studentId = jdbcTemplate.queryForObject(StudentId, String.class, studentName);

        if (studentId != null) {
            int rowsAffected = jdbcTemplate.update(enrollQuery, courseId, studentId);
            if (rowsAffected > 0) {
                return "Enrollment successful!";
            } else {
                return "Enrollment failed. Please try again.";
            }
        } else {
            return "Student '" + studentName + "' not found.";
        }
    }
}