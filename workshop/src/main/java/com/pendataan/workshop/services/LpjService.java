package com.pendataan.workshop.services;

import com.pendataan.workshop.entity.pendataan.LpjWorkshop;
import com.pendataan.workshop.repositories.LpjWorkshopRepository;
import com.pendataan.workshop.utils.FileUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LpjService {

    private static final Logger log = LoggerFactory.getLogger(ProposalService.class);
    public static String uploadDirectory = System.getProperty("user.dir") + "/files/lpj-workshop";
    private final LpjWorkshopRepository lpjWorkshopRepository;

    public ModelAndView getAllLpj() {
        ModelAndView mav = new ModelAndView();
        List<LpjWorkshop> lpjList = lpjWorkshopRepository.findAll();
        mav.getModelMap().addAttribute("lpjList", lpjList);
        mav.setViewName("lpjWorkshop");
        return mav;
    }

    public ModelAndView formLpj(LpjWorkshop lpjWorkshop) {
        ModelAndView mav = new ModelAndView();
        LpjWorkshop lpjInput = new LpjWorkshop();

        lpjInput.setNim(lpjWorkshop.getNim());
        lpjInput.setNama(lpjWorkshop.getNama());
        lpjInput.setJudulWorkshop(lpjWorkshop.getJudulWorkshop());
        lpjInput.setPembicara(lpjWorkshop.getPembicara());
        lpjInput.setTanggalWorkshop(lpjWorkshop.getTanggalWorkshop());
        lpjInput.setSuratLpj(lpjWorkshop.getSuratLpj());

        mav.getModelMap().addAttribute("lpjInput", lpjInput);
        mav.setViewName("formLpj");
        return mav;
    }

    public ResponseEntity<?> uploadlpj(LpjWorkshop lpjWorkshop,
                                       @RequestParam("suratLpj") MultipartFile suratLpj,
                                       HttpServletResponse response) {
        return new ResponseEntity<>("xxxxxx", HttpStatus.OK);
    }

    public byte[] downloadlpj(String docName) {
        Optional<LpjWorkshop> dbFile = lpjWorkshopRepository.findByDocName(docName);
        byte[] file = FileUtils.decompressImage(dbFile.get().getSuratLpj());
        return file;
    }

    public ResponseEntity<?> deletedWorkshop(Long id,
                                             String judulWorkshop,
                                             String docName,
                                             HttpServletResponse response) {
        return new ResponseEntity<>("xxxxxx", HttpStatus.OK);
    }
}
