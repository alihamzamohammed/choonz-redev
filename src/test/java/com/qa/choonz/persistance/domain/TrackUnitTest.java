package com.qa.choonz.persistance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.Track;

import nl.jqno.equalsverifier.EqualsVerifier;

class TrackUnitTest {

	@Test
	void equalsVerify() {
		EqualsVerifier.forClass(Track.class)
				.withPrefabValues(Album.class, new Album("Name", List.of(), new Artist(), List.of(), "Team 1 rules"),
						new Album())
				.withPrefabValues(Playlist.class, new Playlist("Name", "Description", "Artwork", List.of()),
						new Playlist())
				.withPrefabValues(Artist.class, new Artist("Name", List.of(), List.of()), new Artist()).verify();

	}

	@Test
	void toStringTest() {
		StringBuilder builder = new StringBuilder();
		Track track = new Track(1, "name", new Album(), 1, "lyrics", List.of());

		builder.append("Track [id=").append(track.getId()).append(", name=").append(track.getName()).append(", album=")
				.append(track.getAlbum()).append(", duration=").append(track.getDuration()).append(", lyrics=")
				.append(track.getLyrics()).append(", artist=").append(track.getArtist())
				.append(", contributingArtists=").append(track.getContributingArtists()).append("]");

		assertThat(track.toString()).hasToString(builder.toString());

	}
}
