package com.hostel.portal.controller;

import com.hostel.portal.entity.Student;
import com.hostel.portal.event.RegistrationCompleteEvent;
import com.hostel.portal.model.LoginModel;
import com.hostel.portal.model.PasswordModel;
import com.hostel.portal.model.StudentLoginModel;
import com.hostel.portal.model.StudentModel;
import com.hostel.portal.service.Services;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
//@RequestMapping("/v1/")
//@CrossOrigin(origins = "http://localhost:5173/")
public class MainController {

    @Autowired
    private Services service;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/")
    public String Empty(){
        return "Hi This is Empty...";
    }
    @GetMapping("/hello")
    public String helloApi(){
        return "Hello API...";
    }
    @PostMapping("/login")
    public String loginHandler(@RequestBody LoginModel model){
//        System.out.println(1);
        log.info("inside login handler"+model);
        return service.verifyLogin(model);
    }
    @PostMapping("/staffLogin")
    public String loginHandlerStaff(@RequestBody LoginModel model){
//        System.out.println(1);
        log.info("inside staff login handler"+model);
        return service.verifyLoginStaff(model);
    }

    @PostMapping("/std/register")
    public ResponseEntity<String> registerStudent(@RequestBody StudentModel model, HttpServletRequest request){
        Student student = service.registerStudent(model);
        String x="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
//        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(student,x));
        String response = service.automatePublishEvent(student,x);
        log.info("Success -> saved student pending verification");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/std/verifyRegistration")
    public ResponseEntity<Map<String,Boolean>> verifyStudentRegister(@RequestParam("token") String token){
        Map<String,Boolean>response=new HashMap<>();
        boolean isSavedStudent=service.verifyStudentRegister(token);
        response.put("Verified : ",isSavedStudent);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/std/resendVerification")
    public ResponseEntity<String> resendVerificationLink(@RequestParam("token") String token,HttpServletRequest request){
        String response = service.resendVerificationLink(token,request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/std/changePassword/{email}")
    public ResponseEntity<String> changePassword(@PathVariable("email") String email,HttpServletRequest request) {
        String response = service.changePassword(email,request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/std/verifyChangePassword")
    public ResponseEntity<String> verifyPasswordToken(@RequestParam("token") String token ,@RequestBody PasswordModel passwordModel){
        String response =service.verifyStudentPassword(token,passwordModel);
        return ResponseEntity.ok(response);
    }


}
