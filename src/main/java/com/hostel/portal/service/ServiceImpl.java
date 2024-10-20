package com.hostel.portal.service;

import com.hostel.portal.config.JwtHelper;
import com.hostel.portal.entity.Student;
import com.hostel.portal.entity.token.JwtToken;
import com.hostel.portal.entity.token.PasswordToken;
import com.hostel.portal.entity.token.VerificationToken;
import com.hostel.portal.model.LoginModel;
import com.hostel.portal.model.PasswordModel;
import com.hostel.portal.model.StudentLoginModel;
import com.hostel.portal.model.StudentModel;
import com.hostel.portal.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class ServiceImpl implements Services{

    private static final Logger log = LoggerFactory.getLogger(ServiceImpl.class);
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
    private RoomRepository roomRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PasswordRepository passwordRepository;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtHelper helper;
    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Override
    public Student registerStudent(StudentModel model) {
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
                .enabled(false)
                .staff(staffRepository.findById(Long.valueOf(model.getStaff())).get())
                .department(departmentRepository.findByDepartmentName(model.getDepartment()))
                .build();
        studentRepository.save(student);
        return student;
    }

    @Override
    public String verifyLogin(LoginModel model) {
//        System.out.println(3);
            Authentication authentication =authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    model.getEmail(),model.getPassword()
            ));
            if(authentication.isAuthenticated()){
                log.info("aunthentication successful inside verify login method from \"login\" controller");
                String token = helper.generateToken(model);
                JwtToken temp=jwtTokenRepository.findByStudent(studentRepository.findByEmail(model.getEmail()));
                if(temp==null){
                    log.info("jwt token is null so new insertion in db with new token");
                    temp = JwtToken.builder()
                            .token(token)
                            .student(studentRepository.findByEmail(model.getEmail()))
                            .isExpired(false)
                            .build();
                }
                else {
                    log.info("already student is present in db so updation of token and expiration -false ");
                    temp.setIsExpired(false);
                    temp.setToken(token);
                }
//                System.out.println(temp);
                jwtTokenRepository.save(temp);
                log.info("jwt token is updated in DB...");
                return token;
            }
            else throw new AuthenticationCredentialsNotFoundException("from verify login method no such student present");
        }
    @Override
    public String verifyLoginStaff(LoginModel model) {
//        System.out.println(3);
            Authentication authentication =authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    model.getEmail(),model.getPassword()
            ));
            if(authentication.isAuthenticated()){
                log.info("aunthentication successful inside verify login method from \"login\" controller");
                String token = helper.generateToken(model);
                JwtToken temp=jwtTokenRepository.findByStaff(staffRepository.findByEmail(model.getEmail()));
                if(temp==null){
                    log.info("jwt token is null so new insertion in db with new token");
                    temp = JwtToken.builder()
                            .token(token)
                            .staff(staffRepository.findByEmail(model.getEmail()))
                            .isExpired(false)
                            .build();
                }
                else {
                    log.info("already staff is present in db so updation of token and expiration -false ");
                    temp.setIsExpired(false);
                    temp.setToken(token);
                }
//                System.out.println(temp);
                jwtTokenRepository.save(temp);
                log.info("jwt token is updated in DB...");
                return token;
            }
            else throw new AuthenticationCredentialsNotFoundException("from verify login method no such staff present");
        }

    @Override
    public String automatePublishEvent(Student student, String x) {
        String token= UUID.randomUUID().toString();
        saveVerificationTokenForStudent(student,token);
        String url=x+
                "/std/verifyRegistration?token="+token;
        log.info("Click th link to verify the account => "+url);
        return url;
    }

    @Override
    public void saveVerificationTokenForStudent(Student user, String token) {
        verificationTokenRepository.save(new VerificationToken(user,token));
    }

    @Override
    public boolean verifyStudentRegister(String token) {
        VerificationToken verificationToken =verificationTokenRepository.findByToken(token);
        if(verificationToken==null || isStdVerifyTokenValid(verificationToken)){
            log.info("Error in Verification");
            return false;
        }
        Student student=verificationToken.getStudent();
        student.setEnabled(true);
        studentRepository.save(student);
        verificationTokenRepository.delete(verificationToken);
        return true;
    }

    private boolean isStdVerifyTokenValid(VerificationToken verificationToken) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        Date date=verificationToken.getExpirationTime();
        return date.getTime()-calendar.getTime().getTime()<=0;
    }

    @Override
    public String resendVerificationLink(String token , HttpServletRequest request) {
        VerificationToken verificationToken =verificationTokenRepository.findByToken(token);
        if(verificationToken==null ){
            //&& isStdVerifyTokenValid(verificationToken)
            log.info("Error in Verification");
            return "Error in Sending Verification link";
        }
        String x="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
        token=UUID.randomUUID().toString();
        verificationToken.setToken(token);
        verificationTokenRepository.save(verificationToken);
        String url=x+
                "/std/verifyRegistration?token="+token;
        log.info("Click th link to verify the account => "+url);
        return "sent";
    }

    @Override
    public String changePassword(String email,HttpServletRequest request) {
        Student student =studentRepository.findByEmail(email);
        if(student==null){
            log.info("No such valid user");
            return "No such valid user";
        }
        String token= UUID.randomUUID().toString();
        String url="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
        PasswordToken passwordToken = new PasswordToken(student,token);
        passwordRepository.save(passwordToken);
        log.info("password token Succesfully generated");
        url=url+"/std/verifyChangePassword?token="+token;
        log.info("password change url => "+url);
        return url;
    }

    @Override
    public String verifyStudentPassword(String token, PasswordModel passwordModel) {
        PasswordToken passwordToken=passwordRepository.findByToken(token);
        if (passwordToken==null)return "Invalid link";
        Student student=passwordToken.getStudent();
        if(passwordModel.getReset()){
            student.setPassword(passwordEncoder.encode(passwordModel.getNewPassword()));
            studentRepository.save(student);
            passwordRepository.delete(passwordToken);
            return "Password Changed Successfully";
        }
        if(passwordEncoder.matches(passwordModel.getOldPassword(),student.getPassword()) &&
                !isPasswordTokenValid(passwordToken)){
            student.setPassword(passwordEncoder.encode(passwordModel.getNewPassword()));
            studentRepository.save(student);
            passwordRepository.delete(passwordToken);
            return "Password Changed Successfully";
        }
        return "Incorrect Old Password";
    }

    private boolean isPasswordTokenValid(PasswordToken passwordToken) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        Date date=passwordToken.getExpirationTime();
        return date.getTime()-calendar.getTime().getTime()<=0;
    }

}
