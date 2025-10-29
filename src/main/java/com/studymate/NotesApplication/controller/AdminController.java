package com.studymate.NotesApplication.controller;

import com.studymate.NotesApplication.DTO.ApiFormatter;
import com.studymate.NotesApplication.entity.User;
import com.studymate.NotesApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<ApiFormatter> getAllUsers(){
        try {
            return ApiFormatter.success(userService.getAllUsersService() , "Users fetched successfully");
        } catch (Exception e) {
            return ApiFormatter.notfound(null , "Api Call Error");
        }
    }

    @GetMapping("/create")
    public ResponseEntity<ApiFormatter> createAdmin(@RequestBody User user){
        try {
            userService.saveAdmin(user);
            return ApiFormatter.success(user , "Admin created successfully");
        } catch (Exception e) {
            return ApiFormatter.notfound(null , "Api error"+ e);
        }
    }
}
