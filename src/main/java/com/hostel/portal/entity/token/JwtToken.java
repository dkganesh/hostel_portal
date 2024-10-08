package com.hostel.portal.entity.token;

import com.hostel.portal.entity.Staff;
import com.hostel.portal.entity.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user",
            foreignKey = @ForeignKey(
                    name = "FK_JWT_TOKEN"
            ),nullable = true
    )
    private Student student;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "staff",
            foreignKey = @ForeignKey(
                    name = "FK_JWT_TOKEN_STAFF"
            ),nullable = true
    )
    private Staff staff;
    private Boolean isExpired;

}
