package com.example.pisosweb.controller;

import com.example.pisosweb.document.Comentario;
import com.example.pisosweb.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/comentario")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @GetMapping("/")
    private ResponseEntity<List<Comentario>> findAll(){
        try{
            return new ResponseEntity<>(comentarioRepository.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<Comentario> findById(@PathVariable final String id){
        try{
            return new ResponseEntity<>(comentarioRepository.findById(id).get(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/vivienda/{id}")
    private ResponseEntity<List<Comentario>> findByVivienda(@PathVariable final String id){
        try{
            return new ResponseEntity<>(comentarioRepository.findByVivienda(id).get(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/usuario/{id}")
    private ResponseEntity<List<Comentario>> findByUsuario(@PathVariable final String id){
        try{
            return new ResponseEntity<>(comentarioRepository.findByUsuario(id).get(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    private ResponseEntity<Comentario> addComentario(@RequestBody Comentario comentario){
        try{
            //Vivienda vivienda = viviendaRepository.findById(comentario.getVivienda());
            //Usuario usuario = usuarioRepository.findById(comentario.getUsuario());

            //if(vivienda != null && usuario != null) { throw new Exception();}
            return new ResponseEntity<>(comentario, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/borrar/{id}")
    private ResponseEntity<Comentario> deleteComentario(@PathVariable final String id){
        try{
            comentarioRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
