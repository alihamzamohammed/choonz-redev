package com.qa.choonz.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.service.ArtistService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ArtistControllerUnitTest {

	@Autowired
	private ArtistController controller;

	@MockBean
	private ArtistService service;

	private List<ArtistDTO> artistDTOs;

	private Artist validArtist;
	private ArtistDTO validArtistDTO;

	@BeforeEach
	void init() {

		validArtist = new Artist(1, "Eminem", List.of(), List.of());
		validArtistDTO = new ArtistDTO(1, "Eminem", List.of(), List.of());

	}

	@Test
	void createArtistTest() {
		when(service.create(validArtist)).thenReturn(validArtistDTO);

		ResponseEntity<ArtistDTO> response = new ResponseEntity<ArtistDTO>(validArtistDTO, HttpStatus.CREATED);

		assertThat(response).isEqualTo(controller.create(validArtist));

		verify(service, times(1)).create(validArtist);
	}

	@Test
	void readAllArtistsTest() {

		when(service.read()).thenReturn(artistDTOs);

		ResponseEntity<List<ArtistDTO>> response = new ResponseEntity<List<ArtistDTO>>(artistDTOs, HttpStatus.OK);

		assertThat(response).isEqualTo(controller.read());

		verify(service, times(1)).read();
	}

	@Test
	void readArtistByIdTest() {

		when(service.read(validArtistDTO.getId())).thenReturn(validArtistDTO);

		ResponseEntity<ArtistDTO> response = new ResponseEntity<ArtistDTO>(validArtistDTO, HttpStatus.OK);

		assertThat(response).isEqualTo(controller.read(validArtistDTO.getId()));

		verify(service, times(1)).read(validArtistDTO.getId());
	}

	@Test
	void deleteArtistTest() {

		when(service.delete(validArtist.getId())).thenReturn(true);

		ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);

		assertThat(response).isEqualTo(controller.delete(validArtist.getId()));

		verify(service, times(1)).delete(validArtist.getId());
	}

	@Test
	void updateArtistTest() {

		when(service.update(validArtist, validArtist.getId())).thenReturn(validArtistDTO);

		ResponseEntity<ArtistDTO> response = new ResponseEntity<ArtistDTO>(validArtistDTO, HttpStatus.ACCEPTED);

		assertThat(response).isEqualTo(controller.update(validArtist, validArtist.getId()));

		verify(service, times(1)).update(validArtist, validArtist.getId());
	}

}
