package persistance.domain;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Track;

import nl.jqno.equalsverifier.EqualsVerifier;

public class TrackUnitTest {

	@Test
	public void equalsVerify() {
	    EqualsVerifier.forClass(Track.class).withPrefabValues(Album.class, new Album("Name", List.of(), new Artist(), List.of(), "Team 1 rules" ), new Album()).verify();
	
	}
}
