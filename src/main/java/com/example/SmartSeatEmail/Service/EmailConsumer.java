package com.example.SmartSeatEmail.Service;


import com.example.SmartSeatEmail.DTO.collegeDetailsDTO;
import com.example.SmartSeatEmail.DTO.userDTO;
import com.example.SmartSeatEmail.repository.CollegeRepository;
import com.example.SmartSeatEmail.utils.userSet;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class EmailConsumer {

    private final EmailSendService sendMail;

    private final CollegeRepository repocollege;

    private final userSet setU;
    // This method runs automatically when a message arrives
    @KafkaListener(topics = "collegeRegisterTopic", groupId = "email-group")
    public void college(Map<String, String> registrationData) {
        System.out.println("call collge");
        userDTO emailData = setU.setdto(registrationData,"addCollege");
        sendMail.sendWelcomeEmail(emailData,null);
    }

//    @KafkaListener(topics = "studentRegisterTopic", groupId = "email-group")
//    public void student(Map<String, String> registrationData) {
//        userDTO emailData= setU.setdto(registrationData,"addStudent");
//        Long collegeId = Long.parseLong(registrationData.get("collegeId"));
//        collegeDetailsDTO Collegedata =  repocollege.findCollegeDetailsById((collegeId))
//                .orElseThrow(() -> new RuntimeException("College not found with id: " + collegeId));
//        sendMail.sendWelcomeEmail(emailData,Collegedata);
//    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void runAfterStartup() {
//        Map<String, String> registrationData = new HashMap<>();
//        registrationData.put("email", "220170116016@vgecg.ac.in"); // Login Username
//        registrationData.put("name", "Rahul Sharma");
//        registrationData.put("password", "Smart@Temp987");
//        registrationData.put("collegeId", "15");//remove this if want to test for collge
//        student(registrationData);//change method to college if want to test college
//    }
}
