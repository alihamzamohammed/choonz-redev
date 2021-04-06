package com.qa.choonz.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.rest.dto.PlaylistDTO;

public class PlaylistMapper {

	@Autowired
	private ModelMapper mapper;

	public PlaylistDTO mapToDTO(Playlist playlist) {
		return this.mapper.map(playlist, PlaylistDTO.class);
	}

	public Playlist mapToPlaylist(PlaylistDTO playlistDTO) {
		return this.mapper.map(playlistDTO, Playlist.class);
	}

	public List<PlaylistDTO> mapToDTO(List<Playlist> playlist) {

		List<PlaylistDTO> playlistDTO = new ArrayList<>();

		playlist.forEach(p -> playlistDTO.add(mapToDTO(p)));

		return playlistDTO;

	}

	public List<Playlist> mapToPlaylist(List<PlaylistDTO> playlistDTO) {

		List<Playlist> playlist = new ArrayList<>();

		playlistDTO.forEach(p -> playlist.add(mapToPlaylist(p)));

		return playlist;

	}
}
