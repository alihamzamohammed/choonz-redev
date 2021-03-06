package com.qa.choonz.rest.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class PlaylistDTOUnitTest {

	@Test
	void equalsVerify() {

		EqualsVerifier.simple().forClass(PlaylistDTO.class).withPrefabValues(AlbumDTO.class,
				new AlbumDTO("Name", List.of(), List.of(), "Cover", new ArtistRelationshipDTO()), new AlbumDTO())
				.withPrefabValues(PlaylistDTO.class, new PlaylistDTO("Name", "Description", "Artwork", List.of()),
						new PlaylistDTO())
				.withPrefabValues(ArtistDTO.class, new ArtistDTO("Name", List.of(), List.of()), new ArtistDTO())
				.verify();

	}

	@Test
	void toStringTest() {
		StringBuilder builder = new StringBuilder();
		PlaylistDTO playlistDTO = new PlaylistDTO(1, "Name", "Description", "Artwork", List.of());

		builder.append("PlaylistDTO [id=").append(playlistDTO.getId()).append(", name=").append(playlistDTO.getName())
				.append(", description=").append(playlistDTO.getDescription()).append(", artwork=")
				.append(playlistDTO.getArtwork()).append(", tracks=").append(playlistDTO.getTracks()).append("]");

		assertThat(playlistDTO.toString()).hasToString(builder.toString());

	}

}
