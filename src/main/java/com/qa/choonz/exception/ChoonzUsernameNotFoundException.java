package com.qa.choonz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Username not found")
public class ChoonzUsernameNotFoundException extends UsernameNotFoundException {

    /**
     *
     */
    private static final long serialVersionUID = 7395875778217468172L;

    public ChoonzUsernameNotFoundException() {
        super("Username not found");
    }
}
