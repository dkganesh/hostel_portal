package com.hostel.portal.event;

import com.hostel.portal.entity.Student;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private String url;
    private Student student;
    public RegistrationCompleteEvent(Student student,String token){
        super(student);
        this.student=student;
        this.url=token;
    }
}
