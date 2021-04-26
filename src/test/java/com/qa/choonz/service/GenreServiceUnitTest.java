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

import com.qa.choonz.exception.AlbumNotFoundException;
import com.qa.choonz.exception.GenreNotFoundException;
import com.qa.choonz.mapper.GenreMapper;
import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.repository.GenreRepository;
import com.qa.choonz.rest.dto.AlbumGenresRelationshipDTO;
import com.qa.choonz.rest.dto.ArtistRelationshipDTO;
import com.qa.choonz.rest.dto.GenreDTO;

@SpringBootTest
public class GenreServiceUnitTest {
	
	@Autowired
	private GenreService service;
	
	@MockBean
	private GenreRepository repo;
	
	@MockBean 
	private GenreMapper mapper;
	
	private List<Genre> genres;
	private List<GenreDTO> genreDTOs;
	
	private Genre validGenres;
	private GenreDTO validGenreDTOs;
	
	@BeforeEach
	
	public void initSetup() {
		genres = new ArrayList<Genre>();
		genreDTOs = new ArrayList<GenreDTO>();
	
		

		List<Track> listOfTracks = Arrays.asList(new Track(1, "Till I Collapse", 298, "A lot of lyrics here"));
		Artist validArtist = new Artist(1, "Eminem");
		List<Album> listOfAlbums = Arrays.asList(new Album(1, "The Eminem Show", List.of(), validArtist, List.of(), "eminemshow.jpg"));
		validGenres = new Genre(1, "Hip Hop", "Hippity Hoppity", List.of());
		genres.add(validGenres);
		
		
		ArtistRelationshipDTO artistRelationshipDTO = new ArtistRelationshipDTO(1, "Eminem");
		List<AlbumGenresRelationshipDTO> listOfAlbumGenresReationshipDTO = Arrays.asList(new AlbumGenresRelationshipDTO(1, "Hip Hop", List.of(), "Cover", new ArtistRelationshipDTO()));
		validGenreDTOs = new GenreDTO(1, "Hip Hop", "Hippity Hopipity", List.of());
		genreDTOs.add(validGenreDTOs);
		

}
	
	@Test
	void createTest() {
		when(repo.save(Mockito.any(Genre.class))).thenReturn(validGenres);
		when(mapper.mapToDTO(Mockito.any(Genre.class))).thenReturn(validGenreDTOs);

		assertThat(service.create(validGenres)).isEqualTo(validGenreDTOs);

		verify(repo, times(1)).save(validGenres);
		verify(mapper, times(1)).mapToDTO(validGenres);
	}
	
	@Test
	void readTest() {
		when(repo.findAll()).thenReturn(genres);
		when(mapper.listMapToDTO(Mockito.anyList())).thenReturn(genreDTOs);

		assertThat(genreDTOs).isEqualTo(service.readAll());

		verify(repo, times(1)).findAll();
		verify(mapper, times(1)).listMapToDTO(Mockito.anyList());
	}
	@Test
	
	void readIDTest() {
		when(repo.findById(Mockito.anyInt())).thenReturn(Optional.of(validGenres));
		when(mapper.mapToDTO(Mockito.any(Genre.class))).thenReturn(validGenreDTOs);

		assertThat(service.readById(validGenres.getId())).isEqualTo(validGenreDTOs);

		verify(repo, times(1)).findById(Mockito.anyInt());
		verify(mapper, times(1)).mapToDTO(Mockito.any(Genre.class));
	}

	@Test
	void updateTest() {
		when(repo.findById(Mockito.anyInt())).thenReturn(Optional.of(validGenres));
		when(repo.save(Mockito.any(Genre.class))).thenReturn(validGenres);
		when(mapper.mapToDTO(Mockito.any(Genre.class))).thenReturn(validGenreDTOs);

		assertThat(service.update(validGenres, validGenres.getId())).isEqualTo(validGenreDTOs);

		verify(repo, times(1)).findById(Mockito.anyInt());
		verify(repo, times(1)).save(Mockito.any(Genre.class));
		verify(mapper, times(1)).mapToDTO(Mockito.any(Genre.class));
	}

	@Test
	void deleteTest() {

		when(repo.findById(Mockito.anyInt())).thenReturn(Optional.of(validGenres));
		when(repo.existsById(Mockito.anyInt())).thenReturn(false);
		assertThat(service.delete(validGenres.getId())).isTrue();
		verify(repo, times(1)).existsById(Mockito.anyInt());
		verify(repo, times(1)).deleteById(Mockito.anyInt());
		verify(repo, times(1)).findById(Mockito.anyInt());

	}

	@Test
	void deleteNotFoundTest() {
		when(repo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		int id = validGenres.getId();
		assertThrows(GenreNotFoundException.class, () -> {
			service.delete(id);
		});
		verify(repo, times(1)).findById(Mockito.anyInt());
	}

}


