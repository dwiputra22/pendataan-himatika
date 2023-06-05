package com.pendataan.workshop.controllers;

import com.pendataan.workshop.entity.pendataan.LpjWorkshop;
import com.pendataan.workshop.entity.pendataan.ProposalWorkshop;
import com.pendataan.workshop.services.LpjService;
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
public class LpjController {
    private final LpjService lpjService;
    private static final Logger log = LoggerFactory.getLogger(ProposalController.class);

    @GetMapping(path = "/lpj-workshop")
    public ModelAndView showAllLpj() {
        return lpjService.getAllLpj();
    }

    @GetMapping("/lpj-workshop/input")
    public ModelAndView formInputLpj(@ModelAttribute("lpjWorkshop") LpjWorkshop lpjWorkshop)
            throws IOException {
        return lpjService.formLpj(lpjWorkshop);
    }

    @PostMapping(path = "/lpj-workshop/input/upload")
    public @ResponseBody ResponseEntity<?> uploadlpj(LpjWorkshop lpjWorkshop,
                                                      @RequestParam("suratLpj") MultipartFile suratLpj,
                                                      HttpServletResponse response)
            throws IOException {
        return lpjService.uploadlpj(lpjWorkshop, suratLpj, response);
    }

    @GetMapping(path = "/lpj-workkshop/{docName}")
    public ResponseEntity<?> downloadFile(@PathVariable String docName) {
        byte[] fileLpj = lpjService.downloadlpj(docName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/pdf"))
                .body(fileLpj);
    }

    @GetMapping(path = "/lpj-workshop/deleteWorkshop/{id}/{judulWorkshop}")
    public @ResponseBody ResponseEntity<?> deleteWorkshop(@PathVariable("id") Long id,
                                                          @PathVariable("judulWorkshop") String judulWorkshop,
                                                          String docName,
                                                          HttpServletResponse response) {
        return lpjService.deletedWorkshop(id, judulWorkshop, docName, response);
    }
}
