package com.example.pisosweb.controller;

import com.example.pisosweb.document.Comment;
import com.example.pisosweb.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/")
    private ResponseEntity<List<Comment>> findAll(){
        try{
            return new ResponseEntity<>(commentRepository.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @PostMapping("")
    private ResponseEntity<Comment> addComment(@RequestBody Comment comment){
        try{
            //Vivienda vivienda = viviendaRepository.findById(comentario.getVivienda());
            //Usuario usuario = usuarioRepository.findById(comentario.getUsuario());

            //if(vivienda != null && usuario != null) { throw new Exception();}
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Comment> deleteComment(@PathVariable final String id){
        try{
            commentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
