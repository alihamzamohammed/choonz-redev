package com.qa.choonz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.choonz.exception.ArtistNotFoundException;
import com.qa.choonz.mapper.ArtistMapper;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.rest.dto.ArtistDTO;

@Service
public class ArtistService {

    private ArtistRepository repo;
    private ArtistMapper mapper;

    @Autowired
    public ArtistService(ArtistRepository repo, ArtistMapper mapper) {
        super();
        this.repo = repo;
        this.mapper = mapper;
    }

    public ArtistDTO create(Artist artist) {
        Artist created = this.repo.save(artist);
        return this.mapper.mapToDTO(created);
    }

    public List<ArtistDTO> read() {
        return this.mapper.listMapToDTO(this.repo.findAll());
    }

    public ArtistDTO read(int id) {
        Artist found = this.repo.findById(id).orElseThrow(ArtistNotFoundException::new);
        return this.mapper.mapToDTO(found);
    }

    public ArtistDTO update(Artist artist, int id) {
        Artist toUpdate = this.repo.findById(id).orElseThrow(ArtistNotFoundException::new);
        toUpdate.setName(artist.getName());
        toUpdate.setAlbums(artist.getAlbums());
        Artist updated = this.repo.save(toUpdate);
        return this.mapper.mapToDTO(updated);
    }

    public boolean delete(int id) {
        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }
}
