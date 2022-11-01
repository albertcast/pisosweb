package com.example.pisosweb.viviendas;

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

@RestController
@RequestMapping("/api/vivienda")
public class ViviendaController {
    
    @Autowired
    private ViviendaRepository repository;
    String pattern = "MM-dd-yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    @GetMapping("/{id}")
    public Optional<Vivienda> findById(@PathVariable String id) {
        return repository.findById(id);
    }

    @GetMapping(value = "/place", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all flats by place", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Vivienda.class))), responseCode = "200") })
	public Collection<Vivienda> getAllFlatsByPlace(
        @Parameter(description = "place", required = true) @RequestParam("place") final String place) {
		return repository.findByPlace(place);
	}

    @GetMapping(value = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all flats by date", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Vivienda.class))), responseCode = "200") })
	public Collection<Vivienda> getAllFlatsByDate(
        @Parameter(description = "date", required = true) @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) {
            return repository.findByDate(date);
	    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "List all flats", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Vivienda.class))), responseCode = "200") })
	public Collection<Vivienda> getAllFlats() {
		return repository.findAll();
	}

    @PostMapping(value = "/addFlat")
    public ResponseEntity<Vivienda> addFlat(
            @Parameter(description = "title", required = true) @RequestParam("title") final String title,
            @Parameter(description = "place", required = true) @RequestParam("place") final String place,
            @Parameter(description = "description", required = true) @RequestParam("description") final String description,
            @Parameter(description = "date", required = true) @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        Vivienda vivienda = repository.insert(new Vivienda(title, place, description, calendar.getTime()));
        return ResponseEntity.ok(vivienda);
    }

    @PutMapping(value = "/updateFlat")
    public ResponseEntity<Vivienda> updateFlat(
        @Parameter(description = "id", required = true) @RequestParam("id") final String id,
        @Parameter(description = "title", required = false) @RequestParam("title") final String title,
        @Parameter(description = "place", required = false) @RequestParam("place") final String place,
        @Parameter(description = "description", required = false) @RequestParam("description") final String description,
        @Parameter(description = "date", required = false) @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) throws ParseException  {
            Optional<Vivienda> viviendaOpt = repository.findById(id);
            if(!viviendaOpt.isEmpty()) {
                Vivienda vivienda = viviendaOpt.get();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.HOUR_OF_DAY, 1);
                vivienda.setDate(calendar.getTime());
                vivienda.setTitle(title);
                vivienda.setPlace(place);
                vivienda.setDescription(description);
                vivienda = repository.save(vivienda);
                return ResponseEntity.ok(vivienda);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    

    @DeleteMapping(value = "/{id}")
    public void deleteFlat(@PathVariable("id") final String id) {
        Vivienda vivienda = repository.findById(id).get();
        repository.delete(vivienda);
    }

}
