package com.pendataan.peserta.shared.controller;

import com.pendataan.peserta.entity.PesertaWorkshop;
import com.pendataan.peserta.shared.services.AbsensiPesertaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("")
public class AbsensiPesertaController {

    private AbsensiPesertaService absensiPesertaService;

    @GetMapping("/absensi/formAbsensi")
    public ModelAndView formAbsensi(String judulWorkshop) {
        return absensiPesertaService.formAbsensi(judulWorkshop);
    }

    @GetMapping("/absensi/formAbsensi/get")
    public ModelAndView getPeserta(@RequestParam(value = "kodeUnik") String kodeUnik) {
        return absensiPesertaService.getPeserta(kodeUnik);
    }

    @PostMapping("/absensi/formAbsensi/inputAbsensi/{kodeUnik}")
    public ResponseEntity<?> inputAbsensi(@PathVariable(value = "kodeUnik") String kodeUnik,
                                          PesertaWorkshop absensi,
                                          HttpServletResponse response) throws IOException {
        return absensiPesertaService.uploadAbsensi(kodeUnik, absensi, response);
    }
}
