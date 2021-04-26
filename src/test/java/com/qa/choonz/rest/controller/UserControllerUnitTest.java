package com.qa.choonz.rest.controller;

import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

import com.qa.choonz.service.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UserControllerUnitTest {

    @MockBean
    private UserService service;

    @Autowired
    private UserController controller;

    @Test
    void registrationTest() {
        Map<String, String> details = new HashMap<>();
        details.put("username", "test");
        details.put("password", "test");
        details.put("passwordConfirm", "test");
        assertThat(controller.registration(details)).isEqualTo("redirect:login.html?signup=true");
    }

    @Test
    void changePasswordTest() {
        Map<String, String> details = new HashMap<>();
        details.put("username", "test");
        details.put("password", "test");
        details.put("passwordConfirm", "test");
        assertThat(controller.changePassword(details)).isEqualTo("redirect:index.html");
    }

    @Test
    void deleteTest() {
        Map<String, String> details = new HashMap<>();
        details.put("username", "test");
        assertThat(controller.deleteUser(details)).isEqualTo("redirect:perform_logout");
    }
}
