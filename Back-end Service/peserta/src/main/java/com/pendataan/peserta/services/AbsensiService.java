package com.pendataan.peserta.services;

import com.pendataan.peserta.entity.PesertaWorkshop;
import com.pendataan.peserta.entity.Workshop;
import com.pendataan.peserta.repositories.PesertaRepository;
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
public class AbsensiService {

    private static final Logger log = LoggerFactory.getLogger(AbsensiService.class);
    private PesertaRepository absensiRepository;
    private final WorkshopRepository workshopRepository;

    public ModelAndView getPage() {
        ModelAndView mav = new ModelAndView();
        List<Workshop> workshop = workshopRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        mav.getModelMap().addAttribute("workshop", workshop);
        mav.setViewName("absensiWorkshop");
        return mav;
    }

    public ModelAndView findAbsensi(String judulWorkshop) {
        ModelAndView mav = new ModelAndView();
        Workshop workshop = workshopRepository.findByJudulWorkshop(judulWorkshop);
        mav.getModelMap().addAttribute("workshop", workshop);
        mav.setViewName("absensiWorkshop");
        return mav;
    }

    public ResponseEntity<?> getAbsensi(String judulWorkshop) {
//        ModelAndView mav = new ModelAndView();
        try {
             List<PesertaWorkshop> absensiList = absensiRepository.findAll();
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

    public ModelAndView getAbsensiByJudul(String judulWorkshop) {
        ModelAndView mav = new ModelAndView();
        try {
            List<PesertaWorkshop> absensi = absensiRepository.getByJudulWorkshop(judulWorkshop);
            Workshop workshop = workshopRepository.getByJudulWorkshop(judulWorkshop);
            mav.getModelMap().addAttribute("workshop", workshop);
            mav.getModelMap().addAttribute("absensi", absensi);
            mav.setViewName("listAbsensi");
            return mav;
//            return new ResponseEntity<>(absensiList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return mav;
//            return new ResponseEntity<>("Gagal Menampilkan Data", HttpStatus.BAD_REQUEST);
        }
    }

    public ModelAndView editAbsensi(String judulWorkshop, Long id) {
        ModelAndView mav = new ModelAndView();
        PesertaWorkshop absensiWorkshop = absensiRepository.getById(id);
        mav.getModelMap().addAttribute("absensiWorkshop", absensiWorkshop);
        mav.setViewName("editAbsensi");
        return mav;
    }

    public ResponseEntity<?> updateAbsensi(Long id,
                                           String judulWorkshop,
                                           HttpServletResponse response,
                                           PesertaWorkshop peserta) throws IOException {

        PesertaWorkshop upload = absensiRepository.getByJudulWorkshop(id, judulWorkshop);
        upload.setNama(peserta.getNama());
        upload.setEmail(peserta.getEmail());
        upload.setAsalInstansi(peserta.getAsalInstansi());
        upload.setStatus(peserta.getStatus());
        upload.setJudulWorkshop(peserta.getJudulWorkshop());
        upload.setKesan(peserta.getKesan());
        upload.setPesan(peserta.getPesan());
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
        response.sendRedirect("/himatika/absensi");
        return new ResponseEntity<>("Berhasil Menghapus Data", HttpStatus.OK);
    }
}
