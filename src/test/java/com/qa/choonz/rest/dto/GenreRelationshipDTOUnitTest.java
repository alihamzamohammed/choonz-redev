package com.qa.choonz.rest.dto;

import java.util.List;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class GenreRelationshipDTOUnitTest {
	@Test
	void equalsVerify() {
		EqualsVerifier.simple().forClass(GenreRelationshipDTO.class).withPrefabValues(AlbumDTO.class,
				new AlbumDTO("Name", List.of(), List.of(), "Cover", new ArtistRelationshipDTO()), new AlbumDTO())
				.verify();

	}

}
