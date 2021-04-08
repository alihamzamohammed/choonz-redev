package com.qa.choonz.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}