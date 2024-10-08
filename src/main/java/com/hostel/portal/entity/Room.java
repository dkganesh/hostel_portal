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
@Table(uniqueConstraints = @UniqueConstraint(name = "room_uni",columnNames = "room_number"))
@ToString(exclude = "roomMates")
public class Room {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_number")
    private Integer roomNumber;

    @ManyToOne
    private Block block;

    @JsonIgnore
    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY)
    private List<Student> roomMates=new ArrayList<>();
}
