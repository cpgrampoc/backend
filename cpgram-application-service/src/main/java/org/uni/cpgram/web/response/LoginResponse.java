package org.uni.cpgram.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.uni.cpgram.persistence.dto.UserDTO;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String status;
    private String message;
    private UserDTO user;
}
