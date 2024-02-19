package com.pendataan.peserta.shared.services;

import com.pendataan.peserta.entity.Workshop;
import com.pendataan.peserta.repositories.WorkshopRepository;
import com.pendataan.peserta.services.AbsensiService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
@AllArgsConstructor
public class WorkshopService {

    private static final Logger log = LoggerFactory.getLogger(AbsensiService.class);
    private final WorkshopRepository workshopRepository;


    public ModelAndView showHomePage() {
        ModelAndView modelAndView = new ModelAndView();
        List<Workshop> workshop = workshopRepository.findAll();
        modelAndView.getModelMap().addAttribute("workshop", workshop);
        modelAndView.setViewName("homePage");
        return modelAndView;
    }

    public ModelAndView getAllWorkshop() {
        ModelAndView modelAndView = new ModelAndView();
        List<Workshop> workshop = workshopRepository.findAll();
        modelAndView.getModelMap().addAttribute("workshop", workshop);
        modelAndView.setViewName("listWorkshop");
        return modelAndView;
    }

    public ResponseEntity<byte[]> showImage(String docName) {
        Workshop file = workshopRepository.findByDocName(docName);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + docName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.getPosterByte().length)
                .body(file.getPosterByte());
    }

    public ModelAndView getWorkshop(String judulWorkshop) {
        ModelAndView mav = new ModelAndView();
        try {
            Workshop workshop = workshopRepository.getByJudulWorkshop(judulWorkshop);
            mav.getModelMap().addAttribute("workshop",workshop);
            mav.setViewName("workshopDetail");
            return mav;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return mav;
        }
    }
}
