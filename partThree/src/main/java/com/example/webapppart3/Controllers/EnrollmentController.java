package com.example.webapppart3.Controllers;

import com.example.webapppart3.dao.StudentDAOv1;
import com.example.webapppart3.services.StudentService;
import com.example.webapppart3.services.UserAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;


@Controller
@RequestMapping("/enrollment")
public class EnrollmentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private UserAuthentication authManager;

    @GetMapping
    protected String doGet(){
        return "enrollment";
    }
    @PostMapping()
    protected String doPost(@RequestParam String courseId,
                            Model model ,
                            HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        if (token != null && authManager.verifyToken(token)) {
            Claims claims = Jwts.parser().setSigningKey(authManager.getSecretKey()).parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            String role = (String) claims.get("role");

            if(role.equals("Student")){
        String enrollInCourse;
        try {
            enrollInCourse = studentService.enrollInCourse(username, courseId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("enrollInCourse",enrollInCourse);
        return "enrollment";

    }
        else return "error";}
        return "enrollment";
}
}




