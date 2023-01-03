package com.enterprise.storeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@SpringBootApplication
public class StoreappApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreappApplication.class, args);

		 LocalDate fechaA;
		 LocalDateTime fechaB;
		 fechaA =  LocalDate.of(2022, 12, 20);
		fechaB = LocalDateTime.now();
		Long dias = ChronoUnit.DAYS.between(fechaA,fechaB);
		System.out.println(dias);
	}




}
