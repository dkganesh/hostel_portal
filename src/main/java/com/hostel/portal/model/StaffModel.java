package com.hostel.portal.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StaffModel {
    private String name;
    private String email;
    private String phone;
    private String staffId;
    private String password;
}
