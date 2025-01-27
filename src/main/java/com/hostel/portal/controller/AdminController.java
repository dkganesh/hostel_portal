package com.hostel.portal.controller;

import com.hostel.portal.entity.Department;
import com.hostel.portal.model.*;
import com.hostel.portal.service.AdminServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin")
//@CrossOrigin(origins = "http://localhost:5173/")
public class AdminController {
    @Autowired
    private AdminServices adminService;
    @Autowired
    private ApplicationContext context;


    @DeleteMapping("/delete/std/{email}")
    public ResponseEntity<Boolean> deleteStudent(@PathVariable("email") String email){

        boolean isDeleted = adminService.deleteStudentByemail(email);
        return ResponseEntity.ok(isDeleted);
    }

    @PutMapping("/update/std/{email}")
    public ResponseEntity<String> updateStudent(@PathVariable("email") String email ,
                                                @RequestBody StudentModel model){
        String response = adminService.updateStudentByEmail(email,model);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addRoom")
    public String addRoom(@RequestBody RoomModel room){
        return adminService.addRoom(room);
    }
    @GetMapping("/getStudentsOfRoom/{roomNumber}")
    public ResponseEntity<List<StudentModel>>  getStudentsOfRoom(@PathVariable("roomNumber") Integer room){
        //change student to string method
        List<StudentModel> students = adminService.getStudentsOfRoom(room);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/getStudentsOfDepartment/{department}")
    public List<StudentModel> getStudentsOfDepartment(@PathVariable("department") String department){
        return adminService.getStudentsOfDepartment(department);
    }

    @GetMapping("/getStudentsOfBlock/{block}")
    public List<StudentModel> getStudentsOfBlock(@PathVariable("block") Integer block){
        return adminService.getStudentsOfBlock(block);
    }

    @PostMapping("/update/studentPassword")
    public ResponseEntity<String> studentPassword(@RequestBody PasswordModel model)
            throws UserPrincipalNotFoundException {
        return ResponseEntity.ok(adminService.updateStudentPassword(model));
    }

    @PostMapping("/addBlock")
    public ResponseEntity<String > addNewBlock(@RequestBody BlockModel model){
        return ResponseEntity.ok(adminService.addBlock(model));
    }


    @GetMapping("/getStudent/{email}")
    public StudentModel getStudentFromEmail(@RequestHeader("Authorization") String header ,@PathVariable("email") String email){
        System.out.println("Header -> "+header);
        return adminService.getStudentFromEmail(email);
    }

    @PostMapping("/addStudent")
    public ResponseEntity<String> addStudent(@RequestBody StudentModel model){
        return ResponseEntity.ok(adminService.addStudent(model));
    }

    @PostMapping("/addStaff")
    public ResponseEntity<String> addStaff(@RequestBody StaffModel staffModel){
        return ResponseEntity.ok(adminService.addStaff(staffModel));
    }
    @PutMapping("/updateStaff/{email}")
    public ResponseEntity<String> addStaff(@PathVariable("email") String email , @RequestBody StaffModel staffModel){
        return ResponseEntity.ok(adminService.updateStaff(email,staffModel));
    }
    @GetMapping("/getStaff/{email}")
    public StaffModel getStaffFromEmail(@PathVariable("email") String email){
        return adminService.getStaffFromEmail(email);
    }


    @GetMapping("/shutdown-server")
    public void shutdownApp() {
        int exitCode = SpringApplication.exit(context, (ExitCodeGenerator) () -> 0);
        System.exit(exitCode);
    }

    @GetMapping("/getBlocks")
    public List<BlockModel> getBlocks(){
        return adminService.getBlocks();
    }
    @GetMapping("/getRt")
    public List<StaffModel> getRt(){
        return adminService.getRt();
    }
    @GetMapping("/getDepartment")
    public List<DepartmentModel> geDepartment(){
        return adminService.geDepartment();
    }
}
