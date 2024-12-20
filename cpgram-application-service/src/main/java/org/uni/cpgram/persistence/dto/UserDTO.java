package org.uni.cpgram.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {


    private Long id;


    private String name;


    private String mobileNo;


    private String email;


    private String address;


    private String gender;


    private String userType;


    private String password;

    private String column_1;
    private String column_2;
    private String column_3;

    private String created_by;
    private String updated_by;

    private Date createdOn;
    private Date updatedOn;

    private String organization;

}
