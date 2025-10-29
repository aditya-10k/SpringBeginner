package com.studymate.NotesApplication.controller;

import com.studymate.NotesApplication.DTO.ApiFormatter;
import com.studymate.NotesApplication.entity.NewEntry;
import com.studymate.NotesApplication.entity.User;
import com.studymate.NotesApplication.services.EntryService;
import com.studymate.NotesApplication.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {


    @Autowired
    private EntryService entryService;
    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public ResponseEntity<ApiFormatter> getEntries() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return ApiFormatter.success(entryService.getAlltoDosByUser(authentication.getName()), "Entries retrived successfully");
        } catch (Exception e) {
            return ApiFormatter.notfound(null, "Error in the API call");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiFormatter> createEntries(@RequestBody NewEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            entryService.addTodoService(myEntry, authentication.getName());
            return ApiFormatter.created(myEntry, "Entry Created  successfully");
        } catch (RuntimeException e) {
            return ApiFormatter.notfound(null, e.toString());
        }

    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiFormatter> getById(@PathVariable ObjectId id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByUsername(authentication.getName()).orElse(null);
            if (user == null) {
                return ApiFormatter.notfound(null, "User not found");
            }
            boolean isPresent = user.getUserEntries().stream().anyMatch(x -> x.getId().equals(id));
            if (!isPresent) {
                return ApiFormatter.notfound(null, "Entry not found");
            }
            return entryService.getByIdService(id)
                    .map(entry -> ApiFormatter.success(entry, "Entry retrived successfully"))
                    .orElse(ApiFormatter.notfound(null, "Entry not found"));
        } catch (Exception e) {
            return ApiFormatter.notfound(null, "Error in the API call" + e);
        }

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiFormatter> delete(@PathVariable ObjectId id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByUsername(authentication.getName()).orElse(null);
            if (user == null) {
                return ApiFormatter.notfound(null, "User not found");
            }
            boolean isMatch = user.getUserEntries().stream().anyMatch(x -> x.getId().equals(id));
            if (!isMatch) {
                return ApiFormatter.notfound(null, "Entry not found");
            }
            entryService.deletebyIdService(id, authentication.getName());
            return ApiFormatter.success(null, "Entry deleted successfully");
        } catch (Exception e) {
            return ApiFormatter.notfound(null, "Error in the API call" + e);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiFormatter> updateEntry(@PathVariable ObjectId id, @RequestBody NewEntry entry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByUsername(authentication.getName()).orElse(null);
            if (user == null) {
                return ApiFormatter.notfound(null, "User not found");
            }
            boolean isPresent = user.getUserEntries().stream().anyMatch(x -> x.getId().equals(id));
            if (isPresent) {
                NewEntry currentEntry = entryService.getByIdService(id).orElse(null);
                if (currentEntry != null) {
                    currentEntry.setTitle(entry.getTitle() != null && !entry.getTitle().isEmpty() ? entry.getTitle() : currentEntry.getTitle());
                    currentEntry.setDesc(entry.getDesc() != null && !entry.getDesc().isEmpty() ? entry.getDesc() : currentEntry.getDesc());
                    entryService.upDateTodoService(currentEntry);
                    return ApiFormatter.success(currentEntry, "Entry updated successfully");
                }
            }
            return ApiFormatter.notfound(null, "Entry does not exist");
        } catch (Exception e) {
            return ApiFormatter.notfound(null , "Api error "+e);
        }
    }
}
