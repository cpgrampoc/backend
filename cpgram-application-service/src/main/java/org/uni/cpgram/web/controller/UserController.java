package org.uni.cpgram.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.uni.cpgram.persistence.dto.UserDTO;
import org.uni.cpgram.web.request.LoginRequest;
import org.uni.cpgram.web.response.UserResponse;
import org.uni.cpgram.web.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<UserDTO> user = userService.login(loginRequest.getEmail(), loginRequest.getPassword(), loginRequest.getUserType(), loginRequest.getMobileNo());
        if (user.isPresent()) {
            UserResponse response = new UserResponse("200", "Logged in successfully",List.of(user.get()));
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(new UserResponse("401","Invalid Credentials",null));
        }

    }

    @PostMapping("/fetchUsersByUserTypes")
    public ResponseEntity<UserResponse> fetchUsersByUserTypes(@RequestBody List<String> userTypes) {
        List<UserDTO> users = userService.fetchUsersByUserTypes(userTypes);
        if (!CollectionUtils.isEmpty(users)) {
            UserResponse response = new UserResponse("200", "Users fetched successfully",users);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(new UserResponse("401","No users found",null));
        }

    }
}
