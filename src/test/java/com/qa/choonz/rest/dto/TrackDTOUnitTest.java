package com.qa.choonz.rest.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class TrackDTOUnitTest {

	@Test
	void equalsVerify() {
		EqualsVerifier.simple().forClass(TrackDTO.class).withPrefabValues(AlbumDTO.class,
				new AlbumDTO("Name", List.of(), List.of(), "Cover", new ArtistRelationshipDTO()), new AlbumDTO())
				.withPrefabValues(PlaylistDTO.class, new PlaylistDTO("Name", "Description", "Artwork", List.of()),
						new PlaylistDTO())
				.withPrefabValues(ArtistDTO.class, new ArtistDTO("Name", List.of(), List.of()), new ArtistDTO())
				.verify();

	}

	@Test
	void toStringTest() {
		StringBuilder builder = new StringBuilder();
		TrackDTO trackDTO = new TrackDTO(1, "Name", new AlbumRelationshipDTO(), 1, "Lyrics",
				new ArtistRelationshipDTO(), List.of());

		builder.append("TrackDTO [id=").append(trackDTO.getId()).append(", name=").append(trackDTO.getName())
				.append(", album=").append(trackDTO.getAlbum()).append(", duration=").append(trackDTO.getDuration())
				.append(", lyrics=").append(", artist=").append(trackDTO.getArtist()).append(trackDTO.getLyrics())
				.append(", contributingArtists=").append(trackDTO.getContributingArtists()).append("]");

		assertThat(trackDTO.toString()).hasToString(builder.toString());

	}
}
