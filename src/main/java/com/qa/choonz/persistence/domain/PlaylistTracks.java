package com.qa.choonz.persistence.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class PlaylistTracks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@ManyToOne(targetEntity = Playlist.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_playlist_Id")
	private Playlist playlist;

	@NotNull
	@ManyToOne(targetEntity = Track.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_track_id")
	private Track track;

	public PlaylistTracks() {
		super();
	}

	public PlaylistTracks(int id, @NotNull Playlist playlist, @NotNull Track track) {
		super();
		this.id = id;
		this.playlist = playlist;
		this.track = track;
	}

	public PlaylistTracks(@NotNull Playlist playlist, @NotNull Track track) {
		super();
		this.playlist = playlist;
		this.track = track;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlaylistTracks [id=").append(id).append(", playlist=").append(playlist).append(", track=")
				.append(track).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, playlist, track);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PlaylistTracks))
			return false;
		PlaylistTracks other = (PlaylistTracks) obj;
		return id == other.id && Objects.equals(playlist, other.playlist) && Objects.equals(track, other.track);
	}

}
