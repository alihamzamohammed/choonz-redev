package com.qa.choonz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qa.choonz.exception.TrackNotFoundException;
import com.qa.choonz.mapper.TracksMapper;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.repository.TrackRepository;
import com.qa.choonz.rest.dto.TrackDTO;

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
        List<TrackDTO> trackDTOs = new ArrayList<TrackDTO>();

        tracks.forEach(t -> trackDTOs.add(mapper.mapToDTO(t)));
        return trackDTOs;
    }

    public TrackDTO readById(int id) {
        Optional<Track> track = repo.findById(id);

        if (track.isPresent()) {
            return mapper.mapToDTO(track.get());
        } else {
            throw new TrackNotFoundException();
        }
    }

    public TrackDTO update(Track track, int id) {
        Optional<Track> trackInDbOpt = repo.findById(id);
        Track trackInDb;

        if (trackInDbOpt.isPresent()) {
            trackInDb = trackInDbOpt.get();
        } else {
            throw new TrackNotFoundException();
        }

        trackInDb.setName(track.getName());
        trackInDb.setAlbum(track.getAlbum());
        trackInDb.setDuration(track.getDuration());
        trackInDb.setLyrics(track.getLyrics());

        Track updatedTrack = repo.save(trackInDb);
        return mapper.mapToDTO(updatedTrack);
    }

    public boolean delete(int id) {
        if (!repo.existsById(id)) {
            throw new TrackNotFoundException();
        }

        repo.deleteById(id);

        boolean exists = repo.existsById(id);

        return !exists;

    }

}
