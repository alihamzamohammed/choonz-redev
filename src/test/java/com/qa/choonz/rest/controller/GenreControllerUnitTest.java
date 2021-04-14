package com.qa.choonz.rest.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.rest.controller.AlbumController;
import com.qa.choonz.rest.controller.GenreController;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.service.GenreService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GenreControllerUnitTest {

	@Autowired
	private GenreController controller;
	
	@MockBean
	private GenreService service;
	
	private List<Genre> genres;
	private List<GenreDTO> genreDTOs;
		
	private Genre validGenre;
	private GenreDTO validGenreDTO;
	

	@BeforeEach 
	public void init() {
		validGenre = new Genre(1, "Hip Hop","A genre based around rap music.", List.of());
		validGenreDTO = new GenreDTO(1, "Hip Hop","A genre based around rap music.", List.of());
	}
	
	@Test
	public void createGenreTest() {
		when(service.create(validGenre)).thenReturn(validGenreDTO);
		
		ResponseEntity<GenreDTO> response = 
				new ResponseEntity<GenreDTO>(validGenreDTO, HttpStatus.CREATED);
		
		assertThat(response).isEqualTo(controller.create(validGenre));
		
		verify(service, times(1)).create(validGenre);
	}
	
	@Test
	public void readAllGenreTest() {
		when(service.readAll()).thenReturn(genreDTOs);
		
		ResponseEntity<List<GenreDTO>> response = new ResponseEntity<List<GenreDTO>>(genreDTOs, HttpStatus.OK);
		
		assertThat(response).isEqualTo(controller.readAll());
		
		verify(service, times(1)).readAll();
	}	
	
	@Test
	public void readGenreByIdTest() {
		when(service.readById(validGenreDTO.getId())).thenReturn(validGenreDTO);
		
		ResponseEntity<GenreDTO> response = new ResponseEntity<GenreDTO>(validGenreDTO, HttpStatus.OK);
		
		assertThat(response).isEqualTo(controller.readById(validGenreDTO.getId()));
		
		verify(service, times(1)).readById(validGenreDTO.getId());
	}
	

	@Test
	public void deleteGenreTest() {
		when(service.delete(validGenre.getId())).thenReturn(true);
		
		ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
		
		assertThat(response).isEqualTo(controller.delete(validGenre.getId()));
		
		verify(service, times(1)).delete(validGenre.getId());
		
	}
	
	@Test
	public void updateGenreTest() {

		when(service.update(validGenre, validGenre.getId())).thenReturn(validGenreDTO);
		

		ResponseEntity<GenreDTO> response = new ResponseEntity<GenreDTO>(validGenreDTO, HttpStatus.ACCEPTED);
		
		assertThat(response).isEqualTo(controller.update(validGenre, validGenre.getId()));
		
		verify(service, times(1)).update(validGenre, validGenre.getId());
		
	}
}

