package com.pendataan.peserta.services;


import com.pendataan.peserta.entity.PendaftaranWorkshop;
import com.pendataan.peserta.repositories.PendaftaranRepository;
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
public class PendaftaranService {

    private static final Logger log = LoggerFactory.getLogger(AbsensiService.class);
    private final PendaftaranRepository pendaftaranRepository;


    public ResponseEntity<?> getPendaftaran(String judulWorkshop, ProposalWorkshop proposalWorkshop) {
//        ModelAndView mav = new ModelAndView();
        try {
            List<PendaftaranWorkshop> pendaftaranList = pendaftaranRepository.findAll();
//             List<ProposalWorkshop> judul = propWorkshopRepository.getByJudulWorkshop(proposalWorkshop.getJudulWorkshop());
//            mav.getModelMap().addAttribute("absensiList", absensiList);
//            mav.setViewName("absensi");
//            return mav;
            return new ResponseEntity<>(pendaftaranList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
//            return mav;
            return new ResponseEntity<>("Gagal Menampilkan Data", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getPendaftaranByJudul(String judulWorkshop) {
//        ModelAndView mav = new ModelAndView();
        try {
            List<PendaftaranWorkshop> pendaftaranList = pendaftaranRepository.getByJudulWorkshop(judulWorkshop);
//            mav.getModelMap().addAttribute("absensiList", absensiList);
//            mav.setViewName("absensiWorkshop");
//            return mav;
            return new ResponseEntity<>(pendaftaranList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
//            return mav;
            return new ResponseEntity<>("Gagal Menampilkan Data", HttpStatus.BAD_REQUEST);
        }
    }

    public ModelAndView formPendaftaran(PendaftaranWorkshop pendaftaran, String judulWorkshop) {
        ModelAndView mav = new ModelAndView();
//        ProposalWorkshop judul = absensiRepository.findByJudulWorkshop(judulWorkshop);
        PendaftaranWorkshop formPendaftaran = new PendaftaranWorkshop();
        mav.getModelMap().addAttribute("formPendaftaran", formPendaftaran);
        mav.setViewName("formPendaftaran");
        return mav;
    }

    public ResponseEntity<?> uploadPendaftaran(String judulWorkshop, PendaftaranWorkshop pendaftaran) {
        PendaftaranWorkshop pendaftaranWorkshop = new PendaftaranWorkshop();

        pendaftaranWorkshop.setNim(pendaftaran.getNim());
        pendaftaranWorkshop.setNama(pendaftaran.getNama());
        pendaftaranWorkshop.setEmail(pendaftaran.getEmail());
        pendaftaranWorkshop.setProgramStudi(pendaftaran.getProgramStudi());
        pendaftaranWorkshop.setThnAngkatan(pendaftaran.getThnAngkatan());
        pendaftaranWorkshop.setJudulWorkshop(pendaftaran.getJudulWorkshop());

        if (pendaftaranWorkshop != null) {
            log.info("nim: " + pendaftaran.getNim());
            log.info("nama: " + pendaftaran.getNama());
            log.info("email: " +pendaftaran.getEmail());
            log.info("Program Studi: " + pendaftaran.getProgramStudi());
            log.info("Tahun Angkatan: " + pendaftaran.getThnAngkatan());
            log.info("judulWorkshop: " + pendaftaran.getJudulWorkshop());
            pendaftaranRepository.save(pendaftaranWorkshop);
        } else {
            return new ResponseEntity<>("Gagal Menyimpan", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Berhasil Input Absensi", HttpStatus.OK);
    }

    public ModelAndView editPendaftaran(String judulWorkshop, Long id) {
        ModelAndView mav = new ModelAndView();
        PendaftaranWorkshop pendaftaranWorkshop = pendaftaranRepository.getById(id);
        mav.getModelMap().addAttribute("pendaftaranWorkshop", pendaftaranWorkshop);
        mav.setViewName("editPendaftaran");
        return mav;
    }

    public ResponseEntity<?> updatePendaftaran(Long id,
                                           String judulWorkshop,
                                           HttpServletResponse response,
                                           PendaftaranWorkshop pendaftaran) throws IOException {

        PendaftaranWorkshop update = pendaftaranRepository.getByJudulWorkshop(id, judulWorkshop);
        update.setNim(pendaftaran.getNim());
        update.setNama(pendaftaran.getNama());
        update.setProgramStudi(pendaftaran.getProgramStudi());
        update.setThnAngkatan(pendaftaran.getThnAngkatan());
        update.setJudulWorkshop(pendaftaran.getJudulWorkshop());
        update.setUpdatedDate(LocalDateTime.now());
        pendaftaranRepository.save(update);
        response.sendRedirect("/himatika/pendaftaran");
        return new ResponseEntity<>( "Berhasil Memperbarui Data",HttpStatus.OK);
    }

    public ResponseEntity<?> deletedPendaftaran(Long id,
                                            String judulWorkshop,
                                            HttpServletResponse response) throws IOException {
        try {
            if (judulWorkshop != null) {
                pendaftaranRepository.deleteByJudulWorkshop(id, judulWorkshop);
//                response.sendRedirect("/himatika/proposal-workshop");
                return new ResponseEntity<>("Success Deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed Delete", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        response.sendRedirect("/himatika/pendaftaran");
        return new ResponseEntity<>("Berhasil Menghapus Data", HttpStatus.OK);
    }
}
