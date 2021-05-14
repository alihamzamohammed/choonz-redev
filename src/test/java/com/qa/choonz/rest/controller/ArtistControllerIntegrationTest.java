package com.qa.choonz.rest.controller;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.rest.dto.AlbumArtistRelationshipDTO;
import com.qa.choonz.rest.dto.ArtistDTO;
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
class ArtistControllerIntegrationTest {

        @Autowired
        private MockMvc mvc;

        @Autowired
        private ObjectMapper objectMapper;

        private TrackRelationshipDTO track = new TrackRelationshipDTO(1, "Track 1", 1,
                        "A regular test track with no name");
        private GenreRelationshipDTO genre = new GenreRelationshipDTO(1, "pop", "very loud music");
        private AlbumArtistRelationshipDTO albums = new AlbumArtistRelationshipDTO(1, "artist number 1 first album",
                        List.of(track), List.of(genre), "album.png");
        private ArtistDTO validArtistDTO1 = new ArtistDTO(1, "artist number 1", List.of(albums), List.of());
        private ArtistDTO validArtistDTO2 = new ArtistDTO(2, "artist number 2", List.of(), List.of(track));

        @WithAnonymousUser
        @Test
        void readArtistByIdTest() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET,
                                "/artists/read/1");
                mockRequest.accept(MediaType.APPLICATION_JSON);

                ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();

                ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                                .json(objectMapper.writeValueAsString(validArtistDTO1));

                mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
        }

        @WithAnonymousUser
        @Test
        void readAllArtistsTest() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET,
                                "/artists/read");
                mockRequest.accept(MediaType.APPLICATION_JSON);

                ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();

                ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                                .json(objectMapper.writeValueAsString(List.of(validArtistDTO1, validArtistDTO2)));

                System.out.println(validArtistDTO1);
                System.out.println(validArtistDTO2);

                mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);

        }

        @WithMockUser(authorities = { "USER" })
        @Test
        void createArtistTest() throws Exception {
                Artist artistToSave = new Artist("Eminem", List.of(), List.of());
                ArtistDTO expectedArtist = new ArtistDTO(3, "Eminem", List.of(), List.of());

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST,
                                "/artists/create");

                mockRequest.contentType(MediaType.APPLICATION_JSON);
                mockRequest.content(objectMapper.writeValueAsString(artistToSave));
                mockRequest.accept(MediaType.APPLICATION_JSON);

                ResultMatcher statusMatcher = MockMvcResultMatchers.status().isCreated();

                ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                                .json(objectMapper.writeValueAsString(expectedArtist));

                mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
        }

        @WithMockUser(authorities = { "USER" })
        @Test
        void updateArtistTest() throws Exception {
                Artist artistToSave = new Artist("Slim Shady");
                ArtistDTO expectedArtist = new ArtistDTO(1, "Slim Shady", null, List.of());

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.PUT,
                                "/artists/update/1");

                mockRequest.contentType(MediaType.APPLICATION_JSON);
                mockRequest.content(objectMapper.writeValueAsString(artistToSave));
                mockRequest.accept(MediaType.APPLICATION_JSON);

                ResultMatcher statusMatcher = MockMvcResultMatchers.status().isAccepted();

                ResultMatcher contentMatcher = MockMvcResultMatchers.content()
                                .json(objectMapper.writeValueAsString(expectedArtist));

                mvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
        }

        @WithMockUser(authorities = { "USER" })
        @Test
        void deleteArtistTest() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.DELETE,
                                "/artists/delete/1");

                ResultMatcher statusMatcher = MockMvcResultMatchers.status().isNoContent();

                mvc.perform(mockRequest).andExpect(statusMatcher);
        }
}
