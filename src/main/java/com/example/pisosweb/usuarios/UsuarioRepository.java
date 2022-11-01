package com.example.pisosweb.usuarios;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario,String> {

	public Optional<Usuario> findById(String id);
    public List<Usuario> findByEmail(String email);
    public List<Usuario> findByName(String nombre);
    public List<Usuario> findByLastname(String apellidos);
}
