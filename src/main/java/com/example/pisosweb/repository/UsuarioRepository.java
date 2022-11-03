package com.example.pisosweb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.pisosweb.document.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario,String> {

	public Optional<Usuario> findById(String id);
    public List<Usuario> findByEmail(String email);
    public List<Usuario> findByName(String nombre);
    public List<Usuario> findByLastname(String apellidos);
}
