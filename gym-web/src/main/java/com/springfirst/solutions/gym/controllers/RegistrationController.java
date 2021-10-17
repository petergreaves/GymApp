package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.commands.registration.RegistrationStateCommand;
import com.springfirst.solutions.gym.domain.registration.RegistrationState;
import com.springfirst.solutions.gym.services.registration.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    //get a list of all trainers
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public String getAllRegistrations(Model model){

        model.addAttribute("registrations", registrationService.getAllRegistrations());
        return "register/view-registrations-list";
    }

    @GetMapping("/{email}/view")
    @ResponseStatus(HttpStatus.OK)
    public String getSingleRegistration(Model model, @PathVariable("email") String email){

        model.addAttribute("registration", registrationService.findRegistrationByEmail(email));
        return "register/view-registration-detail";
    }

    @GetMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public String getTrainers(Model model){

        RegistrationState newState = RegistrationState
                .builder()
                .isNew(true)
                .build();
        model.addAttribute("registration", newState );
        return "register/create-edit-registration";
    }



    //get a list of all trainers
//    @PostMapping("/new")
//    @ResponseStatus(HttpStatus.OK)
//    public String createNewRegistration(RegistrationStateCommand command){
//
//        model.addAttribute("registrations", registrationService.getAllRegistrations());
//        return "register/view-registrations-list";
//    }
}
