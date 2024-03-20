package com.example.webapppart3.services;


import com.example.webapppart3.conf.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.Base64;

@Service

public class UserAuthentication {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SECRET_KEY = "secret_key";

    public String getSecretKey(){
        return SECRET_KEY;
    }

    public boolean login(String name, String password, String type) {
        String sql = "SELECT password, salt FROM Users WHERE name = ? AND type = ?";

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                String storedHashedPassword = rs.getString("password");
                String storedSalt = rs.getString("salt");

                if (storedHashedPassword != null && storedSalt != null) {
                    byte[] decodedSalt = Base64.getDecoder().decode(storedSalt);
                    return verifyPassword(password, storedHashedPassword, decodedSalt);
                }
                return false;
            }, name, type);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private static boolean verifyPassword(String password, String storedHashedPassword, byte[] storedSalt) {
        boolean isAuthenticated = false;
        String hashedPassword = hashPassword(password, storedSalt);
        System.out.println("in verify");
        System.out.println(hashedPassword);

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

    public String generateToken(String username, String role) {
        String secretKey = SECRET_KEY;
        long expirationTimeInMillis = 3600000;
        return JwtUtil.generateToken(username, role, secretKey, expirationTimeInMillis);
    }

    public boolean verifyToken(String token) {
        String secretKey = SECRET_KEY;
        return JwtUtil.verifyToken(token, secretKey);
    }
    }