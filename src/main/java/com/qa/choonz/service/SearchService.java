package com.qa.choonz.service;

import java.util.List;

import com.qa.choonz.mapper.AlbumMapper;
import com.qa.choonz.mapper.ArtistMapper;
import com.qa.choonz.mapper.GenreMapper;
import com.qa.choonz.mapper.PlaylistMapper;
import com.qa.choonz.mapper.TracksMapper;
import com.qa.choonz.persistence.repository.AlbumRepository;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.persistence.repository.GenreRepository;
import com.qa.choonz.persistence.repository.PlaylistRepository;
import com.qa.choonz.persistence.repository.TrackRepository;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.rest.dto.TrackDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    private AlbumRepository albumRepo;

    @Autowired
    private ArtistRepository artistRepo;

    @Autowired
    private GenreRepository genreRepo;

    @Autowired
    private PlaylistRepository playlistRepo;

    @Autowired
    private TrackRepository trackRepo;

    @Autowired
    private ArtistMapper artistMapper;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private GenreMapper genreMapper;

    @Autowired
    private PlaylistMapper playlistMapper;

    @Autowired
    private TracksMapper tracksMapper;

    public List<AlbumDTO> searchAlbums(String name) {
        return albumMapper.mapToDTO(albumRepo.findByName(name));
    }

    public List<ArtistDTO> searchArtists(String name) {
        return artistMapper.listMapToDTO(artistRepo.findByName(name));
    }

    public List<GenreDTO> searchGenres(String name) {
        return genreMapper.listMapToDTO(genreRepo.findByName(name));
    }

    public List<PlaylistDTO> searchPlaylists(String name) {
        return playlistMapper.mapToDTO(playlistRepo.findByName(name));
    }

    public List<TrackDTO> searchTracks(String name) {
        return tracksMapper.listMapToDTO(trackRepo.findByName(name));
    }
}
