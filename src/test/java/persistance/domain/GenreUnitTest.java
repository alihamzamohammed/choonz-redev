package persistance.domain;

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
}
