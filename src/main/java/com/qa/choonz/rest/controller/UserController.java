package com.qa.choonz.rest.controller;

import java.util.Map;

import com.qa.choonz.persistence.domain.User;
import com.qa.choonz.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/perform_signup")
    public String registration(@RequestParam Map<String, String> formData) {
        User user = new User();
        user.setUsername(formData.get("username"));
        user.setPassword(formData.get("password"));
        user.setPasswordConfirm(formData.get("passwordConfirm"));
        userService.save(user);

        return "redirect:index.html";
    }
}