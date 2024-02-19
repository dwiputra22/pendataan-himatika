package com.pendataan.peserta.services;


import com.pendataan.peserta.entity.PesertaWorkshop;
import com.pendataan.peserta.repositories.PesertaRepository;
import com.pendataan.peserta.entity.Workshop;
import com.pendataan.peserta.repositories.WorkshopRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
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
    private final PesertaRepository pesertaRepository;
    private final WorkshopRepository workshopRepository;


    public ModelAndView getAllPendaftaran() {
        ModelAndView mav = new ModelAndView();
        try {
            List<Workshop> workshop = workshopRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
//            List<PendaftaranWorkshop> pendaftaranList = pendaftaranRepository.findAll();
//             List<ProposalWorkshop> judul = propWorkshopRepository.getByJudulWorkshop(proposalWorkshop.getJudulWorkshop());
            mav.getModelMap().addAttribute("workshop", workshop);
            mav.setViewName("pendaftaranWorkshop");
            return mav;
//            return new ResponseEntity<>(workshop, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return mav;
//            return new ResponseEntity<>("Gagal Menampilkan Data", HttpStatus.BAD_REQUEST);
        }
    }

    public ModelAndView findPendaftaran(String judulWorkshop) {
        ModelAndView mav = new ModelAndView();
        Workshop workshop = workshopRepository.findByJudulWorkshop(judulWorkshop);
        mav.getModelMap().addAttribute("workshop", workshop);
        mav.setViewName("pendaftaranWorkshop");
        return mav;
    }

    public ModelAndView getPendaftaran(String judulWorkshop) {
        ModelAndView mav = new ModelAndView();
        try {
            List<PesertaWorkshop> pendaftaran = pesertaRepository.getByJudulWorkshop(judulWorkshop);
            Workshop workshop = workshopRepository.getByJudulWorkshop(judulWorkshop);
            mav.getModelMap().addAttribute("workshop", workshop);
            mav.getModelMap().addAttribute("pendaftaran", pendaftaran);
            mav.setViewName("listPendaftaran");
            return mav;
//            return new ResponseEntity<>(pendaftaran, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return mav;
//            return new ResponseEntity<>("Gagal Menampilkan Data", HttpStatus.BAD_REQUEST);
        }
    }

    public ModelAndView editPendaftaran(String judulWorkshop, Long id) {
        ModelAndView mav = new ModelAndView();
        PesertaWorkshop pendaftaranWorkshop = pesertaRepository.getById(id);
        mav.getModelMap().addAttribute("pendaftaranWorkshop", pendaftaranWorkshop);
        mav.setViewName("editPendaftaran");
        return mav;
    }

    public ResponseEntity<?> updatePendaftaran(Long id,
                                           String judulWorkshop,
                                           HttpServletResponse response,
                                           PesertaWorkshop pendaftaran) throws IOException {

        PesertaWorkshop update = pesertaRepository.getByJudulWorkshop(id, judulWorkshop);
        update.setNama(pendaftaran.getNama());
        update.setEmail(pendaftaran.getEmail());
        update.setAsalInstansi(pendaftaran.getAsalInstansi());
        update.setPekerjaan(pendaftaran.getPekerjaan());
        update.setJudulWorkshop(pendaftaran.getJudulWorkshop());
        update.setAsalInfo(pendaftaran.getAsalInfo());
        update.setUpdatedDate(LocalDateTime.now());
        pesertaRepository.save(update);
        response.sendRedirect("/himatika/pendaftaran");
        return new ResponseEntity<>( "Berhasil Memperbarui Data",HttpStatus.OK);
    }

    public ResponseEntity<?> deletedPendaftaran(Long id,
                                            String judulWorkshop,
                                            HttpServletResponse response) throws IOException {
        try {
            if (judulWorkshop != null) {
                pesertaRepository.deleteByJudulWorkshop(id, judulWorkshop);
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
