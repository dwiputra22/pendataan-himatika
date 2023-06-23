package com.pendataan.peserta.controllers;

import com.pendataan.peserta.entity.PendaftaranWorkshop;
import com.pendataan.peserta.services.PendaftaranService;
import com.pendataan.workshop.entity.ProposalWorkshop;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/himatika")
@AllArgsConstructor
public class PendaftaranController {

    private PendaftaranService pendaftaranService;

    @GetMapping("/pendaftaran")
    public ResponseEntity getPendaftaran(String judulWorkshop, ProposalWorkshop proposalWorkshop) {
        return pendaftaranService.getPendaftaran(judulWorkshop, proposalWorkshop);
    }

    @GetMapping("/pendaftaran/{judulWorkshop}")
    public ResponseEntity<?> getPendaftaranByJudul(@PathVariable("judulWorkshop") String judulWorkshop) {
        return pendaftaranService.getPendaftaranByJudul(judulWorkshop);
    }

    @GetMapping("/pendaftaran/{judulWorkshop}/formAbsensi")
    public ModelAndView formPendaftaran(PendaftaranWorkshop pendaftaran,
                                        @PathVariable("judulWorkshop") String judulWorkshop) {
        return pendaftaranService.formPendaftaran(pendaftaran, judulWorkshop);
    }

    @PostMapping("/pendaftaran/{judulWorkshop}/formPendaftaran/inputPendaftaran")
    public ResponseEntity<?> inputPendaftaran(@PathVariable("judulWorkshop") String judulWorkshop,
                                          @RequestBody PendaftaranWorkshop pendaftaran) {
        return pendaftaranService.uploadPendaftaran(judulWorkshop, pendaftaran);
    }

    @GetMapping(path = "/pendaftaran/{judulWorkshop}/editPendaftaran/{id}")
    public ModelAndView editPendaftaran(@PathVariable("judulWorkshop") String judulWorkshop,
                                    @PathVariable("id") Long id) {
        return pendaftaranService.editPendaftaran(judulWorkshop, id);
    }

    @PostMapping(path = "/pendaftaran/{judulWorkshop}/{id}")
    public @ResponseBody ResponseEntity<?> updatePendaftaran(
            @PathVariable("id") Long id,
            @PathVariable("judulWorkshop") String judulWorkshop,
            HttpServletResponse response,
            PendaftaranWorkshop pendaftaran) throws IOException {
        return pendaftaranService.updatePendaftaran(id, judulWorkshop, response, pendaftaran);
    }

    @GetMapping(path = "/pendaftaran/{judulWorkshop}/deletePendaftaran/{id}")
    public @ResponseBody ResponseEntity<?> deletePendaftaran(@PathVariable("id") Long id,
                                                         @PathVariable("judulWorkshop") String judulWorkshop,
                                                         HttpServletResponse response) throws IOException {
        return pendaftaranService.deletedPendaftaran(id, judulWorkshop, response);
    }
}
