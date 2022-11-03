package com.example.pisosweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.pisosweb.document.Mensaje;

public interface MensajeRepository extends MongoRepository<Mensaje, String> {

    public Optional<Mensaje> findById(String id);
    public List<Mensaje> findBySender(String sender);
    public List<Mensaje> findByReceiver(String receiver);
    public List<Mensaje> findBySenderAndReceiver(String sender, String receiver);
    
}
