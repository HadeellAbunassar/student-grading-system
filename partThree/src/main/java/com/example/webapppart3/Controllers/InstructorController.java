package com.example.webapppart3.Controllers;

import com.example.webapppart3.services.InstructorService;
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
import java.util.List;

@Controller
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private UserAuthentication authManager;


    @GetMapping()
    protected String doGet(){
        return "instructor";
    }

    @PostMapping()
    protected String doPost(@RequestParam String options,
                            @RequestParam String courseName,
                            Model model ,
                            HttpServletRequest request) throws SQLException {

        boolean isOptionB = false;
        String token = (String) request.getSession().getAttribute("token");

        if (token != null && authManager.verifyToken(token)) {
            Claims claims = Jwts.parser().setSigningKey(authManager.getSecretKey()).parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            String role = (String) claims.get("role");

            if(role.equals("Instructor")){
        if ("showCourses".equals(options)) {
            List<String> instructorCourses = instructorService.showInstructorCourses(username);
            model.addAttribute("username", username);
            model.addAttribute("isOptionB", isOptionB);
            model.addAttribute("msg", instructorCourses);
            return "instructorProfile";
        } else if ("showstucrs".equals(options)) {
            if(!courseName.equals("noCourse")){
                List<String> courseStudents = instructorService.showCourseStudents(courseName, username);
                model.addAttribute("username", username);
                model.addAttribute("msg", courseStudents);
                return "instructorProfile";
            } else {
                isOptionB = true;
                model.addAttribute("username", username);
                model.addAttribute("isOptionB", isOptionB);
                return "instructorProfile";
            }
        } else if ("assigngrades".equals(options)) {
            return "assignGrades";
        } else {
            return "instructor";
        }} else
             return "error";
        }

        return "instructor";
    }
}
