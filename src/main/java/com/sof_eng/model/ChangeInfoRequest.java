package com.sof_eng.model;

import lombok.Data;

@Data
public class ChangeInfoRequest {
    private int id;
    private String username;

    private String chara;
    private String password;

    private String email;
    private String gender;
    private String phone;
    private String org;
}
