package com.qa.choonz.service;

import java.util.Optional;

import com.qa.choonz.persistence.domain.User;
import com.qa.choonz.persistence.repository.ChoonzUserDetails;
import com.qa.choonz.persistence.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ChoonzUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(userName);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Not found: " + userName);
        }

        return user.map(ChoonzUserDetails::new).get();
    }
}