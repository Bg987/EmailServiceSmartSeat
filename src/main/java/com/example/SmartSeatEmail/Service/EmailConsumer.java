package com.example.SmartSeatEmail.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class EmailConsumer {

    private final EmailSendService sendMail;

    // This method runs automatically when a message arrives
    @KafkaListener(topics = "collegeRegisterTopic", groupId = "email-group")
    public void consume(Map<String, String> registrationData) {

        // Extract the values using the keys you set in the Backend
        String email = registrationData.get("email");
        String name = registrationData.get("name");
        String password = registrationData.get("password");

        sendMail.sendWelcomeEmail(email, name, password);
    }
}
