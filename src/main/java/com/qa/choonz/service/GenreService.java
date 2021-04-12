package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

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
    private AlbumService albumService;

    public GenreService(GenreRepository genreRepo, GenreMapper map, AlbumService albumService) {
        super();
        this.genreRepo = genreRepo;
        this.genreMap = map;
        this.albumService = albumService;
    }

    public GenreDTO create(Genre genre) {
        Genre newGenre = genreRepo.save(genre);

        return genreMap.mapToDTO(newGenre);

    }

    public List<GenreDTO> readAll() {
        List<Genre> genres = genreRepo.findAll();
        return genreMap.listMapToDTO(genres);

    }

    public GenreDTO readById(int id) {
        Genre genre = genreRepo.findById(id).orElseThrow(GenreNotFoundException::new);
        return genreMap.mapToDTO(genre);
    }

    public GenreDTO update(Genre genre, int id) {
        Genre genreInDb = genreRepo.findById(id).orElseThrow(GenreNotFoundException::new);
        genreInDb.setName(genre.getName() != null ? genre.getName() : genreInDb.getName());
        genreInDb.setDescription(genre.getDescription() != null ? genre.getDescription() : genreInDb.getDescription());
        genreInDb.setAlbums(genre.getAlbums() != null ? genre.getAlbums() : genreInDb.getAlbums());

        Genre updatedGenre = genreRepo.save(genreInDb);

        return genreMap.mapToDTO(updatedGenre);
    }

    public boolean delete(int id) {
        Genre genre = genreRepo.findById(id).orElseThrow(GenreNotFoundException::new);
        // genre.getAlbums().forEach(album -> {
        // album.setGenre(album.getGenre().stream().filter(g ->
        // !g.getName().equals(genre.getName()))
        // .collect(Collectors.toList()));
        // albumService.update(album, album.getId());
        // });
        genreRepo.deleteById(genre.getId());
        return !genreRepo.existsById(genre.getId());
    }

}
