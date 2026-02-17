package com.example.SmartSeatEmail.DTO;

import java.util.Map;


//DTO for carrying registration data to the Email Service for college and students.
//Using a Record ensures data cannot be modified after capture.

public record userDTO(
        String email,
        String name,
        String password, // Useful if you send a "Welcome" mail with temporary password
        String templateName, // e.g., "registration-success"
        Map<String, Object> templateModel // For dynamic content like {{name}}
) {
    // Compact constructor for basic validation before the mail is queued
    public userDTO {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Target email cannot be null or empty");
        }
    }
}