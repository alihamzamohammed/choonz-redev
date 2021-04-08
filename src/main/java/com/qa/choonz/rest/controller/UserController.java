package com.qa.choonz.rest.controller;

import java.util.Map;

import com.qa.choonz.persistence.domain.User;
import com.qa.choonz.rest.dto.UserDTO;
import com.qa.choonz.service.SecurityService;
import com.qa.choonz.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping(value = "/perform_signup") // , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String registration(@RequestParam Map<String, String> formData) {
        User user = new User();
        user.setUsername(formData.get("username"));
        user.setPassword(formData.get("password"));
        user.setPasswordConfirm(formData.get("passwordConfirm"));
        UserDTO created = userService.save(user);

        // securityService.autoLogin(created.getUsername(), created.getPassword());

        return "redirect:login.html";
    }
}