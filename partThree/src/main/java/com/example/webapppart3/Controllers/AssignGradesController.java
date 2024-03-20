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
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/assignGrades")
public class AssignGradesController {

    @Autowired
    private InstructorService instructorService;


    @Autowired
    private UserAuthentication authManager;


    @GetMapping()
    protected String doGet(){
        return "assignGrades";
    }

    @PostMapping()
    protected String doPost(@RequestParam String studentName,
                                          @RequestParam String courseName,
                                          @RequestParam String grades,
                                          Model model, HttpServletRequest request){
        String token = (String) request.getSession().getAttribute("token");

        if (token != null && authManager.verifyToken(token)) {
            Claims claims = Jwts.parser().setSigningKey(authManager.getSecretKey()).parseClaimsJws(token).getBody();
            String role = (String) claims.get("role");

            if(role.equals("Instructor")) {

                Map<String, Integer> gradesMap = new HashMap<>();
                gradesMap.put(studentName, Integer.valueOf(grades));
                String msg = null;
                try {
                    msg = instructorService.insertingStudentsGrades(courseName, (HashMap<String, Integer>) gradesMap);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                model.addAttribute("msg", msg);
                return "assignGrades";
            }

}
        else return "error";

        return "assignGrades";
    }
}



