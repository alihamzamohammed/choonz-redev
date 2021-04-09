package com.qa.choonz.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.qa.choonz.persistence.domain.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {

    List<Album> findByName(String name);

}
