package com.pendataan.workshop.controllers;

import com.pendataan.workshop.services.HomeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/himatika")
public class HomeController {
    private final HomeService homeService;
    @GetMapping
    public ModelAndView home() {
        return homeService.getIndex();
    }
}
