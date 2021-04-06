package com.qa.choonz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.qa.choonz.exception.GenreNotFoundException;
import com.qa.choonz.mapper.GenreMapper;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.repository.GenreRepository;
import com.qa.choonz.rest.dto.GenreDTO;

@Service
public class GenreService {

    private GenreRepository genreRepo;
    private GenreMapper genreMap;

    public GenreService(GenreRepository genreRepo, GenreMapper map) {
        super();
        this.genreRepo = genreRepo;
        this.genreMap = genreMap;
    }

    public GenreDTO create(Genre genre) {
        Genre newGenre = genreRepo.save(genre);
        
        return genreMap.mapToDTO(newGenre);
        
    }

    public List<GenreDTO> readAll() {
      List <Genre> genres = genreRepo.findAll(); 
      List<GenreDTO> genreDTOs = new ArrayList<GenreDTO>();
      
      genres.forEach(g -> genreDTOs.add(genreMap.mapToDTO(g)));
      return genreDTOs;
      
    }

    public GenreDTO readById(long id) {
       Optional <Genre> genre = genreRepo.findById(id);
       
       if (genre.isPresent()) {
			return genreMap.mapToDTO(genre.get());
		} else {
			throw new GenreNotFoundException();
		}
    }

    public GenreDTO update(Genre genre, long id) {
        Optional<Genre> genreInDbOpt = genreRepo.findById(id);
        Genre genreInDb;
        
        if (genreInDbOpt.isPresent()) {
			genreInDb = genreInDbOpt.get();
		} else {
			throw new GenreNotFoundException();
		}
        
        genreInDb.setName(genre.getName());
        genreInDb.setDescription(genre.getDescription());
        genreInDb.setAlbums(genre.getAlbums());
        
        Genre updatedGenre = genreRepo.save(genreInDb);
        
        return genreMap.mapToDTO(updatedGenre);
    }

    public boolean delete(long id) {
    	
    	if (!genreRepo.existsById(id)) {
			throw new GenreNotFoundException();
		}
		genreRepo.deleteById(id);

		boolean exists = genreRepo.existsById(id);

		return !exists;
		
    }

}
