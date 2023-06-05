package com.pendataan.workshop.controllers;

import com.pendataan.workshop.entity.pendataan.ProposalWorkshop;
import com.pendataan.workshop.services.ProposalService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/himatika")
public class ProposalController {

    private final ProposalService proposalService;
    private static final Logger log = LoggerFactory.getLogger(ProposalController.class);

    @GetMapping(path = "/proposal-workshop")
    public ModelAndView showAllProposal() {
        return proposalService.getAllProposal();
    }

    @GetMapping("/proposal-workshop/input")
    public ModelAndView formInputProposal(@ModelAttribute("propWorkshop") ProposalWorkshop propWorkshop)
            throws IOException {
        return proposalService.formProposal(propWorkshop);
    }

    @PostMapping(path = "/proposal-workshop/input/upload")
    public @ResponseBody ResponseEntity<?> uploadProp(ProposalWorkshop propWorkshop,
                                                      @RequestParam("suratProposal") MultipartFile suratProposal,
                                                      HttpServletResponse response)
            throws IOException {
        return proposalService.uploadProposal(propWorkshop, suratProposal, response);
    }

    @GetMapping(path = "/proposal-workkshop/{docName}")
    public ResponseEntity<?> downloadFile(@PathVariable String docName) {
        byte[] fileProposal = proposalService.downloadProposal(docName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/pdf"))
                .body(fileProposal);
    }

    @GetMapping(path = "/proposal-workshop/deleteWorkshop/{id}/{judulWorkshop}")
    public @ResponseBody ResponseEntity<?> deleteWorkshop(@PathVariable("id") Long id,
                                       @PathVariable("judulWorkshop") String judulWorkshop,
                                       String docName,
                                       HttpServletResponse response) {
        return proposalService.deletedWorkshop(id, judulWorkshop, docName, response);
    }
}
