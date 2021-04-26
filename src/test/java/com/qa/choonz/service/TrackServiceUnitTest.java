package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.choonz.exception.TrackNotFoundException;
import com.qa.choonz.mapper.TracksMapper;
import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.repository.TrackRepository;
import com.qa.choonz.rest.dto.AlbumRelationshipDTO;
import com.qa.choonz.rest.dto.ArtistRelationshipDTO;
import com.qa.choonz.rest.dto.TrackDTO;

@SpringBootTest
class TrackServiceUnitTest {
	
	@Autowired
	private TrackService service;
	
	@MockBean
	private TrackRepository repo;
	
	@MockBean
	private TracksMapper mapper;
	
	private List<Track> tracks;
	private List<TrackDTO> trackDTOs;
	
	private Track validTracks;
	private TrackDTO validTrackDTOs;
	
	@BeforeEach
	
	public void initSetup() {
		
		tracks = new ArrayList<Track>();
		trackDTOs = new ArrayList<TrackDTO>();
		
		Artist validArtist = new Artist(1, "Eminem");
		Album validAlbum = new Album(1, "The Eminem Show", List.of(), validArtist, List.of(), "eminemshow.jpg");
		validTracks = new Track(1, "The Real Slim Shady", new Album(), 10, "Please Stand Up", List.of());
		tracks.add(validTracks);
		
		
		AlbumRelationshipDTO validRelationshipDTO = new AlbumRelationshipDTO(1, "The Eminem Show", List.of(), "eminemshow.jpg");
		ArtistRelationshipDTO validArtistRelationshipDTO = new ArtistRelationshipDTO(1, "Eminem");
		validTrackDTOs = new TrackDTO(1, "The Real Slim Shady", new AlbumRelationshipDTO(), 10, "Please Stand Up",
				validArtistRelationshipDTO, List.of());
		trackDTOs.add(validTrackDTOs);
		
	}

@Test
void createTest() {
	when(repo.save(Mockito.any(Track.class))).thenReturn(validTracks);
	when(mapper.mapToDTO(Mockito.any(Track.class))).thenReturn(validTrackDTOs);

	assertThat(service.create(validTracks)).isEqualTo(validTrackDTOs);

	verify(repo, times(1)).save(validTracks);
	verify(mapper, times(1)).mapToDTO(validTracks);
}

@Test
void readTest() {
	when(repo.findAll()).thenReturn(tracks);
	when(mapper.listMapToDTO(Mockito.anyList())).thenReturn(trackDTOs);

	assertThat(trackDTOs).isEqualTo(service.readAll());

	verify(repo, times(1)).findAll();
	verify(mapper, times(1)).listMapToDTO(Mockito.anyList());
}
@Test

void readIDTest() {
	when(repo.findById(Mockito.anyInt())).thenReturn(Optional.of(validTracks));
	when(mapper.mapToDTO(Mockito.any(Track.class))).thenReturn(validTrackDTOs);

	assertThat(service.readById(validTracks.getId())).isEqualTo(validTrackDTOs);

	verify(repo, times(1)).findById(Mockito.anyInt());
	verify(mapper, times(1)).mapToDTO(Mockito.any(Track.class));
}

@Test
void updateTest() {
	when(repo.findById(Mockito.anyInt())).thenReturn(Optional.of(validTracks));
	when(repo.save(Mockito.any(Track.class))).thenReturn(validTracks);
	when(mapper.mapToDTO(Mockito.any(Track.class))).thenReturn(validTrackDTOs);

	assertThat(service.update(validTracks, validTracks.getId())).isEqualTo(validTrackDTOs);

	verify(repo, times(1)).findById(Mockito.anyInt());
	verify(repo, times(1)).save(Mockito.any(Track.class));
	verify(mapper, times(1)).mapToDTO(Mockito.any(Track.class));
}

@Test
void deleteTest() {

	when(repo.findById(Mockito.anyInt())).thenReturn(Optional.of(validTracks));
	when(repo.existsById(Mockito.anyInt())).thenReturn(false);
	assertThat(service.delete(validTracks.getId())).isTrue();
	verify(repo, times(1)).existsById(Mockito.anyInt());
	verify(repo, times(1)).deleteById(Mockito.anyInt());
	verify(repo, times(1)).findById(Mockito.anyInt());

}

@Test
void deleteNotFoundTest() {
	when(repo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
	int id = validTracks.getId();
	assertThrows(TrackNotFoundException.class, () -> {
		service.delete(id);
	});
	verify(repo, times(1)).findById(Mockito.anyInt());
}

}
