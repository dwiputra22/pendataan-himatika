package com.pendataan.workshop.controllers;

import com.pendataan.workshop.entity.DokumentasiWorkshop;
import com.pendataan.workshop.services.DokumentasiService;
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
@RequestMapping("/himatika")
public class DokumentasiController {

    private final DokumentasiService dokumentasiService;
    private static final Logger log = LoggerFactory.getLogger(DokumentasiController.class);

    @GetMapping("/dokumentasi/{judulWorkshop}/input-dokumentasi")
    public ModelAndView formProposal(@PathVariable("judulWorkshop") String judulWorkshop)
            throws IOException {
        return dokumentasiService.formDokumentasi(judulWorkshop);
    }

    @RequestMapping("/dokumentasi/{judulWorkshop}/input-dokumentasi/upload")
    public ResponseEntity<?> uploadDokumen(DokumentasiWorkshop dokumentasiWorkshop,
                                           @RequestParam("file") MultipartFile fileDokumentasi,
                                           @PathVariable("judulWorkshop") String judulWorkshop,
                                           HttpServletResponse response)
            throws IOException {
        return dokumentasiService.upload(dokumentasiWorkshop, fileDokumentasi, judulWorkshop, response);
    }

    @RequestMapping("/dokumentasi/{judulWorkshop}/edit-dokumentasi")
    public ModelAndView editDokumentasi(@PathVariable("judulWorkshop") String judulWorkshop) {
        return dokumentasiService.edit(judulWorkshop);
    }

    @RequestMapping("/dokumentasi/{judulWorkshop}/edit-dokumentasi/update")
    public ResponseEntity<?> updateDokumentasi() {
        return dokumentasiService.update();
    }

    @RequestMapping("/dokumentasi/{judulWorkshop}/delete-dokumentasi")
    public ResponseEntity<?> deleteDokumentasi() {
        return dokumentasiService.delete();
    }
}
