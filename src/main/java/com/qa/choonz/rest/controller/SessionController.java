package com.qa.choonz.rest.controller;

import com.qa.choonz.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@PreAuthorize("permitAll()")
public class SessionController {

    @Autowired
    private SessionService service;

    @GetMapping(value = "/username")
    @ResponseBody
    public String currentUserName() {
        return service.getUsername();
    }
}