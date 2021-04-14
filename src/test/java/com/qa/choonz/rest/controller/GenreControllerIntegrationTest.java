package com.qa.choonz.rest.controller;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.rest.dto.AlbumGenresRelationshipDTO;
import com.qa.choonz.rest.dto.ArtistRelationshipDTO;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.rest.dto.TrackRelationshipDTO;

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
class GenreControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private TrackRelationshipDTO track = new TrackRelationshipDTO(1, "Track 1", 1, "A regular test track with no name");
    private ArtistRelationshipDTO artist = new ArtistRelationshipDTO(1, "artist number 1");

    private AlbumGenresRelationshipDTO album = new AlbumGenresRelationshipDTO(1, "artist number 1 first album",
            List.of(track), "album.png", artist);
    private GenreDTO validGenre = new GenreDTO(1, "pop", "very loud music", List.of(album));

    @WithAnonymousUser
    @Test
    void readGenresByIdTest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/genres/read/1");
        mockRequest.accept(MediaType.APPLICATION_JSON);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();

        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(validGenre));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
    }

    @WithAnonymousUser
    @Test
    void readAllGenressTest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/genres/read");
        mockRequest.accept(MediaType.APPLICATION_JSON);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();

        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(List.of(validGenre)));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);

    }

    @WithMockUser(authorities = { "USER" })
    @Test
    void createGenresTest() throws Exception {
        Genre genreToSave = new Genre("New Genre", "New Description");
        GenreDTO expectedGenre = new GenreDTO(2, "New Genre", "New Description", null);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/genres/create");

        mockRequest.contentType(MediaType.APPLICATION_JSON);
        mockRequest.content(objectMapper.writeValueAsString(genreToSave));
        mockRequest.accept(MediaType.APPLICATION_JSON);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isCreated();

        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(expectedGenre));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
    }

    @WithMockUser(authorities = { "USER" })
    @Test
    void updateGenresTest() throws Exception {
        Genre genreToSave = new Genre("New Genre", "New Description");
        GenreDTO expectedGenre = new GenreDTO(1, "New Genre", "New Description", List.of());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.PUT, "/genres/update/1");

        mockRequest.contentType(MediaType.APPLICATION_JSON);
        mockRequest.content(objectMapper.writeValueAsString(genreToSave));
        mockRequest.accept(MediaType.APPLICATION_JSON);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isAccepted();

        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(expectedGenre));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
    }

    @WithMockUser(authorities = { "USER" })
    @Test
    void deleteGenresTest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.DELETE,
                "/genres/delete/1");

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isNoContent();

        mvc.perform(mockRequest).andExpect(statusMatcher);
    }
}
