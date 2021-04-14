package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.validateMockitoUsage;

import java.util.ArrayList;
import java.util.List;

import com.qa.choonz.mapper.TracksMapper;
import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.repository.AlbumRepository;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.persistence.repository.GenreRepository;
import com.qa.choonz.persistence.repository.TrackRepository;
import com.qa.choonz.rest.dto.TrackDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TrackServiceIntegrationTest {

    @Autowired
    private TrackService service;

    @Autowired
    private TrackRepository repo;

    @Autowired
    private TracksMapper mapper;

    @Autowired
    private ArtistRepository artistRepo;

    @Autowired
    private GenreRepository genreRepo;

    @Autowired
    private AlbumRepository albumRepo;

    private List<Track> tracks;
    private List<TrackDTO> trackDTOs;

    private Track validTrack;
    private TrackDTO validTrackDTO;

    private Album validAlbum;

    @BeforeEach
    void init() {
        repo.deleteAll();

        Artist validArtist = new Artist("Eminem");
        validArtist = artistRepo.save(validArtist);

        Genre validGenre = new Genre("Hip Hop", "A genre based around rap music.");
        validGenre = genreRepo.save(validGenre);

        validAlbum = new Album("The Eminem Show", validArtist, List.of(validGenre), "eminemshow.jpg");
        validAlbum = albumRepo.save(validAlbum);

        validTrack = new Track("Till I Collapse", validAlbum, 298, "Random Lyrics", List.of(), validArtist);
        validTrack = repo.save(validTrack);

        tracks = List.of(validTrack);
        validTrackDTO = mapper.mapToDTO(validTrack);

        tracks = List.of(validTrack);
        trackDTOs = List.of(validTrackDTO);

    }

    @Test
    void createTrackTest() {
        Track newTrack = new Track("Hello World", validAlbum, 100, "test.png");
        assertThat(service.create(newTrack)).isEqualTo(mapper.mapToDTO(newTrack));
    }

    @Test
    void readAllTest() {
        assertThat(service.readAll()).isEqualTo(trackDTOs);
    }

    @Test
    void readByIdTest() {
        assertThat(service.readById(validTrack.getId())).isEqualTo(mapper.mapToDTO(validTrack));
    }

    @Test
    void updateTest() {
        Track toUpdate = validTrack;
        toUpdate.setName("Updated track");
        assertThat(service.update(toUpdate, toUpdate.getId())).isEqualTo(mapper.mapToDTO(toUpdate));
    }

    @Test
    void deleteTest() {
        assertThat(service.delete(validTrack.getId())).isTrue();
    }

}
