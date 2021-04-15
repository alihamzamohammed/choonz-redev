package com.qa.choonz.service;

import static org.mockito.Mockito.when;

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class SearchServiceUnitTest {

    @MockBean
    private AlbumRepository albumRepo;

    @MockBean
    private ArtistRepository artistRepo;

    @MockBean
    private GenreRepository genreRepo;

    @MockBean
    private PlaylistRepository playlistRepo;

    @MockBean
    private TrackRepository trackRepo;

    List<Album> albums;
    List<Artist> artists;
    List<Genre> genres;
    List<Playlist> playlists;
    List<Track> tracks;

    @BeforeEach
    void init() {
        Album goodAlbum = new Album();
        Album badAlbum = new Album();
        goodAlbum.setName("Good Name");
        badAlbum.setName("Bad Name");
        albums = List.of(goodAlbum, badAlbum);

        Artist goodArtist = new Artist();
        Artist badArtist = new Artist();
        goodArtist.setName("Good Artist");
        badArtist.setName("Bad Artist");
        artists = List.of(goodArtist, badArtist);

        Genre goodGenre = new Genre();
        Genre badGenre = new Genre();
        goodGenre.setName("Good Genre");
        badGenre.setName("Bad Genre");
        genres = List.of(goodGenre, badGenre);

        Playlist goodPlaylist = new Playlist();
        Playlist badPlaylist = new Playlist();
        goodPlaylist.setName("Good Playlist");
        badPlaylist.setName("Bad Playlist");
        playlists = List.of(goodPlaylist, badPlaylist);

        Track goodTrack = new Track();
        Track badTrack = new Track();
        goodTrack.setName("Good Track");
        badTrack.setName("Bad Track");
        tracks = List.of(goodTrack, badTrack);
    }

    @Test
    void searchAlbumsTest() {
        when(albumRepo.findAll()).thenReturn(albums);
    }

    @Test
    void searchArtistsTest() {

    }

    @Test
    void searchGenresTest() {

    }

    @Test
    void searchPlaylistsTest() {

    }

    @Test
    void searchTracksTest() {

    }

}
