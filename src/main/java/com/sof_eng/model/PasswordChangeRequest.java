package com.sof_eng.model;

import lombok.Data;

@Data
public class PasswordChangeRequest {
    private String username;
    private String password;
}
