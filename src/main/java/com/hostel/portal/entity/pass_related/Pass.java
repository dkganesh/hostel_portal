package com.hostel.portal.entity.pass_related;

import com.hostel.portal.entity.Staff;
import com.hostel.portal.entity.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date startDate;
    private Date endDate;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Staff staff;
    private boolean isApproved=false;
    private String token;
}
