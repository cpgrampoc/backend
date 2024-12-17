package org.uni.cpgram.web.service;

import org.uni.cpgram.model.User;
import org.uni.cpgram.persistence.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<UserDTO> fetchUsersByUserTypes(List<String> userTypes);
    public Optional<UserDTO> login(String email, String password, String userType, String mobileNumber);
}
