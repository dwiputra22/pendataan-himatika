package com.pendataan.workshop.controllers;

import com.pendataan.workshop.entity.Workshop;
import com.pendataan.workshop.services.WorkshopService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/himatika")
public class WorkshopController {

    private final WorkshopService workshopService;

    @GetMapping(path = "/workshop")
    public ModelAndView listWorkshop() {
        return workshopService.getAllWorkshop();
    }

    @GetMapping(path = "/formWorkshop/input")
    public ModelAndView formWorkshop(Workshop workshop) {
        return workshopService.input(workshop);
    }

    @PostMapping(path = "/formWorkshop/input/upload")
    public @ResponseBody ResponseEntity<?> inputWorkshop(Workshop workshop,
                                                         @RequestParam("poster")MultipartFile poster,
                                                         HttpServletResponse response)
            throws IOException {
        return workshopService.uploadWorkshop(workshop, poster, response);
    }
}
