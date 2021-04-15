package com.qa.choonz.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.qa.choonz.service.SessionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;

@SpringBootTest
class SessionControllerUnitTest {

    @Autowired
    private SessionController controller;

    @MockBean
    private SessionService service;

    @WithAnonymousUser
    @Test
    void currentUserNameTest() {
        when(service.getUsername()).thenReturn("testUser");
        assertThat(controller.currentUserName()).isEqualTo("testUser");
        verify(service, times(1)).getUsername();
    }
}
