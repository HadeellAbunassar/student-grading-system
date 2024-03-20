package com.example.webapppart3.dao;

import com.example.webapppart3.models.Instructor;
import com.example.webapppart3.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import  java.util.Base64;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Repository
public class AdminDAOv1 implements AdminDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String addStudent(String name) {
        Student newStudent = new Student(name);

        byte[] salt = generateSalt();
        String hashedPassword = hashPassword(newStudent.getPassword(), salt);

        String sql = "INSERT INTO Users (name, password, salt, type) VALUES (?, ?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, newStudent.getName(), hashedPassword, Base64.getEncoder().encodeToString(salt), "Student");

        if (rowsAffected > 0)
            return newStudent.toString();
        else
            throw new RuntimeException("An error occurred while inserting a new student. Please try again later.");
    }

    @Override
    public String addInstructor(String name) {
        Instructor newInstructor = new Instructor(name);

        byte[] salt = generateSalt();
        String hashedPassword = hashPassword(newInstructor.getPassword(), salt);

        String sql = "INSERT INTO Users (name, password, salt, type) VALUES (?, ?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, newInstructor.getName(), hashedPassword, Base64.getEncoder().encodeToString(salt), "Instructor");

        if (rowsAffected > 0)
            return newInstructor.toString();
        else
            throw new RuntimeException("An error occurred while inserting a new instructor. Please try again later.");
    }

    @Override
    public String assignInstructorToCourse(String courseName, String instructorName) {
        String instructorId = jdbcTemplate.queryForObject(
                "SELECT userId FROM Users WHERE name = ? AND type = 'Instructor'", String.class, instructorName);

        if (instructorId == null) {
            return "There is no instructor with the name " + instructorName;
        }

        int rowsAffected = jdbcTemplate.update("INSERT INTO Courses (name, instructorId) VALUES (?, ?)",
                courseName, instructorId);

        if (rowsAffected > 0) {
            return "Course " + courseName + " assigned to instructor " + instructorName + " successfully.";
        } else {
            throw new RuntimeException("An error occurred while assigning the course to the instructor. Please try again later.");
        }
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


