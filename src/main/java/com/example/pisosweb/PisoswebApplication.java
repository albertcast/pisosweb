package com.example.pisosweb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PisoswebApplication implements CommandLineRunner{

	@Autowired
	private EjemploRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(PisoswebApplication.class, args);
	}
	
	 @Override
	 public void run(String... args) throws Exception {
	 /*
	 deleteAll();
	 addSampleData();
	 listAll();
	 findFirst();
	 */
	 }
	/* 
	 
	 
	 public void deleteAll() {
	 System.out.println("Deleting all records..");
	 repository.deleteAll();
	 }
	 
	 public void addSampleData() {
	 System.out.println("Adding sample data");
	 repository.save(new Ejemplo("Jack Bauer", "New York", "aaa"));
	 repository.save(new Ejemplo("Harvey Spectre", "London", "bbb"));
	 repository.save(new Ejemplo("Mike Ross", "New Jersey", "ccc"));
	 repository.save(new Ejemplo("Louise Litt", "Kathmandu", "ddd"));
	 }
	 
	 public void listAll() {
	 System.out.println("Listing sample data");
	 repository.findAll().forEach(u -> System.out.println(u));
	 }
	 
	 public void findFirst() {
	 System.out.println("Finding first by Name");
	 Ejemplo u = repository.findByFirstName("Louise Litt");
	 System.out.println(u);
	 Ejemplo a = repository.findByAbc("aaa");
	 System.out.println(a);
	 }
	 */
	 
	
	
	
}
