package org.uni.cpgram;

import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;



@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@Configuration
public class CpgramApplicationService extends SpringBootServletInitializer {

	@Value("${app.timezone}")
	private String timeZone;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CpgramApplicationService.class, args);
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	
	  @PostConstruct public void initialize() {
	  TimeZone.setDefault(TimeZone.getTimeZone(timeZone)); }
	 

}
