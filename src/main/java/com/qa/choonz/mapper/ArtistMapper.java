package com.qa.choonz.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.rest.dto.ArtistDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {

    private ModelMapper modelMapper;

    @Autowired
    public ArtistMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ArtistDTO mapToDTO(Artist artist) {
        return this.modelMapper.map(artist, ArtistDTO.class);
    }

    public Artist mapToArtist(ArtistDTO artistDTO) {
        return this.modelMapper.map(artistDTO, Artist.class);
    }

    public List<ArtistDTO> listMapToDTO(List<Artist> artists) {
        return artists.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<Artist> listMapToArtist(List<ArtistDTO> artistDTOs) {
        return artistDTOs.stream().map(this::mapToArtist).collect(Collectors.toList());
    }

}
