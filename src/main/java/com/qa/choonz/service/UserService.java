package com.qa.choonz.service;

import com.qa.choonz.exception.ChoonzUsernameNotFoundException;
import com.qa.choonz.mapper.UserMapper;
import com.qa.choonz.persistence.domain.User;
import com.qa.choonz.persistence.repository.UserRepository;
import com.qa.choonz.rest.dto.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserMapper mapper;

    public UserDTO save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("USER");
        return mapper.mapToDTO(userRepository.save(user));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(ChoonzUsernameNotFoundException::new);
    }

    public UserDTO update(User user) {
        User toUpdate = findByUsername(user.getUsername());
        toUpdate.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        toUpdate.setPasswordConfirm(user.getPasswordConfirm());
        toUpdate.setRoles("USER");
        return mapper.mapToDTO(userRepository.save(toUpdate));
    }

    public Boolean delete(User user) {
        User toDelete = findByUsername(user.getUsername());
        userRepository.deleteById(toDelete.getId());
        return !userRepository.existsById(toDelete.getId());
    }
}
