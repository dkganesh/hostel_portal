package com.hostel.portal.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class StaffModel {
    private String name;
    private String email;
    private String phone;
    private String staffId;
    private String password;
    private Long id;

}
