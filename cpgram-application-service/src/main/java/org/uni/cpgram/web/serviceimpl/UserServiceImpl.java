package org.uni.cpgram.web.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uni.cpgram.model.User;
import org.uni.cpgram.persistence.dto.UserDTO;
import org.uni.cpgram.repository.UserRepository;
import org.uni.cpgram.web.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;


    public Optional<UserDTO> login(String email, String password, String userType, String mobileNumber) {
        Optional<User> user = null;
        if(userType.equalsIgnoreCase("Citizen")){
            user = userRepository.findByMobileNoAndPassword(mobileNumber, password);
        }else {
            user = userRepository.findByEmailAndPassword(email, password);
        }

        return Optional.ofNullable(mapToUserDTO(user));
    }

    public List<UserDTO> fetchUsersByUserTypes(List<String> userTypes) {
        List<User> users = userRepository.findByUserTypeIn(userTypes);
        return users.stream().map(this::mapToUserDTO).collect(Collectors.toList());
    }

    public UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setMobileNo(user.getMobileNo());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(user.getAddress());
        userDTO.setGender(user.getGender());
        userDTO.setUserType(user.getUserType());
        userDTO.setPassword(user.getPassword());
        userDTO.setColumn_1(user.getColumn_1());
        userDTO.setColumn_2(user.getColumn_2());
        userDTO.setColumn_3(user.getColumn_3());
        userDTO.setOrganization(user.getOrganization());
        userDTO.setCreated_by(user.getCreated_by());
        userDTO.setUpdated_by(user.getUpdated_by());
        userDTO.setCreatedOn(user.getCreatedOn());
        userDTO.setUpdatedOn(user.getUpdatedOn());
        return userDTO;
    }


    public UserDTO mapToUserDTO(Optional<User> userOptional) {
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setMobileNo(user.getMobileNo());
            userDTO.setEmail(user.getEmail());
            userDTO.setAddress(user.getAddress());
            userDTO.setGender(user.getGender());
            userDTO.setUserType(user.getUserType());
            userDTO.setPassword(user.getPassword());
            userDTO.setColumn_1(user.getColumn_1());
            userDTO.setColumn_2(user.getColumn_2());
            userDTO.setColumn_3(user.getColumn_3());
            userDTO.setOrganization(user.getOrganization());
            userDTO.setCreated_by(user.getCreated_by());
            userDTO.setUpdated_by(user.getUpdated_by());
            userDTO.setCreatedOn(user.getCreatedOn());
            userDTO.setUpdatedOn(user.getUpdatedOn());
            return userDTO;
        } else {
            return null;
        }
    }
}
