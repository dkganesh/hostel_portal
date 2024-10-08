package com.hostel.portal.repository.pass_related;

import com.hostel.portal.entity.pass_related.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepository extends JpaRepository<Security,Integer> {
}
