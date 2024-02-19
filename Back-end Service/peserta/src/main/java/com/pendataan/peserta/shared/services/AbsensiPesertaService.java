package com.pendataan.peserta.shared.services;

import com.pendataan.peserta.entity.PesertaWorkshop;
import com.pendataan.peserta.repositories.PesertaRepository;
import com.pendataan.peserta.repositories.WorkshopRepository;
import com.pendataan.peserta.services.AbsensiService;
import com.pendataan.peserta.shared.dto.StatusPeserta;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AbsensiPesertaService {

    private static final Logger log = LoggerFactory.getLogger(AbsensiService.class);
    private PesertaRepository pesertaRepository;
    private final WorkshopRepository workshopRepository;


    public ModelAndView formAbsensi(String judulWorkshop) {
        ModelAndView mav = new ModelAndView();
        PesertaWorkshop formAbsensi = new PesertaWorkshop();
        mav.getModelMap().addAttribute("formAbsensi", formAbsensi);
        mav.setViewName("formAbsensi");
        return mav;
    }

    public ModelAndView getPeserta(String kodeUnik) {
        ModelAndView mav = new ModelAndView();
        PesertaWorkshop peserta = pesertaRepository.findByKodeUnik(kodeUnik);
        PesertaWorkshop formAbsensi = new PesertaWorkshop();
        mav.getModelMap().addAttribute("formAbsensi", formAbsensi);
        mav.getModelMap().addAttribute("peserta", peserta);
        mav.setViewName("formAbsensi");
        return mav;
    }

    public ResponseEntity<?> uploadAbsensi(String kodeUnik,
                                           PesertaWorkshop absensi,
                                           HttpServletResponse response) throws IOException {
        PesertaWorkshop absensiWorkshop = pesertaRepository.getByKodeUnik(kodeUnik);

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//        LocalDateTime parsedDateTime = LocalDateTime.parse(absensi.getTanggalWorkshop(), formatter);

        absensiWorkshop.setKesan(absensi.getKesan());
        absensiWorkshop.setPesan(absensi.getPesan());
        absensiWorkshop.setStatus(StatusPeserta.ABSEN.toString());
        absensiWorkshop.setUpdatedDate(LocalDateTime.now());

        if (absensiWorkshop != null) {
            log.info("Pesan: " + absensi.getPesan());
            log.info("Kesan: " + absensi.getKesan());
            pesertaRepository.save(absensiWorkshop);
        } else {
            return new ResponseEntity<>("Gagal Menyimpan", HttpStatus.BAD_REQUEST);
        }
        response.sendRedirect("/");
        return new ResponseEntity<>("Berhasil Input Absensi", HttpStatus.OK);
    }
}
