package com.pendataan.peserta.controllers;

import com.pendataan.peserta.entity.PesertaWorkshop;
import com.pendataan.peserta.services.AbsensiService;
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
    public ModelAndView getPage() {
        return absensiService.getPage();
    }

    @GetMapping(path = "/absensi/get")
    public ModelAndView findAbsensi(@RequestParam(value = "judulWorkshop") String judulWorkshop) {
        return absensiService.findAbsensi(judulWorkshop);
    }

    @GetMapping("/absensiDetail")
    public ResponseEntity getAbsensi(String judulWorkshop) {
        return absensiService.getAbsensi(judulWorkshop);
    }

    @GetMapping("/absensi/{judulWorkshop}")
    public ModelAndView getAbsensiByJudul(@PathVariable("judulWorkshop") String judulWorkshop) {
        return absensiService.getAbsensiByJudul(judulWorkshop);
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
            PesertaWorkshop peserta) throws IOException {
        return absensiService.updateAbsensi(id, judulWorkshop, response, peserta);
    }

    @GetMapping(path = "/absensi/{judulWorkshop}/deleteAbsensi/{id}")
    public @ResponseBody ResponseEntity<?> deleteAbsensi(@PathVariable("id") Long id,
                                                          @PathVariable("judulWorkshop") String judulWorkshop,
                                                          HttpServletResponse response) throws IOException {
        return absensiService.deletedAbsensi(id, judulWorkshop, response);
    }
}
