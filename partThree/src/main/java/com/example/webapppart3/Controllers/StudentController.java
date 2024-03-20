package com.example.webapppart3.Controllers;

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
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserAuthentication authManager;

    @GetMapping()
    protected String doGet(){
        return "student";
    }

    @PostMapping()
    protected String doPost(@RequestParam String options,
                            @RequestParam String course,
                            Model model ,
                            HttpServletRequest request) throws SQLException {
        String token = (String) request.getSession().getAttribute("token");

        if (token != null && authManager.verifyToken(token)) {
            Claims claims = Jwts.parser().setSigningKey(authManager.getSecretKey()).parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            String role = (String) claims.get("role");

            if(role.equals("Student")){
                 if ("showGrades".equals(options)) {
                 List<String> studentGrades = studentService.showGrades(username);
                 model.addAttribute("studentChoice", studentGrades);
                 return "studentProfile";
                } else if ("showCourseInfo".equals(options)) {
                List<String> courseInfo = studentService.showCourseStat(course,username);
                model.addAttribute("studentChoice", courseInfo);
                return "studentProfile";
                } else if ("showYourCourses".equals(options)) {
                  List<String> enrollmentCourses = studentService.showEnrolledCoursesWithInstructor(username);
                model.addAttribute("studentChoice", enrollmentCourses);
                return "studentProfile";
                } else if ("enrollInCourse".equals(options)) {
                List<String> availableCourses = studentService.showAvailableCourses();
                model.addAttribute("availableCourses", availableCourses);
                return "enrollment";
                 }
              }
            else
                 return "student";
        }
        else
            return "error";


        return "student";
    }

}
