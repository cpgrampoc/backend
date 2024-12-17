package org.uni.cpgram.web.request;


import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
    private String userType;
    private String mobileNo;
}
