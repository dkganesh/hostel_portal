package com.hostel.portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"students","staff"})
@Table(uniqueConstraints = @UniqueConstraint(
        name = "uni_block_name",
        columnNames = "block_name"
))
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "block_name",nullable = false)
    private Integer blockName;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Staff staff;

    @JsonIgnore
    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Student>students=new ArrayList<>();
}
