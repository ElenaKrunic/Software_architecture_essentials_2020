package com.projekat.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Aplikacija {
	
    private static final Logger LOGGER = LogManager.getLogger(Aplikacija.class);

	public static void main(String[] args) {
		SpringApplication.run(Aplikacija.class, args);
		
		LOGGER.info("Pokrenuta aplikacija");
	}
}
