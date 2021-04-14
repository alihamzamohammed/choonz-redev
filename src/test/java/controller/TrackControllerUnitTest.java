package controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.controller.TrackController;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.service.TrackService;

@SpringBootTest
public class TrackControllerUnitTest {
	
	@Autowired
	private TrackController controller;
	
	@MockBean
	private TrackService service;
	
	private List<Track> tracks;
	private List<TrackDTO> trackDTOs;
		
	private Track validTrack;
	private TrackDTO validTrackDTO;
	
	@BeforeEach
	public void init() {
		validTrack = new Track(1, "Till I Collapse", 298, "Lots of lyrics");
		
		validTrackDTO = new TrackDTO(1, "Till I Collapse", 298, "Lots of lyrics");
	}
	
	@Test
	public void createTrackTest() {
		when(service.create(validTrack)).thenReturn(validTrackDTO);
		
		ResponseEntity<TrackDTO> response = 
				new ResponseEntity<TrackDTO>(validTrackDTO, HttpStatus.CREATED);
		
		assertThat(response).isEqualTo(controller.create(validTrack));
		
		verify(service, times(1)).create(validTrack);
	}
	
	@Test
	public void readAllTracksTest() {
		when(service.readAll()).thenReturn(trackDTOs);
		
		ResponseEntity<List<TrackDTO>> response = new ResponseEntity<List<TrackDTO>>(trackDTOs, HttpStatus.OK);
		
		assertThat(response).isEqualTo(controller.readAll());
		
		verify(service, times(1)).readAll();
	}
	
	@Test
	public void readTrackByIdTest() {
		when(service.readById(validTrackDTO.getId())).thenReturn(validTrackDTO);
		
		ResponseEntity<TrackDTO> response = new ResponseEntity<TrackDTO>(validTrackDTO, HttpStatus.OK);
		
		assertThat(response).isEqualTo(controller.readById(validTrackDTO.getId()));
		
		verify(service, times(1)).readById(validTrackDTO.getId());
	}
	
	@Test
	public void deleteTrackTest() {
		when(service.delete(validTrack.getId())).thenReturn(true);
		
		ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
		
		assertThat(response).isEqualTo(controller.delete(validTrack.getId()));
		
		verify(service, times(1)).delete(validTrack.getId());
		
	}
	
	@Test
	public void updateTrackTest() {
		
		when(service.update(validTrack, validTrack.getId())).thenReturn(validTrackDTO);
		
		
		ResponseEntity<TrackDTO> response = new ResponseEntity<TrackDTO>(validTrackDTO, HttpStatus.ACCEPTED);
		
		assertThat(response).isEqualTo(controller.update(validTrack, validTrack.getId()));
		
		verify(service, times(1)).update(validTrack, validTrack.getId());
	}
	
	
	
}
