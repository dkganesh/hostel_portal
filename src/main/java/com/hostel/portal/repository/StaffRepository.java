package com.hostel.portal.repository;

import com.hostel.portal.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {
    Staff findByEmail(String email);
}
