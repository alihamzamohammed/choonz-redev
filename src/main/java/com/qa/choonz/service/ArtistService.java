package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

import com.qa.choonz.exception.ArtistNotFoundException;
import com.qa.choonz.mapper.ArtistMapper;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.rest.dto.ArtistDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {

    private ArtistRepository repo;
    private ArtistMapper mapper;
    private AlbumService albumService;
    private TrackService trackService;

    @Autowired
    public ArtistService(ArtistRepository repo, ArtistMapper mapper, AlbumService albumService,
            TrackService trackService) {
        super();
        this.repo = repo;
        this.mapper = mapper;
        this.albumService = albumService;
        this.trackService = trackService;
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
        Artist artist = this.repo.findById(id).orElseThrow(ArtistNotFoundException::new);
        artist.getAlbums().forEach(album -> albumService.delete(album.getId()));

        artist.getContributedTracks().forEach(track -> {
            track.setContributingArtists(track.getContributingArtists().stream()
                    .filter(a -> a.getName().equals(artist.getName())).collect(Collectors.toList()));
            trackService.update(track, track.getId());
        });
        this.repo.deleteById(artist.getId());
        return !this.repo.existsById(artist.getId());
    }
}
