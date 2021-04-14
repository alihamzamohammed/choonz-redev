package com.qa.choonz.rest.controller;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.rest.dto.AlbumRelationshipDTO;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.rest.dto.ArtistRelationshipDTO;
import com.qa.choonz.rest.dto.GenreRelationshipDTO;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.rest.dto.TrackDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:test-schema.sql",
        "classpath:test-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class PlaylistControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private GenreRelationshipDTO genre = new GenreRelationshipDTO(1, "pop", "very loud music");
    private ArtistRelationshipDTO validArtistDTO1 = new ArtistRelationshipDTO(1, "artist number 1");
    private ArtistRelationshipDTO validArtistDTO2 = new ArtistRelationshipDTO(2, "artist number 2");
    private AlbumRelationshipDTO album = new AlbumRelationshipDTO(1, "artist number 1 first album", List.of(genre),
            "album.png");

    private TrackDTO track = new TrackDTO(1, "Track 1", album, 1, "A regular test track with no name", validArtistDTO1,
            List.of(validArtistDTO2));

    private PlaylistDTO validPlaylist = new PlaylistDTO(1, "My Playlist", "my favourite songs", "playlist.png",
            List.of(track));

    @WithAnonymousUser
    @Test
    void readPlaylistByIdTest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/playlists/read/1");
        mockRequest.accept(MediaType.APPLICATION_JSON);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();

        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(validPlaylist));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
    }

    @WithAnonymousUser
    @Test
    void readAllPlaylistsTest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/playlists/read");
        mockRequest.accept(MediaType.APPLICATION_JSON);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();

        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(List.of(validPlaylist)));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);

    }

    @WithMockUser(authorities = { "USER" })
    @Test
    void createPlaylistTest() throws Exception {
        Playlist playlistToSave = new Playlist("new playlist", "test playlist", "test.png", List.of());
        PlaylistDTO expectedPlaylist = new PlaylistDTO(2, "new playlist", "test playlist", "test.png", List.of());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST,
                "/playlists/create");

        mockRequest.contentType(MediaType.APPLICATION_JSON);
        mockRequest.content(objectMapper.writeValueAsString(playlistToSave));
        mockRequest.accept(MediaType.APPLICATION_JSON);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isCreated();

        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(expectedPlaylist));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
    }

    @WithMockUser(authorities = { "USER" })
    @Test
    void updatePlaylistTest() throws Exception {
        Playlist playlistToSave = new Playlist("updated playlist", "this is the updated one", "update.png", List.of());
        PlaylistDTO expectedPlaylist = new PlaylistDTO(1, "updated playlist", "this is the updated one", "update.png",
                List.of());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.PUT,
                "/playlists/update/1");

        mockRequest.contentType(MediaType.APPLICATION_JSON);
        mockRequest.content(objectMapper.writeValueAsString(playlistToSave));
        mockRequest.accept(MediaType.APPLICATION_JSON);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isAccepted();

        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(expectedPlaylist));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
    }

    @WithMockUser(authorities = { "USER" })
    @Test
    void deletePlaylistTest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.DELETE,
                "/playlists/delete/1");

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isNoContent();

        mvc.perform(mockRequest).andExpect(statusMatcher);
    }
}
