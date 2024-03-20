package org.example;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class UserAuthentication {

    public boolean testCredentials(String name, String password, String type , Connection connection) throws SQLException {
        boolean isAuthenticated = false;

        String storedHashedPassword = null;
        String storedSalt = null;

        String sql = "SELECT password, salt FROM Users WHERE name = ? AND type = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, type);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    storedHashedPassword = resultSet.getString("password");
                    storedSalt = resultSet.getString("salt");
                }
            }
        }


        if (storedHashedPassword != null && storedSalt != null) {
            byte[] decodedSalt = Base64.getDecoder().decode(storedSalt);
            isAuthenticated = verifyPassword(password, storedHashedPassword, decodedSalt);
        }

        return isAuthenticated;
    }

    private static boolean verifyPassword(String password, String storedHashedPassword, byte[] storedSalt) {
        boolean isAuthenticated = false;
        String hashedPassword = hashPassword(password, storedSalt);
        isAuthenticated = hashedPassword.equals(storedHashedPassword);

        return isAuthenticated;
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

}
