package com.hostel.portal.repository;

import com.hostel.portal.entity.Block;
import com.hostel.portal.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block,Long> {
    Block findByBlockName(Integer blockName);
}
