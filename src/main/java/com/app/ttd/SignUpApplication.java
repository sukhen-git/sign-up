package com.app.ttd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.app.ttd.audit.TTDAuditorAwareImpl;



@SpringBootApplication
@ComponentScan("com.app.ttd")
@EnableJpaRepositories(basePackages = "com.app.ttd.repository")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SignUpApplication {
	
	@Bean
	public AuditorAware<String> auditorAware() {
		return new TTDAuditorAwareImpl();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SignUpApplication.class, args);
	}

}
