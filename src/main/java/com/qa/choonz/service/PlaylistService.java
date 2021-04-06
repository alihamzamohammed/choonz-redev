package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.choonz.exception.PlaylistNotFoundException;
import com.qa.choonz.mapper.PlaylistMapper;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.repository.PlaylistRepository;
import com.qa.choonz.rest.dto.PlaylistDTO;

@Service
public class PlaylistService {

    private PlaylistRepository repo;
    private PlaylistMapper mapper;

    @Autowired
    public PlaylistService(PlaylistRepository repo, PlaylistMapper mapper) {
        super();
        this.repo = repo;
        this.mapper = mapper;
    }

    public PlaylistDTO create(Playlist playlist) {
        Playlist created = this.repo.save(playlist);
        return this.mapper.mapToDTO(created);
    }

    public List<PlaylistDTO> read() {
      
    	List<Playlist> playlistDTO = this.repo.findAll();
    	
    	return this.mapper.mapToDTO(playlistDTO);
  
    }

    public PlaylistDTO read(long id) {
        Playlist found = this.repo.findById(id).orElseThrow(PlaylistNotFoundException::new);
        return this.mapper.mapToDTO(found);
    }

    public PlaylistDTO update(Playlist playlist, long id) {
        Playlist toUpdate = this.repo.findById(id).orElseThrow(PlaylistNotFoundException::new);
        toUpdate.setName(playlist.getName());
        toUpdate.setDescription(playlist.getDescription());
        toUpdate.setArtwork(playlist.getArtwork());
        toUpdate.setTracks(playlist.getTracks());
        Playlist updated = this.repo.save(playlist);
        return this.mapper.mapToDTO(updated);
    }

    public boolean delete(long id) {
        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }

}
