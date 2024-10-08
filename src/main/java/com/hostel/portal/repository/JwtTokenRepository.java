package com.hostel.portal.repository;

import com.hostel.portal.entity.Staff;
import com.hostel.portal.entity.Student;
import com.hostel.portal.entity.token.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken ,Integer> {
    JwtToken findByToken(String actualToken);

    JwtToken findByStudent(Student byEmail);

    JwtToken findByStaff(Staff byEmail);
}
