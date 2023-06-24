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

    @GetMapping(path = "/lpj-workshop/{workshop_id}")
    public ModelAndView showLpj(@PathVariable() Long workshopId) {
        return lpjService.getLpj(workshopId);
    }

    @GetMapping("/lpj-workshop/{workshop_id}/input")
    public ModelAndView formInputLpj(@PathVariable("workshop_id") Long workshopId,
                                     @ModelAttribute("lpjWorkshop") LpjWorkshop lpjWorkshop)
            throws IOException {
        return lpjService.formLpj(workshopId, lpjWorkshop);
    }

    @PostMapping(path = "/lpj-workshop/{workshop_id}/input/upload")
    public @ResponseBody ResponseEntity<?> uploadlpj(@PathVariable("workshop_id") Long workshopId,
                                                     LpjWorkshop lpjWorkshop,
                                                     @RequestParam("suratLpj") MultipartFile suratLpj,
                                                     HttpServletResponse response)
            throws IOException {
        return lpjService.uploadlpj(workshopId, lpjWorkshop, suratLpj, response);
    }

    @GetMapping(path = "/lpj-workshop/{id}/download/{docName}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") Long id,
                                          @PathVariable String docName) throws IOException {
//        ResponseEntity<Resource> fileLpj = lpjService.downloadlpj(docName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(fileLpj.getBody());
        return lpjService.downloadlpj(id, docName);
    }

    @GetMapping(path = "/lpj-workshop/{workshop_id}/deleteLpj")
    public @ResponseBody ResponseEntity<?> deleteLpj(@PathVariable("id") Long id,
                                                     @PathVariable("workshop_id") Long workshopId,
                                                     String docName,
                                                     HttpServletResponse response) {
        return lpjService.deletedLpj(id, workshopId, docName, response);
    }

    @GetMapping(path = "/lpj-workshop/{workshop_id}/editLpj")
    public ModelAndView editLpj(@PathVariable("workshop_id") Long workshopId) {
        return lpjService.editLpj(workshopId);
    }

    @PostMapping(path = "/lpj-workshop/{workshop_id}/updateLpj/{id}")
    public @ResponseBody ResponseEntity<?> updateLpj(@PathVariable("workshop_id") Long workshopId,
                                                     @PathVariable("id") Long id,
                                                     @RequestParam("suratLpj") MultipartFile suratLpj,
                                                     HttpServletResponse response,
                                                     LpjWorkshop lpjWorkshop) throws IOException {
        return lpjService.updateLpj(workshopId, id, suratLpj, response, lpjWorkshop);
    }
}
