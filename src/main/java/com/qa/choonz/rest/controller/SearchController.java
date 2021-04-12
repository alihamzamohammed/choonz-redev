package com.qa.choonz.rest.controller;

import java.util.List;

import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.service.SearchService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {

    private SearchService service;

    public SearchController(SearchService service) {
        super();
        this.service = service;
    }

    @GetMapping("/albums/{name}")
    public ResponseEntity<List<AlbumDTO>> searchAlbums(@PathVariable String name) {
        return new ResponseEntity<>(service.searchAlbums(name), HttpStatus.OK);
    }

    @GetMapping("/artists/{name}")
    public ResponseEntity<List<ArtistDTO>> searchArtists(@PathVariable String name) {
        return new ResponseEntity<>(service.searchArtists(name), HttpStatus.OK);
    }

    @GetMapping("/genres/{name}")
    public ResponseEntity<List<GenreDTO>> searchGenres(@PathVariable String name) {
        return new ResponseEntity<>(service.searchGenres(name), HttpStatus.OK);
    }

    @GetMapping("/tracks/{name}")
    public ResponseEntity<List<TrackDTO>> searchTracks(@PathVariable String name) {
        return new ResponseEntity<>(service.searchTracks(name), HttpStatus.OK);
    }

    @GetMapping("/playlists/{name}")
    public ResponseEntity<List<PlaylistDTO>> searchPlaylists(@PathVariable String name) {
        return new ResponseEntity<>(service.searchPlaylists(name), HttpStatus.OK);
    }
}
