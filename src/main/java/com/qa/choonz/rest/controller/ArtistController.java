package com.qa.choonz.rest.controller;

import java.util.List;

import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.service.ArtistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artists")
@CrossOrigin
@PreAuthorize("hasAuthority('USER')")
public class ArtistController {

    private ArtistService service;

    @Autowired
    public ArtistController(ArtistService service) {
        super();
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<ArtistDTO> create(@RequestBody Artist artist) {
        return new ResponseEntity<>(this.service.create(artist), HttpStatus.CREATED);
    }

    @GetMapping("/read")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ArtistDTO>> read() {
        return new ResponseEntity<>(this.service.read(), HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ArtistDTO> read(@PathVariable int id) {
        return new ResponseEntity<>(this.service.read(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ArtistDTO> update(@RequestBody Artist artist, @PathVariable int id) {
        return new ResponseEntity<>(this.service.update(artist, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ArtistDTO> delete(@PathVariable int id) {
        return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
