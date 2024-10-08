package com.hostel.portal.service;

import com.hostel.portal.entity.Student;
import com.hostel.portal.model.LoginModel;
import com.hostel.portal.model.PasswordModel;
import com.hostel.portal.model.StudentLoginModel;
import com.hostel.portal.model.StudentModel;
import jakarta.servlet.http.HttpServletRequest;

public interface Services {
    Student registerStudent(StudentModel model);

    void saveVerificationTokenForStudent(Student user, String token);

    boolean verifyStudentRegister(String token);

    String resendVerificationLink(String token , HttpServletRequest request);

    String  changePassword(String email, HttpServletRequest request);

    String verifyStudentPassword(String token , PasswordModel passwordModel);
    String verifyLoginStaff(LoginModel model);

    String automatePublishEvent(Student student, String x);

    String verifyLogin(LoginModel model);
}
