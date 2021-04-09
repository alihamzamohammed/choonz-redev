package com.qa.choonz.service;

import java.util.List;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.repository.AlbumRepository;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.persistence.repository.GenreRepository;
import com.qa.choonz.persistence.repository.PlaylistRepository;
import com.qa.choonz.persistence.repository.TrackRepository;

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

    public List<Album> searchAlbums(String name) {
        return albumRepo.findByName(name);
    }

    public List<Artist> searchArtists(String name) {
        return artistRepo.findByName(name);
    }

    public List<Genre> searchGenres(String name) {
        return genreRepo.findByName(name);
    }

    public List<Playlist> searchPlaylists(String name) {
        return playlistRepo.findByName(name);
    }

    public List<Track> searchTracks(String name) {
        return trackRepo.findByName(name);
    }
}
