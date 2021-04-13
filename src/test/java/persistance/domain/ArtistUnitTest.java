package persistance.domain;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;

import nl.jqno.equalsverifier.EqualsVerifier;

public class ArtistUnitTest {
	@Test
	public void equalsVerify() {
	    EqualsVerifier.forClass(Artist.class).withPrefabValues(Album.class, new Album("Name", List.of(), new Artist(), List.of(), "Team 1 rules" ), new Album()).verify();
	}
}
