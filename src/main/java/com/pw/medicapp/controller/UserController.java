package com.pw.medicapp.controller;

import com.pw.medicapp.model.User;
import com.pw.medicapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;


    // GET /api/user
    @GetMapping("list")
    public ResponseEntity<List<User>> getAllUsers() {
        // Recupera la lista di tutti gli utenti dal service
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
