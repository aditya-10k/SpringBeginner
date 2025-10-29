package com.studymate.NotesApplication.services;

import com.studymate.NotesApplication.entity.NewEntry;
import com.studymate.NotesApplication.entity.User;
import com.studymate.NotesApplication.repository.todoRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class EntryService {

    @Autowired
    private todoRepo newtodo ;
    @Autowired
    private UserService userService;

    @Transactional
    public void addTodoService(NewEntry newEntry , String username){
        User user = userService.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        newEntry.setDate(LocalDateTime.now());
        NewEntry saved =  newtodo.save(newEntry);
        user.getUserEntries().add(saved);
        userService.saveUser(user);
    }

    public void upDateTodoService(NewEntry newEntry){
        newtodo.save(newEntry);
    }

    public List<NewEntry> getAlltoDosByUser(String  username){
        User user = userService.findUserByUsername(username).orElseThrow(() ->new  RuntimeException("User not found"));
       return user.getUserEntries();
//        return newtodo.findAll();
    }

    public Optional<NewEntry> getByIdService(ObjectId id){
       return newtodo.findById(id);
    }

    @Transactional
    public boolean deletebyIdService(ObjectId id, String username){
        User user = userService.findUserByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
        boolean check = user.getUserEntries().removeIf(x -> x.getId().equals(id));
        if(check) {
            userService.saveUser(user);
            newtodo.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }

}
