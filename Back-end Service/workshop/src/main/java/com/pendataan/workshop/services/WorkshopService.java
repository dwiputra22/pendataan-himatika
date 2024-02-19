package com.pendataan.workshop.services;

import com.pendataan.workshop.dto.StatusWorkshop;
import com.pendataan.workshop.entity.Workshop;
import com.pendataan.workshop.repositories.WorkshopRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class WorkshopService {

    private final WorkshopRepository workshopRepository;


    public ModelAndView getAllWorkshop() {
        ModelAndView modelAndView = new ModelAndView();
        List<Workshop> workshop = workshopRepository.findAll();
        modelAndView.getModelMap().addAttribute("workshop", workshop);
        modelAndView.setViewName("workshop");
        return modelAndView;
    }

    public ModelAndView input(Workshop workshop) {
        ModelAndView mav = new ModelAndView();
        Workshop inputWorkshop = new Workshop();
        mav.getModelMap().addAttribute("inputWorkshop", inputWorkshop);
        mav.setViewName("formWorkshop");
        return mav;
    }

    public ResponseEntity<?> uploadWorkshop(Workshop workshop,
                                            MultipartFile poster,
                                            HttpServletResponse response) throws IOException {
        Workshop upload = new Workshop();

        upload.setJudulWorkshop(workshop.getJudulWorkshop());
        upload.setPembicara(workshop.getPembicara());
        upload.setTanggalWorkshop(workshop.getTanggalWorkshop());
        upload.setDescription(workshop.getDescription());
        upload.setLinkWhatsapp(workshop.getLinkWhatsapp());
        upload.setPosterByte(poster.getBytes());
        upload.setDocName(poster.getOriginalFilename());
        upload.setType(poster.getContentType());
        upload.setStatus(String.valueOf(StatusWorkshop.CREATED));
        upload.setCreatedDate(LocalDateTime.now());

        workshopRepository.save(upload);
        response.sendRedirect("/himatika/workshop");
        return ResponseEntity.status(HttpStatus.OK).body(upload);
    }
}
