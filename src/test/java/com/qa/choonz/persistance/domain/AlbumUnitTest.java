package com.qa.choonz.persistance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Playlist;

import nl.jqno.equalsverifier.EqualsVerifier;

class AlbumUnitTest {

	@Test
	void equalsVerify() {
		EqualsVerifier.forClass(Artist.class)
				.withPrefabValues(Artist.class, new Artist(1, "Name", List.of(), List.of()), new Artist())
				.withPrefabValues(Playlist.class, new Playlist("Name", "Description", "Artwork", List.of()),
						new Playlist())
				.withPrefabValues(Album.class, new Album("Name", List.of(), new Artist(), List.of(), "Team 1 rules"),
						new Album())
				.verify();
	}

	@Test
	void toStringTest() {

		StringBuilder builder = new StringBuilder();
		Album album = new Album(1, "Name", List.of(), new Artist(), List.of(), "Team 1 rules");

		builder.append("Album [id=").append(album.getId()).append(", name=").append(album.getName()).append(", tracks=")
				.append(album.getTracks()).append(", artist=").append(album.getArtist()).append(", genre=")
				.append(album.getGenre()).append(", cover=").append(album.getCover()).append("]");

		assertThat(album.toString()).hasToString(builder.toString());

	}
}
