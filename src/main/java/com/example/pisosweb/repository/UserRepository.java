package com.example.pisosweb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.pisosweb.document.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {

	public Optional<User> findById(String id);
    public List<User> findByEmailContainsIgnoreCase(String email);
    public List<User> findByNameContainsIgnoreCase(String nombre);
    public List<User> findByLastnameContainsIgnoreCase(String apellidos);
    public Optional<User> findByAccountAuthentication(String accountAuthentication);
}
