package com.hostel.portal.repository;

import com.hostel.portal.entity.token.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<PasswordToken,Integer> {
    PasswordToken findByToken(String token);
}
