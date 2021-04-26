package com.qa.choonz.rest.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class AlbumDTOUnitTest {

	@Test
	void equalsVerify() {
		EqualsVerifier.simple().forClass(AlbumDTO.class)
				.withPrefabValues(ArtistDTO.class, new ArtistDTO(1, "Name", List.of(), List.of()), new ArtistDTO())
				.withPrefabValues(PlaylistDTO.class, new PlaylistDTO("Name", "Description", "Artwork", List.of()),
						new PlaylistDTO())
				.withPrefabValues(AlbumDTO.class,
						new AlbumDTO("Name", List.of(), List.of(), "Cover", new ArtistRelationshipDTO()),
						new AlbumDTO())
				.verify();
	}

	@Test
	void toStringTest() {

		StringBuilder builder = new StringBuilder();
		AlbumDTO albumDTO = new AlbumDTO(1, "Name", List.of(), List.of(), "Cover", new ArtistRelationshipDTO());

		builder.append("AlbumDTO [id=").append(albumDTO.getId()).append(", name=").append(albumDTO.getName())
				.append(", tracks=").append(albumDTO.getTracks()).append(", artist=").append(albumDTO.getArtist())
				.append(", genre=").append(albumDTO.getGenre()).append(", cover=").append(albumDTO.getCover())
				.append("]");

		assertThat(albumDTO.toString()).hasToString(builder.toString());

	}
}
