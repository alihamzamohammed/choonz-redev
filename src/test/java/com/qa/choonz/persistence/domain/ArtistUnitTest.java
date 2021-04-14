package com.qa.choonz.persistence.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Playlist;

import nl.jqno.equalsverifier.EqualsVerifier;

public class ArtistUnitTest {
	@Test
	public void equalsVerify() {
		EqualsVerifier.forClass(Album.class)
				.withPrefabValues(Album.class, new Album("Name", List.of(), new Artist(), List.of(), "Team 1 rules"),
						new Album())
				.withPrefabValues(Playlist.class, new Playlist("Name", "Description", "Artwork", List.of()),
						new Playlist())
				.withPrefabValues(Artist.class, new Artist("Name", List.of(), List.of()), new Artist()).verify();
	}

	@Test
	public void toStringTest() {
		StringBuilder builder = new StringBuilder();
		Artist artist = new Artist(1, "Name", List.of(), List.of());

		builder.append("Artist [id=").append(artist.getId()).append(", name=").append(artist.getName())
				.append(", albums=").append(artist.getAlbums()).append(", contributedTracks=")
				.append(artist.getContributedTracks()).append("]");

		assertThat(artist.toString()).hasToString(builder.toString());
	}
}
