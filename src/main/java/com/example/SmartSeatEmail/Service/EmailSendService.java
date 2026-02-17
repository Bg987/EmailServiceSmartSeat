package com.example.SmartSeatEmail.Service;

import com.example.SmartSeatEmail.DTO.collegeDetailsDTO;
import com.example.SmartSeatEmail.DTO.userDTO;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import org.thymeleaf.context.Context;


@Service
public class EmailSendService {


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;


    //utilize virtual threads for concurrent mail sending
    @Async
    public void sendWelcomeEmail(userDTO user, collegeDetailsDTO college) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            context.setVariable("name", user.name());
            context.setVariable("password", user.password());
            context.setVariable("email" , user.email());
            if(college!=null){
                context.setVariable("collegeName", college.collegeName());
                context.setVariable("address", college.address());
                context.setVariable("mobileNumber", college.mobileNumber());
                context.setVariable("mail", college.mail());
            }
            // This refers to welcome-email.html in src/main/resources/templates
            String htmlContent = templateEngine.process(user.templateName(), context);

            helper.setTo(user.email());
            helper.setSubject("Welcome to SmartSeat AI!"+(college!=null?" student":""));
            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println(">> Email sent successfully to: " + user.email());
        } catch (Exception e) {
            System.err.println(">> Failed to send email: " + e.getMessage());
        }
    }
}
