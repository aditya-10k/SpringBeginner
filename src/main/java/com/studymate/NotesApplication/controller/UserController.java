package com.studymate.NotesApplication.controller;

import com.studymate.NotesApplication.DTO.ApiFormatter;
import com.studymate.NotesApplication.entity.User;
import com.studymate.NotesApplication.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

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

    @PostMapping("/create")
    public ResponseEntity<ApiFormatter> createUser(@RequestBody User user){
        try {
            userService.saveUser(user);
            return ApiFormatter.created(user , "User Created successfully");
        } catch (Exception e) {
            return ApiFormatter.notfound(null , "Api call Error" + e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiFormatter> getUserById(@PathVariable ObjectId id){
        try {
            return userService.findUserById(id)
                            .map(user -> ApiFormatter.success(user , "User fetched successfully"))
                                    .orElse(ApiFormatter.notfound(null , "User not found"));
        } catch (Exception e) {
            return ApiFormatter.notfound(null , "Api call error");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiFormatter> changeUserInfo(@PathVariable ObjectId id , @RequestBody User user){
        try {
            User old = userService.findUserById(id).orElse(null);

            if(old != null){
                old.setName(user.getName() != null && !user.getName().isEmpty() ? user.getName() : old.getName());
                old.setPassword(user.getPassword() != null && !user.getPassword().isEmpty() ? user.getPassword() : old.getPassword());
                old.setUsername(user.getUsername() != null && !user.getUsername().isEmpty() ? user.getUsername() : old.getUsername());
                userService.saveUser(old);
                return ApiFormatter.success(old, "User Info updated successfully");
            }
            else {
                return ApiFormatter.notfound(null , "User not found");
            }
        } catch (Exception e) {
            return ApiFormatter.notfound(null, "Api call error");
        }

    }

}
