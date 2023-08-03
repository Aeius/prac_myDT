package com.weData;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeDataApplication {

	public static void main(String[] args) {
		System.out.println("중단점 찍어봤습니다.");
		SpringApplication.run(WeDataApplication.class, args);
	}

}
