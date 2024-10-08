package com.hostel.portal.service;

import com.hostel.portal.config.JwtHelper;
import com.hostel.portal.entity.Staff;
import com.hostel.portal.entity.Student;
import com.hostel.portal.entity.pass_related.Pass;
import com.hostel.portal.model.LoginModel;
import com.hostel.portal.model.PassModel;
import com.hostel.portal.repository.StaffRepository;
import com.hostel.portal.repository.StudentRepository;
import com.hostel.portal.repository.pass_related.PassRepository;
import com.hostel.portal.service.helper.PassHelper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BetaService {

    @Autowired
    private PassHelper helper;
    @Autowired
    private PassRepository passRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private StudentRepository studentRepository;

    public String issuePassQrtoken(PassModel model) throws ParseException {
        System.out.println(model);

//        Pass p = passRepository.
        return "";
    }

    public String verifyPassToken(String token) {
        if(helper.validatePassToken(token)){
            return "Success";
        }
        return "failed";
    }

    public String approvePass(Integer id) {
        Pass p= passRepository.findById(id.longValue()).get();
        p.setApproved(true);
        passRepository.save(p);
        return "approved";
    }

    public String declinePass(Integer id) {
        Pass p= passRepository.findById(id.longValue()).get();
        passRepository.delete(p);
        return "declined";
    }

    public String applyPass(PassModel model) throws ParseException {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate;
        Date endDate;
        try {
             startDate = dateFormat.parse(model.getStartDate());
             endDate = dateFormat.parse(model.getEndDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String token = helper.generatePassToken(model);
        System.out.println("passtoken -> "+token);
        Pass pass = Pass.builder()
                .startDate(startDate)
                .endDate(endDate)
                .staff(staffRepository.findById(Long.valueOf(model.getStaff())).get())
                .student(studentRepository.findByEmail(model.getEmail()))
                .token(token)
                .isApproved(false).build();
        passRepository.save(pass);
        return "Pass Applied";
    }

    public List<PassModel> getPassList(String email) {
        Staff staff = staffRepository.findByEmail(email);
        return passRepository.findByStaff(staff).stream().map(e->
                PassModel.builder()
                        .id(Math.toIntExact(e.getId()))
                        .name(e.getStudent().getName())
                        .startDate(String.valueOf(e.getStartDate()))
                        .endDate(String.valueOf(e.getEndDate()))
                        .parentName(e.getStudent().getParentName())
                        .parentPhone(e.getStudent().getParentPhone())
                        .isApproved(e.isApproved())
                        .build()).toList();
    }

    public String getToken(String email) {
        Student s=studentRepository.findByEmail(email);
        List<Pass> passes = passRepository.findByStudent(s);
//        System.out.println(passes);
        for(int i=passes.size()-1;i>=0;i--){
            if(passes.get(i).isApproved())return passes.get(i).getToken();
        }
        return null;
    }
}
