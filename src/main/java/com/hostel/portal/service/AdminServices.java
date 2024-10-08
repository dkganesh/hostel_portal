package com.hostel.portal.service;

import com.hostel.portal.entity.*;
import com.hostel.portal.model.*;
import com.hostel.portal.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminServices {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoomRepository roomRepository;

    private Student findStudentByEmail(String email){
        return studentRepository.findByEmail(email);
    }

    public boolean deleteStudentByemail(String email) {
        Student student = findStudentByEmail(email);
        if(student==null){
            log.info("Student not Available");
            return false;
        }
        studentRepository.delete(student);
        return true;
    }

    public String updateStudentByEmail(String email, StudentModel model) {
        Student student = findStudentByEmail(email);
        if(student==null)return "No such Student";
        student.setName(model.getName());
        student.setEmail(model.getEmail());
        student.setPhone(model.getPhone());
        student.setParentName(model.getParentName());
        student.setParentPhone(model.getParentPhone());
        student.setFacultyName(model.getFacultyName());
        student.setFacultyPhone(model.getFacultyPhone());
        student.setPassword(passwordEncoder.encode(model.getPassword()));
        student.setBlock(blockRepository.findByBlockName(model.getBlock()));
        student.setDepartment(departmentRepository.findByDepartmentName(model.getDepartment()));
        student.setStaff(staffRepository.findById(Long.valueOf(model.getStaff())).get());
        student.setRegNo(model.getRegNo());
        student.setRoom(roomRepository.findById(model.getRoom()).get());
        student.setNativePlace(model.getNativePlace());
        studentRepository.save(student);
        return "Updated";
    }

    public String addRoom(RoomModel room) {
        Room newRoom=Room.builder()
                .roomNumber(room.getRoomNumber())
                .block(blockRepository.findByBlockName(room.getBlock()))
                .build();
        roomRepository.save(newRoom);
        return "success";
    }

    public List<StudentModel> getStudentsOfRoom(Integer room) {

        Room r= roomRepository.findById(room).get();
//        System.out.println(r.getRoomMates());
        List<Student> x=studentRepository.findAllByRoom(r);
        List<StudentModel> students = x.stream().map(e->
                new StudentModel(
                        e.getName(),e.getEmail(),e.getPhone(),e.getRegNo()
                        ,e.getParentName(),e.getParentPhone(),e.getFacultyName(),e.getFacultyPhone()
                        ,e.getNativePlace(),e.getDepartment().getDepartmentName()
                )).collect(Collectors.toList());
        return students;
    }

    public StudentModel getStudentFromEmail(String email) {
        Student model = studentRepository.findByEmail(email);
//        System.out.println(model);
        StudentModel student=new StudentModel();
        student.setName(model.getName());
        student.setEmail(model.getEmail());
        student.setPhone(model.getPhone());
        student.setParentName(model.getParentName());
        student.setParentPhone(model.getParentPhone());
        student.setFacultyName(model.getFacultyName());
        student.setFacultyPhone(model.getFacultyPhone());
        student.setPassword("");
        student.setBlock(model.getBlock().getBlockName());
        student.setDepartment(model.getDepartment().getDepartmentName());
        student.setStaff(Math.toIntExact(model.getStaff().getId()));
        student.setRegNo(model.getRegNo());
        student.setRoom(model.getRoom().getRoomNumber());
        student.setNativePlace(model.getNativePlace());
        return student;
    }

    public String addStudent(StudentModel model) {
        Student student =Student.builder()
                .name(model.getName())
                .email(model.getEmail())
                .phone(model.getPhone())
                .password(passwordEncoder.encode(model.getPassword()))
                .parentName(model.getParentName())
                .parentPhone(model.getParentPhone())
                .nativePlace(model.getNativePlace())
                .regNo(model.getRegNo())
                .facultyName(model.getFacultyName())
                .facultyPhone(model.getFacultyPhone())
                .block(blockRepository.findByBlockName(model.getBlock()))
                .room(roomRepository.findById(model.getRoom()).get())
                .enabled(true)
                .staff(staffRepository.findById(Long.valueOf(model.getStaff())).get())
                .department(departmentRepository.findByDepartmentName(model.getDepartment()))
                .build();
        studentRepository.save(student);

        return "Success";
    }

    public String addStaff(StaffModel staffModel) {
        Staff staff=Staff.builder()
                .name(staffModel.getName())
                .phone(staffModel.getPhone())
                .email(staffModel.getEmail())
                .staffId(staffModel.getStaffId())
                .password(passwordEncoder.encode(staffModel.getPassword()))
                .build();
        staffRepository.save(staff);
        return "Success";
    }
    public String updateStaff(String email ,StaffModel staffModel) {
        Staff staff= staffRepository.findByEmail(email);
        if(staff==null)return "Invalid";
        staff.setName(staffModel.getName());
        staff.setPhone(staffModel.getPhone());
        staff.setEmail(staffModel.getEmail());
        staff.setStaffId(staffModel.getStaffId());
        staff.setPassword(passwordEncoder.encode(staffModel.getPassword()));
        staffRepository.save(staff);
        return "Updated";
    }

    public StaffModel getStaffFromEmail(String email) {
        Staff staff= staffRepository.findByEmail(email);
        StaffModel model=StaffModel.builder()
                .name(staff.getName())
                .email(staff.getEmail())
                .phone(staff.getPhone())
                .staffId(staff.getStaffId())
                .password("")
                .build();
        return model;
    }

    public List<StudentModel> getStudentsOfDepartment(String department) {
        Department dept= departmentRepository.findByDepartmentName(department);
        return studentRepository.findAllByDepartment(dept).stream().map(e->
                new StudentModel(
                        e.getName(),e.getEmail(),e.getRegNo()
                        ,e.getPhone(),e.getBlock().getBlockName(),
                        e.getRoom().getRoomNumber()
                )).collect(Collectors.toList());
    }

    public List<StudentModel> getStudentsOfBlock(Integer block) {
        System.out.println(block);
        Block blk=blockRepository.findByBlockName(block);
        return studentRepository.findAllByBlock(blk).stream().map(
                e->new StudentModel(
                        e.getName(),e.getEmail()
                        ,e.getPhone(),e.getRegNo()
                        ,e.getDepartment().getDepartmentName(),
                        e.getRoom().getRoomNumber()
                )).collect(Collectors.toList());
    }

    public String updateStudentPassword(PasswordModel model) throws UserPrincipalNotFoundException {
        Student student = studentRepository.findByEmail(model.getEmail());
        if(student==null)
            throw new UserPrincipalNotFoundException("No such sudent found ...");
        student.setPassword(passwordEncoder.encode(model.getNewPassword()));
        studentRepository.save(student);
        return "Updated";
    }

    public String addBlock(BlockModel model) {
        Staff staff=staffRepository.findById(Long.valueOf(model.getStaff())).get();
        if(staff==null){
            return "No such staff available";
        }
        Block b=Block.builder()
                .blockName(model.getBlockName())
                .staff(staff)
                .build();
        blockRepository.save(b);
        return "Added";
    }
}
