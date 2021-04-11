package service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
import com.qa.choonz.service.AlbumService;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceUnitTest {
	
	@InjectMocks
	private AlbumService service;
	
	@Mock
	private AlbumRepository repo;
	
	@Mock
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
		
		
		List<TrackRelationshipDTO> listOfTrackDTOs = Arrays.asList(new TrackRelationshipDTO(1, "Till I Collapse", 298, "A lot of lyrics here"));
		ArtistRelationshipDTO validArtistDTO = new ArtistRelationshipDTO(1, "Eminem");
		List<GenreRelationshipDTO> listOfGenreDTOs = Arrays.asList(new GenreRelationshipDTO(1, "Hip Hop", "A genre based around rap music."));
		validAlbumDTO = new AlbumDTO(1,"The Eminem Show", listOfTrackDTOs,listOfGenreDTOs,"eminemshow.jpg",validArtistDTO);
	}
	
	@Test
	public void createTest() {
		when(repo.save(Mockito.any(Album.class))).thenReturn(validAlbum);
		when(mapper.mapToDTO(Mockito.any(Album.class))).thenReturn(validAlbumDTO);
		
		assertThat(service.create(validAlbum)).isEqualTo(validAlbumDTO);
	}
	
	@Test
	public void readTest() {
		
	}
	
	@Test
	public void readIDTest() {
		
	}
	
	@Test
	public void updateTest() {
		
	}
	
	@Test
	public void deleteTest() {
		
	}
	
	
}
