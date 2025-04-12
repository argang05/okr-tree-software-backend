package com.example.okr_tree_software_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OkrTreeSoftwareBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(OkrTreeSoftwareBackendApplication.class, args);
	}

}
