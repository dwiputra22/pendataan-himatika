package com.pendataan.peserta.controllers;

import com.pendataan.peserta.entity.AbsensiWorkshop;
import com.pendataan.peserta.services.AbsensiService;
import com.pendataan.workshop.entity.ProposalWorkshop;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/himatika")
public class AbsensiController {

    private AbsensiService absensiService;

    @GetMapping("/absensi")
    public ResponseEntity getAbsensi(String judulWorkshop, ProposalWorkshop proposalWorkshop) {
        return absensiService.getAbsensi(judulWorkshop, proposalWorkshop);
    }

    @GetMapping("/absensi/{judulWorkshop}")
    public ResponseEntity<?> getAbsensiByJudul(@PathVariable("judulWorkshop") String judulWorkshop) {
        return absensiService.getAbsensiByJudul(judulWorkshop);
    }

    @GetMapping("/absensi/{judulWorkshop}/formAbsensi")
    public ModelAndView formAbsensi(AbsensiWorkshop absensi,
                                    @PathVariable("judulWorkshop") String judulWorkshop) {
        return absensiService.formAbsensi(absensi, judulWorkshop);
    }

    @PostMapping("/absensi/{judulWorkshop}/formAbsensi/inputAbsensi")
    public ResponseEntity<?> inputAbsensi(@PathVariable("judulWorkshop") String judulWorkshop,
                                          @RequestBody AbsensiWorkshop absensi) {
        return absensiService.uploadAbsensi(judulWorkshop, absensi);
    }

    @GetMapping(path = "/absensi/{judulWorkshop}/editAbsensi/{id}")
    public ModelAndView editAbsensi(@PathVariable("judulWorkshop") String judulWorkshop,
                                    @PathVariable("id") Long id) {
        return absensiService.editAbsensi(judulWorkshop, id);
    }

    @PostMapping(path = "/absensi/{judulWorkshop}/{id}")
    public @ResponseBody ResponseEntity<?> updateAbsensi(
            @PathVariable("id") Long id,
            @PathVariable("judulWorkshop") String judulWorkshop,
            HttpServletResponse response,
            AbsensiWorkshop absensi) throws IOException {
        return absensiService.updateAbsensi(id, judulWorkshop, response, absensi);
    }

    @GetMapping(path = "/absensi/{judulWorkshop}/deleteAbsensi/{id}")
    public @ResponseBody ResponseEntity<?> deleteAbsensi(@PathVariable("id") Long id,
                                                          @PathVariable("judulWorkshop") String judulWorkshop,
                                                          HttpServletResponse response) throws IOException {
        return absensiService.deletedAbsensi(id, judulWorkshop, response);
    }
}
