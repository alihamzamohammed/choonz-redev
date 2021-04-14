package rest.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.dto.ArtistRelationshipDTO;
import com.qa.choonz.rest.dto.GenreDTO;

import nl.jqno.equalsverifier.EqualsVerifier;

public class GenreDTOUnitTest {
	
	@Test
	public void equalsVerify() {
	    EqualsVerifier.simple().forClass(GenreDTO.class).withPrefabValues(AlbumDTO.class, new AlbumDTO("Name", List.of(), List.of(), "Cover", new ArtistRelationshipDTO() ), new AlbumDTO()).verify();

	}
	
	@Test
	public void toStringTest() {
		StringBuilder builder = new StringBuilder();
		GenreDTO genreDTO = new GenreDTO(1,"Name", "Team 1 rules", List.of());
		
		 builder.append("GenreDTO [id=").append(genreDTO.getId()).append(", name=").append(genreDTO.getName()).append(", description=")
         .append(genreDTO.getDescription()).append(", albums=").append(genreDTO.getAlbums()).append("]");
		
		assertThat(genreDTO.toString()).hasToString(builder.toString());
	} 
}


