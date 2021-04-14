package com.qa.choonz.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.service.GenreService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class GenreControllerUnitTest {

	@Autowired
	private GenreController controller;

	@MockBean
	private GenreService service;

	private List<GenreDTO> genreDTOs;

	private Genre validGenre;
	private GenreDTO validGenreDTO;

	@BeforeEach
	void init() {
		validGenre = new Genre(1, "Hip Hop", "A genre based around rap music.", List.of());
		validGenreDTO = new GenreDTO(1, "Hip Hop", "A genre based around rap music.", List.of());
	}

	@Test
	void createGenreTest() {
		when(service.create(validGenre)).thenReturn(validGenreDTO);

		ResponseEntity<GenreDTO> response = new ResponseEntity<GenreDTO>(validGenreDTO, HttpStatus.CREATED);

		assertThat(response).isEqualTo(controller.create(validGenre));

		verify(service, times(1)).create(validGenre);
	}

	@Test
	void readAllGenreTest() {
		when(service.readAll()).thenReturn(genreDTOs);

		ResponseEntity<List<GenreDTO>> response = new ResponseEntity<List<GenreDTO>>(genreDTOs, HttpStatus.OK);

		assertThat(response).isEqualTo(controller.readAll());

		verify(service, times(1)).readAll();
	}

	@Test
	void readGenreByIdTest() {
		when(service.readById(validGenreDTO.getId())).thenReturn(validGenreDTO);

		ResponseEntity<GenreDTO> response = new ResponseEntity<GenreDTO>(validGenreDTO, HttpStatus.OK);

		assertThat(response).isEqualTo(controller.readById(validGenreDTO.getId()));

		verify(service, times(1)).readById(validGenreDTO.getId());
	}

	@Test
	void deleteGenreTest() {
		when(service.delete(validGenre.getId())).thenReturn(true);

		ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);

		assertThat(response).isEqualTo(controller.delete(validGenre.getId()));

		verify(service, times(1)).delete(validGenre.getId());

	}

	@Test
	void updateGenreTest() {

		when(service.update(validGenre, validGenre.getId())).thenReturn(validGenreDTO);

		ResponseEntity<GenreDTO> response = new ResponseEntity<GenreDTO>(validGenreDTO, HttpStatus.ACCEPTED);

		assertThat(response).isEqualTo(controller.update(validGenre, validGenre.getId()));

		verify(service, times(1)).update(validGenre, validGenre.getId());

	}
}
