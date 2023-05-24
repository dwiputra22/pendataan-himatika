package com.pendataan.workshop.controllers;

import com.pendataan.workshop.entity.pendataan.LpjWorkshop;
import com.pendataan.workshop.entity.pendataan.ProposalWorkshop;
import com.pendataan.workshop.services.PendataanService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/himatika")
public class PendataanController {

    private final PendataanService pendataanService;
    private static final Logger log = LoggerFactory.getLogger(PendataanController.class);

    @GetMapping
    public ModelAndView home(ProposalWorkshop propWorkshop) {
        return pendataanService.getAllData(propWorkshop);
    }

    @GetMapping("/proposal-workshop")
    public ModelAndView showAllProposal(@ModelAttribute("propWorkshop") ProposalWorkshop propWorkshop)
            throws IOException {
        return pendataanService.getAllProposal(propWorkshop);
    }

    @PostMapping(path = "/proposal-workshop/upload")
    public @ResponseBody ResponseEntity<?> uploadProp(ProposalWorkshop propWorkshop,
                                                      @RequestParam("suratProposal") MultipartFile suratProposal,
                                                      BindingResult bindingResult,
                                                      HttpServletRequest request)
            throws IOException {
        return pendataanService.uploadProposal(propWorkshop, bindingResult, suratProposal, request);
    }

    @PostMapping(path = "/lpj-workshop")
    public String uploadLpj(@RequestBody MultipartFile file,
                            @RequestBody LpjWorkshop lpjWorkshop) {
        return pendataanService.uploadLpj(file, lpjWorkshop);
    }
}
