package com.sar.psapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PsappApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsappApplication.class, args);
	}
}
