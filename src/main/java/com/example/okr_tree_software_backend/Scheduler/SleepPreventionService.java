package com.example.okr_tree_software_backend.Scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SleepPreventionService {
    private static final String API_URL = "https://okr-tree-app-backend-9mcm.onrender.com/api/v1/health-check";

    @Autowired
    private RestTemplate restTemplate;

    //    @Scheduled(cron = "*/10 * * * * *")

    // Schedule to run every 14 minutes
    @Scheduled(cron = "0 */14 * * * *")
    public void sendHealthCheckRequest() {
        try {
            restTemplate.exchange(API_URL, HttpMethod.GET, null, Void.class); // Sending request without collecting a response
            System.out.println("Health check request sent successfully at " + System.currentTimeMillis());
        } catch (Exception e) {
            System.err.println("Error during health check request: " + e.getMessage());
        }
    }
}
