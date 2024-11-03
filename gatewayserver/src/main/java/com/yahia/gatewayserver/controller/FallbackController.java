package com.yahia.gatewayserver.controller;

import com.yahia.gatewayserver.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @Autowired
    private EmailService emailService;

    @RequestMapping("/contactSupport")
    public Mono<String>  contactSupport(){

        // Define email details
        String to = "ya.akermi@esi-sba.dz";  // Replace with the support email
        String subject = "Support Request";
        String text = "An error has occurred, please assist the user.";

        // Send email
        emailService.sendSupportEmail(to, subject, text);

        return Mono.just("An error has occured, please try after sometime or contact support team (GatewayServer) !!");
    }

}
