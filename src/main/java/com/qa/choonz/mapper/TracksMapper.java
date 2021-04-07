package com.qa.choonz.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.TrackDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TracksMapper {
	private ModelMapper modelMapper;

	@Autowired
	public TracksMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public TrackDTO mapToDTO(Track track) {
		return this.modelMapper.map(track, TrackDTO.class);
	}

	public Track mapToTrack(TrackDTO trackDTO) {
		return this.modelMapper.map(trackDTO, Track.class);
	}

	public List<TrackDTO> listMapToDTO(List<Track> tracks) {
		return tracks.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public List<Track> listMapToTrack(List<TrackDTO> trackDTOs) {
		return trackDTOs.stream().map(this::mapToTrack).collect(Collectors.toList());
	}

}
