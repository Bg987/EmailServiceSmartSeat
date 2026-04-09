package com.example.SmartSeatEmail.Service;

import com.example.SmartSeatEmail.DTO.collegeDetailsDTO;
import com.example.SmartSeatEmail.DTO.userDTO;
import jakarta.mail.MessagingException;
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
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendWelcomeEmail(userDTO user, collegeDetailsDTO college) {
        try {
            // 1. Prepare Thymeleaf Content
            Context context = new Context();
            context.setVariable("name", user.name());
            context.setVariable("password", user.password());
            context.setVariable("email", user.email());

            if (college != null) {
                context.setVariable("collegeName", college.collegeName());
                context.setVariable("address", college.address());
                context.setVariable("mobileNumber", college.mobileNumber());
                context.setVariable("mail", college.mail());
            }

            String htmlContent = templateEngine.process(user.templateName(), context);

            // 2. Create Email Message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("bhavyagodhaniya2004@gmail.com", "SmartSeat AI");
            helper.setTo(user.email());
            helper.setSubject("Welcome to SmartSeat AI!" + (college != null ? " student" : ""));
            helper.setText(htmlContent, true); // true = HTML content

            // 3. Send Email
            mailSender.send(message);
            System.out.println(">> Email sent successfully to: " + user.email());

        } catch (MessagingException e) {
            System.err.println(">> Email sending failed to: " + user.email());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(">> Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}