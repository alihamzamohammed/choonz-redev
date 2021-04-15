package com.qa.choonz.rest.controller;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.dto.ArtistRelationshipDTO;
import com.qa.choonz.rest.dto.GenreRelationshipDTO;
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
class AlbumControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private TrackRelationshipDTO track = new TrackRelationshipDTO(1, "Track 1", 1, "A regular test track with no name");
    private ArtistRelationshipDTO artist = new ArtistRelationshipDTO(1, "artist number 1");
    private GenreRelationshipDTO genre = new GenreRelationshipDTO(1, "pop", "very loud music");

    private AlbumDTO validAlbum = new AlbumDTO(1, "artist number 1 first album", List.of(track), List.of(genre),
            "album.png", artist);

    @WithAnonymousUser
    @Test
    void readAlbumByIdTest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/albums/read/1");
        mockRequest.accept(MediaType.APPLICATION_JSON);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();

        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(validAlbum));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
    }

    @WithAnonymousUser
    @Test
    void readAllAlbumsTest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/albums/read");
        mockRequest.accept(MediaType.APPLICATION_JSON);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();

        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(List.of(validAlbum)));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);

    }

    @WithMockUser(authorities = { "USER" })
    @Test
    void createAlbumTest() throws Exception {
        Artist newArtist = new Artist();
        newArtist.setId(1);
        ArtistRelationshipDTO newArtistDTO = new ArtistRelationshipDTO();
        newArtistDTO.setId(1);
        Album albumToSave = new Album("New album", newArtist, null, "test.png");
        AlbumDTO expectedAlbum = new AlbumDTO(2, "New album", null, null, "test.png", newArtistDTO);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/albums/create");

        mockRequest.contentType(MediaType.APPLICATION_JSON);
        mockRequest.content(objectMapper.writeValueAsString(albumToSave));
        mockRequest.accept(MediaType.APPLICATION_JSON);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isCreated();

        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(expectedAlbum));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
    }

    @WithMockUser(authorities = { "USER" })
    @Test
    void updateAlbumTest() throws Exception {
        Artist newArtist = new Artist();
        newArtist.setId(1);
        ArtistRelationshipDTO newArtistDTO = new ArtistRelationshipDTO();
        newArtistDTO.setId(1);
        Album albumToSave = new Album("New album", newArtist, null, "test.png");
        AlbumDTO expectedAlbum = new AlbumDTO(1, "New album", null, List.of(genre), "test.png", newArtistDTO);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.PUT, "/albums/update/1");

        mockRequest.contentType(MediaType.APPLICATION_JSON);
        mockRequest.content(objectMapper.writeValueAsString(albumToSave));
        mockRequest.accept(MediaType.APPLICATION_JSON);

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isAccepted();

        ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                .json(objectMapper.writeValueAsString(expectedAlbum));

        mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
    }

    @WithMockUser(authorities = { "USER" })
    @Test
    void deleteAlbumTest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.DELETE,
                "/albums/delete/1");

        ResultMatcher statusMatcher = MockMvcResultMatchers.status().isNoContent();

        mvc.perform(mockRequest).andExpect(statusMatcher);
    }
}
