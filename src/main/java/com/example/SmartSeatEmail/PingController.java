package com.example.SmartSeatEmail;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class PingController {

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        // Returns a 200 OK response with a simple body
        return ResponseEntity.ok("pong");
    }
}
