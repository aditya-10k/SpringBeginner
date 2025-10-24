package com.studymate.NotesApplication.services;

import com.studymate.NotesApplication.entity.NewEntry;
import com.studymate.NotesApplication.entity.User;
import com.studymate.NotesApplication.repository.todoRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class EntryService {

    @Autowired
    private todoRepo newtodo ;
    @Autowired
    private UserService userService;

    public void addTodoService(NewEntry newEntry , ObjectId id){
        User user = userService.findUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        newEntry.setDate(LocalDateTime.now());
        NewEntry saved =  newtodo.save(newEntry);
        user.getUserEntries().add(saved);
        userService.saveUser(user);
    }

    public void addTodoService(NewEntry newEntry){
        newtodo.save(newEntry);
    }

    public List<NewEntry> getAlltoDosByUser(ObjectId id){
        User user = userService.findUserById(id).orElseThrow(() ->new  RuntimeException("User not found"));
       return user.getUserEntries();
//        return newtodo.findAll();
    }

    public Optional<NewEntry> getByIdService(ObjectId id){
       return newtodo.findById(id);
    }

    public boolean deletebyIdService(ObjectId id, ObjectId userID){
        User user = userService.findUserById(userID).orElseThrow(()-> new RuntimeException("User not found"));
        user.getUserEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        newtodo.deleteById(id);
        return true;
    }

}
