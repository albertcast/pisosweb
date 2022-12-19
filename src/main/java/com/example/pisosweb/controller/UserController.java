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

import com.example.pisosweb.document.Comment;
import com.example.pisosweb.document.Message;
import com.example.pisosweb.document.User;
import com.example.pisosweb.repository.CommentRepository;
import com.example.pisosweb.repository.MessageRepository;
import com.example.pisosweb.repository.UserRepository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@CrossOrigin(origins = {"http://localhost:8080","https://pisoswebcliente.herokuapp.com"})
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository mensajeRepository;
    @Autowired
    private CommentRepository comentarioRepository;
    
   
	
    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable String id) {
        return userRepository.findById(id);
    }
    
    @GetMapping(value = "/usersByAccountAuthentication", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> findByAccountAuthentication(@RequestParam("accountAuthentication") String accountAuthentication) {
        return userRepository.findByAccountAuthentication(accountAuthentication);
    }

    @GetMapping(value = "/usersByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all users by email", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200") })
	public Collection<User> getAllUsersByEmail(
        @Parameter(description = "email", required = true) @RequestParam("email") final String email) {
		return userRepository.findByEmail(email);
	}
    
    @GetMapping(value = "/usersByName", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all users by name", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200") })
	public Collection<User> getAllUsersByName(
        @Parameter(description = "name", required = true) @RequestParam("name") final String name) {
		return userRepository.findByName(name);
	}

    @GetMapping(value = "/usersByLastname", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all users by lastname", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200") })
	public Collection<User> getAllUsersByLastname(
        @Parameter(description = "lastname", required = true) @RequestParam("lastname") final String lastname) {
		return userRepository.findByLastname(lastname);
	}

    @GetMapping(value = "/usersByChats", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all persons that chat with a specific user", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200") })
	public Collection<User> getAllUsersChatting(
        @Parameter(description = "usuarioId", required = true) @RequestParam("usuarioId") final String usuarioId) {
        List<Message> userMessages = new ArrayList<>();
        userMessages.addAll(mensajeRepository.findBySender(usuarioId));
        userMessages.addAll(mensajeRepository.findByReceiver(usuarioId));
        List<String> userIds = new ArrayList<>();
        List<User> usersChatting = new ArrayList<>();
        
        for(Message m : userMessages) {
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

    @GetMapping(value = "/usersByCommentInFlat", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all users that have sent at least one comment for a specific flat", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200") })
	public Collection<User> getAllCommentsUsersByFlat(
        @Parameter(description = "vivienda", required = true) @RequestParam("vivienda") final String vivienda) {
        List<User> commentUsers = new ArrayList<>();
        List<String> commentUsersIds = new ArrayList<>();
        List<Comment> flatComments = comentarioRepository.findByApartment(vivienda).get();

        for(Comment c : flatComments) {
            String user = c.getUser();
            if(!commentUsersIds.contains(user)) {
                commentUsersIds.add(user);
                commentUsers.add(this.findById(user).get());
            }
        }

        return commentUsers;

	}

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "List all users", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))), responseCode = "200") })
	public Collection<User> getAll() {
		return userRepository.findAll();
	}
    
    
    @PostMapping(value = "/")
    public ResponseEntity<User> addUser(
    		@Parameter(description = "email") @RequestParam("email") final String email,
            @Parameter(description = "name") @RequestParam("name") final String name,
            @Parameter(description = "lastname") @RequestParam("lastname") final String lastname,
            @Parameter(description = "age") @RequestParam("age") final Integer age,
            @Parameter(description = "accountAuthentication", required = true) @RequestParam("accountAuthentication") final String accountAuthentication) throws ParseException {
    	if(findByAccountAuthentication(accountAuthentication).isEmpty()) {
	        User user = userRepository.insert(new User(email, name, lastname, age, accountAuthentication));
	        return ResponseEntity.ok(user);
    	} else {
    		return null;
    	}
    	
    }
    
    
    @PutMapping(value = "/")
    public ResponseEntity<User> updateUser(
        @Parameter(description = "id", required = true) @RequestParam("id") final String id,
        @Parameter(description = "email", required = false) @RequestParam("email") final String email,
        @Parameter(description = "name", required = false) @RequestParam("name") final String name,
        @Parameter(description = "lastname", required = false) @RequestParam("lastname") final String lastname,
        @Parameter(description = "age", required = false) @RequestParam("age") final Integer age) throws ParseException  {
            Optional<User> userOpt = userRepository.findById(id);
            if(!userOpt.isEmpty()) {
                User user = userOpt.get();
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
    
    @DeleteMapping(value = "/")
    public void deleteUser(@RequestParam("id") final String id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
    }
    

}
