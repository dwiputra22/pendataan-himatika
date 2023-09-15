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

    @GetMapping(path = "/lpj-workshop/{workshopid}")
    public ModelAndView showLpj(@PathVariable("workshopId") Long workshopId) {
        return lpjService.getLpj(workshopId);
    }

    @GetMapping("/lpj-workshop/{workshopId}/input")
    public ModelAndView formInputLpj(@PathVariable("workshopId") Long workshopId,
                                     @ModelAttribute("lpjWorkshop") LpjWorkshop lpjWorkshop)
            throws IOException {
        return lpjService.formLpj(workshopId, lpjWorkshop);
    }

    @PostMapping(path = "/lpj-workshop/{workshopId}/input/upload")
    public @ResponseBody ResponseEntity<?> uploadlpj(@PathVariable("workshopId") Long workshopId,
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

    @GetMapping(path = "/lpj-workshop/deleteLpj/{id}")
    public @ResponseBody ResponseEntity<?> deleteLpj(@PathVariable("id") Long id,
                                                     String docName,
                                                     HttpServletResponse response) {
        return lpjService.deletedLpj(id, docName, response);
    }

    @GetMapping(path = "/lpj-workshop/editLpj/{id}")
    public ModelAndView editLpj(@PathVariable("id") Long id) {
        return lpjService.editLpj(id);
    }

    @PostMapping(path = "/lpj-workshop/editLpj/{id}/update")
    public @ResponseBody ResponseEntity<?> updateLpj(@PathVariable("id") Long id,
                                                     @RequestParam("suratLpj") MultipartFile suratLpj,
                                                     HttpServletResponse response,
                                                     LpjWorkshop lpjWorkshop) throws IOException {
        return lpjService.updateLpj(id, suratLpj, response, lpjWorkshop);
    }
}
