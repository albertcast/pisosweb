package com.example.pisosweb;


import com.example.pisosweb.repository.EjemploRepository;
import com.example.pisosweb.repository.MessageRepository;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PisoswebApplication implements CommandLineRunner{

	@Autowired
	private EjemploRepository repository;
	private MessageRepository mensajeRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(PisoswebApplication.class, args);
	}
	
	 @Override
	 public void run(String... args) throws Exception {
	 
	 }
	 
	
}
