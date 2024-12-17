package org.uni.cpgram.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "m_user_master")
@Data
@NoArgsConstructor
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "gender")
    private String gender;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "passowrd")
    private String password;

    @Column(name = "column_1")
    private String column_1;

    @Column(name = "column_2")
    private String column_2;

    @Column(name = "column_3")
    private String column_3;

    private String created_by;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn;
    private String updated_by;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on")
    private Date updatedOn;


    @Column(name = "organization")
    private String organization;




}