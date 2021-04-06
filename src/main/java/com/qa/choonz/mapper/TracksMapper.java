package com.qa.choonz.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.TrackDTO;


public class TracksMapper {
private ModelMapper modelMapper;
	
	@Autowired
	public TracksMapper (ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public TrackDTO mapToDTO(Track track) {
		return this.modelMapper.map(track, TrackDTO.class);
	}
	
	public Track mapToGenre(TrackDTO trackDTO) {
        return this.modelMapper.map(trackDTO, Track.class);
	}
	
}
