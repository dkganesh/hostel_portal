package com.hostel.portal.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PassModel {
    private Integer id;
    private String email;
    private String name;

    private Integer staff;
    private String startDate;
    private String endDate;
    private String parentName;
    private String parentPhone;
    private Boolean isApproved;
}
