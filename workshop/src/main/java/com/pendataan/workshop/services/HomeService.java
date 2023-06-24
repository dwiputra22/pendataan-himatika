package com.pendataan.workshop.services;

import com.pendataan.workshop.entity.Workshop;
import com.pendataan.workshop.repositories.WorkshopRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class HomeService {

    private final WorkshopRepository workshopRepository;

    public ModelAndView getIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("himatika");
        return modelAndView;
    }

    public ModelAndView input(Workshop workshop) {
        ModelAndView mav = new ModelAndView();
        Workshop inputWorkshop = new Workshop();
        inputWorkshop.setJudulWorkshop(workshop.getJudulWorkshop());
        inputWorkshop.setPembicara(workshop.getPembicara());
        inputWorkshop.setTanggalWorkshop(workshop.getTanggalWorkshop());
        mav.getModelMap().addAttribute("inputWorkshop", inputWorkshop);
        mav.setViewName("formWorkshop");
        return mav;
    }

    public ResponseEntity<?> uploadProposal(Workshop workshop, HttpServletResponse response) throws IOException {
        Workshop upload = new Workshop();

        upload.setJudulWorkshop(workshop.getJudulWorkshop());
        upload.setPembicara(workshop.getPembicara());
        upload.setTanggalWorkshop(workshop.getTanggalWorkshop());
        upload.setCreatedDate(LocalDateTime.now());

        workshopRepository.save(upload);
        response.sendRedirect("/himatika");
        return ResponseEntity.status(HttpStatus.OK).body(upload);
    }
}
