package com.qa.choonz.rest.controller;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.AlbumRelationshipDTO;
import com.qa.choonz.rest.dto.ArtistRelationshipDTO;
import com.qa.choonz.rest.dto.GenreRelationshipDTO;
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
class TrackControllerIntegrationTest {

        @Autowired
        private MockMvc mvc;

        @Autowired
        private ObjectMapper objectMapper;

        private GenreRelationshipDTO genre = new GenreRelationshipDTO(1, "pop", "very loud music");
        private ArtistRelationshipDTO validArtistDTO1 = new ArtistRelationshipDTO(1, "artist number 1");
        private ArtistRelationshipDTO validArtistDTO2 = new ArtistRelationshipDTO(2, "artist number 2");
        private AlbumRelationshipDTO album = new AlbumRelationshipDTO(1, "artist number 1 first album", List.of(genre),
                        "album.png");

        private TrackDTO validTrack = new TrackDTO(1, "Track 1", album, 1, "A regular test track with no name",
                        validArtistDTO1, List.of(validArtistDTO2));

        @WithAnonymousUser
        @Test
        void readTrackByIdTest() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET,
                                "/tracks/read/1");
                mockRequest.accept(MediaType.APPLICATION_JSON);

                ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();

                ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                                .json(objectMapper.writeValueAsString(validTrack));

                mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
        }

        @WithAnonymousUser
        @Test
        void readAllTracksTest() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET,
                                "/tracks/read");
                mockRequest.accept(MediaType.APPLICATION_JSON);

                ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();

                ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                                .json(objectMapper.writeValueAsString(List.of(validTrack)));

                mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);

        }

        @WithMockUser(authorities = { "USER" })
        @Test
        void createTrackTest() throws Exception {
                Album newAlbum = new Album();
                newAlbum.setId(1);
                AlbumRelationshipDTO newAlbumDTO = new AlbumRelationshipDTO();
                newAlbumDTO.setId(1);

                Track trackToSave = new Track("New Track", newAlbum, 10, "Example lyrics");
                TrackDTO expectedTrack = new TrackDTO(2, "New Track", newAlbumDTO, 10, "Example lyrics", null, null);

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST,
                                "/tracks/create");

                mockRequest.contentType(MediaType.APPLICATION_JSON);
                mockRequest.content(objectMapper.writeValueAsString(trackToSave));
                mockRequest.accept(MediaType.APPLICATION_JSON);

                ResultMatcher statusMatcher = MockMvcResultMatchers.status().isCreated();

                ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                                .json(objectMapper.writeValueAsString(expectedTrack));

                mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
        }

        @WithMockUser(authorities = { "USER" })
        @Test
        void updateTrackTest() throws Exception {
                Album newAlbum = new Album();
                newAlbum.setId(1);
                AlbumRelationshipDTO newAlbumDTO = new AlbumRelationshipDTO();
                newAlbumDTO.setId(1);

                Track trackToSave = new Track("New Track", newAlbum, 10, "Example lyrics");
                TrackDTO expectedTrack = new TrackDTO(1, "New Track", newAlbumDTO, 10, "Example lyrics",
                                validArtistDTO1, List.of(validArtistDTO2));

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.PUT,
                                "/tracks/update/1");

                mockRequest.contentType(MediaType.APPLICATION_JSON);
                mockRequest.content(objectMapper.writeValueAsString(trackToSave));
                mockRequest.accept(MediaType.APPLICATION_JSON);

                ResultMatcher statusMatcher = MockMvcResultMatchers.status().isAccepted();

                ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                                .json(objectMapper.writeValueAsString(expectedTrack));

                mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
        }

        @WithMockUser(authorities = { "USER" })
        @Test
        void deleteTrackTest() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.DELETE,
                                "/tracks/delete/1");

                ResultMatcher statusMatcher = MockMvcResultMatchers.status().isNoContent();

                mvc.perform(mockRequest).andExpect(statusMatcher);
        }
}
