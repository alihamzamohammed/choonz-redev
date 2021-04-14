package controller;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.rest.controller.PlaylistController;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.service.PlaylistService;

@SpringBootTest(classes = PlaylistController.class)
public class PlaylistControllerUnitTest {
  
	@Autowired
	private PlaylistController controller;
	
	@MockBean
	private PlaylistService service;
	
	private List<Playlist> playlists;
	private List<PlaylistDTO> playlistDTOs;
		
	private Playlist validPlaylist;
	private PlaylistDTO validPlaylistDTO;
	
	@BeforeEach
	public void init() {

		validPlaylist = new Playlist(1, "Rap", "All the best in hip hop", "Artwork Picture", List.of());
		
		validPlaylistDTO = new PlaylistDTO(1, "Rap", "All the best in hip hop", "Artwork Picture", List.of());
	}
	
	@Test
	public void createPlaylistTest() {
		when(service.create(validPlaylist)).thenReturn(validPlaylistDTO);
		
		ResponseEntity<PlaylistDTO> response = 
				new ResponseEntity<PlaylistDTO>(validPlaylistDTO, HttpStatus.CREATED);
		
		assertThat(response).isEqualTo(controller.create(validPlaylist));
		
		verify(service, times(1)).create(validPlaylist);
	}
	
	@Test
	public void readAllPlaylistTest() {
		when(service.read()).thenReturn(playlistDTOs);
		
		ResponseEntity<List<PlaylistDTO>> response = new ResponseEntity<List<PlaylistDTO>>(playlistDTOs, HttpStatus.OK);
		
		assertThat(response).isEqualTo(controller.read());
		
		verify(service, times(1)).read();
	}	
	
	@Test
	public void readPlaylistByIdTest() {
		when(service.read(validPlaylistDTO.getId())).thenReturn(validPlaylistDTO);
		
		ResponseEntity<PlaylistDTO> response = new ResponseEntity<PlaylistDTO>(validPlaylistDTO, HttpStatus.OK);
		
		assertThat(response).isEqualTo(controller.read(validPlaylistDTO.getId()));
		
		verify(service, times(1)).read(validPlaylistDTO.getId());
	}
	
	@Test
	public void deletePlaylistTest() {
		when(service.delete(validPlaylist.getId())).thenReturn(true);
		
		ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
		
		assertThat(response).isEqualTo(controller.delete(validPlaylist.getId()));
		
		verify(service, times(1)).delete(validPlaylist.getId());
		
	}
	
	@Test
	public void updatePlaylistTest() {
		
		when(service.update(validPlaylist, validPlaylist.getId())).thenReturn(validPlaylistDTO);
		
		
		ResponseEntity<PlaylistDTO> response = new ResponseEntity<PlaylistDTO>(validPlaylistDTO, HttpStatus.ACCEPTED);
		
		assertThat(response).isEqualTo(controller.update(validPlaylist, validPlaylist.getId()));
		
		verify(service, times(1)).update(validPlaylist, validPlaylist.getId());
	}
	
}
