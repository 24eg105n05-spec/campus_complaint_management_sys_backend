package com.campuscomplaint.campuscomplaintbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class HealthController {

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> home() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "Campus Complaint Backend API",
                "message", "Backend is running smoothly!"
        ));
    }

    @GetMapping("/api/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP"
        ));
    }

    @GetMapping({"/login", "/register", "/dashboard", "/new-complaint"})
    public ResponseEntity<Map<String, String>> frontendRouteNotice() {
        return ResponseEntity.ok(Map.of(
                "service", "Campus Complaint Backend API",
                "notice", "This URL is your backend API server. To view the user interface, please open your Frontend URL on Render.",
                "status", "UP"
        ));
    }
}
