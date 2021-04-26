package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.qa.choonz.mapper.AlbumMapper;
import com.qa.choonz.mapper.ArtistMapper;
import com.qa.choonz.mapper.GenreMapper;
import com.qa.choonz.mapper.PlaylistMapper;
import com.qa.choonz.mapper.TracksMapper;
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
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.dto.ArtistRelationshipDTO;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.rest.dto.TrackDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SearchServiceUnitTest {

    @Autowired
    private SearchService service;

    @MockBean
    private AlbumMapper albumMapper;

    @MockBean
    private ArtistMapper artistMapper;

    @MockBean
    private GenreMapper genreMapper;

    @MockBean
    private PlaylistMapper playlistMapper;

    @MockBean
    private TracksMapper trackMapper;

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

    private List<Album> albums;
    private List<Artist> artists;
    private List<Genre> genres;
    private List<Playlist> playlists;
    private List<Track> tracks;

    private Album goodAlbum;
    private Album badAlbum;
    private AlbumDTO expectedAlbum;

    private Artist goodArtist;
    private Artist badArtist;
    private ArtistDTO expectedArtist;

    private Genre goodGenre;
    private Genre badGenre;
    private GenreDTO expectedGenre;

    private Playlist goodPlaylist;
    private Playlist badPlaylist;
    private Playlist playlistWithCA;
    private PlaylistDTO expectedPlaylist;
    private PlaylistDTO expectedPlaylistWithCA;

    private Track goodTrack;
    private Track badTrack;
    private Track trackWithCA;
    private TrackDTO expectedTrack;
    private TrackDTO expectedTrackWithCA;

    @BeforeEach
    void init() {
        goodAlbum = new Album();
        badAlbum = new Album();
        expectedAlbum = new AlbumDTO();
        goodAlbum.setName("Good Name");
        badAlbum.setName("Bad Name");
        expectedAlbum.setName("Good Name");
        albums = List.of(goodAlbum, badAlbum);

        goodArtist = new Artist();
        badArtist = new Artist();
        expectedArtist = new ArtistDTO();
        goodArtist.setName("Good Artist");
        badArtist.setName("Bad Artist");
        expectedArtist.setName("Good Artist");
        artists = List.of(goodArtist, badArtist);

        goodGenre = new Genre();
        badGenre = new Genre();
        expectedGenre = new GenreDTO();
        goodGenre.setName("Good Genre");
        badGenre.setName("Bad Genre");
        expectedGenre.setName("Good Genre");
        genres = List.of(goodGenre, badGenre);

        goodTrack = new Track();
        badTrack = new Track();
        trackWithCA = new Track();
        expectedTrack = new TrackDTO();
        expectedTrackWithCA = new TrackDTO();
        goodTrack.setName("Good Track");
        badTrack.setName("Bad Track");
        trackWithCA.setName("Good Track");
        trackWithCA.setContributingArtists(List.of(new Artist()));
        expectedTrack.setName("Good Track");
        expectedTrackWithCA.setName("Good Track");
        expectedTrackWithCA.setContributingArtists(List.of(new ArtistRelationshipDTO()));
        tracks = List.of(goodTrack, badTrack);

        goodPlaylist = new Playlist();
        badPlaylist = new Playlist();
        playlistWithCA = new Playlist();
        expectedPlaylist = new PlaylistDTO();
        expectedPlaylistWithCA = new PlaylistDTO();
        goodPlaylist.setName("Good Playlist");
        playlistWithCA.setName("Good Playlist");
        playlistWithCA.setTracks(List.of(trackWithCA));
        badPlaylist.setName("Bad Playlist");
        expectedPlaylist.setName("Good Playlist");
        expectedPlaylistWithCA.setName("Good Playlist");
        expectedPlaylistWithCA.setTracks(List.of(expectedTrackWithCA));
        playlists = List.of(goodPlaylist, badPlaylist, playlistWithCA);
    }

    @Test
    void searchAlbumsTest() {
        when(albumRepo.findAll()).thenReturn(albums);
        when(albumMapper.mapToDTO(Mockito.anyList())).thenReturn(List.of(expectedAlbum));
        assertThat(service.searchAlbums("good")).isEqualTo(List.of(expectedAlbum));
        verify(albumRepo, times(1)).findAll();
        verify(albumMapper, times(1)).mapToDTO(Mockito.anyList());
    }

    @Test
    void searchArtistsTest() {
        when(artistRepo.findAll()).thenReturn(artists);
        when(artistMapper.listMapToDTO(Mockito.anyList())).thenReturn(List.of(expectedArtist));
        assertThat(service.searchArtists("good")).isEqualTo(List.of(expectedArtist));
        verify(artistRepo, times(1)).findAll();
        verify(artistMapper, times(1)).listMapToDTO(Mockito.anyList());
    }

    @Test
    void searchGenresTest() {
        when(genreRepo.findAll()).thenReturn(genres);
        when(genreMapper.listMapToDTO(Mockito.anyList())).thenReturn(List.of(expectedGenre));
        assertThat(service.searchGenres("good")).isEqualTo(List.of(expectedGenre));
        verify(genreRepo, times(1)).findAll();
        verify(genreMapper, times(1)).listMapToDTO(Mockito.anyList());
    }

    @Test
    void searchPlaylistsWithContributingArtistsTest() {
        when(playlistRepo.findAll()).thenReturn(playlists);
        when(playlistMapper.mapToDTO(Mockito.anyList())).thenReturn(List.of(expectedPlaylist, expectedPlaylistWithCA));
        assertThat(service.searchPlaylists("good", true)).isEqualTo(List.of(expectedPlaylist, expectedPlaylistWithCA));
        verify(playlistRepo, times(1)).findAll();
        verify(playlistMapper, times(1)).mapToDTO(Mockito.anyList());
    }

    @Test
    void searchPlaylistWithoutContributingArtistsTest() {
        when(playlistRepo.findAll()).thenReturn(playlists);
        when(playlistMapper.mapToDTO(Mockito.anyList())).thenReturn(List.of(expectedPlaylist));
        assertThat(service.searchPlaylists("good", false)).isEqualTo(List.of(expectedPlaylist));
        verify(playlistRepo, times(1)).findAll();
        verify(playlistMapper, times(1)).mapToDTO(Mockito.anyList());
    }

    @Test
    void searchTracksWithContributingArtistsTest() {
        when(trackRepo.findAll()).thenReturn(tracks);
        when(trackMapper.listMapToDTO(Mockito.anyList())).thenReturn(List.of(expectedTrack, expectedTrackWithCA));
        assertThat(service.searchTracks("good", true)).isEqualTo(List.of(expectedTrack, expectedTrackWithCA));
        verify(trackRepo, times(1)).findAll();
        verify(trackMapper, times(1)).listMapToDTO(Mockito.anyList());
    }

    @Test
    void searchTracksWithoutContributingArtistTest() {
        when(trackRepo.findAll()).thenReturn(tracks);
        when(trackMapper.listMapToDTO(Mockito.anyList())).thenReturn(List.of(expectedTrack));
        assertThat(service.searchTracks("good", false)).isEqualTo(List.of(expectedTrack));
        verify(trackRepo, times(1)).findAll();
        verify(trackMapper, times(1)).listMapToDTO(Mockito.anyList());
    }

}
