package com.pendataan.workshop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PendataanController {

    @GetMapping("/himatika")
    public String home(){
        return "himatika";
    }
}
