package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

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
        return albumMapper.mapToDTO(albumRepo.findAll().stream().filter(album -> album.getName().contains(name))
                .collect(Collectors.toList()));
    }

    public List<ArtistDTO> searchArtists(String name) {
        return artistMapper.listMapToDTO(artistRepo.findAll().stream().filter(artist -> artist.getName().contains(name))
                .collect(Collectors.toList()));
    }

    public List<GenreDTO> searchGenres(String name) {
        return genreMapper.listMapToDTO(genreRepo.findAll().stream().filter(genre -> genre.getName().contains(name))
                .collect(Collectors.toList()));
    }

    public List<PlaylistDTO> searchPlaylists(String name, Boolean contributingArtists) {
        if (Boolean.FALSE.equals(contributingArtists)) {
            return playlistMapper.mapToDTO(playlistRepo.findAll().stream()
                    .filter(playlist -> playlist.getName().contains(name)).filter(playlist -> playlist.getTracks()
                            .stream().anyMatch(track -> track.getContributingArtists().isEmpty()))
                    .collect(Collectors.toList()));
        } else {
            return playlistMapper.mapToDTO(playlistRepo.findAll().stream()
                    .filter(playlist -> playlist.getName().contains(name)).collect(Collectors.toList()));
        }
    }

    public List<TrackDTO> searchTracks(String name, Boolean contributingArtists) {
        if (Boolean.FALSE.equals(contributingArtists)) {
            return tracksMapper
                    .listMapToDTO(trackRepo.findAll().stream().filter(track -> track.getName().contains(name))
                            .filter(track -> track.getContributingArtists().isEmpty()).collect(Collectors.toList()));
        } else {
            return tracksMapper.listMapToDTO(trackRepo.findAll().stream()
                    .filter(track -> track.getName().contains(name)).collect(Collectors.toList()));
        }
    }
}
