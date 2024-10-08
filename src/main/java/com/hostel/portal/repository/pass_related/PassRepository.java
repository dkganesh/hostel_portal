package com.hostel.portal.repository.pass_related;

import com.hostel.portal.entity.Staff;
import com.hostel.portal.entity.Student;
import com.hostel.portal.entity.pass_related.Pass;
import com.hostel.portal.model.PassModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassRepository extends JpaRepository<Pass,Long> {

    List<Pass> findByStaff(Staff staff);

    List<Pass> findByStudent(Student s);

//    @Query(value = "select p from Pass p, Staff s where p.s=?1")
//    List<Pass> findByStaffCondition(Staff staff);
}
