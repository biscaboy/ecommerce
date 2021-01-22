package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Resources:
 * https://www.javaprogramto.com/2020/08/how-to-make-delay-in-java-thread-sleep.html
 * https://www.baeldung.com/java-generating-random-numbers-in-range
 * https://www.baeldung.com/spring-request-param
 * https://mkyong.com/webservices/jax-rs/restful-java-client-with-apache-httpclient/
 * https://alvinalexander.com/java/java-apache-httpclient-restful-client-examples/
 * https://stackoverflow.com/questions/18186722/how-to-setup-spring-logs-for-tomcat
 * https://www.baeldung.com/spring-boot-configure-tomcat
 * https://tomcat.apache.org/tomcat-9.0-doc/config/valve.html
 * https://blog.behrang.org/2019/10/26/spring-boot-tomcat-log4j2.html
 * https://www.tutorialspoint.com/log4j/log4j_patternlayout.htm
 * https://howtodoinjava.com/log4j/log4j-rolling-file-appender/
 * https://stackoverflow.com/questions/23869207/what-is-the-significance-of-log4j-rootlogger-property-in-log4j-properties-file
 * https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html#Lifecycle_Reference
 * https://www.baeldung.com/maven-goals-phases
 * https://www.baeldung.com/slf4j-classpath-multiple-bindings
 * https://logging.apache.org/log4j/2.x/manual/configuration.html#ConfigurationSyntax
 * https://logging.apache.org/log4j/2.x/manual/configuration.html#ConfigurationSyntax
 * https://mkyong.com/logging/log4j-log4j-properties-examples/
 * https://docs.spring.io/spring-boot/docs/1.2.3.RELEASE/reference/htmlsingle/#boot-features-custom-log-levels
 * https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockmvc-example/
 * https://dzone.com/articles/spring-boot-rest-api-unit-testing-with-junit
 * https://www.baeldung.com/parameterized-tests-junit-5
 * https://docs.spring.io/spring-security/site/docs/current/reference/html5/#oauth2Client-auth-grant-support
 * https://bezkoder.com/jwt-json-web-token/
 * https://bezkoder.com/spring-boot-jwt-mysql-spring-security-architecture/
 * https://www.javadevjournal.com/spring/spring-security-userdetailsservice/
 * https://jwt.io/introduction
 * https://www.baeldung.com/spring-response-header
 * https://code-held.com/2019/05/09/custom-authentication-with-spring-security/
 * https://www.baeldung.com/spring-response-entity
 * https://www.baeldung.com/spring-security-csrf
 * https://www.baeldung.com/spring-cors
 * https://spring.io/guides/topicals/spring-security-architecture/
 * https://spring.io/guides/topicals/spring-security-architecture/#web-security
 * https://github.com/spring-projects/spring-security/blob/master/web/src/main/java/org/springframework/security/web/authentication/UsernamePasswordAuthenticationFilter.java
 * https://www.freecodecamp.org/news/how-to-setup-jwt-authorization-and-authentication-in-spring/
 * https://bitbucket.org/b_c/jose4j/wiki/JWT%20Examples
 * https://scotch.io/tutorials/the-ins-and-outs-of-token-based-authentication
 * https://www.baeldung.com/spring-security-method-security
 * https://www.baeldung.com/spring-security-integration-tests
 * https://docs.splunk.com/Documentation/Splunk/8.1.1/SearchTutorial
 * https://github.com/junit-team/junit5-samples/tree/r5.7.0/junit5-jupiter-starter-maven
 * http://maven.apache.org/shared/maven-archiver/examples/classpath.html
 * https://stackoverflow.com/questions/29920434/maven-adding-mainclass-in-pom-xml-with-the-right-folder-path
 * https://www.baeldung.com/executable-jar-with-maven
 *
 */

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class eCommerceApplication {
	// TODO update readme with resources
	// TODO add Postman tests to Test resources
	// TODO add roles to users (update token, add url for updating roles (admin only), update user object)
	// TODO add method security
	// TODO Splunk analysis
	// TODO update pipeline to Jenkins
	// TODO deploy API to an AWS server
	// TODO update security constants
	private static final Logger logger = LoggerFactory.getLogger(eCommerceApplication.class);

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		logger.debug("Bean " + BCryptPasswordEncoder.class.getName() + " loading into the application context.");
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(eCommerceApplication.class, args);
	}

}
