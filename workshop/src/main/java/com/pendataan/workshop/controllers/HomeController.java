package com.pendataan.workshop.controllers;

import com.pendataan.workshop.entity.Workshop;
import com.pendataan.workshop.services.HomeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/himatika")
public class HomeController {
    private final HomeService homeService;

    @GetMapping
    public ModelAndView home() {
        return homeService.getIndex();
    }

    @GetMapping(path = "/formWorkshop/input")
    public ModelAndView formWorkshop(Workshop workshop) {
        return homeService.input(workshop);
    }

    @PostMapping(path = "/formWorkshop/input/upload")
    public @ResponseBody ResponseEntity<?> inputWorkshop(Workshop workshop,
                                                         HttpServletResponse response)
            throws IOException {
        return homeService.uploadProposal(workshop, response);
    }
}
