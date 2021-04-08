package com.qa.choonz.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // if (authentication instanceof AnonymousAuthenticationToken) {
        // return "Not logged in";
        // }
        return authentication.getName();
    }
}
