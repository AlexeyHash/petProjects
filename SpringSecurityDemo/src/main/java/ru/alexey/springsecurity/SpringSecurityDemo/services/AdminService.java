package ru.alexey.springsecurity.SpringSecurityDemo.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void toDo(){
        System.out.println("admin");
    }
}
