package com.example.SmartSeatEmail.Service;

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
    public void sendWelcomeEmail(String to, String name, String password) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("password", password);
            context.setVariable("email" , to);
            // This refers to welcome-email.html in src/main/resources/templates
            String htmlContent = templateEngine.process("addCollege", context);

            helper.setTo(to);
            helper.setSubject("Welcome to SmartSeat AI!");
            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println(">> Email sent successfully to: " + to);
        } catch (Exception e) {
            System.err.println(">> Failed to send email: " + e.getMessage());
        }
    }
}
