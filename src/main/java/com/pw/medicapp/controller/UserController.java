package com.pw.medicapp.controller;


import com.pw.medicapp.DTO.UserDTO;
import com.pw.medicapp.model.enums.UserRole;
import com.pw.medicapp.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(required = false) UserRole role) {
        // Recupera la lista di tutti gli utenti dal service
        return ResponseEntity.ok(userService.getAllUsers(role));
    }

    // GET /api/user/fiscalCode/{fc}
    @GetMapping("/fiscalCode/{fiscalCode}")
    public ResponseEntity<UserDTO> getUserByFiscalCode(@PathVariable String fiscalCode) {
        // Cerca l'utente per codice fiscale
        return ResponseEntity.ok(userService.getUserByFiscalCode(fiscalCode));
    }

    @PostMapping("/new-user")
    public ResponseEntity<UserDTO> createUser(
            @Valid @RequestBody UserDTO userDTO,
            @NotNull(message = "Il ruolo è obbligatorio") @RequestParam UserRole role) {
        userDTO.setRole(role);
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    //devo poter modificare anche un solo campo alla volta
    @PutMapping("/update-user/{fiscalCode}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String fiscalCode, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(fiscalCode, userDTO));
    }

    @DeleteMapping("/delete-user/{fiscalCode}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable String fiscalCode) {
        userService.deleteUser(fiscalCode);
        return ResponseEntity.noContent().build();
    }

}
