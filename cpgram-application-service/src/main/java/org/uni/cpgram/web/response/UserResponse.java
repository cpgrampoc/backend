package org.uni.cpgram.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.uni.cpgram.persistence.dto.UserDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class UserResponse {
    private String status;
    private String message;
    private List<UserDTO> user;
}
