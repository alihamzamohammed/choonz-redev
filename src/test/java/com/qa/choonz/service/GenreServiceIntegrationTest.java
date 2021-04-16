package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.qa.choonz.mapper.GenreMapper;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.repository.GenreRepository;
import com.qa.choonz.rest.dto.GenreDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GenreServiceIntegrationTest {

    @Autowired
    private GenreService service;

    @Autowired
    private GenreRepository repo;

    @Autowired
    private GenreMapper mapper;

    private List<Genre> genres;
    private List<GenreDTO> genreDTOs;

    private Genre validGenre;
    private GenreDTO validGenreDTO;

    @BeforeEach
    void init() {
        repo.deleteAll();

        validGenre = new Genre("Rock", "Really loud music", List.of());

        validGenre = repo.save(validGenre);

        validGenreDTO = mapper.mapToDTO(validGenre);

        genres = List.of(validGenre);
        genreDTOs = List.of(validGenreDTO);

    }

    @Test
    void createGenreTest() {
        Genre newGenre = new Genre("Jazz", "Old music", List.of());
        assertThat(service.create(newGenre)).isEqualTo(mapper.mapToDTO(newGenre));
    }

    @Test
    void readAllTest() {
        assertThat(service.readAll()).isEqualTo(genreDTOs);
    }

    @Test
    void readByIdTest() {
        assertThat(service.readById(validGenre.getId())).isEqualTo(validGenreDTO);
    }

    @Test
    void updateTest() {
        Genre toUpdate = validGenre;
        toUpdate.setName("Updated Genre Name");
        assertThat(service.update(toUpdate, toUpdate.getId())).isEqualTo(mapper.mapToDTO(toUpdate));
    }

    @Test
    void deleteTest() {
        assertThat(service.delete(validGenre.getId())).isTrue();
    }

}
