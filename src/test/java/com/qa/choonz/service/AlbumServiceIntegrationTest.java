package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import com.qa.choonz.mapper.AlbumMapper;
import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.repository.AlbumRepository;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.dto.ArtistRelationshipDTO;
import com.qa.choonz.rest.dto.GenreRelationshipDTO;
import com.qa.choonz.rest.dto.TrackRelationshipDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AlbumServiceIntegrationTest {

    @Autowired
    private AlbumService service;

    @Autowired
    private AlbumRepository repo;

    @Autowired
    private AlbumMapper mapper;

    private List<Album> albums;
    private List<AlbumDTO> albumDTOs;

    private Album validAlbum;
    private AlbumDTO validAlbumDTO;

    private List<Track> listOfTracks;
    private Artist validArtist;
    private List<Genre> listOfGenres;

    @BeforeEach
    void init() {
        listOfTracks = Arrays.asList(new Track(1, "Till I Collapse", 298, "A lot of lyrics here"));
        validArtist = new Artist(1, "Eminem");
        listOfGenres = Arrays.asList(new Genre(1, "Hip Hop", "A genre based around rap music."));
        validAlbum = new Album(1, "The Eminem Show", listOfTracks, validArtist, listOfGenres, "eminemshow.jpg");

        List<TrackRelationshipDTO> listOfTrackDTOs = Arrays
                .asList(new TrackRelationshipDTO(1, "Till I Collapse", 298, "A lot of lyrics here"));

        ArtistRelationshipDTO validArtistDTO = new ArtistRelationshipDTO(1, "Eminem");

        List<GenreRelationshipDTO> listOfGenreDTOs = Arrays
                .asList(new GenreRelationshipDTO(1, "Hip Hop", "A genre based around rap music."));

        validAlbumDTO = new AlbumDTO(1, "The Eminem Show", listOfTrackDTOs, listOfGenreDTOs, "eminemshow.jpg",
                validArtistDTO);

    }

    @Test
    void createAlbumTest() {
        Album newAlbum = new Album("Hello World", listOfTracks, validArtist, listOfGenres, "test.png");
        assertThat(service.create(newAlbum)).isEqualTo(mapper.mapToDTO(newAlbum));
    }

}
