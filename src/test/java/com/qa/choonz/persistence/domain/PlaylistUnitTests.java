package com.qa.choonz.persistence.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.persistence.domain.Track;

import nl.jqno.equalsverifier.EqualsVerifier;

public class PlaylistUnitTests {

	@Test
	public void equalsVerify() {
		
		EqualsVerifier.forClass(Track.class).withPrefabValues(Album.class, new Album("Name", List.of(), new Artist(), List.of(), "Team 1 rules" ), new Album())
	    .withPrefabValues(Playlist.class, new Playlist("Name", "Description", "Artwork", List.of()),
				new Playlist())
	    .withPrefabValues(Artist.class, new Artist("Name", List.of(), List.of()), new Artist()).verify();
		
	}
	@Test
	public void toStringTest() {
		StringBuilder builder = new StringBuilder();
		Playlist playlist = new Playlist(1,"Name", "Description", "Artwork",List.of());
		
	     builder.append("Playlist [id=").append(playlist.getId()).append(", name=").append(playlist.getName()).append(", description=")
	             .append(playlist.getDescription()).append(", artwork=").append(playlist.getArtwork()).append(", tracks=").append(playlist.getTracks())
	              .append("]");
		
	     assertThat(playlist.toString()).hasToString(builder.toString());
		
	}
}
