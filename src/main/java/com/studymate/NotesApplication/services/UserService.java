package com.studymate.NotesApplication.services;

import com.studymate.NotesApplication.entity.User;
import com.studymate.NotesApplication.repository.userRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private userRepo user ;

    private static final PasswordEncoder passwordencoder = new BCryptPasswordEncoder();

     public List<User> getAllUsersService(){
        return user.findAll();
    }

     public void saveNewUser(User newUser){
         newUser.setPassword(passwordencoder.encode(newUser.getPassword()));
         newUser.setRoles(Arrays.asList("USER"));
         user.save(newUser);
    }

    public void saveUser(User newUser){
         user.save(newUser);
    }

    public void saveAdmin(User newUser){
        newUser.setPassword(passwordencoder.encode(newUser.getPassword()));
        newUser.setRoles(Arrays.asList("USER","ADMIN"));
        user.save(newUser);
    }

     public void deleteUser(ObjectId username){
        user.deleteById(username);
    }

     public Optional<User> findUserById(ObjectId id){
        return user.findById(id);
    }

     public Optional<User> findUserByUsername(String username){
         return user.findByUsername(username);
     }

     public void deleteUserByUsername(String username){
         user.deleteByUsername(username);
     }
}

