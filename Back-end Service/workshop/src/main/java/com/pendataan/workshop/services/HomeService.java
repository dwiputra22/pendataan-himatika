package com.pendataan.workshop.services;

import com.pendataan.workshop.entity.Workshop;
import com.pendataan.workshop.repositories.WorkshopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
@AllArgsConstructor
public class HomeService {

    private final WorkshopRepository workshopRepository;

    public ModelAndView getIndex() {
        ModelAndView modelAndView = new ModelAndView();
        List<Workshop> workshop = workshopRepository.findAll();
        modelAndView.getModelMap().addAttribute("workshop", workshop);
        modelAndView.setViewName("himatika");
        return modelAndView;
    }
}
