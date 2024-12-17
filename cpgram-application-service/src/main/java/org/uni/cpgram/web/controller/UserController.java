package org.uni.cpgram.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uni.cpgram.persistence.dto.UserDTO;
import org.uni.cpgram.web.request.LoginRequest;
import org.uni.cpgram.web.response.LoginResponse;
import org.uni.cpgram.web.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<UserDTO> user = userService.login(loginRequest.getEmail(), loginRequest.getPassword(), loginRequest.getUserType(), loginRequest.getMobileNo());
        if (user.isPresent()) {
            LoginResponse response = new LoginResponse("200", "Logged in successfully",user.get());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(new LoginResponse("401","Invalid Credentials",null));
        }
    }
}
