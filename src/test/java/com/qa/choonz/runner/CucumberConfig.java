package com.qa.choonz.runner;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class CucumberConfig {

    // @LocalServerPort
    // private int port;

    @Bean
    public int getPort() {
        return 8082;
    }

}
