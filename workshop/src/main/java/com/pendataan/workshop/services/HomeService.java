package com.pendataan.workshop.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
@AllArgsConstructor
public class HomeService {

    public ModelAndView getIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("himatika");
        return modelAndView;
    }
}
