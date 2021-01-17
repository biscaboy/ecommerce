package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@EnableJpaRepositories("com.example.demo.model.persistence.repositories")
//@EntityScan("com.example.demo.model.persistence")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class eCommerceApplication {
	// TODO fix the problem where the logger loads two libraries for the project.
	private static final Logger logger = LoggerFactory.getLogger(eCommerceApplication.class);

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		logger.debug("Bean " + BCryptPasswordEncoder.class.getName() + " loading into the application context");
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public LogMessageFormatter logMessageFormatter() {
//		logger.debug("Bean " + LogMessageFormatter.class.getName() + " loading into the application context");
//		return new LogMessageFormatter();
//	}

	public static void main(String[] args) {
		System.out.println("This should trigger a Jenkins build.  I'm playing aournd.");
		SpringApplication.run(eCommerceApplication.class, args);
	}

}
