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
@ToString(exclude = {"students","passList"})
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String staffId;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Student> students=new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "staff",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Pass> passList=new ArrayList<>();

}
