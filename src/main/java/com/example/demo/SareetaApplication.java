package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableJpaRepositories("com.example.demo.model.persistence.repositories")
@EntityScan("com.example.demo.model.persistence")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SareetaApplication {

	private static final Logger logger = LoggerFactory.getLogger(SareetaApplication.class);

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		logger.debug("Bean " + BCryptPasswordEncoder.class.getName() + " loading into the application context");
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		System.out.println("This should trigger a Jenkins build.  I'm playing aournd.");
		SpringApplication.run(SareetaApplication.class, args);
	}

}
