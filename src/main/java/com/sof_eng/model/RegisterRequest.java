package com.sof_eng.model;

import lombok.Data;
    @Data
    public class RegisterRequest {

        private String name;

        private String idCard;

        private String password;

        private String phone;

        private String email;
    }
