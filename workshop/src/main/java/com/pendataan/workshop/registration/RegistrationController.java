package com.pendataan.workshop.registration;

import com.pendataan.workshop.entity.users.Users;
import com.pendataan.workshop.services.UsersService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final UsersService usersService;

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }
    @GetMapping
    public ModelAndView register (@ModelAttribute("user") Users users) {
        return registrationService.register(users);
    }

    @PostMapping
    public String processRegister(@Valid RegistrationRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "api/v1/registration";
        }
        log.info(">> users : {}",request.toString());
        return registrationService.processRegister(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
