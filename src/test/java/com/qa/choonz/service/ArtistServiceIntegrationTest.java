package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.qa.choonz.mapper.ArtistMapper;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.repository.AlbumRepository;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.rest.dto.ArtistDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArtistServiceIntegrationTest {

    @Autowired
    private ArtistService service;

    @Autowired
    private ArtistRepository repo;

    @Autowired
    private ArtistMapper mapper;

    @Autowired
    private AlbumRepository albumRepo;

    private List<Artist> artists;
    private List<ArtistDTO> artistDTOs;

    private Artist validArtist;
    private ArtistDTO validArtistDTO;

    @BeforeEach
    void init() {
        albumRepo.deleteAll();
        repo.deleteAll();

        validArtist = new Artist("Eminem", List.of(), List.of());

        validArtist = repo.save(validArtist);

        validArtistDTO = mapper.mapToDTO(validArtist);

        artists = List.of(validArtist);
        artistDTOs = List.of(validArtistDTO);

    }

    @Test
    void createArtistTest() {
        Artist newArtist = new Artist("Slim Shady");
        assertThat(service.create(newArtist)).isEqualTo(mapper.mapToDTO(newArtist));
    }

    @Test
    void readAllTest() {
        assertThat(service.read()).isEqualTo(artistDTOs);
    }

    @Test
    void readByIdTest() {
        assertThat(service.read(validArtist.getId())).isEqualTo(validArtistDTO);
    }

    @Test
    void updateTest() {
        Artist toUpdate = validArtist;
        toUpdate.setName("Updated Artist Name");
        assertThat(service.update(toUpdate, toUpdate.getId())).isEqualTo(mapper.mapToDTO(toUpdate));
    }

    @Test
    void deleteTest() {
        assertThat(service.delete(validArtist.getId())).isTrue();
    }

}
