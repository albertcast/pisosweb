package com.example.pisosweb.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.pisosweb.document.Vivienda;

public interface ViviendaRepository extends MongoRepository<Vivienda, String> {

    public Optional<Vivienda> findById(String id);
    public List<Vivienda> findByPlace(String place);
    public List<Vivienda> findByDate(Date date);

}
