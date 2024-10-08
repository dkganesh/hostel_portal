package com.hostel.portal.entity.pass_related;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String phone;
    private String email;
    @JsonIgnore
    @OneToMany(mappedBy = "security",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Entry> entries=new ArrayList<>();
}
