package com.hostel.portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hostel.portal.entity.pass_related.Pass;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = @UniqueConstraint(
        name = "student_uni_constraint",
        columnNames = "email"
))
//@ToString(exclude = {"staff","department","block","room"})
@ToString(exclude = {"passes"})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "email",nullable = false)
    private String email;
    private String phone;
    @Column(length = 60)
    private String password;
    private String regNo;
    private String parentName;
    private String parentPhone;
    private String facultyName;
    private String facultyPhone;
    private String nativePlace;

    @ManyToOne(fetch = FetchType.EAGER)
    private Staff staff;

    @ManyToOne(fetch = FetchType.EAGER)
    private Department department;

    @ManyToOne(fetch = FetchType.EAGER)
    private Block block;

    @ManyToOne
    private Room room;
    private boolean enabled=false;

    @JsonIgnore
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Pass> passes=new ArrayList<>();

}
