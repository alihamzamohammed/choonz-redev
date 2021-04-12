package com.qa.choonz.service;

import java.util.List;

import com.qa.choonz.exception.TrackNotFoundException;
import com.qa.choonz.mapper.TracksMapper;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.repository.TrackRepository;
import com.qa.choonz.rest.dto.TrackDTO;

import org.springframework.stereotype.Service;

@Service
public class TrackService {

    private TrackRepository repo;
    private TracksMapper mapper;

    public TrackService(TrackRepository repo, TracksMapper mapper) {
        super();
        this.repo = repo;
        this.mapper = mapper;
    }

    public TrackDTO create(Track track) {
        Track newTrack = repo.save(track);
        return mapper.mapToDTO(newTrack);
    }

    public List<TrackDTO> readAll() {
        List<Track> tracks = repo.findAll();
        return mapper.listMapToDTO(tracks);
    }

    public TrackDTO readById(int id) {
        Track track = repo.findById(id).orElseThrow(TrackNotFoundException::new);
        return mapper.mapToDTO(track);
    }

    public TrackDTO update(Track track, int id) {
        Track trackInDb = repo.findById(id).orElseThrow(TrackNotFoundException::new);
        trackInDb.setName(track.getName() != null ? track.getName() : trackInDb.getName());
        trackInDb.setAlbum(track.getAlbum() != null ? track.getAlbum() : trackInDb.getAlbum());
        trackInDb.setDuration(track.getDuration() != null ? track.getDuration() : trackInDb.getDuration());
        trackInDb.setLyrics(track.getLyrics() != null ? track.getLyrics() : trackInDb.getLyrics());
        Track updatedTrack = repo.save(trackInDb);
        return mapper.mapToDTO(updatedTrack);
    }

    public boolean delete(int id) {
        repo.deleteById(id);
        return !repo.existsById(id);
    }

}
