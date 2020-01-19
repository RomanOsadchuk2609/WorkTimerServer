package com.osadchuk.worktimerserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

@RestController
@RequestMapping("/timer")
@Slf4j
public class TimerController {

    @PostMapping("/login")
    public String login(@RequestParam(value = "authToken") String authTokenBase64) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] authTokenBytes = decoder.decodeBuffer(authTokenBase64);
            ByteArrayInputStream bis = new ByteArrayInputStream(authTokenBytes);
            ObjectInput in = new ObjectInputStream(bis);
            Authentication authToken = (UsernamePasswordAuthenticationToken) in.readObject();
            SecurityContextHolder.getContext().setAuthentication(authToken);
            return "Login successfully!";
        } catch (AuthenticationException | IOException | ClassNotFoundException e) {
            log.error("Login failure, please try again.", e);
            return "Login failure, please try again.";
        }
    }
}
