package com.qa.choonz.rest.controller;

import java.util.List;

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.service.TrackService;

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
@RequestMapping("/tracks")
@CrossOrigin
@PreAuthorize("hasAuthority('USER')")
public class TrackController {

    private TrackService service;

    public TrackController(TrackService service) {
        super();
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<TrackDTO> create(@RequestBody Track track) {
        return new ResponseEntity<>(service.create(track), HttpStatus.CREATED);
    }

    @GetMapping("/read")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<TrackDTO>> readAll() {
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<TrackDTO> readById(@PathVariable int id) {
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TrackDTO> update(@RequestBody Track track, @PathVariable int id) {
        return new ResponseEntity<>(service.update(track, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable int id) {
        return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
