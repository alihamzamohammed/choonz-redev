package com.qa.choonz.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.rest.dto.GenreDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {
	private ModelMapper modelMapper;

	@Autowired
	public GenreMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public GenreDTO mapToDTO(Genre track) {
		return this.modelMapper.map(track, GenreDTO.class);
	}

	public Genre mapToGenre(GenreDTO trackDTO) {
		return this.modelMapper.map(trackDTO, Genre.class);
	}

	public List<GenreDTO> listMapToDTO(List<Genre> genres) {
		return genres.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public List<Genre> listMapToGenre(List<GenreDTO> genreDTOs) {
		return genreDTOs.stream().map(this::mapToGenre).collect(Collectors.toList());
	}

}
