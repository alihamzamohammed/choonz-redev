package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.choonz.exception.ArtistNotFoundException;
import com.qa.choonz.mapper.ArtistMapper;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.rest.dto.AlbumArtistRelationshipDTO;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.dto.ArtistRelationshipDTO;
import com.qa.choonz.rest.dto.GenreRelationshipDTO;
import com.qa.choonz.rest.dto.TrackRelationshipDTO;

@SpringBootTest
class ArtistServiceUnitTest {

	@Autowired
	private ArtistService service;

	@MockBean
	private ArtistRepository repo;

	@MockBean
	private ArtistMapper mapper;

	private List<Artist> artists;
	private List<ArtistDTO> artistDTOs;

	private Artist validArtist;
	private ArtistDTO validArtistDTO;

	@BeforeEach

	public void initSetup() {
		artists = new ArrayList<Artist>();
		artistDTOs = new ArrayList<ArtistDTO>();

		validArtist = new Artist(1, "Eminem");
		validArtist.setAlbums(List.of());
		artists.add(validArtist);

		ArtistRelationshipDTO validArtistRelationshipDTO = new ArtistRelationshipDTO(1, "Eminem");
		List<GenreRelationshipDTO> listOfGenreDTOs = Arrays
				.asList(new GenreRelationshipDTO(1, "Hip Hop", "A genre based around rap music."));

		List<TrackRelationshipDTO> listOfTrackDTOs = Arrays
				.asList(new TrackRelationshipDTO(1, "Till I Collapse", 298, "A lot of lyrics here"));

		AlbumArtistRelationshipDTO albumArtistRelationshipDTO = new AlbumArtistRelationshipDTO(1, "Eminem", List.of(),
				List.of(), "Cover");

		validArtistDTO = new ArtistDTO(1, "Eminem", List.of(albumArtistRelationshipDTO), listOfTrackDTOs);

		artistDTOs.add(validArtistDTO);

	}

	@Test
	void createTest() {
		when(repo.save(Mockito.any(Artist.class))).thenReturn(validArtist);
		when(mapper.mapToDTO(Mockito.any(Artist.class))).thenReturn(validArtistDTO);

		assertThat(service.create(validArtist)).isEqualTo(validArtistDTO);

		verify(repo, times(1)).save(validArtist);
		verify(mapper, times(1)).mapToDTO(validArtist);
	}

	@Test
	void readTest() {
		when(repo.findAll()).thenReturn(artists);
		when(mapper.listMapToDTO(Mockito.anyList())).thenReturn(artistDTOs);

		assertThat(artistDTOs).isEqualTo(service.read());

		verify(repo, times(1)).findAll();
		verify(mapper, times(1)).listMapToDTO(artists);
	}

	@Test
	void readIDTest() {
		when(repo.findById(Mockito.anyInt())).thenReturn(Optional.of(validArtist));
		when(mapper.mapToDTO(Mockito.any(Artist.class))).thenReturn(validArtistDTO);

		assertThat(service.read(validArtist.getId())).isEqualTo(validArtistDTO);

		verify(repo, times(1)).findById(Mockito.anyInt());
		verify(mapper, times(1)).mapToDTO(Mockito.any(Artist.class));
	}

	@Test
	void updateTest() {
		when(repo.findById(Mockito.anyInt())).thenReturn(Optional.of(validArtist));
		when(repo.save(Mockito.any(Artist.class))).thenReturn(validArtist);
		when(mapper.mapToDTO(Mockito.any(Artist.class))).thenReturn(validArtistDTO);

		assertThat(service.update(validArtist, validArtist.getId())).isEqualTo(validArtistDTO);

		verify(repo, times(1)).findById(Mockito.anyInt());
		verify(repo, times(1)).save(Mockito.any(Artist.class));
		verify(mapper, times(1)).mapToDTO(Mockito.any(Artist.class));
	}

	@Test
	void deleteTest() {

		when(repo.findById(Mockito.anyInt())).thenReturn(Optional.of(validArtist));
		when(repo.existsById(Mockito.anyInt())).thenReturn(false);
		assertThat(service.delete(validArtist.getId())).isTrue();
		verify(repo, times(1)).existsById(Mockito.anyInt());
		verify(repo, times(1)).deleteById(Mockito.anyInt());
		verify(repo, times(1)).findById(Mockito.anyInt());

	}

	@Test
	void deleteNotFoundTest() {
		when(repo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		int id = validArtist.getId();
		assertThrows(ArtistNotFoundException.class, () -> {
			service.delete(id);
		});
		verify(repo, times(1)).findById(Mockito.anyInt());
	}

}
