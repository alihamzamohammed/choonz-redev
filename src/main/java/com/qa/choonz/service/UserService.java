package com.qa.choonz.service;

import com.qa.choonz.persistence.domain.User;
import com.qa.choonz.rest.dto.UserDTO;

public interface UserService {
    UserDTO save(User user);

    User findByUsername(String username);
}