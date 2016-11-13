package com.aynulin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContentPlannerApiApplication {
	private static final Logger logger = Logger.getLogger(ContentPlannerApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ContentPlannerApiApplication.class, args);
	}
}
