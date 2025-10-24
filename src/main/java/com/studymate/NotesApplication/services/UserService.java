package com.studymate.NotesApplication.services;

import com.studymate.NotesApplication.entity.User;
import com.studymate.NotesApplication.repository.userRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private userRepo user ;

     public List<User> getAllUsersService(){
        return user.findAll();
    }

     public void saveUser(User newUser){
        user.save(newUser);
    }

     public void deleteUser(ObjectId username){
        user.deleteById(username);
    }

     public Optional<User> findUserById(ObjectId id){
        return user.findById(id);
    }
}

