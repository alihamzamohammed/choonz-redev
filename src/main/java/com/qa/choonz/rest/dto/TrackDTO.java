package com.qa.choonz.rest.dto;

import java.util.List;
import java.util.Objects;

import com.qa.choonz.persistence.domain.PlaylistTracks;

public class TrackDTO {

	private int id;
	private String name;
	private AlbumRelationshipDTO album;
	private Integer duration;
	private String lyrics;
	private ArtistRelationshipDTO artist;

	public TrackDTO() {
		super();
	}

	public TrackDTO(int id, String name, AlbumRelationshipDTO album, Integer duration, String lyrics,
			ArtistRelationshipDTO artist) {
		super();
		this.id = id;
		this.name = name;
		this.album = album;
		this.duration = duration;
		this.lyrics = lyrics;
		this.artist = artist;

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

	@Override
	public int hashCode() {
		return Objects.hash(album, duration, id, lyrics, name, artist);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TrackDTO))
			return false;
		TrackDTO other = (TrackDTO) obj;
		return Objects.equals(album, other.album) && duration == other.duration && id == other.id
				&& Objects.equals(lyrics, other.lyrics) && Objects.equals(name, other.name)
				&& Objects.equals(artist, other.artist);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TrackDTO [id=").append(id).append(", name=").append(name).append(", album=").append(album)
				.append(", duration=").append(duration).append(", lyrics=").append(", artist=").append(artist)
				.append(lyrics).append("]");
		return builder.toString();
	}

}
