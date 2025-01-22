package com.project.bankmgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BankmgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankmgmtApplication.class, args);
	}

}
