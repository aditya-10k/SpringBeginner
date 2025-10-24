package com.studymate.NotesApplication.controller;

import com.studymate.NotesApplication.DTO.ApiFormatter;
import com.studymate.NotesApplication.entity.NewEntry;
import com.studymate.NotesApplication.services.EntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {


    @Autowired
    private EntryService entryService ;


    @GetMapping("/all-entries/{id}")
    public ResponseEntity<ApiFormatter> getEntries(@PathVariable ObjectId id){
        try {
            return  ApiFormatter.success(entryService.getAlltoDosByUser(id) ,"Entries retrived successfully" );
        } catch (Exception e) {
            return  ApiFormatter.notfound(null , "Error in the API call");
        }
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<ApiFormatter> createEntries(@PathVariable ObjectId id,@RequestBody NewEntry myEntry){
        try {
            entryService.addTodoService(myEntry , id);
            return ApiFormatter.created(myEntry, "Entry Created  successfully");
        } catch (RuntimeException e) {
            return ApiFormatter.notfound(null , e.toString());
        }

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiFormatter> getById(@PathVariable ObjectId id){
        try {
            return entryService.getByIdService(id)
                    .map(entry -> ApiFormatter.success(entry , "Entry retrived successfully"))
                    .orElse(ApiFormatter.notfound(null  , "Entry not found"));
        }catch (Exception e){
            return ApiFormatter.notfound(null , "Error in the API call" + e);
        }

    }

    @DeleteMapping ("/delete/{userID}/{id}")
    public ResponseEntity<ApiFormatter> deleteById(@PathVariable ObjectId id , @PathVariable ObjectId userID){
        try{
            entryService.deletebyIdService(id , userID);
                    return ApiFormatter.success(null , "Entry deleted successfully");

        } catch (Exception e) {
            return ApiFormatter.notfound(null , "Error in the API call" + e);
        }
    }

    @PutMapping ("/update/{userId}/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId id, @RequestBody NewEntry myentry , @PathVariable ObjectId userId){
        NewEntry old = entryService.getByIdService(id).orElse(null);
        try {
            if(old != null){
                old.setTitle(myentry.getTitle() != null && !myentry.getTitle().isEmpty() ? myentry.getTitle() : old.getTitle() );
                old.setDesc(myentry.getDesc() != null && !myentry.getDesc().isEmpty() ? myentry.getDesc() : old.getDesc() );
                entryService.addTodoService(old);
                return ApiFormatter.success(old , "Entry updated successfully");
            }
            else {
                return ApiFormatter.notfound(null , "Entry not found");
            }

        } catch (Exception e) {
            return ApiFormatter.notfound(null , "Error in Api call");
        }
    }
}
