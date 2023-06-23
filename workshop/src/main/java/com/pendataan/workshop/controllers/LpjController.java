package com.pendataan.workshop.controllers;

import com.pendataan.workshop.entity.LpjWorkshop;
import com.pendataan.workshop.services.LpjService;
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
public class LpjController {

    private final LpjService lpjService;
    private static final Logger log = LoggerFactory.getLogger(LpjController.class);

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

    @GetMapping(path = "/lpj-workshop/{docName}", produces = "application/pdf")
    public ResponseEntity<?> downloadFile(@PathVariable String docName) throws IOException {
//        ResponseEntity<Resource> fileLpj = lpjService.downloadlpj(docName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(fileLpj.getBody());
        return lpjService.downloadlpj(docName);
    }

    @GetMapping(path = "/lpj-workshop/deleteWorkshop/{id}/{judulWorkshop}")
    public @ResponseBody ResponseEntity<?> deleteLpj(@PathVariable("id") Long id,
                                                     @PathVariable("judulWorkshop") String judulWorkshop,
                                                     String docName,
                                                     HttpServletResponse response) {
        return lpjService.deletedLpj(id, judulWorkshop, docName, response);
    }

    @GetMapping(path = "/lpj-workshop/editLpj/{id}")
    public ModelAndView editLpj(@PathVariable("id") Long id) {
        return lpjService.editLpj(id);
    }

    @PostMapping(path = "/lpj-workshop/{id}")
    public @ResponseBody ResponseEntity<?> updateLpj(@PathVariable("id") Long id,
                                                     @RequestParam("suratLpj") MultipartFile suratLpj,
                                                     HttpServletResponse response,
                                                     LpjWorkshop lpjWorkshop) throws IOException {
        return lpjService.updateLpj(id, suratLpj, response, lpjWorkshop);
    }
}
