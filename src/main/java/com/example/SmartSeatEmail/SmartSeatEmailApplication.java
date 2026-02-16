package com.example.SmartSeatEmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync //enable virtual threading
public class SmartSeatEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartSeatEmailApplication.class, args);
	}

}
