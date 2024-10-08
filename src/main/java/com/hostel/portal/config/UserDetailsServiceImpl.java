package com.hostel.portal.config;

import com.hostel.portal.entity.Staff;
import com.hostel.portal.entity.Student;
import com.hostel.portal.repository.StaffRepository;
import com.hostel.portal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StaffRepository staffRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(2);
        Student student = studentRepository.findByEmail(email);
        Staff staff=null;
        if(student==null){
            staff=staffRepository.findByEmail(email);
        }
        if(student==null && staff==null)throw new UsernameNotFoundException("Invalid user");
        if(student!=null){
            return new User(
                    student.getEmail(),
                    student.getPassword(),true,student.isEnabled(),true,true,
                    Collections.singleton(new SimpleGrantedAuthority("USER"))
            );
        }
        return new User(
                staff.getEmail(),
                staff.getPassword(),true,true,true,true,
                Collections.singleton(new SimpleGrantedAuthority("STAFF"))
        );
    }
}
