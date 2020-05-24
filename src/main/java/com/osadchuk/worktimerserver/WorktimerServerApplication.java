package com.osadchuk.worktimerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WorktimerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorktimerServerApplication.class, args);
	}

}
