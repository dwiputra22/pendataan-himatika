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


    @GetMapping(path = "/dokumentasi")
    public ModelAndView showAllDokumentasi() {
        return dokumentasiService.getAllDokumentasi();
    }

    @GetMapping(path = "/dokumentasi/{workshopId}")
    public ModelAndView showProposal(@PathVariable("workshopId") Long workshopId) {
        return dokumentasiService.getDokumentasi(workshopId);
    }

    @GetMapping("/dokumentasi/{workshopId}/input-dokumentasi")
    public ModelAndView formProposal(@PathVariable("workshopId") Long workshopId)
            throws IOException {
        return dokumentasiService.formDokumentasi(workshopId);
    }

    @RequestMapping("/dokumentasi/{workshopId}/input-dokumentasi/upload")
    public ResponseEntity<?> uploadDokumen(DokumentasiWorkshop dokumentasiWorkshop,
                                           @RequestParam("file") MultipartFile fileDokumentasi,
                                           @PathVariable("workshopId") Long workshopId,
                                           HttpServletResponse response)
            throws IOException {
        return dokumentasiService.upload(dokumentasiWorkshop, fileDokumentasi, workshopId, response);
    }

    @GetMapping(path = "/dokumentasi/{id}/download/{docName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") Long id,
                                               @PathVariable("docName") String docName) {
//        byte[] fileProposal = proposalService.downloadProposal(docName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("application/pdf"))
//                .body(fileProposal);
        return dokumentasiService.downloadDokumentasi(id, docName);
    }

    @RequestMapping("/dokumentasi/edit-dokumentasi/{id}")
    public ModelAndView editDokumentasi(@PathVariable("id") Long id) {
        return dokumentasiService.edit(id);
    }

    @RequestMapping("/dokumentasi/edit-dokumentasi/{id}/update")
    public ResponseEntity<?> updateDokumentasi(@PathVariable("id") Long id,
                                               DokumentasiWorkshop dok,
                                               @RequestParam("file") MultipartFile fileDokumentasi,
                                               HttpServletResponse response) throws IOException {
        return dokumentasiService.update(id, dok, fileDokumentasi, response);
    }

    @RequestMapping("/dokumentasi/delete-dokumentasi/{id}")
    public ResponseEntity<?> deleteDokumentasi(@PathVariable("id") Long id,
                                               String fileName,
                                               HttpServletResponse response) {
        return dokumentasiService.delete(id, fileName, response);
    }
}
