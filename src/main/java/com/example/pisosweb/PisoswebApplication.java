package com.example.pisosweb;


import com.example.pisosweb.repository.EjemploRepository;
import com.example.pisosweb.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
