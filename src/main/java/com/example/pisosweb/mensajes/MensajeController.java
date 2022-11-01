package com.example.pisosweb.mensajes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
@RequestMapping("/api/mensajes")
public class MensajeController {
    
    @Autowired
    private MensajeRepository repository;

    @GetMapping("/{id}")
    public Optional<Mensaje> findById(@PathVariable String id) {
        return repository.findById(id);
    }

    @GetMapping(value = "/sender/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all messages by one sender", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Mensaje.class))), responseCode = "200") })
	public Collection<Mensaje> getAllSenderMessages(@PathVariable String userId) {
		return repository.findBySender(userId);
	}

    @GetMapping(value = "/receiver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all messages by one sender", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Mensaje.class))), responseCode = "200") })
	public Collection<Mensaje> getAllReceiverMessages(@PathVariable String userId) {
		return repository.findByReceiver(userId);
	}

    @GetMapping(value = "/chats/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List all conversations by one user", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Mensaje.class))), responseCode = "200") })
	public Collection<Mensaje> getAllUserMessages(@PathVariable String userId) {
        List<Mensaje> allMessages = new ArrayList<>();
        allMessages.addAll(this.getAllSenderMessages(userId));
        allMessages.addAll(this.getAllReceiverMessages(userId));
		return allMessages;
	}

    @GetMapping(value = "/user/{firstUserId}/{secondUserId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "List a full conversation between two users", responses = {
            @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Mensaje.class))), responseCode = "200") })
    public Collection<Mensaje> findBySenderAndReceiver(@PathVariable String firstUserId, @PathVariable String secondUserId) {
        List<Mensaje> fullConversation = new ArrayList<>();
        fullConversation.addAll(repository.findBySenderAndReceiver(firstUserId, secondUserId));
        fullConversation.addAll(repository.findBySenderAndReceiver(secondUserId, firstUserId));
        Collections.sort(fullConversation, new Comparator<Mensaje>() {
            public int compare(Mensaje m1, Mensaje m2) {
                return m1.getDate().compareTo(m2.getDate());
            }
        });

        return fullConversation;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "List all messages", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Mensaje.class))), responseCode = "200") })
	public Collection<Mensaje> getAllMessages() {
		return repository.findAll();
	}

    @PostMapping(value = "/")
    public ResponseEntity<Mensaje> addMensaje(
            @Parameter(description = "sender", required = true) @RequestParam("sender") final String sender,
            @Parameter(description = "receiver", required = true) @RequestParam("receiver") final String receiver,
            @Parameter(description = "content", required = true) @RequestParam("content") final String content) {

        Mensaje mensaje = repository.insert(new Mensaje(sender, receiver, content, new Date(System.currentTimeMillis())));
        return ResponseEntity.ok(mensaje);
    
    }

    @PutMapping(value = "/")
    public ResponseEntity<Mensaje> updateMessage(
        @Parameter(description = "id", required = true) @RequestParam("id") final String id,
        @Parameter(description = "content", required = true) @RequestParam("content") final String content) {
            
            Optional<Mensaje> mensajeOpt = repository.findById(id);
            if(!mensajeOpt.isEmpty()) {
                Mensaje mensaje = mensajeOpt.get();
                mensaje.setContent(content);
                mensaje = repository.save(mensaje);
                return ResponseEntity.ok(mensaje);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    

    @DeleteMapping(value = "/{id}")
    public void deleteMessage(@PathVariable("id") final String id) {
            Mensaje mensaje = repository.findById(id).get();
            repository.delete(mensaje);
        }

}
