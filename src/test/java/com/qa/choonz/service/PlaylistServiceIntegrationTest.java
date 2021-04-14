package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.qa.choonz.mapper.PlaylistMapper;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.repository.PlaylistRepository;
import com.qa.choonz.rest.dto.PlaylistDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PlaylistServiceIntegrationTest {

    @Autowired
    private PlaylistService service;

    @Autowired
    private PlaylistRepository repo;

    @Autowired
    private PlaylistMapper mapper;

    private List<Playlist> playlists;
    private List<PlaylistDTO> playlistDTOs;

    private Playlist validPlaylist;
    private PlaylistDTO validPlaylistDTO;

    @BeforeEach
    void init() {
        repo.deleteAll();

        validPlaylist = new Playlist("My Playlist 1", "Really loud music", "playlistArt.png", List.of());

        validPlaylist = repo.save(validPlaylist);

        validPlaylistDTO = mapper.mapToDTO(validPlaylist);

        playlists = List.of(validPlaylist);
        playlistDTOs = List.of(validPlaylistDTO);

    }

    @Test
    void createPlaylistTest() {
        Playlist newPlaylist = new Playlist("My Playlist 2", "Old music", "test.png", List.of());
        assertThat(service.create(newPlaylist)).isEqualTo(mapper.mapToDTO(newPlaylist));
    }

    @Test
    void readAllTest() {
        assertThat(service.read()).isEqualTo(playlistDTOs);
    }

    @Test
    void readByIdTest() {
        assertThat(service.read(validPlaylist.getId())).isEqualTo(validPlaylistDTO);
    }

    @Test
    void updateTest() {
        Playlist toUpdate = validPlaylist;
        toUpdate.setName("Updated Playlist Name");
        assertThat(service.update(toUpdate, toUpdate.getId())).isEqualTo(mapper.mapToDTO(toUpdate));
    }

    @Test
    void deleteTest() {
        assertThat(service.delete(validPlaylist.getId())).isTrue();
    }

}
