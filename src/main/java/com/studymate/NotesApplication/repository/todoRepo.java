package com.studymate.NotesApplication.repository;

import com.studymate.NotesApplication.entity.NewEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface todoRepo extends MongoRepository<NewEntry, ObjectId> {
}
