package com.pendataan.peserta.shared.controller;

import com.pendataan.peserta.entity.PesertaWorkshop;
import com.pendataan.peserta.shared.services.PendaftaranPesertaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class PendaftaranPesertaController {

    private PendaftaranPesertaService pendaftaranPesertaService;

    @GetMapping("/pendaftaran/formPendaftaran")
    public ModelAndView formPendaftaran() {
        return pendaftaranPesertaService.formPendaftaran();
    }

    @PostMapping("/pendaftaran/formPendaftaran/inputPendaftaran")
    public ResponseEntity<?> inputPendaftaran(PesertaWorkshop pendaftaran,
                                              HttpServletResponse response) throws IOException {
        return pendaftaranPesertaService.uploadPendaftaran(pendaftaran, response);
    }
}
