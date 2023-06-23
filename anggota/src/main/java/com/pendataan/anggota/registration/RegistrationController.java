package com.pendataan.anggota.registration;

import com.pendataan.anggota.entity.Anggota;
import com.pendataan.anggota.services.AnggotaService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AnggotaService anggotaService;

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }
    @GetMapping
    public ModelAndView register (@ModelAttribute("user") Anggota anggota) {
        return registrationService.register(anggota);
    }

    @PostMapping
    public String processRegister(@Valid RegistrationRequest request,
                                  BindingResult bindingResult,
                                  HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors()) {
            return "api/v1/registration";
        }
        log.info(">> users : {}",request.toString());
        return registrationService.processRegister(request,response);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token, HttpServletResponse response) throws IOException {
        return registrationService.confirmToken(token, response);
    }
}
