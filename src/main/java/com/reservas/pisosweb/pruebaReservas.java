package com.reservas.pisosweb;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.pisosweb.controller.BookingController;
import com.example.pisosweb.document.Booking;
import com.example.pisosweb.repository.BookingRepository;

@SpringBootApplication
public class pruebaReservas implements CommandLineRunner {
	
	@Autowired
	private BookingController r;
	@Autowired
	private BookingRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(pruebaReservas.class, args);
	}
	
	 @Override
	 public void run(String... args) throws Exception {
	 deleteAll();
	 addSampleData();
	 listAll();
	 find();
	 }
	
	 
	 
	 public void deleteAll() {
	 System.out.println("Deleting all records..");
	 repository.deleteAll();
	 }
	 
	 public void addSampleData() {
	 System.out.println("Adding sample data");
	 repository.save(new Booking("Panchito",LocalDate.of(2022, 04, 04), LocalDate.of(2022, 04, 04), "Palacio"));
	 repository.save(new Booking("Panchito",LocalDate.of(2022, 04, 04), LocalDate.of(2022, 04, 04), "Palacio"));
	 repository.save(new Booking("Panchito",LocalDate.of(2022, 04, 04), LocalDate.of(2022, 04, 06), "Palacio"));
	 repository.save(new Booking("Francis",LocalDate.of(2022, 03, 04), LocalDate.of(2022, 04, 04), "Casa rural"));
	 repository.save(new Booking("Eugenio",LocalDate.of(2022, 04, 04), LocalDate.of(2022, 04, 04), "Hotel entero pami"));
	 }
	 
	 public void listAll() {
	 System.out.println("Listing sample data");
	 repository.findAll().forEach(u -> System.out.println(u));
	 }
	 
	 public void find() {
	 System.out.println("Finding by guest");
	 List<Booking> u = r.findByGuest("Panchito");
	 u.forEach(i -> System.out.println(i));
	 
	 /*System.out.println("Finding by arrival date");
	 u = r.findByArrivalDate(LocalDate.of(2022, 04, 04));
	 u.forEach(i -> System.out.println(i));*/
	 
	 /*System.out.println("Finding by departure date");
	 u = r.findByDepartureDate(LocalDate.of(2022, 04, 04));
	 u.forEach(i -> System.out.println(i));
	 
	 System.out.println("Finding by apartment");
	 u = r.findByApartment("Casa");
	 u.forEach(i -> System.out.println(i));*/
	 }
	 
	 
	 
	
	
	
}
