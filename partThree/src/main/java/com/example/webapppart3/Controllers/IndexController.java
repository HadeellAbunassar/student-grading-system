package com.example.webapppart3.Controllers;


import com.example.webapppart3.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    UserAuthentication authManager;

    @Autowired
    HttpSession session;

    @GetMapping()
            public String home(){
                return "index";
            }

    @PostMapping()
    public String login(@RequestParam String name,
                        @RequestParam String password,
                        @RequestParam String role,
                        Model model) {
        try {
            if (authManager.login(name, password, role)) {
                String token = authManager. generateToken(name, role);
                session.setAttribute("token", token);
                return redirectToRole(role);
            } else {
                model.addAttribute("msg", "Invalid username or password. Please try again.");
                return "index";
            }
        } catch (Exception e) {
            model.addAttribute("msg", "An unexpected error occurred. Please try again later.");
            return "index";
        }
    }

    private String redirectToRole(String role) {
        switch (role) {
            case "Admin":
                return "admin";
            case "Student":
                return "student";
            case "Instructor":
                return "instructor";
            default:
                return "index";
        }
    }


     }
