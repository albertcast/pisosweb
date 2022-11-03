package com.example.pisosweb.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.pisosweb.document.Apartment;

public interface ApartmentRepository extends MongoRepository<Apartment, String> {

    public Optional<Apartment> findById(String id);
    public List<Apartment> findByPlace(String place);
    public List<Apartment> findByDate(Date date);

}
