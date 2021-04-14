package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;
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

@SpringBootTest
public class AlbumServiceUnitTest {
	
	
	@Autowired
	private AlbumService service;
	
	@MockBean
	private AlbumRepository repo;
	
	@MockBean
	private AlbumMapper mapper;
	
	private List<Album> albums;
	private List<AlbumDTO> albumDTOs;
	
	private Album validAlbum;
	private AlbumDTO validAlbumDTO;
	
	@BeforeEach
	public void initSetup() {
		albums = new ArrayList<Album>();
		albumDTOs = new ArrayList<AlbumDTO>();
		
		List<Track> listOfTracks = Arrays.asList(new Track(1, "Till I Collapse", 298, "A lot of lyrics here"));
		Artist validArtist = new Artist(1, "Eminem");
		List<Genre> listOfGenres = Arrays.asList(new Genre(1, "Hip Hop", "A genre based around rap music."));
		validAlbum = new Album(1,"The Eminem Show", listOfTracks,validArtist, listOfGenres, "eminemshow.jpg");
		albums.add(validAlbum);
		
		List<TrackRelationshipDTO> listOfTrackDTOs = Arrays.asList(new TrackRelationshipDTO(1, "Till I Collapse", 298, "A lot of lyrics here"));
		ArtistRelationshipDTO validArtistDTO = new ArtistRelationshipDTO(1, "Eminem");
		List<GenreRelationshipDTO> listOfGenreDTOs = Arrays.asList(new GenreRelationshipDTO(1, "Hip Hop", "A genre based around rap music."));
		validAlbumDTO = new AlbumDTO(1,"The Eminem Show", listOfTrackDTOs,listOfGenreDTOs,"eminemshow.jpg",validArtistDTO);
		albumDTOs.add(validAlbumDTO);
	}
	
	@Test
	public void createTest() {
		when(repo.save(Mockito.any(Album.class))).thenReturn(validAlbum);
		when(mapper.mapToDTO(Mockito.any(Album.class))).thenReturn(validAlbumDTO);
		
		assertThat(service.create(validAlbum)).isEqualTo(validAlbumDTO);
		
		verify(repo,times(1)).save(validAlbum);
		verify(mapper, times(1)).mapToDTO(validAlbum);
	}
	
	@Test
	public void readTest() {
	        when(repo.findAll()).thenReturn(albums);
	        when(mapper.mapToDTO(albums)).thenReturn(albumDTOs);

	        assertThat(albumDTOs).isEqualTo(service.read());

	        verify(repo, times(1)).findAll();
	        verify(mapper, times(1)).mapToDTO(albums);
	    }
	
	@Test
	public void readIDTest() {
		
	}
	
	/* @Test
	public void updateTest() {

	        Optional<Category> optional = Optional.of(validCategory);
	        when(categoryRepository.findById(Mockito.anyInt())).thenReturn(optional);
	        when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(validCategory);
	        when(categoryMapper.mapToDTO(Mockito.any(Category.class))).thenReturn(validCategoryDTO);

	        assertThat(validCategoryDTO).isEqualTo(categoryService.updateCategory(validCategory.getId(), validCategory));

	        verify(categoryRepository, times(1)).findById(Mockito.anyInt());
	        verify(categoryRepository, times(1)).save(Mockito.any(Category.class));
	        verify(categoryMapper, times(1)).mapToDTO(Mockito.any(Category.class));
	    }
		
	*/
	
	@Test
	public void deleteTest() {
		   
	       when(repo.findById(Mockito.anyInt())).thenReturn(Optional.of(validAlbum));
	       when(repo.existsById(Mockito.anyInt())).thenReturn(false);
		   assertThat(service.delete(validAlbum.getId())).isTrue();
		   verify(repo, times(1)).existsById(Mockito.anyInt());
		   verify(repo, times(1)).deleteById(Mockito.anyInt());
		   verify(repo, times(1)).findById(Mockito.anyInt());
		   
		
	}
		
	}
	
	

