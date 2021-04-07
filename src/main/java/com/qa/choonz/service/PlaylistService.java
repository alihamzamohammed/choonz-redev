package com.qa.choonz.service;

import java.util.List;
import java.util.Optional;

import com.qa.choonz.exception.PlaylistNotFoundException;
import com.qa.choonz.mapper.PlaylistMapper;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.PlaylistTracks;
import com.qa.choonz.persistence.repository.PlaylistRepository;
import com.qa.choonz.persistence.repository.PlaylistTracksRepository;
import com.qa.choonz.rest.dto.PlaylistDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService {

    private PlaylistRepository repo;
    private PlaylistTracksRepository playlistTracksRepo;
    private PlaylistMapper mapper;

    @Autowired
    public PlaylistService(PlaylistRepository repo, PlaylistMapper mapper,
            PlaylistTracksRepository playlistTracksRepo) {
        super();
        this.repo = repo;
        this.mapper = mapper;
        this.playlistTracksRepo = playlistTracksRepo;
    }

    public PlaylistDTO create(Playlist playlist) {
        Playlist created = this.repo.save(playlist);
        created.getPlaylistTracks().forEach(
                playlistTrack -> this.playlistTracksRepo.save(new PlaylistTracks(created, playlistTrack.getTrack())));
        return this.mapper.mapToDTO(created);
    }

    public List<PlaylistDTO> read() {

        List<Playlist> playlistDTO = this.repo.findAll();

        return this.mapper.mapToDTO(playlistDTO);

    }

    public PlaylistDTO read(int id) {
        Playlist found = this.repo.findById(id).orElseThrow(PlaylistNotFoundException::new);
        return this.mapper.mapToDTO(found);
    }

    public PlaylistDTO update(Playlist playlist, int id) {
        Playlist toUpdate = this.repo.findById(id).orElseThrow(PlaylistNotFoundException::new);
        toUpdate.setName(playlist.getName());
        toUpdate.setDescription(playlist.getDescription());
        toUpdate.setArtwork(playlist.getArtwork());
        toUpdate.setPlaylistTracks(playlist.getPlaylistTracks());
        toUpdate.getPlaylistTracks().forEach(playlistTrack -> this.playlistTracksRepo.save(playlistTrack));
        Playlist updated = this.repo.save(playlist);
        return this.mapper.mapToDTO(updated);
    }

    public boolean delete(int id) {
        Optional<Playlist> optional = this.repo.findById(id);
        if (optional.isPresent()) {
            optional.get().getPlaylistTracks()
                    .forEach(playlistTrack -> this.playlistTracksRepo.deleteById(playlistTrack.getId()));
            this.repo.deleteById(id);
        } else {
            throw new PlaylistNotFoundException();
        }
        return !this.repo.existsById(id);
    }

}
