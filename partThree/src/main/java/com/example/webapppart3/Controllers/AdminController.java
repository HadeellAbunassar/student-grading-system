package com.example.webapppart3.Controllers;

import com.example.webapppart3.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping()
    protected String doGet(){
        return "admin";
    }

    @PostMapping()
    protected String doPost(@RequestParam String options,
                            @RequestParam String studentname,
                            @RequestParam String Instructorname,
                            @RequestParam String coursename,
                            @RequestParam String Instructorname1,
                            Model model) {
        String result;
        try {
            if ("addstudent".equals(options)) {
                result = adminService.addStudent(studentname);
            } else if ("addinstructor".equals(options)) {
                result = adminService.addInstructor(Instructorname);
            } else {
                result = adminService.assignInstructorToCourse(coursename, Instructorname1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("msg", result);
        return "admin";
    }
}
