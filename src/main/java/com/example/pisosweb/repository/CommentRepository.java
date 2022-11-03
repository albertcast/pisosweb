package com.example.pisosweb.repository;

import com.example.pisosweb.document.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment,String> {

    Optional<List<Comment>> findByApartment(String apartment);

    Optional<List<Comment>> findByUser(String user);


}
