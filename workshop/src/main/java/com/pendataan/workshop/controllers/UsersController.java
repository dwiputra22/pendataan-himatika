package com.pendataan.workshop.controllers;


import com.pendataan.workshop.entity.users.Users;
import com.pendataan.workshop.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<Users> registerUsers(@RequestBody Users users) {
        return ResponseEntity.ok(usersService.registerUsers(users));
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(usersService.getAllUsers());
    }

    @GetMapping("/{nim}")
    public ResponseEntity<Optional<Users>> getUsers(
            @RequestParam(value = "nim", defaultValue = "") String nim) {
        return ResponseEntity.ok(usersService.getUsers(nim));
    }


}
