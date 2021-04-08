package com.qa.choonz.persistence.repository;

import java.util.Optional;

import com.qa.choonz.persistence.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String userName);

}