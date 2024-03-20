package org.example.dao;

import org.example.dao.InstructorDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class InstructorDAOv1 implements InstructorDAO {
    Connection connection;
    public InstructorDAOv1(Connection connection) throws  SQLException {
        this.connection=connection;
    }
    public  ArrayList<String> showInstructorCourses(String name){
        ArrayList<String> instructorCourses = new ArrayList<String>();
        String instructorId="-1";

        String sql = "SELECT userId FROM Users WHERE name = ? AND type = 'Instructor' ";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    instructorId =resultSet.getString("userId");
                } else {
                    System.out.println("An error Just occur , try again later");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String coursesSql = "SELECT courseId, name FROM Courses WHERE instructorId = ?";
        try (PreparedStatement coursesStatement = connection.prepareStatement(coursesSql)) {
            coursesStatement.setString(1, instructorId);

            try (ResultSet coursesResultSet = coursesStatement.executeQuery()) {
                while (coursesResultSet.next()) {
                    String courseId = coursesResultSet.getString("courseId");
                    String courseName = coursesResultSet.getString("name");
                    String courseInfo =  "Course id " + courseId + ": Course Name " + courseName;
                    instructorCourses.add(courseInfo);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return instructorCourses;

    }

    public String showCourseStudents(String courseName,String instructorName){
        String instructorId="-1";
        StringBuilder result = new StringBuilder();

        String sql1 = "SELECT userId FROM Users WHERE name = ? AND type = 'Instructor' ";
        try (PreparedStatement statement = connection.prepareStatement(sql1)) {
            statement.setString(1, instructorName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    instructorId =resultSet.getString("userId");
                } else {
                    System.out.println("An error Just occur , try again later");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String courseIdQuery = "SELECT courseId FROM Courses WHERE name = ? AND instructorId = ?";
        int courseId = -1;
        try (PreparedStatement courseIdStatement = connection.prepareStatement(courseIdQuery)) {
            courseIdStatement.setString(1, courseName);
            courseIdStatement.setString(2, instructorId);
            try (ResultSet courseIdResultSet = courseIdStatement.executeQuery()) {
                if (courseIdResultSet.next()) {
                    courseId = courseIdResultSet.getInt("courseId");
                } else {
                    return "Course " + courseName + "  not taught by " + instructorName + ".";
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        String studentQuery = "SELECT u.name AS student_name " +
                "FROM CourseEnrollment ce " +
                "JOIN Users u ON ce.studentId = u.userId " +
                "WHERE ce.courseId = ?";
        try (PreparedStatement studentStatement = connection.prepareStatement(studentQuery)) {
            studentStatement.setInt(1, courseId);
            try (ResultSet studentResultSet = studentStatement.executeQuery()) {
                result.append("Students enrolled in ").append(courseName).append(" (Instructor: ").append(instructorName).append("):\n");
                boolean found = false;
                while (studentResultSet.next()) {
                    String studentName = studentResultSet.getString("student_name");
                    result.append("- ").append(studentName).append("\n");
                    found = true;
                }
                if (!found) {
                    result.append("No students enrolled in this course.");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return result.toString();

    }
    public void insertingStudentsGrades(String CourseName,HashMap<String, Integer> gradesMap) {

        String selectCourseIdSql = "SELECT courseId FROM Courses WHERE name = ?";
        int courseId = -1;
        try (PreparedStatement selectCourseIdStatement = connection.prepareStatement(selectCourseIdSql)) {
            selectCourseIdStatement.setString(1, CourseName);
            try (ResultSet resultSet = selectCourseIdStatement.executeQuery()) {
                if (resultSet.next()) {
                    courseId = resultSet.getInt("courseId");
                } else {
                    System.out.println("Course not found.");
                    return;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (HashMap.Entry<String, Integer> entry : gradesMap.entrySet()) {
            String studentName = entry.getKey();
            int grade = entry.getValue();

            String selectStudentIdSql = "SELECT userId FROM Users WHERE name = ?";
            int studentId = -1;
            try (PreparedStatement selectStudentIdStatement = connection.prepareStatement(selectStudentIdSql)) {
                selectStudentIdStatement.setString(1, studentName);
                try (ResultSet resultSet = selectStudentIdStatement.executeQuery()) {
                    if (resultSet.next()) {
                        studentId = resultSet.getInt("userId");
                    } else {
                        System.out.println("Student '" + studentName + "' not found.");
                        continue;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            String insertGradeSql = "INSERT INTO Grades (studentId, CourseId, grade) VALUES (?, ?, ?)";
            try (PreparedStatement insertGradeStatement = connection.prepareStatement(insertGradeSql)) {
                insertGradeStatement.setInt(1, studentId);
                insertGradeStatement.setInt(2, courseId);
                insertGradeStatement.setInt(3, grade);
                insertGradeStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}



