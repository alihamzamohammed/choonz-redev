package com.qa.choonz.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.dto.ArtistRelationshipDTO;
import com.qa.choonz.rest.dto.GenreRelationshipDTO;
import com.qa.choonz.rest.dto.TrackRelationshipDTO;
import com.qa.choonz.service.AlbumService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
class AlbumControllerUnitTest {

	@Autowired
	private AlbumController controller;

	@MockBean
	private AlbumService service;

	private List<AlbumDTO> albumDTOs;

	private Album validAlbum;
	private AlbumDTO validAlbumDTO;

	@BeforeEach
	void init() {
		List<Track> listOfTracks = Arrays.asList(new Track(1, "Till I Collapse", 298, "A lot of lyrics here"));
		Artist validArtist = new Artist(1, "Eminem");
		List<Genre> listOfGenres = Arrays.asList(new Genre(1, "Hip Hop", "A genre based around rap music."));
		validAlbum = new Album(1, "The Eminem Show", listOfTracks, validArtist, listOfGenres, "eminemshow.jpg");

		List<TrackRelationshipDTO> listOfTrackDTOs = Arrays
				.asList(new TrackRelationshipDTO(1, "Till I Collapse", 298, "A lot of lyrics here"));
		ArtistRelationshipDTO validArtistDTO = new ArtistRelationshipDTO(1, "Eminem");
		List<GenreRelationshipDTO> listOfGenreDTOs = Arrays
				.asList(new GenreRelationshipDTO(1, "Hip Hop", "A genre based around rap music."));
		validAlbumDTO = new AlbumDTO(1, "The Eminem Show", listOfTrackDTOs, listOfGenreDTOs, "eminemshow.jpg",
				validArtistDTO);
	}

	@WithMockUser(authorities = { "USER" })
	@Test
	void createAlbumTest() {
		when(service.create(validAlbum)).thenReturn(validAlbumDTO);

		ResponseEntity<AlbumDTO> response = new ResponseEntity<AlbumDTO>(validAlbumDTO, HttpStatus.CREATED);

		assertThat(response).isEqualTo(controller.create(validAlbum));

		verify(service, times(1)).create(validAlbum);
	}

	@WithAnonymousUser
	@Test
	void readAllAlbumsTest() {
		when(service.read()).thenReturn(albumDTOs);

		ResponseEntity<List<AlbumDTO>> response = new ResponseEntity<List<AlbumDTO>>(albumDTOs, HttpStatus.OK);

		assertThat(response).isEqualTo(controller.read());

		verify(service, times(1)).read();
	}

	@WithAnonymousUser
	@Test
	void readAlbumByIdTest() {
		when(service.read(validAlbumDTO.getId())).thenReturn(validAlbumDTO);

		ResponseEntity<AlbumDTO> response = new ResponseEntity<AlbumDTO>(validAlbumDTO, HttpStatus.OK);

		assertThat(response).isEqualTo(controller.read(validAlbumDTO.getId()));

		verify(service, times(1)).read(validAlbumDTO.getId());
	}

	@WithMockUser(authorities = { "USER" })
	@Test
	void deleteAlbumTest() {
		when(service.delete(validAlbum.getId())).thenReturn(true);

		ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);

		assertThat(response).isEqualTo(controller.delete(validAlbum.getId()));

		verify(service, times(1)).delete(validAlbum.getId());

	}

	@WithMockUser(authorities = { "USER" })
	@Test
	void updateAlbumTest() {

		when(service.update(validAlbum, validAlbum.getId())).thenReturn(validAlbumDTO);

		ResponseEntity<AlbumDTO> response = new ResponseEntity<AlbumDTO>(validAlbumDTO, HttpStatus.ACCEPTED);

		assertThat(response).isEqualTo(controller.update(validAlbum, validAlbum.getId()));

		verify(service, times(1)).update(validAlbum, validAlbum.getId());
	}

}
