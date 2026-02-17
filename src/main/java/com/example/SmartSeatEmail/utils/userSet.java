package com.example.SmartSeatEmail.utils;

import com.example.SmartSeatEmail.DTO.userDTO;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class userSet {


    public userDTO setdto(Map<String, String> registrationData,String template){
        return new userDTO(
                registrationData.get("email"),
                registrationData.get("name"),
                registrationData.get("password"),
                template,
                Map.of("userName", registrationData.get("name"))
        );
    }
}
