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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.choonz.mapper.ArtistMapper;
import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.dto.ArtistRelationshipDTO;
import com.qa.choonz.rest.dto.GenreRelationshipDTO;
import com.qa.choonz.rest.dto.TrackRelationshipDTO;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceUnitTest {
	
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
		
		List<Track> listOfTracks = Arrays.asList(new Track(1, "Till I Collapse", 298, "A lot of lyrics here"));
		validArtist = new Artist(1, "Eminem");
		List<Genre> listOfGenres = Arrays.asList(new Genre(1, "Hip Hop", "A genre based around rap music."));
		Album validAlbum = new Album(1,"The Eminem Show", listOfTracks,validArtist, listOfGenres, "eminemshow.jpg");
		artists.add(validArtist);
		
		List<TrackRelationshipDTO> listOfTrackDTOs = Arrays.asList(new TrackRelationshipDTO(1, "Till I Collapse", 298, "A lot of lyrics here"));
		validArtistDTO = new ArtistDTO(1, "Eminem", List.of(), listOfTrackDTOs);
		List<GenreRelationshipDTO> listOfGenreDTOs = Arrays.asList(new GenreRelationshipDTO(1, "Hip Hop", "A genre based around rap music."));
		AlbumDTO validAlbumDTO = new AlbumDTO("Name", List.of(), List.of(), "Cover", new ArtistRelationshipDTO());
		artistDTOs.add(validArtistDTO);
	}
	
	@Test
	public void createTest() {
		when(repo.save(Mockito.any(Artist.class))).thenReturn(validArtist);
		when(mapper.mapToDTO(Mockito.any(Artist.class))).thenReturn(validArtistDTO);
		
		assertThat(service.create(validArtist)).isEqualTo(validArtistDTO);
		
		verify(repo,times(1)).save(validArtist);
		verify(mapper, times(1)).mapToDTO(validArtist);
	}
	
	@Test
	public void readTest() {
	        when(repo.findAll()).thenReturn(artists);
	        when(mapper.listMapToDTO(artists)).thenReturn(artistDTOs);

	        assertThat(artistDTOs).isEqualTo(service.read());

	        verify(repo, times(1)).findAll();
	        verify(mapper, times(1)).listMapToDTO(artists);//changed to list
	    }

	@Test
	public void deleteTest() {
		   
	       when(repo.findById(Mockito.anyInt())).thenReturn(Optional.of(validArtist));
	       when(repo.existsById(Mockito.anyInt())).thenReturn(false);
		   assertThat(service.delete(validArtist.getId())).isTrue();
		   verify(repo, times(1)).existsById(Mockito.anyInt());
		   verify(repo, times(1)).deleteById(Mockito.anyInt());
		   verify(repo, times(1)).findById(Mockito.anyInt());
		   
		
	}
}
