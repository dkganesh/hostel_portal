package com.hostel.portal.event.listeners;

import com.hostel.portal.entity.Student;
import com.hostel.portal.event.RegistrationCompleteEvent;
import com.hostel.portal.service.Services;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationCompleteEventListner implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private Services service;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        Student user=event.getStudent();

        String token= UUID.randomUUID().toString();
        service.saveVerificationTokenForStudent(user,token);
        String url=event.getUrl()+
                "/std/verifyRegistration?token="+token;
        log.info("Click th link to verify the account => "+url);
    }
}
