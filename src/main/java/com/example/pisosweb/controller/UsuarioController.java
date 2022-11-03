package com.example.pisosweb.controller;

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

import com.example.pisosweb.document.Comentario;
import com.example.pisosweb.document.Mensaje;
import com.example.pisosweb.document.Usuario;
import com.example.pisosweb.repository.ComentarioRepository;
import com.example.pisosweb.repository.MensajeRepository;
import com.example.pisosweb.repository.UsuarioRepository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
    private UsuarioRepository userRepository;
    @Autowired
    private MensajeRepository mensajeRepository;
    @Autowired
    private ComentarioRepository comentarioRepository;
    
	
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

    @GetMapping(value = "/chatsUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all persons that chat with a specific user", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Usuario.class))), responseCode = "200") })
	public Collection<Usuario> getAllUsersChatting(
        @Parameter(description = "usuarioId", required = true) @RequestParam("usuarioId") final String usuarioId) {
        List<Mensaje> userMessages = new ArrayList<>();
        userMessages.addAll(mensajeRepository.findBySender(usuarioId));
        userMessages.addAll(mensajeRepository.findByReceiver(usuarioId));
        List<String> userIds = new ArrayList<>();
        List<Usuario> usersChatting = new ArrayList<>();
        
        for(Mensaje m : userMessages) {
            String sender = m.getSender();
            String receiver = m.getReceiver();
            if(sender.equals(usuarioId) && !userIds.contains(receiver)) {
                userIds.add(receiver);
                usersChatting.add(userRepository.findById(receiver).get());
            } else if (receiver.equals(usuarioId) && !userIds.contains(sender)){
                userIds.add(sender);
                usersChatting.add(userRepository.findById(sender).get());
            }
        }
        
        return usersChatting;

	}

    @GetMapping(value = "/commentUsersFlat", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all users that have sent at least one comment for a specific flat", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Usuario.class))), responseCode = "200") })
	public Collection<Usuario> getAllCommentsUsersByFlat(
        @Parameter(description = "vivienda", required = true) @RequestParam("vivienda") final String vivienda) {
        List<Usuario> commentUsers = new ArrayList<>();
        List<String> commentUsersIds = new ArrayList<>();
        List<Comentario> flatComments = comentarioRepository.findByVivienda(vivienda).get();

        for(Comentario c : flatComments) {
            String usuario = c.getUsuario();
            if(!commentUsersIds.contains(usuario)) {
                commentUsersIds.add(usuario);
                commentUsers.add(this.findById(usuario).get());
            }
        }

        return commentUsers;

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
