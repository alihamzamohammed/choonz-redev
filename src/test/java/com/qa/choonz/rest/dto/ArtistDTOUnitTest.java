package com.qa.choonz.rest.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class ArtistDTOUnitTest {

	@Test
	void equalsVerify() {
		EqualsVerifier.simple().forClass(ArtistDTO.class).withPrefabValues(AlbumDTO.class,
				new AlbumDTO("Name", List.of(), List.of(), "Cover", new ArtistRelationshipDTO()), new AlbumDTO())
				.withPrefabValues(PlaylistDTO.class, new PlaylistDTO("Name", "Description", "Artwork", List.of()),
						new PlaylistDTO())
				.verify();
	}

	@Test
	void toStringTest() {
		StringBuilder builder = new StringBuilder();
		ArtistDTO artistDTO = new ArtistDTO(1, "Name", List.of(), List.of());

		builder.append("ArtistDTO [id=").append(artistDTO.getId()).append(", name=").append(artistDTO.getName())
				.append(", albums=").append(artistDTO.getAlbums()).append(", contributedTracks=")
				.append(artistDTO.getContributedTracks()).append("]");

		assertThat(artistDTO.toString()).hasToString(builder.toString());
	}
}
