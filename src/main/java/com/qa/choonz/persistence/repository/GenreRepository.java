package com.qa.choonz.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.qa.choonz.persistence.domain.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

    List<Genre> findByName(String name);
}
