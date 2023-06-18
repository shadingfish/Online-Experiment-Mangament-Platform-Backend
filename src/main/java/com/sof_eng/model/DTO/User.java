package com.sof_eng.model.DTO;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String chara;
    private String password;
    private String gender;
    private String phone;
    private String email;
    private String org;
}
