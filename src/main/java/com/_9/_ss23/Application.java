package com._9._ss23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class Application {

	public static void main(String[] args) {
		 SpringApplication.run(Application.class, args);
	}

}
