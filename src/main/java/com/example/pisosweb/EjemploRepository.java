package com.example.pisosweb;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EjemploRepository extends MongoRepository<Ejemplo, String> {

  public Ejemplo findByFirstName(String firstName);
  public List<Ejemplo> findByLastName(String lastName);
  public Ejemplo findByAbc(String abc);
  public Optional<Ejemplo> findById(String id);

}