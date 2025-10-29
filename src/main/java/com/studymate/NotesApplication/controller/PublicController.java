package com.studymate.NotesApplication.controller;

import com.studymate.NotesApplication.DTO.ApiFormatter;
import com.studymate.NotesApplication.entity.User;
import com.studymate.NotesApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiFormatter> createUser(@RequestBody User user) {
        try {
            userService.saveNewUser(user);
            return ApiFormatter.created(user, "User Created successfully");
        } catch (Exception e) {
            return ApiFormatter.notfound(null, "Api call Error" + e);

        }
    }
}