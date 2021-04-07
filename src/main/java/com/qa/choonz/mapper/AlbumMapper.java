package com.qa.choonz.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.rest.dto.AlbumDTO;

@Component
public class AlbumMapper {

	@Autowired
	private ModelMapper mapper;

	public AlbumDTO mapToDTO(Album album) {
		return this.mapper.map(album, AlbumDTO.class);

	}

	public Album mapToAlbum(AlbumDTO albumDTO) {
		return this.mapper.map(albumDTO, Album.class);
	}

	public List<AlbumDTO> mapToDTO(List<Album> album) {

		List<AlbumDTO> albumDTO = new ArrayList<>();

		album.forEach(a -> albumDTO.add(mapToDTO(a)));

		return albumDTO;
	}

	public List<Album> mapToAlbum(List<AlbumDTO> albumDTO) {

		List<Album> album = new ArrayList<>();

		albumDTO.forEach(a -> album.add(mapToAlbum(a)));

		return album;

	}
}
