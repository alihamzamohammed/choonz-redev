package com.qa.choonz.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.dto.ArtistRelationshipDTO;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.service.SearchService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithAnonymousUser;

@SpringBootTest
class SearchControllerUnitTest {

    @Autowired
    private SearchController controller;

    @MockBean
    private SearchService service;

    private AlbumDTO expectedAlbum;

    private ArtistDTO expectedArtist;

    private GenreDTO expectedGenre;

    private PlaylistDTO expectedPlaylist;
    private PlaylistDTO expectedPlaylistWithCA;

    private TrackDTO expectedTrack;
    private TrackDTO expectedTrackWithCA;

    @BeforeEach
    void init() {
        expectedAlbum = new AlbumDTO();
        expectedAlbum.setName("Good Name");

        expectedArtist = new ArtistDTO();
        expectedArtist.setName("Good Artist");

        expectedGenre = new GenreDTO();
        expectedGenre.setName("Good Genre");

        expectedTrack = new TrackDTO();
        expectedTrackWithCA = new TrackDTO();
        expectedTrack.setName("Good Track");
        expectedTrackWithCA.setName("Good Track");
        expectedTrackWithCA.setContributingArtists(List.of(new ArtistRelationshipDTO()));

        expectedPlaylist = new PlaylistDTO();
        expectedPlaylistWithCA = new PlaylistDTO();
        expectedPlaylist.setName("Good Playlist");
        expectedPlaylistWithCA.setName("Good Playlist");
        expectedPlaylistWithCA.setTracks(List.of(expectedTrackWithCA));

    }

    @WithAnonymousUser
    @Test
    void searchAlbumsTest() {
        when(service.searchAlbums(Mockito.anyString())).thenReturn(List.of(expectedAlbum));
        assertThat(controller.searchAlbums("good"))
                .isEqualTo(new ResponseEntity<List<AlbumDTO>>(List.of(expectedAlbum), HttpStatus.OK));
        verify(service, times(1)).searchAlbums(Mockito.anyString());
    }

    @WithAnonymousUser
    @Test
    void searchArtistsTest() {
        when(service.searchArtists(Mockito.anyString())).thenReturn(List.of(expectedArtist));
        assertThat(controller.searchArtists("good"))
                .isEqualTo(new ResponseEntity<List<ArtistDTO>>(List.of(expectedArtist), HttpStatus.OK));
        verify(service, times(1)).searchArtists(Mockito.anyString());
    }

    @WithAnonymousUser
    @Test
    void searchGenresTest() {
        when(service.searchGenres(Mockito.anyString())).thenReturn(List.of(expectedGenre));
        assertThat(controller.searchGenres("good"))
                .isEqualTo(new ResponseEntity<List<GenreDTO>>(List.of(expectedGenre), HttpStatus.OK));
        verify(service, times(1)).searchGenres(Mockito.anyString());
    }

    @WithAnonymousUser
    @Test
    void searchPlaylistsWithContributingArtistsTest() {
        when(service.searchPlaylists(Mockito.anyString(), eq(true)))
                .thenReturn(List.of(expectedPlaylist, expectedPlaylistWithCA));
        assertThat(controller.searchPlaylists("good", true)).isEqualTo(new ResponseEntity<List<PlaylistDTO>>(
                List.of(expectedPlaylist, expectedPlaylistWithCA), HttpStatus.OK));
        verify(service, times(1)).searchPlaylists(Mockito.anyString(), eq(true));
    }

    @WithAnonymousUser
    @Test
    void searchPlaylistsWithoutContributingArtistsTest() {
        when(service.searchPlaylists(Mockito.anyString(), eq(false))).thenReturn(List.of(expectedPlaylist));
        assertThat(controller.searchPlaylists("good", false))
                .isEqualTo(new ResponseEntity<List<PlaylistDTO>>(List.of(expectedPlaylist), HttpStatus.OK));
        verify(service, times(1)).searchPlaylists(Mockito.anyString(), eq(false));
    }

    @WithAnonymousUser
    @Test
    void searchTracksWithContributingArtistsTest() {
        when(service.searchTracks(Mockito.anyString(), eq(true)))
                .thenReturn(List.of(expectedTrack, expectedTrackWithCA));
        assertThat(controller.searchTracks("good", true)).isEqualTo(
                new ResponseEntity<List<TrackDTO>>(List.of(expectedTrack, expectedTrackWithCA), HttpStatus.OK));
        verify(service, times(1)).searchTracks(Mockito.anyString(), eq(true));
    }

    @WithAnonymousUser
    @Test
    void searchTracksWithoutContributingArtistsTest() {
        when(service.searchTracks(Mockito.anyString(), eq(false))).thenReturn(List.of(expectedTrack));
        assertThat(controller.searchTracks("good", false))
                .isEqualTo(new ResponseEntity<List<TrackDTO>>(List.of(expectedTrack), HttpStatus.OK));
        verify(service, times(1)).searchTracks(Mockito.anyString(), eq(false));
    }

}
