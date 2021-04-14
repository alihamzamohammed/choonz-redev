package com.qa.choonz.persistance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;

import nl.jqno.equalsverifier.EqualsVerifier;

public class GenreUnitTest {
	
	@Test
	public void equalsVerify() {
	    EqualsVerifier.forClass(Genre.class).withPrefabValues(Album.class, new Album("Name", List.of(), new Artist(), List.of(), "Team 1 rules" ), new Album()).verify();

	}
	
	@Test
	public void toStringTest() {
		StringBuilder builder = new StringBuilder();
		Genre genre = new Genre(1,"Name", "Team 1 rules");
		
		builder.append("Genre [id=").append(genre.getId()).append(", name=").append(genre.getName()).append(", description=")
        .append(genre.getDescription()).append(", albums=").append(genre.getAlbums()).append("]");

		
		assertThat(genre.toString()).hasToString(builder.toString());
	}
}
