package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
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

import com.qa.choonz.exception.PlaylistNotFoundException;
import com.qa.choonz.mapper.PlaylistMapper;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.repository.PlaylistRepository;
import com.qa.choonz.rest.dto.PlaylistDTO;

@SpringBootTest 
class PlaylistServiceUnitTest {
	
	@Autowired
	private PlaylistService service;
	
	@MockBean
	private PlaylistRepository repo;
	
	@MockBean 
	private PlaylistMapper mapper;
	
	private List<Playlist> playlist;
	private List<PlaylistDTO> playlistDTO;
	
	private Playlist validPlaylist;
	private PlaylistDTO validPlaylistDTO;
	
	@BeforeEach
	public void initSetup() {
		playlist = new ArrayList<Playlist>();
		playlistDTO = new ArrayList<PlaylistDTO>();
		
		validPlaylist = new Playlist(1, "My Playlist", "This is my Playlist", "Artwork", List.of());
		playlist.add(validPlaylist);
		
		validPlaylistDTO = new PlaylistDTO(1, "My Playlist", "This is my Playlist", "Artwork", List.of());
		playlistDTO.add(validPlaylistDTO);
		
	}

	@Test
	void createTest() {
		when(repo.save(Mockito.any(Playlist.class))).thenReturn(validPlaylist);
		when(mapper.mapToDTO(Mockito.any(Playlist.class))).thenReturn(validPlaylistDTO);
		
		assertThat(service.create(validPlaylist)).isEqualTo(validPlaylistDTO);
		
		verify(repo,times(1)).save(validPlaylist);
		verify(mapper, times(1)).mapToDTO(validPlaylist);
	}
	
	@Test
	void readTest() {
	        when(repo.findAll()).thenReturn(playlist);
	        when(mapper.mapToDTO(Mockito.anyList())).thenReturn(playlistDTO);

	        assertThat(playlistDTO).isEqualTo(service.read());

	        verify(repo, times(1)).findAll();
	        verify(mapper, times(1)).mapToDTO(playlist);
	    }
	
	@Test
	void readIDTest() {
		when(repo.findById(Mockito.anyInt())).thenReturn(Optional.of(validPlaylist));
		when(mapper.mapToDTO(Mockito.any(Playlist.class))).thenReturn(validPlaylistDTO);

		assertThat(service.read(validPlaylist.getId())).isEqualTo(validPlaylistDTO);

		verify(repo, times(1)).findById(Mockito.anyInt());
		verify(mapper, times(1)).mapToDTO(Mockito.any(Playlist.class));
	}

	@Test
	void updateTest() {
		when(repo.findById(Mockito.anyInt())).thenReturn(Optional.of(validPlaylist));
		when(repo.save(Mockito.any(Playlist.class))).thenReturn(validPlaylist);
		when(mapper.mapToDTO(Mockito.any(Playlist.class))).thenReturn(validPlaylistDTO);

		assertThat(service.update(validPlaylist, validPlaylist.getId())).isEqualTo(validPlaylistDTO);

		verify(repo, times(1)).findById(Mockito.anyInt());
		verify(repo, times(1)).save(Mockito.any(Playlist.class));
		verify(mapper, times(1)).mapToDTO(Mockito.any(Playlist.class));
	}

	@Test
	void deleteTest() {
		   
	       when(repo.findById(Mockito.anyInt())).thenReturn(Optional.of(validPlaylist));
	       when(repo.existsById(Mockito.anyInt())).thenReturn(false);
		   assertThat(service.delete(validPlaylist.getId())).isTrue();
		   verify(repo, times(1)).existsById(Mockito.anyInt());
		   verify(repo, times(1)).deleteById(Mockito.anyInt());
		   verify(repo, times(1)).findById(Mockito.anyInt());
		   
	}
	
	@Test
	void deleteNotFoundTest() {
		when(repo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		int id = validPlaylist.getId();
		assertThrows(PlaylistNotFoundException.class, () -> {
			service.delete(id);
		});
		verify(repo, times(1)).findById(Mockito.anyInt());
	}

}

	
	
	
	


