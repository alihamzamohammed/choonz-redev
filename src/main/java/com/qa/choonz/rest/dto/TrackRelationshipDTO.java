package com.qa.choonz.rest.dto;

import java.util.Objects;

public class TrackRelationshipDTO {

	private int id;
	private String name;
	private int duration;
	private String lyrics;

	public TrackRelationshipDTO() {
		super();
	}

	public TrackRelationshipDTO(int id, String name, int duration, String lyrics) {
		super();
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.lyrics = lyrics;
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

	@Override
	public int hashCode() {
		return Objects.hash(duration, id, lyrics, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TrackRelationshipDTO))
			return false;
		TrackRelationshipDTO other = (TrackRelationshipDTO) obj;
		return duration == other.duration && id == other.id && Objects.equals(lyrics, other.lyrics)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TrackRelationshipDTO [id=").append(id).append(", name=").append(name).append(", duration=")
				.append(duration).append(", lyrics=").append(lyrics).append("]");
		return builder.toString();
	}

}
