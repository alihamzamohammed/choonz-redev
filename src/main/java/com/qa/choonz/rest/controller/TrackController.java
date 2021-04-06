package com.qa.choonz.rest.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.service.TrackService;

@RestController
@RequestMapping("/tracks")
@CrossOrigin
public class TrackController {

    private TrackService service;

    public TrackController(TrackService service) {
        super();
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<TrackDTO> create(@RequestBody Track track) {
    	TrackDTO newTrack = service.create(track);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", String.valueOf(newTrack.getId()));
        
        return new ResponseEntity<TrackDTO>(newTrack, headers, HttpStatus.CREATED);
    }

    @GetMapping("/read")
    public ResponseEntity<List<TrackDTO>> readAll() {
    	List<TrackDTO> data = service.readAll();
		return new ResponseEntity<List<TrackDTO>>(data, HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<TrackDTO> readById(@PathVariable long id) {
    	 TrackDTO track = service.readById(id);
         
         return new ResponseEntity<TrackDTO>(track, HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<TrackDTO> update(@RequestBody Track track, @PathVariable long id) {
        TrackDTO updateTrack = service.update(track, id);
    	 
         return new ResponseEntity<TrackDTO>(updateTrack, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id) {
    	service.delete(id);
    	return new ResponseEntity<Boolean>(true, HttpStatus.NO_CONTENT); 
    }
}
