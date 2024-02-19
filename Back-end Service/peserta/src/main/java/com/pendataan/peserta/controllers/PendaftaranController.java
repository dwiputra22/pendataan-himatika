package com.pendataan.peserta.controllers;

import com.pendataan.peserta.entity.PesertaWorkshop;
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
    public ModelAndView showAllPendaftaran() {
        return pendaftaranService.getAllPendaftaran();
    }

    @GetMapping(path = "/pendaftaran/get")
    public ModelAndView findAbsensi(@RequestParam(value = "judulWorkshop") String judulWorkshop) {
        return pendaftaranService.findPendaftaran(judulWorkshop);
    }

    @GetMapping("/pendaftaran/{judulWorkshop}")
    public ModelAndView getPendaftaranByJudul(@PathVariable("judulWorkshop") String judulWorkshop) {
        return pendaftaranService.getPendaftaran(judulWorkshop);
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
                                                             PesertaWorkshop pendaftaran) throws IOException {
        return pendaftaranService.updatePendaftaran(id, judulWorkshop, response, pendaftaran);
    }

    @GetMapping(path = "/pendaftaran/{judulWorkshop}/deletePendaftaran/{id}")
    public @ResponseBody ResponseEntity<?> deletePendaftaran(@PathVariable("id") Long id,
                                                             @PathVariable("judulWorkshop") String judulWorkshop,
                                                             HttpServletResponse response) throws IOException {
        return pendaftaranService.deletedPendaftaran(id, judulWorkshop, response);
    }
}
