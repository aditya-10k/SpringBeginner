package com.studymate.NotesApplication.repository;

import com.studymate.NotesApplication.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepo extends MongoRepository<User , ObjectId> {

    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);

}
