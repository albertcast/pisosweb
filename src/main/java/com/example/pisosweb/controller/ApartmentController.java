package com.example.pisosweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pisosweb.document.Apartment;
import com.example.pisosweb.document.User;
import com.example.pisosweb.repository.ApartmentRepository;
import com.example.pisosweb.repository.UserRepository;

@CrossOrigin(origins = {"http://localhost:8080","https://pisoswebcliente.herokuapp.com"})
@RestController
@RequestMapping("/api/apartment")
public class ApartmentController {
    
    @Autowired
    private ApartmentRepository repository;
    @Autowired
    private UserRepository userRepository;
    String pattern = "MM-dd-yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    @GetMapping("/{id}")
    public Optional<Apartment> findById(@PathVariable String id) {
        return repository.findById(id);
    }

    @GetMapping(value = "/place", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all flats by place", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Apartment.class))), responseCode = "200") })
	public Collection<Apartment> getAllFlatsByPlace(
        @Parameter(description = "place", required = true) @RequestParam("place") final String place) {
		return repository.findByPlace(place);
	}

    @GetMapping(value = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all flats by date", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Apartment.class))), responseCode = "200") })
	public Collection<Apartment> getAllFlatsByDate(
        @Parameter(description = "date", required = true) @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) {
            return repository.findByDate(date);
	    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "List all flats", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Apartment.class))), responseCode = "200") })
	public Collection<Apartment> getAllFlats() {
		return repository.findAll();
	}
    
    @GetMapping(value = "/owner", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all flats by owner", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Apartment.class))), responseCode = "200") })
	public Collection<Apartment> getAllFlatsByOwner(
        @Parameter(description = "owner", required = true) @RequestParam("owner") final String owner) {
		return repository.findByOwner(owner);
	}

    @PostMapping(value = "/addFlat")
    public ResponseEntity<Apartment> addFlat(
            @Parameter(description = "title", required = true) @RequestParam("title") final String title,
            @Parameter(description = "place", required = true) @RequestParam("place") final String place,
            @Parameter(description = "description", required = true) @RequestParam("description") final String description,
            @Parameter(description = "date", required = true) @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date,
            @Parameter(description = "owner", required = true) @RequestParam("owner") final String owner) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        Apartment apartment = repository.insert(new Apartment(title, place, description, calendar.getTime(), owner));
        return ResponseEntity.ok(apartment);
    }

    @PutMapping(value = "/updateFlat")
    public ResponseEntity<Apartment> updateFlat(
        @Parameter(description = "id", required = true) @RequestParam("id") final String id,
        @Parameter(description = "title", required = false) @RequestParam("title") final String title,
        @Parameter(description = "place", required = false) @RequestParam("place") final String place,
        @Parameter(description = "description", required = false) @RequestParam("description") final String description,
        @Parameter(description = "date", required = false) @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date,
        @Parameter(description = "owner", required = true) @RequestParam("owner") final String owner) throws ParseException  {
            Optional<Apartment> apartmentOpt = repository.findById(id);
            if(!apartmentOpt.isEmpty() && !userRepository.findById(owner).isEmpty()) {
                Apartment apartment = apartmentOpt.get();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.HOUR_OF_DAY, 1);
                apartment.setDate(calendar.getTime());
                apartment.setTitle(title);
                apartment.setPlace(place);
                apartment.setDescription(description);
                apartment.setOwner(owner);
                apartment = repository.save(apartment);
                return ResponseEntity.ok(apartment);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    

    @DeleteMapping(value = "/{id}")
    public void deleteFlat(@PathVariable("id") final String id) {
        Apartment apartment = repository.findById(id).get();
        repository.delete(apartment);
    }

}
