package com.hostel.portal.model;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffModel {
    private String name;
    private String email;
    private String phone;
    private String staffId;
    private String password;
    private Long id;


    public StaffModel(Long id, String name) {
        this.id=id;
        this.name=name;
    }
}
