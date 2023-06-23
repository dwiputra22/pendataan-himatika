package com.pendataan.peserta.services;

import com.pendataan.peserta.entity.AbsensiWorkshop;
import com.pendataan.peserta.repositories.AbsensiRepository;
import com.pendataan.workshop.entity.ProposalWorkshop;
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
import java.util.List;

@Service
@AllArgsConstructor
public class AbsensiService {

    private static final Logger log = LoggerFactory.getLogger(AbsensiService.class);
    private AbsensiRepository absensiRepository;

    public ResponseEntity<?> getAbsensi(String judulWorkshop, ProposalWorkshop proposalWorkshop) {
//        ModelAndView mav = new ModelAndView();
        try {
             List<AbsensiWorkshop> absensiList = absensiRepository.findAll();
//             List<ProposalWorkshop> judul = propWorkshopRepository.getByJudulWorkshop(proposalWorkshop.getJudulWorkshop());
//            mav.getModelMap().addAttribute("absensiList", absensiList);
//            mav.setViewName("absensi");
//            return mav;
            return new ResponseEntity<>(absensiList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
//            return mav;
            return new ResponseEntity<>("Gagal Menampilkan Data", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getAbsensiByJudul(String judulWorkshop) {
//        ModelAndView mav = new ModelAndView();
        try {
            List<AbsensiWorkshop> absensiList = absensiRepository.getByJudulWorkshop(judulWorkshop);
//            mav.getModelMap().addAttribute("absensiList", absensiList);
//            mav.setViewName("absensiWorkshop");
//            return mav;
            return new ResponseEntity<>(absensiList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
//            return mav;
            return new ResponseEntity<>("Gagal Menampilkan Data", HttpStatus.BAD_REQUEST);
        }
    }

    public ModelAndView formAbsensi(AbsensiWorkshop absensi, String judulWorkshop) {
        ModelAndView mav = new ModelAndView();
//        ProposalWorkshop judul = absensiRepository.findByJudulWorkshop(judulWorkshop);
        AbsensiWorkshop formAbsensi = new AbsensiWorkshop();
        mav.getModelMap().addAttribute("formAbsensi", formAbsensi);
        mav.setViewName("formAbsensi");
        return mav;
    }

    public ResponseEntity<?> uploadAbsensi(String judulWorkshop, AbsensiWorkshop absensi) {
        AbsensiWorkshop absensiWorkshop = new AbsensiWorkshop();

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//        LocalDateTime parsedDateTime = LocalDateTime.parse(absensi.getTanggalWorkshop(), formatter);

        absensiWorkshop.setNim(absensi.getNim());
        absensiWorkshop.setNama(absensi.getNama());
        absensiWorkshop.setEmail(absensi.getEmail());
        absensiWorkshop.setProgramStudi(absensi.getProgramStudi());
        absensiWorkshop.setThnAngkatan(absensi.getThnAngkatan());
        absensiWorkshop.setTanggalWorkshop(absensi.getTanggalWorkshop());
        absensiWorkshop.setJudulWorkshop(absensi.getJudulWorkshop());
        absensiWorkshop.setKesan(absensi.getKesan());
        absensiWorkshop.setSaran(absensi.getSaran());

        if (absensiWorkshop != null) {
            log.info("nim: " + absensi.getNim());
            log.info("nama: " + absensi.getNama());
            log.info("email: " +absensi.getEmail());
            log.info("Program Studi: " + absensi.getProgramStudi());
            log.info("Tahun Angkatan: " + absensi.getThnAngkatan());
            log.info("judulWorkshop: " + absensi.getJudulWorkshop());
            log.info("tanggalWorkshop: " + absensi.getTanggalWorkshop());
            log.info("saran: " + absensi.getSaran());
            log.info("Kesan: " + absensi.getKesan());
            absensiRepository.save(absensiWorkshop);
        } else {
            return new ResponseEntity<>("Gagal Menyimpan", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Berhasil Input Absensi", HttpStatus.OK);
    }

    public ModelAndView editAbsensi(String judulWorkshop, Long id) {
        ModelAndView mav = new ModelAndView();
        AbsensiWorkshop absensiWorkshop = absensiRepository.getById(id);
        mav.getModelMap().addAttribute("absensiWorkshop", absensiWorkshop);
        mav.setViewName("editAbsensi");
        return mav;
    }

    public ResponseEntity<?> updateAbsensi(Long id,
                                           String judulWorkshop,
                                           HttpServletResponse response,
                                           AbsensiWorkshop absensi) throws IOException {

        AbsensiWorkshop upload = absensiRepository.getByJudulWorkshop(id, judulWorkshop);
        upload.setNim(absensi.getNim());
        upload.setNama(absensi.getNama());
        upload.setProgramStudi(absensi.getProgramStudi());
        upload.setThnAngkatan(absensi.getThnAngkatan());
        upload.setTanggalWorkshop(absensi.getTanggalWorkshop());
        upload.setJudulWorkshop(absensi.getJudulWorkshop());
        upload.setKesan(absensi.getKesan());
        upload.setSaran(absensi.getSaran());
        upload.setUpdatedDate(LocalDateTime.now());
        absensiRepository.save(upload);
        response.sendRedirect("/himatika/proposal-workshop");
        return new ResponseEntity<>( "Berhasil Memperbarui Data",HttpStatus.OK);
    }

    public ResponseEntity<?> deletedAbsensi(Long id,
                                             String judulWorkshop,
                                             HttpServletResponse response) throws IOException {
        try {
            if (judulWorkshop != null) {
                absensiRepository.deleteByJudulWorkshop(id, judulWorkshop);
//                response.sendRedirect("/himatika/proposal-workshop");
                return new ResponseEntity<>("Success Deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed Delete", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        response.sendRedirect("/himatika/proposal-workshop");
        return new ResponseEntity<>("Berhasil Menghapus Data", HttpStatus.OK);
    }
}
