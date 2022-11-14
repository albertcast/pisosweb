package com.example.pisosweb.controller;

import com.example.pisosweb.document.Apartment;
import com.example.pisosweb.document.Comment;
import com.example.pisosweb.document.User;
import com.example.pisosweb.repository.ApartmentRepository;
import com.example.pisosweb.repository.CommentRepository;
import com.example.pisosweb.repository.UserRepository;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    private List<Comment> findAll(){
    	return commentRepository.findAll();
        
    }

    @GetMapping("/{id}")
    private ResponseEntity<Comment> findById(@PathVariable final String id){
        try{
            return new ResponseEntity<>(commentRepository.findById(id).get(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/apartment/{id}")
    private ResponseEntity<List<Comment>> findByApartment(@PathVariable final String id){
        try{
            return new ResponseEntity<>(commentRepository.findByApartment(id).get(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{id}")
    private ResponseEntity<List<Comment>> findByUser(@PathVariable final String id){
        try{
            return new ResponseEntity<>(commentRepository.findByUser(id).get(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping( "/addComment")
    private ResponseEntity<Comment> addComment(
            @Parameter(description = "user", required = true) @RequestParam("user") final String user,
            @Parameter(description = "apartment", required = true) @RequestParam("apartment") final String apartment,
            @Parameter(description = "text", required = true) @RequestParam("text") final String text,
            @Parameter(description = "rating", required = true) @RequestParam("rating") final String rating){
        try{
            String ap = apartmentRepository.findById(apartment).get().getId();
            String usr = userRepository.findById(user).get().getUsuarioId();
            Comment comment;

            if(ap == null || usr == null) { throw new Exception();}
            comment = new Comment(text,rating,usr,ap);
            commentRepository.insert(comment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping( "/updateComment")
    public ResponseEntity<Comment> updateComment(
            @Parameter(description = "id", required = true) @RequestParam("id") final String id,
            @Parameter(description = "user", required = true) @RequestParam("user") final String user,
            @Parameter(description = "apartment", required = true) @RequestParam("apartment") final String apartment,
            @Parameter(description = "text", required = true) @RequestParam("text") final String text,
            @Parameter(description = "rating", required = true) @RequestParam("rating") final String rating) throws ParseException {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if(!commentOpt.isEmpty()) {
            Comment comment = commentOpt.get();
            comment.setUser(user);
            comment.setApartment(apartment);
            comment.setText(text);
            comment.setRating(rating);
            comment = commentRepository.save(comment);
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{id}")
    private void deleteComment(@PathVariable final String id){
        commentRepository.deleteById(id);
    }


}
