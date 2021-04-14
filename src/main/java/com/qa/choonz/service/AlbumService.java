package com.qa.choonz.service;

import java.util.List;

import com.qa.choonz.exception.AlbumNotFoundException;
import com.qa.choonz.mapper.AlbumMapper;
import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.repository.AlbumRepository;
import com.qa.choonz.rest.dto.AlbumDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {

	private AlbumRepository repo;
	private AlbumMapper mapper;

	@Autowired
	public AlbumService(AlbumRepository repo, AlbumMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	public AlbumDTO create(Album album) {
		Album created = this.repo.save(album);
		return this.mapper.mapToDTO(created);
	}

	
	public List<AlbumDTO> read() {

		List<Album> albumDTO = repo.findAll();

		return this.mapper.mapToDTO(albumDTO);
	}

	public AlbumDTO read(int id) {
		Album found = this.repo.findById(id).orElseThrow(AlbumNotFoundException::new);
		return this.mapper.mapToDTO(found);
	}

	public AlbumDTO update(Album album, int id) {
		Album toUpdate = this.repo.findById(id).orElseThrow(AlbumNotFoundException::new);
		toUpdate.setName(album.getName() != null ? album.getName() : toUpdate.getName());
		toUpdate.setTracks(album.getTracks());
		toUpdate.setArtist(album.getArtist());
		toUpdate.setCover(album.getCover() != null ? album.getCover() : toUpdate.getCover());
		toUpdate.setGenre(album.getGenre() != null ? album.getGenre() : toUpdate.getGenre());
		Album updated = this.repo.save(toUpdate);
		return this.mapper.mapToDTO(updated);
	}

	public boolean delete(int id) {
		Album album = this.repo.findById(id).orElseThrow(AlbumNotFoundException::new);
		this.repo.deleteById(album.getId());
		return !this.repo.existsById(album.getId());
	}

}
