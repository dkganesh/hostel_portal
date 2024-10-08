package com.hostel.portal.repository;

import com.hostel.portal.entity.Block;
import com.hostel.portal.entity.Department;
import com.hostel.portal.entity.Room;
import com.hostel.portal.entity.Student;
import com.hostel.portal.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Student findByEmail(String email);

    List<Student> findAllByRoom(Room r);

    List<Student> findAllByDepartment(Department dept);

    List<Student> findAllByBlock(Block blk);
}
