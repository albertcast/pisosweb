package com.example.pisosweb.repository;

import com.example.pisosweb.document.Comentario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ComentarioRepository extends MongoRepository<Comentario,String> {

    Optional<List<Comentario>> findByVivienda(String vivienda);

    Optional<List<Comentario>> findByUsuario(String usuario);


}
