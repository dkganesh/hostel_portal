package com.hostel.portal.entity.token;

import com.hostel.portal.entity.Student;
import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student_verify_token")
public class VerificationToken {
    private static final int WTIME = 15;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date expirationTime;
    private String token;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "student",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_STUDENT_VERIFY_TOKEN")
    )
    private Student student;

    public VerificationToken(Student student, String token){
        super();
        this.token=token;
        this.student= student;
        this.expirationTime=createTime();
    }
    public VerificationToken (String token){
        super();
        this.token=token;
        this.expirationTime=createTime();
    }
    private Date createTime(){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,WTIME);
        return new Date(calendar.getTime().getTime());
    }
}
