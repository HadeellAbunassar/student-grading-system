package org.example.dao;


import org.example.dbManager;
import org.example.models.Course;
import org.example.models.Instructor;
import org.example.models.Student;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.Base64;

public class AdminDAOv1 implements AdminDAO {
    private dbManager databaseManager;
    Connection connection;

    public AdminDAOv1 ()  {
        databaseManager = databaseManager.getInstance();
        connection = databaseManager.getConnection();
    }
    public String addStudent(String name)  {
        Student newS = new Student(name);

        boolean AddedUser = false;

        byte[] salt = generateSalt();

        String hashedPassword = hashPassword(newS.getPassword(), salt);

        String sql = "INSERT INTO Users (name, password, salt, type) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, hashedPassword);
            statement.setString(3, Base64.getEncoder().encodeToString(salt));
            statement.setString(4, "Student");

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                AddedUser = true;
            }


        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while inserting a new Student. Please try again later.", e);

        }

        if(!AddedUser)
            return "An error occurred while inserting a new Student. Please try again later.";
        else
            return newS.toString();
    }

    public String addInstructor(String name) {

        Instructor newI = new Instructor(name);

        boolean AddedUser = false;

        byte[] salt = generateSalt();

        String hashedPassword = hashPassword(newI.getPassword(), salt);


        String sql = "INSERT INTO Users (name, password, salt, type) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, hashedPassword);
            statement.setString(3, Base64.getEncoder().encodeToString(salt));
            statement.setString(4, "Instructor");

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                AddedUser = true;
            }



        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while inserting a new Instructor. Please try again later.", e);

        }

        if(!AddedUser)
            return "An error occurred while inserting a new Instructor. Please try again later.";
        else
            return newI.toString();
    }

    public String assignInstructorToCourse(String courseName,String InstructorName)  {
        Course newC = new Course(courseName,InstructorName);
        String instructorId="-1";
        boolean addedCourse = false;

        String sql = "SELECT userId FROM Users WHERE name = ? AND type = 'Instructor' ";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, InstructorName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    instructorId =resultSet.getString("userId");
                } else {
                    System.out.println("The instructor you are trying to assign the course for is not valid");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        String sql2 = "INSERT INTO Courses (name,instructorId) VALUES (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql2)) {
            statement.setString(1, newC.getCourseName());
            statement.setString(2,instructorId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                addedCourse = true;
            }


        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while assigning the course to the Instructor. Please try again later.", e);

        }

        if(!addedCourse)
            return "An error occurred while inserting a new Instructor. Please try again later.";
        else
            return newC.toString();
    }
    private static String hashPassword(String password, byte[] salt) {
        int iterations = 10000;
        int keyLength = 128;
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }


}


