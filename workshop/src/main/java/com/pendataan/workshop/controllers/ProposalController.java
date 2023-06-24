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

    @GetMapping(path = "/proposal-workshop/{workshopId}")
    public ModelAndView showProposal(@PathVariable("workshopId") Long workshopId) {
        return proposalService.getProposal(workshopId);
    }

    @GetMapping(path = "/proposal-workshop/{workshopId}/input")
    public ModelAndView formProposal(@PathVariable("workshopId") Long workshopId,
                                     @ModelAttribute("propWorkshop") ProposalWorkshop propWorkshop)
            throws IOException {
        return proposalService.formProposal(workshopId,  propWorkshop);
    }

    @PostMapping(path = "/proposal-workshop/{workshopId}/input/upload")
    public @ResponseBody ResponseEntity<?> inputProp(@PathVariable("workshopId") Long workshopId,
                                                     ProposalWorkshop propWorkshop,
                                                     @RequestParam("suratProposal") MultipartFile suratProposal,
                                                     HttpServletResponse response)
            throws IOException {
        return proposalService.uploadProposal(workshopId, propWorkshop, suratProposal, response);
    }

    @GetMapping(path = "/proposal-workshop/{id}/download/{docName}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") Long id,
                                          @PathVariable("docName") String docName) {
//        byte[] fileProposal = proposalService.downloadProposal(docName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("application/pdf"))
//                .body(fileProposal);
        return proposalService.downloadProposal(id, docName);
    }

    @GetMapping(path = "/proposal-workshop/{workshop_id}/deleteProposal")
    public @ResponseBody ResponseEntity<?> deleteProposal(@PathVariable("workshop_id") Long workshopId,
                                                          String fileName,
                                                          HttpServletResponse response) {
        return proposalService.deletedProposal(workshopId, fileName, response);
    }

    @GetMapping(path = "/proposal-workshop/{workshop_id}/editProposal")
    public ModelAndView editProposal(@PathVariable("workshop_id") Long workshopId) {
        return proposalService.editProposal(workshopId);
    }

    @PostMapping(path = "/proposal-workshop/{workshop_id}/update/{id}")
    public @ResponseBody ResponseEntity<?> updateProposal(@PathVariable("workshop_id") Long workshopId,
                                                          @PathVariable("id") Long id,
                                                          @RequestParam("suratProposal") MultipartFile suratProposal,
                                                          HttpServletResponse response,
                                                          ProposalWorkshop proposalWorkshop) throws IOException {
        return proposalService.updateProposal(workshopId, id, suratProposal, response, proposalWorkshop);
    }
}
