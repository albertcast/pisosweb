package com.example.pisosweb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.pisosweb.document.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {

	public Optional<User> findById(String id);
    public List<User> findByEmail(String email);
    public List<User> findByName(String nombre);
    public List<User> findByLastname(String apellidos);
}
