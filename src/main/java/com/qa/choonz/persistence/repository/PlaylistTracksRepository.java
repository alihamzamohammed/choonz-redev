package com.qa.choonz.persistence.repository;

import com.qa.choonz.persistence.domain.PlaylistTracks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistTracksRepository extends JpaRepository<PlaylistTracks, Integer> {

}
