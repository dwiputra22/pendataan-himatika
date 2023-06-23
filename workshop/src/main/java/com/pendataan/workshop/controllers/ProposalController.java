package com.pendataan.workshop.controllers;

import com.pendataan.workshop.entity.ProposalWorkshop;
import com.pendataan.workshop.services.ProposalService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public ModelAndView formProposal(@ModelAttribute("propWorkshop") ProposalWorkshop propWorkshop)
            throws IOException {
        return proposalService.formProposal(propWorkshop);
    }

    @PostMapping(path = "/proposal-workshop/input/upload")
    public @ResponseBody ResponseEntity<?> inputProp(ProposalWorkshop propWorkshop,
                                                      @RequestParam("suratProposal") MultipartFile suratProposal,
                                                      HttpServletResponse response)
            throws IOException {
        return proposalService.uploadProposal(propWorkshop, suratProposal, response);
    }

    @GetMapping(path = "/proposal-workshop/{docName}")
    public ResponseEntity<?> downloadFile(@PathVariable String docName) {
//        byte[] fileProposal = proposalService.downloadProposal(docName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("application/pdf"))
//                .body(fileProposal);
        return proposalService.downloadProposal(docName);
    }

    @GetMapping(path = "/proposal-workshop/deleteProposal/{id}/{judulWorkshop}")
    public @ResponseBody ResponseEntity<?> deleteProposal(@PathVariable("id") Long id,
                                                          @PathVariable("judulWorkshop") String judulWorkshop,
                                                          String fileName,
                                                          HttpServletResponse response) {
        return proposalService.deletedProposal(id, judulWorkshop, fileName, response);
    }

    @GetMapping(path = "/proposal-workshop/editProposal/{id}")
    public ModelAndView editProposal(@PathVariable("id") Long id) {
        return proposalService.editProposal(id);
    }

    @PostMapping(path = "/proposal-workshop/{id}")
    public @ResponseBody ResponseEntity<?> updateProposal(
                                        @PathVariable("id") Long id,
                                        @RequestParam("suratProposal") MultipartFile suratProposal,
                                        HttpServletResponse response,
                                        ProposalWorkshop proposalWorkshop) throws IOException {
        return proposalService.updateProposal(id, suratProposal, response, proposalWorkshop);
    }
}
