package com.qa.choonz.rest.dto;

import java.util.List;
import java.util.Objects;

public class TrackDTO {

	private int id;
	private String name;
	private AlbumRelationshipDTO album;
	private Integer duration;
	private String lyrics;
	private ArtistRelationshipDTO artist;
	private List<ArtistRelationshipDTO> contributingArtists;

	public TrackDTO() {
		super();
	}

	public TrackDTO(int id, String name, AlbumRelationshipDTO album, Integer duration, String lyrics,
			ArtistRelationshipDTO artist, List<ArtistRelationshipDTO> contributingArtists) {
		super();
		this.id = id;
		this.name = name;
		this.album = album;
		this.duration = duration;
		this.lyrics = lyrics;
		this.artist = artist;
		this.contributingArtists = contributingArtists;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AlbumRelationshipDTO getAlbum() {
		return album;
	}

	public void setAlbum(AlbumRelationshipDTO album) {
		this.album = album;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

	public ArtistRelationshipDTO getArtist() {
		return this.artist;
	}

	public void setArtist(ArtistRelationshipDTO artist) {
		this.artist = artist;
	}

	public List<ArtistRelationshipDTO> getContributingArtists() {
		return this.contributingArtists;
	}

	public void setContributingArtists(List<ArtistRelationshipDTO> contributingArtists) {
		this.contributingArtists = contributingArtists;
	}

	@Override
	public final int hashCode() {
		return Objects.hash(album, duration, id, lyrics, name, artist, contributingArtists);
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TrackDTO))
			return false;
		TrackDTO other = (TrackDTO) obj;
		return Objects.equals(album, other.album) && Objects.equals(duration, other.duration) && id == other.id
				&& Objects.equals(lyrics, other.lyrics) && Objects.equals(name, other.name)
				&& Objects.equals(artist, other.artist)
				&& Objects.equals(contributingArtists, other.contributingArtists);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TrackDTO [id=").append(id).append(", name=").append(name).append(", album=").append(album)
				.append(", duration=").append(duration).append(", lyrics=").append(", artist=").append(artist)
				.append(lyrics).append(", contributingArtists=").append(contributingArtists).append("]");
		return builder.toString();
	}

}
