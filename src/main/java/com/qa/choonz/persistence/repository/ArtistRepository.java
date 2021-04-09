package com.qa.choonz.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.qa.choonz.persistence.domain.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {

    List<Artist> findByName(String name);

}
