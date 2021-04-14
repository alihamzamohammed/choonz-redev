package com.qa.choonz.rest.dto;

import java.util.List;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class ArtistRelationshipDTOUnitTest {
	@Test
	void equalsVerify() {
		EqualsVerifier.simple().forClass(ArtistRelationshipDTO.class).withPrefabValues(AlbumDTO.class,
				new AlbumDTO("Name", List.of(), List.of(), "Cover", new ArtistRelationshipDTO()), new AlbumDTO())
				.withPrefabValues(PlaylistDTO.class, new PlaylistDTO("Name", "Description", "Artwork", List.of()),
						new PlaylistDTO())
				.verify();
	}

}
