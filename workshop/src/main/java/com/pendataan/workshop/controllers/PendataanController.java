package com.pendataan.workshop.controllers;

import com.pendataan.workshop.services.PendataanService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/himatika")
public class PendataanController {

    private final PendataanService pendataanService;
    @GetMapping
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        return pendataanService.getAllData;
    }
}
