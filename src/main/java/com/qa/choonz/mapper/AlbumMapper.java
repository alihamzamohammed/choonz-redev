package com.qa.choonz.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.rest.dto.AlbumDTO;

public class AlbumMapper {

	@Autowired
	private ModelMapper mapper;
	

	private AlbumDTO mapToDTO(Album album) {
	   return this.mapper.map(album, AlbumDTO.class);
	    
	}
	
	private Album mapToAlbum(AlbumDTO albumDTO) {
		return this.mapper.map(albumDTO, Album.class);
	}

	
	private List<AlbumDTO> mapToDTO(List<Album> album) {
		
		List<AlbumDTO> albumDTO = new ArrayList<>();
		
		album.forEach(a -> albumDTO.add(mapToDTO(a)));
		
		return albumDTO;
	}
	
	private List<Album> mapToAlbum(List<AlbumDTO> albumDTO) {
		
		List<Album> album = new ArrayList<>();
		
		albumDTO.forEach(a -> album.add(mapToAlbum(a)));
		
		return album;
		
	}
}
