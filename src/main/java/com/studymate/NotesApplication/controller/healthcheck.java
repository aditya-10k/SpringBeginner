package com.studymate.NotesApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class healthcheck {

    @GetMapping("/health")
    String getHealth(){
        return "ok";
    }
}
