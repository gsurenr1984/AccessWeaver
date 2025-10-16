package com.sitha.accessweaver.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String userName;
    private String password;
    private String fName;
    private String lName;
    private String mName;
    private String address;
    private String proofOfIdentityType;
    private String proofOfIdentityNbr;
    private String pNo;
    private String email;
}
