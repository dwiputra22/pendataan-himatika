package com.pendataan.workshop.registration;

import com.pendataan.workshop.entity.users.Users;
import com.pendataan.workshop.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final UsersService usersService;


    @GetMapping
    public ModelAndView register (@ModelAttribute("user") @Valid Users users) {
        ModelAndView modelAndView = new ModelAndView();
        Users user = new Users();
        user.setNim(users.getNim());
        user.setNama(users.getNama());
        user.setEmail(users.getEmail());
        user.setPassword(users.getPassword());
        modelAndView.getModelMap().addAttribute("user", users);
        modelAndView.setViewName("register");
        return modelAndView;
    }
//    public ModelAndView register(@ModelAttribute("user") @Valid Users users,
//                                 RegistrationRequest request,
//                                 Errors errors) {
//        String register = registrationService.processRegister(request);
//        ModelAndView mav = new ModelAndView(register);
//        try {
//            usersService.signUpUser(users);
//        } catch (IllegalStateException unfe) {
//            mav.addObject("message", "An account for that username/email already exists.");
//            return mav;
//        }
//        return new ModelAndView("successRegister", "user", users);
//    }

    @PostMapping(path = "/process-register")
    public String processRegister(@RequestBody RegistrationRequest request) {
        return registrationService.processRegister(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
