package com.example.okr_tree_software_backend.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/health-check")
public class HealthCheckController {
    @GetMapping
    public String getHealthCheckReport(){
        return "API Functionality Status: OK!";
    }
}
