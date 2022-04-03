package com.example.demo;

import com.example.demo.controller.AnimalController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Zoologico {

	public static void main(String[] args) {

		SpringApplication.run(Zoologico.class, args);
	}

}
