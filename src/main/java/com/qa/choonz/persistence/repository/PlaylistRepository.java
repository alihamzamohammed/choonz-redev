package com.qa.choonz.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.qa.choonz.persistence.domain.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {

    List<Playlist> findByName(String name);

}
