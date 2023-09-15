package com.pendataan.peserta.controllers;

import com.pendataan.peserta.entity.PendaftaranWorkshop;
import com.pendataan.peserta.services.PendaftaranService;
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
    public ResponseEntity showAllPendaftaran(String judulWorkshop) {
        return pendaftaranService.getAllPendaftaran(judulWorkshop);
    }

    @GetMapping("/pendaftaran/{workshopId}")
    public ResponseEntity<?> getPendaftaranByJudul(@PathVariable("workshopId") Long workshopId) {
        return pendaftaranService.getPendaftaranByIdWorkshop(workshopId);
    }

    @GetMapping("/pendaftaran/{workshopId}/formPendaftaran")
    public ModelAndView formPendaftaran(@PathVariable("workshopId") Long workshopId) {
        return pendaftaranService.formPendaftaran(workshopId);
    }

    @PostMapping("/pendaftaran/formPendaftaran/inputPendaftaran")
    public ResponseEntity<?> inputPendaftaran(@RequestParam("judulWorkshop") String judulWorkshop,
                                              @RequestBody PendaftaranWorkshop pendaftaran,
                                              HttpServletResponse response) {
        return pendaftaranService.uploadPendaftaran(judulWorkshop, pendaftaran, response);
    }

    @GetMapping(path = "/pendaftaran/{judulWorkshop}/editPendaftaran/{id}")
    public ModelAndView editPendaftaran(@PathVariable("judulWorkshop") String judulWorkshop,
                                        @PathVariable("id") Long id) {
        return pendaftaranService.editPendaftaran(judulWorkshop, id);
    }

    @PostMapping(path = "/pendaftaran/{judulWorkshop}/{id}")
    public @ResponseBody ResponseEntity<?> updatePendaftaran(@PathVariable("id") Long id,
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
