package com.example.pisosweb.usuarios;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collection;
import java.util.Optional;


@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
    private UsuarioRepository userRepository;
    
	
    @GetMapping("/{id}")
    public Optional<Usuario> findById(@PathVariable String id) {
        return userRepository.findById(id);
    }

    @GetMapping(value = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all users by email", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Usuario.class))), responseCode = "200") })
	public Collection<Usuario> getAllUsersByEmail(
        @Parameter(description = "email", required = true) @RequestParam("email") final String email) {
		return userRepository.findByEmail(email);
	}
    
    @GetMapping(value = "/name", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all users by name", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Usuario.class))), responseCode = "200") })
	public Collection<Usuario> getAllUsersByName(
        @Parameter(description = "name", required = true) @RequestParam("name") final String name) {
		return userRepository.findByName(name);
	}

    @GetMapping(value = "/lastname", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all users by lastname", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Usuario.class))), responseCode = "200") })
	public Collection<Usuario> getAllUsersByLastname(
        @Parameter(description = "lastname", required = true) @RequestParam("lastname") final String lastname) {
		return userRepository.findByLastname(lastname);
	}

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "List all users", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Usuario.class))), responseCode = "200") })
	public Collection<Usuario> getAll() {
		return userRepository.findAll();
	}
    
    
    @PostMapping(value = "/addUser")
    public ResponseEntity<Usuario> addUser(
    		@Parameter(description = "email", required = true) @RequestParam("email") final String email,
            @Parameter(description = "name", required = true) @RequestParam("name") final String name,
            @Parameter(description = "lastname", required = true) @RequestParam("lastname") final String lastname,
            @Parameter(description = "age", required = true) @RequestParam("age") final Integer age) throws ParseException {
        Usuario user = userRepository.insert(new Usuario(email, name, lastname, age));
        return ResponseEntity.ok(user);
    }
    
    
    @PutMapping(value = "/updateUser")
    public ResponseEntity<Usuario> updateUser(
        @Parameter(description = "id", required = true) @RequestParam("id") final String id,
        @Parameter(description = "email", required = false) @RequestParam("email") final String email,
        @Parameter(description = "name", required = false) @RequestParam("name") final String name,
        @Parameter(description = "lastname", required = false) @RequestParam("lastname") final String lastname,
        @Parameter(description = "age", required = false) @RequestParam("age") final Integer age) throws ParseException  {
            Optional<Usuario> userOpt = userRepository.findById(id);
            if(!userOpt.isEmpty()) {
                Usuario user = userOpt.get();
                user.setEmail(email);
                user.setNombre(name);
                user.setApellidos(lastname);
                user.setEdad(age);
                user = userRepository.save(user);
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    
    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable("id") final String id) {
        Usuario usuario = userRepository.findById(id).get();
        userRepository.delete(usuario);
    }

}
