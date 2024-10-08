package com.hostel.portal.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordModel {
    private String email;
    private String oldPassword;
    private String newPassword;
    private Boolean reset;

}
