package com.pendataan.peserta.shared.controller;

import com.pendataan.peserta.shared.services.WorkshopService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class WorkshopController {

    private final WorkshopService workshopService;

    @GetMapping(path = "")
    public ModelAndView home() {
        return workshopService.showHomePage();
    }

    @GetMapping(path = "/list-workshop")
    public ModelAndView listWorkshop() {
        return workshopService.getAllWorkshop();
    }

    @GetMapping(path = "/list-workshop/{judulWorkshop}")
    public ModelAndView getWorkshop(@PathVariable("judulWorkshop") String judulWorkshop) {
        return workshopService.getWorkshop(judulWorkshop);
    }

    @GetMapping(path = "/poster/show/{docName}")
    public ResponseEntity<byte[]> showPoster(@PathVariable("docName") String docName) {
//        byte[] fileProposal = proposalService.downloadProposal(docName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("application/pdf"))
//                .body(fileProposal);
        return workshopService.showImage(docName);
    }
}
