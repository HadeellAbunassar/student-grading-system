package org.example.dao;
import java.sql.*;
import java.util.ArrayList;

public class StudentDAOv1 implements StudentDAO {

    Connection connection;

    public StudentDAOv1( Connection connection) {
        this.connection=connection;
    }



    public ArrayList<String> showGrades(String studentName) throws SQLException {
        ArrayList<String> gradesList = new ArrayList<>();

        String studentIdQuery = "SELECT userId FROM Users WHERE name = ?";
        int studentId = -1;

        try (PreparedStatement studentIdStatement = connection.prepareStatement(studentIdQuery)) {
            studentIdStatement.setString(1, studentName);
            try (ResultSet resultSet = studentIdStatement.executeQuery()) {
                if (resultSet.next()) {
                    studentId = resultSet.getInt("userId");
                } else {
                    gradesList.add("Student '" + studentName + "' not found.");
                    return gradesList;
                }
            }
        }

        String sql = "SELECT c.name AS course_name, g.grade " +
                "FROM Grades g " +
                "JOIN Courses c ON g.CourseId = c.courseId " +
                "WHERE g.studentId = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    gradesList.add("No grades found for the student: " + studentName);
                    return gradesList;
                }
                gradesList.add("Grades for student: " + studentName);
                gradesList.add("Course Name\t\tGrade");
                while (resultSet.next()) {
                    String courseName = resultSet.getString("course_name");
                    int grade = resultSet.getInt("grade");
                    gradesList.add(courseName + "\t\t" + grade);
                }
            }
        }

        return gradesList;
    }

    public ArrayList<String> showCourse(String courseName) throws SQLException {
        ArrayList<String> courseInfo = new ArrayList<>();

        String courseIdQuery = "SELECT courseId FROM Courses WHERE name = ?";
        int courseId = -1;

        try (PreparedStatement courseIdStatement = connection.prepareStatement(courseIdQuery)) {
            courseIdStatement.setString(1, courseName);
            try (ResultSet resultSet = courseIdStatement.executeQuery()) {
                if (resultSet.next()) {
                    courseId = resultSet.getInt("courseId");
                } else {
                    courseInfo.add("Course '" + courseName + "' not found.");
                    return courseInfo;
                }
            }
        }
        String statisticsQuery = "SELECT MIN(grade) AS min_grade, MAX(grade) AS max_grade, AVG(grade) AS avg_grade " +
                "FROM Grades WHERE courseId = ?";
        double minGrade = 0, maxGrade = 0, avgGrade = 0;

        try (PreparedStatement statisticsStatement = connection.prepareStatement(statisticsQuery)) {
            statisticsStatement.setInt(1, courseId);
            try (ResultSet resultSet = statisticsStatement.executeQuery()) {
                if (resultSet.next()) {
                    minGrade = resultSet.getDouble("min_grade");
                    maxGrade = resultSet.getDouble("max_grade");
                    avgGrade = resultSet.getDouble("avg_grade");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        courseInfo.add("Course Name: " + courseName);
        courseInfo.add("Minimum Grade: " + minGrade);
        courseInfo.add("Maximum Grade: " + maxGrade);
        courseInfo.add("Average Grade: " + avgGrade);

        return courseInfo;
    }

    public ArrayList<String> showEnrolledCoursesWithInstructor(String name) throws SQLException {
        ArrayList<String> enrolledCourses = new ArrayList<>();

        int studentId = -1;

        try (PreparedStatement studentIdStatement = connection.prepareStatement("SELECT userId FROM Users WHERE name = ?")) {
            studentIdStatement.setString(1, name);
            try (ResultSet resultSet = studentIdStatement.executeQuery()) {
                if (resultSet.next()) {
                    studentId = resultSet.getInt("userId");
                } else {
                    enrolledCourses.add("Student '" + name + "' not found.");
                    return enrolledCourses;
                }
            }
        }

        String query = "SELECT c.name AS course_name, u.name AS instructor_name " +
                "FROM courseEnrollment ce " +
                "JOIN Courses c ON ce.courseId = c.courseId " +
                "JOIN Users u ON c.instructorId = u.userId " +
                "WHERE ce.studentId = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String courseName = resultSet.getString("course_name");
                    String instructorName = resultSet.getString("instructor_name");
                    enrolledCourses.add(courseName + " (Instructor: " + instructorName + ")");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return enrolledCourses;
    }


    public String showAvailableCourses() throws SQLException {
        StringBuilder result = new StringBuilder();
        String query = "SELECT c.courseId, c.name AS course_name, u.name AS instructor_name " +
                "FROM Courses c " +
                "JOIN Users u ON c.instructorId = u.userId";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                result.append("Available Courses:\n");
                while (resultSet.next()) {
                    int courseId = resultSet.getInt("courseId");
                    String courseName = resultSet.getString("course_name");
                    String instructorName = resultSet.getString("instructor_name");
                    result.append("Course ID: ").append(courseId).append(", Course Name: ").append(courseName).append(", Instructor: ").append(instructorName).append("\n");
                }
            }
        }


        return result.toString();
    }

    public String enrollInCourse(String studentName, String courseId)  {
        String studentIdQuery = "SELECT userId FROM Users WHERE name = ?";
        int studentId = -1;
        int CourseId = Integer.parseInt(courseId);
        try (PreparedStatement studentIdStatement = connection.prepareStatement(studentIdQuery)) {
            studentIdStatement.setString(1, studentName);
            try (ResultSet resultSet = studentIdStatement.executeQuery()) {
                if (resultSet.next()) {
                    studentId = resultSet.getInt("userId");
                } else {
                    return "Student '" + studentName + "' not found.";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String enrollQuery = "INSERT INTO CourseEnrollment (courseId, studentId) VALUES (?, ?)";
        try (PreparedStatement enrollStatement = connection.prepareStatement(enrollQuery)) {
            enrollStatement.setInt(1, CourseId);
            enrollStatement.setInt(2, studentId);
            int rowsAffected = enrollStatement.executeUpdate();
            if (rowsAffected > 0) {
                return "Enrollment successful!";
            } else {
                return "Enrollment failed. Please try again.";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}




