package com.hostel.portal.model;

import com.hostel.portal.entity.Department;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
public class StudentModel {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String regNo;
    private String parentName;
    private String parentPhone;
    private String facultyName;
    private String facultyPhone;
    private String nativePlace;
    private Integer staff;
    private String department;
    private Integer block;
    private Integer room;

    public StudentModel(String name, String email,
                        String regNo, String phone,
                        Integer block, Integer room) {
        this.name = name;
        this.email = email;
        this.regNo = regNo;
        this.phone = phone;
        this.block = block;
        this.room = room;
    }

    public StudentModel(String name, String email, String phone, String regNo,
                        String parentName, String parentPhone, String facultyName,
                        String facultyPhone, String nativePlace, String department) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.regNo = regNo;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.facultyName = facultyName;
        this.facultyPhone = facultyPhone;
        this.nativePlace = nativePlace;
        this.department = department;
    }

    public StudentModel(String name, String email,
                        String phone, String regNo,
                        String department, Integer room) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.regNo = regNo;
        this.department = department;
        this.room = room;
    }


}
