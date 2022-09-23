package ru.alexey.springsecurity.SpringSecurityDemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.alexey.springsecurity.SpringSecurityDemo.security.PersonDetails;
import ru.alexey.springsecurity.SpringSecurityDemo.services.AdminService;

@Controller
public class HelloController {

    private final AdminService adminService;

    @Autowired
    public HelloController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/hello")
    String hello(){
        return "/hello";
    }

    @GetMapping("/show")
    String show(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());
        return "/hello";
    }

    @GetMapping("/admin")
    String admin(){
        adminService.toDo();
        return "/admin";
    }
}
