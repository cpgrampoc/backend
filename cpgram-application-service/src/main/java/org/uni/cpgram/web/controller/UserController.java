package org.uni.cpgram.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uni.cpgram.model.User;
import org.uni.cpgram.persistence.dto.UserDTO;
import org.uni.cpgram.web.request.LoginRequest;
import org.uni.cpgram.web.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest loginRequest) {
        Optional<UserDTO> user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).body(null));
    }
}
