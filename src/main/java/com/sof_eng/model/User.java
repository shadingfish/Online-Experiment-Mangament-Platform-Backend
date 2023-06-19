package com.sof_eng.model;

import lombok.Data;

@Data
    public class User {
    private Long id;
    private String username;
    private String chara;
    private String password;
    private String email;
    private String gender;
    private String phone;
    private String org;

    public User() {
    }

    public User(Long id, String username, String chara, String password, String email, String gender, String phone, String org) {
        this.id = id;
        this.username = username;
        this.chara = chara;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.org = org;
    }
}
